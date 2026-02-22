## Situation
- Firm needed GenAI chat but compliance prohibited using ChatGPT/Claude due to training data risks
- Built a compliant assistant using secure, locally hosted models and enterprise endpoints
## Task
- Team of 3 built a secure, multi-provider chat platform
- Requirements: compliance, multi-provider support, multi-modal inputs, accurate responses
## Action
Architecture:
- Moved from OpenAI SDK + Next.js prototype → Microservices + OOP
- Stack: React/TypeScript, Node.js, MongoDB, Python/FastAPI RAG with Postgres/pgvector
## My contributions:
1. Multi-Provider Adapter System
- Built generic conversation workflow (token management, context building, history retrieval, I/O parsing, MongoDB persistence)
	- Customized per model:
		- IT's secure LLM endpoint, OpenAI/Anthropic/Google, Ollama
		- BaseClient class (TypeScript): lifecycle methods; providers inherit and override encoding/formatting/extraction
		- Enabled adding new providers without touching core logic

2. improve response Accuracy with Router Chain + RAG
	- Router Chain: LLM classifies tasks → routes to specialized agents (uer prompt -> augment by system instructions to give accurate response )
	- RAG: on the frontend file tagging ( by task )for keyword filtering; backend used pgvector with postgreSql , use metadata to perform pre-filtering, then use KNN with cosine similarity to retrieve top K results (RAG service)
	- Measured precision@k, citation coverage, latency (use chatgpt to rate the 2 response); tuned system prompts and arch to improve accuracy and speed

3. configure docker Network to fulfill Compliance + On-Prem deployment
	- VPN container routing for geo-restrictions; CI/CD validation in GitHub Actions
	- Deployed Llama-3 via Ollama; backend adapters for token counting and usage recording
	- Compared cloud vs on-prem (cost/latency/accuracy); built routing layer based on sensitivity and constraints
## Result
- Dockerized platform; on-prem + installable releases; Windows.bat auto-installer
- Presented to department heads → positive feedback → IT department adopted



# My Three Main Contributions
1) Multi-Provider Integration (the adapter work)

I built a generic conversation flow for all models, then customized per-provider.
The base flow handles:
- Token management and estimation
- Building context (conversation history)
- Fetching previous messages
- Parsing I/O (requests and responses)
- Saving to MongoDB with conversation IDs

Then I added per-model overrides for:
- Our IT team’s secure LLM endpoint
- OpenAI, Anthropic, Google
- Locally hosted models via Ollama

We use a base class pattern—BaseClient in TypeScript—with abstract methods that each provider implements. That made it easy to add new providers without touching core logic.

2) Improving Accuracy with Router Chain and RAG

For compliance and summarization accuracy, I used a Router Chain that routes prompts to the right agent based on intent. The router uses an LLM to decide whether a task is compliance, summarization, or general Q&A, then routes to specialized agents with tailored prompts.

For the RAG part:
- Tagged files by task (e.g., "HKT compliance", "marketing")
- Only retrieve matching chunks during keyword matching
- This shrinks search space and feeds more relevant context

I used Meilisearch for fast keyword filtering and pgvector for semantic search, which reduced latency and improved precision. We measured accuracy and latency, then tuned prompts and cache strategies to improve both.

3) Network Compliance and On-Prem Deployment

We mounted a VPN container so all service traffic routes through it to handle regional restrictions (like OpenAI geo-blocks). All containers in our Docker network use the VPN for outbound requests.

I also deployed Llama-3 locally with Ollama and wrote backend adapters to match the same token counting, context handling, and usage recording flows as cloud providers.




## How We Handle Sessions and Auth
use clerk.js that handle JWT, sesison token and cookies that store user preferencs

## The OOP Structure
We have a BaseClient class in TypeScript that defines the whole flow—check balance, build context, estimate tokens, send request, parse response, record usage. Then each provider (OpenAI, Anthropic, Google, our custom endpoint, Ollama) extends it and overrides what it needs (encoding, message formatting, how to extract usage from responses). This keeps the core consistent while allowing customization.

## Cloud vs On-Prem Decision Making
We ran side-by-side tests:
- On-prem (Ollama): cheaper, full control, but needs infrastructure
- Cloud enterprise endpoints: better performance and advanced features, but higher cost
We measured token costs, latency, and accuracy, then built a routing layer that picks the right backend per conversation based on sensitivity, latency needs, and budget.

## Compliance Features
We track everything: every token spend is recorded as a transaction in MongoDB with user, model, conversation ID, and context. Before each API call, we check balance; if insufficient, we reject early. We also log violations (like balance checks) for auditing.

For multi-modal support, we handle text, files, and images. Each provider exposes different capabilities, so we route those features accordingly.