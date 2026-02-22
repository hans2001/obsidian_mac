allows you to define and **manage multi-container** Docker applications using a **single YAML file** (typically named `docker-compose.yml`). It simplifies orchestration for development and testing

**Key Components in `docker-compose.yml`:**
- **services:** Define each container (e.g., web app, database).
- **build:** Instructions to build an image from a Dockerfile.
- **image:** Specify an image directly if it’s pre-built.
- **ports:** Map container ports to host ports.
- **volumes:** Attach persistent storage or share files between host and container.
- **depends_on:** Define dependency order among services ( controls container startup order )
```yaml
version: '3.8'

services:
  web:
    build: .
    ports:
      - "3000:3000"
    volumes:
      - .:/usr/src/app
    depends_on:
      - db

  db:
    image: postgres:13
    environment:
      POSTGRES_USER: myuser
      POSTGRES_PASSWORD: mypassword
      POSTGRES_DB: mydb
    ports:
      - "5432:5432"
    volumes:
      - db-data:/var/lib/postgresql/data

volumes:
  db-data:
```

**Usage Commands:**
- `docker-compose up` – Start all defined services.
- `docker-compose down` – Stop and remove containers, networks, and volumes created by up.
- `docker-compose build` – Build or rebuild services.

## Advanced
**Multi-Environment Configuration:**  
Use multiple Compose files (e.g., `docker-compose.override.yml`, `docker-compose.prod.yml`) to tailor your setup for different environments. You can merge files with the `-f` flag.
```bash
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

**Dependency Management & Scaling:**  
Utilize the `depends_on` property for controlling service startup order, and the `scale` option (or `docker-compose up --scale`) to run multiple instances of a service, which is particularly useful during **load testing**.

**Custom Networks & External Connectivity:**  
Create custom networks to isolate services or enable communication with external systems. For example, you can define multiple networks and attach specific services to each.
```yaml
networks:
  front:
  back:

services:
  web:
    image: my-web-app
    networks:
      - front
  db:
    image: postgres:13
    networks:
      - back
```

## Librechat 
## Docker.compose.yaml
```yaml
services:
	api:
	container_name: LibreChat
	ports:
		- "${PORT}:${PORT}"
	depends_on:
		- mongodb
		- rag_api
	image: ghcr.io/danny-avila/librechat-dev:latest
	restart: always
	user: "${UID}:${GID}"
	extra_hosts:
		- "host.docker.internal:host-gateway"
	environment:
		- HOST=0.0.0.0
		- MONGO_URI=mongodb://mongodb:27017/LibreChat
		- MEILI_HOST=http://meilisearch:7700
		- RAG_PORT=${RAG_PORT:-8000}
		- RAG_API_URL=http://rag_api:${RAG_PORT:-8000}
	volumes:
		- type: bind
		source: ./.env
		target: /app/.env
		- ./images:/app/client/public/images
		- ./uploads:/app/uploads
		- ./logs:/app/api/logs
	
	mongodb:
		container_name: chat-mongodb
		image: mongo
		restart: always
		user: "${UID}:${GID}"
		volumes:
		   	- ./data-node:/data/db
		command: mongod --noauth
	
	meilisearch:
		container_name: chat-meilisearch
		image: getmeili/meilisearch:v1.12.3
		restart: always
		user: "${UID}:${GID}"
		environment:
			- MEILI_HOST=http://meilisearch:7700
			- MEILI_NO_ANALYTICS=true
			- MEILI_MASTER_KEY=${MEILI_MASTER_KEY}
		volumes:
			- ./meili_data_v1.12:/meili_data
	
	vectordb:
		container_name: vectordb
		image: ankane/pgvector:latest
		environment:
			POSTGRES_DB: mydatabase
			POSTGRES_USER: myuser
			POSTGRES_PASSWORD: mypassword
		restart: always
		volumes:
			- pgdata2:/var/lib/postgresql/data
	
	rag_api:
		container_name: rag_api
		image: ghcr.io/danny-avila/librechat-rag-api-dev-lite:latest
		environment:
			- DB_HOST=vectordb
			- RAG_PORT=${RAG_PORT:-8000}
		restart: always
		depends_on:
			- vectordb
		env_file:
			- .env

volumes:
	pgdata2:
```

## Volume
A way that share data between the host and containers ( one or multiple )
no data lose despite container state 

**Source**:
file or dir in host machine 
**Target:** 
path inside the container whee host file woll be mounted! 

**Example** 
```yaml
volumes:
  - type: bind
    source: ./librechat.yaml
    target: /app/librechat.yaml
```

**Bind mounts:** 
map a file from the host filesystem to a location in the container 
```yml
volumes:
  - ./data-node:/data/db
```
This configuration means that changes in `./data-node` on the host directly reflect in the container's `/data/db` directory.

**Named mounts:** 
volumes managed by docker, and are referenced by name
```yaml
volumes:
  pgdata2:
```

used in later services as: 
```yml
volumes:
  - pgdata2:/var/lib/postgresql/data
```

## Environment
set env variables inside containers 
```yaml
environment:
  - HOST=0.0.0.0
  - MONGO_URI=mongodb://mongodb:27017/LibreChat
```
change application behavior without rebuilding the image! 

## Deploy-compose.yaml
the file is a separate docker compose configuration intended for deployment! 
should disable all exposing port except the client port: 80 / 443 (https) (3080: backend port )
```yml
services:
	api:
		image: ghcr.io/danny-avila/librechat-dev-api:latest
		container_name: LibreChat-API
		ports:
		- 3080:3080
		depends_on:
		- mongodb
		- rag_api
		restart: always
		extra_hosts:
		- "host.docker.internal:host-gateway"
		env_file:
		- .env
		environment:
		- HOST=0.0.0.0
		- NODE_ENV=production
		- MONGO_URI=mongodb://mongodb:27017/LibreChat
		- MEILI_HOST=http://meilisearch:7700
		- RAG_PORT=${RAG_PORT:-8000}
		- RAG_API_URL=http://rag_api:${RAG_PORT:-8000}
		volumes:
		- type: bind
		source: ./librechat.yaml
		target: /app/librechat.yaml
		- ./images:/app/client/public/images
		- ./uploads:/app/uploads
		- ./logs:/app/api/logs
	
	client:
		image: nginx:1.27.0-alpine
		container_name: LibreChat-NGINX
		ports:
		- 80:80
		- 443:443
		depends_on:
		- api
		restart: always
		volumes:
		- ./client/nginx.conf:/etc/nginx/conf.d/default.conf
	
	mongodb:
		container_name: chat-mongodb
		image: mongo
		restart: always
		volumes:
		- ./data-node:/data/db
		command: mongod --noauth
		meilisearch:
		container_name: chat-meilisearch
		image: getmeili/meilisearch:v1.12.3
		restart: always
		env_file:
		- .env
		environment:
		- MEILI_HOST=http://meilisearch:7700
		- MEILI_NO_ANALYTICS=true
		volumes:
		- ./meili_data_v1.12:/meili_data
	
	vectordb:
		image: ankane/pgvector:latest
		environment:
			POSTGRES_DB: mydatabase
			POSTGRES_USER: myuser
			POSTGRES_PASSWORD: mypassword
		restart: always
		volumes:
		- pgdata2:/var/lib/postgresql/data
	
	rag_api:
		image: ghcr.io/danny-avila/librechat-rag-api-dev-lite:latest
		environment:
		- DB_HOST=vectordb
		- RAG_PORT=${RAG_PORT:-8000}
		restart: always
		depends_on:
		- vectordb
		env_file:
		- .env

volumes:
	pgdata2:
```

Diff ( deploy-compose.yml vs docker-compose.yml ): 
- **Deploy File:** Client Service ports 80 (HTTP) and 443 (HTTPS) for web traffic.
- **Client Service:** Explicitly adds an NGINX service (`client`) to serve the frontend.
- Inline variables are loaded from env file
- Set NODE_EBNV as production for the api service
- Hide port for internal services (mongodb, rag_api ) -> referenced by container name 
