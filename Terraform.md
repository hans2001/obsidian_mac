- You want Fargate (no EC2 to babysit)
- You have a VPC + subnets already (or you can add them later)
- Region: us-east-1
```hcl
########################################
# provider + variables
########################################
provider "aws" {
  region = "us-east-1"
}

variable "project_name" {
  default = "myapp"
}

########################################
# ECR repository
########################################
resource "aws_ecr_repository" "app_repo" {
  name = "${var.project_name}-repo"

  image_scanning_configuration {
    scan_on_push = true
  }

  lifecycle_policy {
    policy = <<EOF
{
  "rules": [
    {
      "rulePriority": 1,
      "description": "Keep last 10 images",
      "selection": {
        "tagStatus": "any",
        "countType": "imageCountMoreThan",
        "countNumber": 10
      },
      "action": { "type": "expire" }
    }
  ]
}
EOF
  }
}

########################################
# Networking (VERY SIMPLIFIED)
# In reality you'd use your own VPC + subnets.
########################################
data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

########################################
# ECS cluster
########################################
resource "aws_ecs_cluster" "app_cluster" {
  name = "${var.project_name}-cluster"
}

########################################
# IAM role for ECS task execution (pull from ECR, logs, etc)
########################################
resource "aws_iam_role" "ecs_task_execution_role" {
  name = "${var.project_name}-task-execution-role"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_policy" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

########################################
# ECS task definition
########################################
resource "aws_ecs_task_definition" "app_task" {
  family                   = "${var.project_name}-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"

  execution_role_arn = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name      = "app"
      image     = "${aws_ecr_repository.app_repo.repository_url}:latest"
      essential = true
      portMappings = [
        {
          containerPort = 3000
          hostPort      = 3000
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/ecs/${var.project_name}"
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}

########################################
# CloudWatch log group (for ECS logs)
########################################
resource "aws_cloudwatch_log_group" "app_logs" {
  name              = "/ecs/${var.project_name}"
  retention_in_days = 7
}

########################################
# Security group for ECS service
########################################
resource "aws_security_group" "app_sg" {
  name        = "${var.project_name}-sg"
  description = "Allow HTTP"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

########################################
# ECS Service + Fargate
########################################
resource "aws_ecs_service" "app_service" {
  name            = "${var.project_name}-service"
  cluster         = aws_ecs_cluster.app_cluster.id
  task_definition = aws_ecs_task_definition.app_task.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = data.aws_subnets.default.ids
    security_groups = [aws_security_group.app_sg.id]
    assign_public_ip = true
  }

  depends_on = [aws_cloudwatch_log_group.app_logs]
}

########################################
# Outputs
########################################
output "ecr_repository_url" {
  value = aws_ecr_repository.app_repo.repository_url
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.app_cluster.name
}

output "ecs_service_name" {
  value = aws_ecs_service.app_service.name
}
```

## Github actions
build → push to ECR → update ECS service

You now have:
- ECR_REPO_URL from Terraform (output)
- ecs_cluster_name
- ecs_service_name

You store these as GitHub Secrets:
- AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_REGION
- ECR_REPO_URL
- ECS_CLUSTER_NAME
- ECS_SERVICE_NAME

```yaml
name: Build and Deploy to ECS

on:
  push:
    branches: [ "main" ]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repo
        uses: actions/checkout@v4

      - name: Configure AWS credentials
        uses: aws-actions/configure-aws-credentials@v4
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: ${{ secrets.AWS_REGION }}

      - name: Log in to Amazon ECR
        id: ecr-login
        uses: aws-actions/amazon-ecr-login@v2

      - name: Build, tag, and push image to ECR
        env:
          ECR_REPO: ${{ secrets.ECR_REPO_URL }}
        run: |
          IMAGE_TAG=${GITHUB_SHA::7}
          docker build -t $ECR_REPO:$IMAGE_TAG .
          docker push $ECR_REPO:$IMAGE_TAG
          echo "IMAGE_TAG=$IMAGE_TAG" >> $GITHUB_ENV

      - name: Update ECS service to use new image
        env:
          ECR_REPO: ${{ secrets.ECR_REPO_URL }}
          ECS_CLUSTER: ${{ secrets.ECS_CLUSTER_NAME }}
          ECS_SERVICE: ${{ secrets.ECS_SERVICE_NAME }}
        run: |
          NEW_IMAGE="$ECR_REPO:${IMAGE_TAG}"
          echo "New image: $NEW_IMAGE"

          # Get current task definition JSON
          TASK_DEF_ARN=$(aws ecs describe-services \
            --cluster $ECS_CLUSTER \
            --services $ECS_SERVICE \
            --query 'services[0].taskDefinition' \
            --output text)

          aws ecs describe-task-definition \
            --task-definition $TASK_DEF_ARN \
            --query 'taskDefinition' \
            > task-def.json

          # Create new task definition with updated image
          NEW_TASK_DEF=$(jq \
            --arg IMAGE "$NEW_IMAGE" \
            '.containerDefinitions[0].image = $IMAGE | del(.taskDefinitionArn, .revision, .status, .requiresAttributes, .compatibilities)' \
            task-def.json)

          echo "$NEW_TASK_DEF" > new-task-def.json

          NEW_TASK_DEF_ARN=$(aws ecs register-task-definition \
            --cli-input-json file://new-task-def.json \
            --query 'taskDefinition.taskDefinitionArn' \
            --output text)

          # Update ECS service to use the new task definition
          aws ecs update-service \
            --cluster $ECS_CLUSTER \
            --service $ECS_SERVICE \
            --task-definition $NEW_TASK_DEF_ARN
```
1. Build Docker image
2. Tag it with short commit SHA
3. Push to ECR
4. Get current ECS task definition
5. Clone it, change container image, register a new revision
6. Point the ECS service to the new task definition → rolling deployment

# Real-world structure:
### **Terraform side**
You usually have **separate ECS services and ECR repos** per environment, or at least separate services:
- Cluster: maybe shared or separate (different service and environment)
- Services:
    - myapp-dev-service
    - myapp-staging-service
    - myapp-prod-service
- Optionally:
    - myapp-dev-repo, myapp-staging-repo, myapp-prod-repo
Often done via Terraform workspaces, modules, or var.environment.
```hcl
variable "env" {}

resource "aws_ecr_repository" "app_repo" {
  name = "myapp-${var.env}-repo"
}

resource "aws_ecs_service" "app_service" {
  name = "myapp-${var.env}-service"
  # ...
}
```

### **GitHub Actions side**
You have separate jobs or separate workflows (trigger for diff branches or tags)(send to different environment in terraform):
- On push to develop → deploy to dev ECS service
- On push to main but not tagged → deploy to staging
- On GitHub Release → deploy to prod
```yaml
on:
  push:
    branches: [ "develop", "main" ]
  release:
    types: [published]

jobs:
  deploy-dev:
    if: github.ref == 'refs/heads/develop'
    # uses dev ECR + dev ECS service

  deploy-staging:
    if: github.ref == 'refs/heads/main' && github.event_name == 'push'
    # uses staging ECR / ECS

  deploy-prod:
    if: github.event_name == 'release' && github.event.release.prerelease == false
    # uses prod ECR / ECS
```