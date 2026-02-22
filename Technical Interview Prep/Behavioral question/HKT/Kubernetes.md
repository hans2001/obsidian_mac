**Pod YAML Manifest:**  
A pod manifest specifies metadata (like name and labels) and a spec that contains an array of containers. Each container has properties such as the image to use, ports to expose, environment variables, and volumes.
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-app-pod
  labels:
    app: my-app
spec:
  containers:
    - name: main-container
      image: my-app:latest
      ports:
        - containerPort: 3000
    - name: sidecar-logger
      image: log-collector:latest
      # This container might collect logs from the main container.
```
apiVersion & kind: resource type
metadata: provide a name and labels for organizational purpose
**spec:** Defines the pod’s desired state, including an array of containers.
- The first container (`main-container`) is your primary application.
- The second container (`sidecar-logger`) is an example of a sidecar, which can help with tasks like logging.

**Using kubectl:**  
Once your YAML file (e.g., `pod.yaml`) is defined, you can create the pod in your Kubernetes cluster by running:
```bash
kubectl apply -f pod.yaml
```
This command tells Kubernetes to create (or update) a pod as described in the YAML file.

You can verify the pod’s status with:
```bash 
kubectl get pods
```

pod use case: 
**Primary Application Container:**  
Typically, a pod hosts one main container that runs your core application (e.g., a web server, API service, or worker process).

**Sidecar Containers:**  
These are helper containers that run alongside the primary container. Examples include:
- **Logging/Monitoring Agents:** For collecting logs or metrics.
- **Proxy/Adapters:** Like a service mesh proxy (e.g., Envoy) to manage network traffic.
- **Configuration Updaters:** For dynamically updating configuration or certificates.

_Example:_  
Imagine an **e-commerce** platform with separate services for user management, product catalog, and order processing:
- **User Service Pod:** Handles authentication and profile management.
- **Product Service Pod:** Manages product listings, search, and inventory.
- **Order Service Pod:** Processes customer orders and integrates with payment gateways.
Each of these services is isolated in its own pod (or set of pods) so that if one service experiences high load or failure, it doesn’t impact the others.

Autoscaling and Resource Management
Automatically scales the number of pod replicas based on metrics (e.g., CPU, memory, or custom metrics).
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: my-app-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-app-deployment
  minReplicas: 2
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 50
```