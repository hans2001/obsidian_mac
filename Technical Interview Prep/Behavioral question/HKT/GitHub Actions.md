## Continuous integration
```yaml
name: CI Pipeline

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v3

      - name: Set up Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '16'

      - name: Install Dependencies
        run: npm install

      - name: Run Tests
        run: npm test
```
the workflow triggers on code pushes and pull requests to the main branch

## Continuous deployment
```yaml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  build-test-deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v3

      - name: Build Application
        run: npm run build

      - name: Run Tests
        run: npm test

      - name: Deploy Application
        if: success()  # Only deploy if the previous steps succeeded
        run: |
          echo "Deploying to production..."
          ./deploy.sh
```
This workflow builds the application, runs tests, and if everything passes, it proceeds to deploy the application using a custom deploy script.
## Custom workflows
```yaml
name: Custom Multi-Job Workflow

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v3
      - name: Run Linter
        run: npm run lint

  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v3
      - name: Run Tests
        run: npm test

  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v3
      - name: Build Application
        run: npm run build

  deploy:
    needs: [lint, test, build]
    runs-on: ubuntu-latest
    steps:
      - name: Deploy Application
        run: ./deploy.sh
```
This custom workflow runs three jobs in parallel: linting, testing, and building. The deploy job depends on all previous jobs and will only run if each one succeeds, ensuring that only quality code is deployed.

## Network testing Pipeline
**Summary**
- Brings up the complete Docker Compose stack (using your base file plus the override).
- Checks the external IP of the VPN container and then verifies (via a geo-IP lookup) that it belongs to the desired region (Singapore in this example).
- Retrieves the external IP from the API container.
- Confirms that both IPs match.

**.github/workflows/network-integration-test.yaml**
```yaml 
name: CI/CD with VPN Integration Tests

on:
  push:
    branches:
      - main
  workflow_dispatch:

jobs:
  integration-test:
    runs-on: ubuntu-latest
    steps:
      # Checkout the repository
      - name: Checkout repository
        uses: actions/checkout@v4

      # Install Docker Compose (if not already available)
      - name: Install Docker Compose
        run: |
          sudo apt-get update
          sudo apt-get install -y docker-compose jq

      # Prepare environment if needed
      - name: Prepare environment file
        run: cp .env.example .env

      # Bring up the Docker Compose stack (base file + override)
      - name: Start Docker Compose Stack
        run: docker-compose up -d

      # Allow time for services to initialize (especially the VPN connection)
      - name: Wait for services to initialize
        run: sleep 30

      # Step 1: Check the VPN container's external IP
      - name: Get VPN external IP
        id: vpn_ip
        run: |
          # Retrieve external IP from the VPN container using ifconfig.me
          VPN_IP=$(docker exec nordvpn curl -s ifconfig.me)
          echo "VPN External IP: $VPN_IP"
          echo "::set-output name=ip::$VPN_IP"

      # Step 2: Verify the geo-location of the VPN IP is Singapore
      - name: Verify VPN IP geo-location
        env:
          VPN_IP: ${{ steps.vpn_ip.outputs.ip }}
          IPINFO_TOKEN: ${{ secrets.IPINFO_TOKEN }}  # If your ipinfo.io account requires a token
        run: |
          # Query ipinfo.io for geo data; if no token is needed, you can omit ?token=...
          GEO_JSON=$(curl -s "https://ipinfo.io/${VPN_IP}?token=${IPINFO_TOKEN}")
          echo "Geo Data: $GEO_JSON"
          REGION=$(echo $GEO_JSON | jq -r '.region')
          echo "VPN External IP Region: $REGION"
          if [ "$REGION" != "Singapore" ]; then
            echo "Error: VPN external IP is not in Singapore!"
            exit 1
          fi
          echo "Success: VPN external IP region is Singapore."

      # Step 3: Get the external IP from the API container
      - name: Get API container external IP
        id: api_ip
        run: |
          SERVICE_IP=$(docker exec LibreChat curl -s ifconfig.me)
          echo "API Container External IP: $SERVICE_IP"
          echo "::set-output name=ip::$SERVICE_IP"

      # Step 4: Check that the API container's external IP matches the VPN container's external IP
      - name: Check matching external IPs
        run: |
          if [ "${{ steps.vpn_ip.outputs.ip }}" != "${{ steps.api_ip.outputs.ip }}" ]; then
            echo "Error: VPN container IP and API container IP do not match!"
            exit 1
          fi
          echo "Success: Both containers share the same external IP."

      # Tear down the Docker Compose stack (always run)
      - name: Tear down Docker Compose Stack
        if: always()
        run: docker-compose down
```

**Network namespace sharing**: when api container's network mode set to the vpn ones, they share the same network stack such as network interfaces, IP addresses and routing tables etc!

## Geo-IP lookup services
Here we use curl https://ipinfo.io/72.79.123.10 (replace suffix with container ip) to check ip location for the VPN container, and match the city and region field (should be singapore etc)
```
{
  "ip": "72.79.123.10",
  "hostname": "pool-72-79-123-10.nwrknj.east.verizon.net",
  "city": "Newark",
  "region": "New Jersey",
  "country": "US",
  "loc": "40.7357,-74.1724",
  "org": "AS701 Verizon Business",
  "postal": "07102",
  "timezone": "America/New_York",
  "readme": "https://ipinfo.io/missingauth"
}
```

**docker-compose.override.yaml**
```yml
# docker-compose.override.yaml
# Do not edit the base file directly. This override file adds VPN routing.
services:
  vpn:
    container_name: nordvpn
    image: ghcr.io/bubuntux/nordvpn:latest
    cap_add:
      - NET_ADMIN
      - NET_RAW
    devices:
      - /dev/net/tun
    environment:
      - USER=your_nordvpn_username
      - PASS=your_nordvpn_password
      - CONNECT=Singapore            # Desired region; adjust as needed
      - TECHNOLOGY=NordLynx          # Or use OpenVPN if preferred
      - NETWORK=192.168.1.0/24       # Define your local network (adjust to match your host)
    ports:
      - "8080:80"                  # (Optional) expose a port if needed for local access via VPN
    restart: unless-stopped

  # Override the API container to route its traffic through the VPN container
  api:
    network_mode: "service:nordvpn"
```
for routing the necessary services network mode to the new network container 