# Docker
**Containers vs. Virtual Machines:**  
Docker containers share the **host OS kernel** and are lightweight, while VMs include a **full OS**. Containers package applications with all their dependencies, ensuring consistency across environments.

**Images & Dockerfile:**
- **Image:** A snapshot of your app including code, libraries, and runtime.
- **Dockerfile:** A script that contains instructions to build a Docker image (e.g., setting base image, copying files, running commands).

```dockerfile
# Use an official Node.js runtime as a parent image
FROM node:16-alpine

# Set working directory
WORKDIR /usr/src/app

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Bundle app source
COPY . .

# Expose port and define the command to run the app
EXPOSE 3000
CMD [ "npm", "start" ]
```
create container form images with **docker run**

**Common Commands:**
- `docker build -t my-app .` – Build an image from a Dockerfile.
- `docker run -d -p 3000:3000 my-app` – Run a container in detached mode mapping port 3000.
- `docker ps` – List running containers.
- `docker stop <container_id>` – Stop a running container.

**docker image** vs **docker container** 
image: read-only template that defines everything needed to run the application 
container: runtime instance of an image, an isolated process on system that is defined by the image! 

**Key Differences:**
- **Immutability vs. Runtime State:**
    - **Image:** Immutable and static.
    - **Container:** Mutable runtime state that can change as the application runs.
- **Template vs. Instance:**
    - **Image:** Acts as a blueprint for building containers.
    - **Container:** The actual running instance created from that blueprint.
- **Lifecycle:**
    - **Image:** Built once and stored in a registry (like Docker Hub).
    - **Container:** Launched from an image, may have its own lifecycle (start, stop, restart, remove).
## Advanced 
```dockerfile
# Stage 1: Build
FROM node:16-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Production
FROM node:16-alpine
WORKDIR /app
COPY --from=build /app/dist ./dist
COPY package*.json ./
RUN npm install --only=production
EXPOSE 3000
CMD [ "node", "dist/index.js" ]
```
use docker secrets to manage sensitive data
set cpu and memory limits with (--cpus, --memory) , use health checks -> container reliability

# Docker Networks
### **Bridge network**
when docker is installed, a default bridge network is created! containers within this network can communicate using their IP address! 
**Usage:**  
Ideal for single-host container communication.
```bash
docker network ls
docker run -d --name container1 alpine sleep 300
docker run -d --name container2 --network bridge alpine sleep 300
```
### **Host Network**
Containers share the host’s networking namespace. There’s no network isolation between the container and the host.
**Usage:**  
Useful when you need maximum network performance or to avoid the overhead of NAT.
```bash
docker run --network host nginx
```
Here, the container’s ports are not isolated from the host’s ports.
### **Overlay Network**
Enables communication between containers across different Docker hosts. Overlay networks are typically used in Docker Swarm or Kubernetes environments.
**Usage:**  
Ideal for multi-host, distributed applications.
```bash
docker network create -d overlay my_overlay_net
docker service create --name my_service --network my_overlay_net my_image
```
### **Macvlan Network:**
Assigns a MAC address to each container, making them appear as physical devices on your network.
**Usage:**  
Useful when you need containers to be directly accessible on the physical network (e.g., for legacy applications).
```bash
docker network create -d overlay my_overlay_net
docker service create --name my_service --network my_overlay_net my_image
```

```
## Docker file
multistage build: build for each packages , then copies all their build artifacts to the final image( dev-api ) ,and expose the backend server with a port

- **Alpine Linux:**  
Alpine is a very lightweight Linux distribution known for its small footprint.
- **`node:20-alpine`:**  
This is a Node.js runtime image built on Alpine Linux. It provides a minimal environment with Node.js installed, which helps keep the overall image size small compared to other base images.
## COPY
- **What It Does:**  
    The `COPY` command takes files or directories from your build context (the source on your machine or repository) and copies them into the container’s filesystem at a specified destination.
- **Example in This Dockerfile:**
```dockerfile
COPY package*.json ./
```    
- **Source:** All files matching `package*.json` in the build context.
- **Destination:** The current directory in the container (set by `WORKDIR /app`).


if **COPY** were not used, the container will not have the required source code or configurations  -> errors during dependency installation! 

**Copying Between Stages**
```dockerfile
COPY --from=data-provider-build /app/packages/data-provider/dist /app/packages/data-provider/dist
```
copy files from a previous build stage (`data-provider-build`) into the current stage. This ensures that compiled assets from one stage are available in later stages.

## RUN vs CMD 
**Relation to package.json Scripts:**
- The `RUN` command can execute any shell command and is often used to run build scripts defined in `package.json`(like `npm run build`).
- The `CMD` command is not limited to package.json scripts. It’s the final command that keeps the container running (the entry point of your application).

- **`librechat-dev-api` (using Dockerfile.multi with target `api-build`):**
    - This image is built from the multi-stage Dockerfile.
    - It combines all components (backend API plus the built frontend assets).
    - It is optimized for running the API (on port 3080) along with the static frontend files.

- **`librechat-dev` (using Dockerfile with target `node`):**
    - This image is built from a different Dockerfile.
    - It runs a combined setup where both the frontend is built (via `npm run frontend`) and the backend is run (with `npm run backend`) in one container.
    - This image might be tailored for development environments where you want a single container handling both parts, or it might have different configurations (like mounting files, using non-pruned dependencies, etc.).