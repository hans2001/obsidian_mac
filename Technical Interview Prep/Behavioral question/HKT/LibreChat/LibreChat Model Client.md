Goal  
Our chat runtime is built around `BaseClient`. `BaseClient` coordinates a message send cycle end-to-end: load history, build context, call the model, save the reply, and account for usage. Everything else is a pluggable module.

Why  
We want:
- consistent behavior across different model providers,
- one place to enforce token limits / summarization / storage,
- swappable persistence (Mongo, etc.),
- clean accounting/billing.
    
Key idea  
`BaseClient` is the conductor. It does not “do the work,” it calls collaborators. Each collaborator has a single job and a separate doc.

High-level flow (per user message)
1. Load conversation state
    - Get past messages + conversation metadata from storage.

2. Add the new user message
    - Normalize it, attach files if any, assign IDs.

3. Build the model prompt
    - Inject system instructions.
    - Pull in relevant context (RAG / file snippets).
    - Trim history to fit token budget (summaries if needed).
    - Output: final prompt array + prompt token counts.
        
4. Check budget / quota
    - Make sure the user is allowed to spend these tokens.
    - Fail early if they're out of credit.
        
5. Call the model provider
    - Send the built prompt to the target model (OpenAI, etc.).
    - Support streaming and non-streaming.
    - Get assistant reply + usage info.
        
6. Persist + record usage
    - Save the assistant message.
    - Save token usage for billing / analytics.

That’s the entire runtime loop.

Main modules
`BaseClient`
- Owns the flow above.
- Exposes `sendMessage()`.
- Provider subclasses extend this to hook into specific model APIs.

`PersistenceAdapter`
- Reads/writes conversations and messages to the DB (Mongo for us).
- Also stores per-conversation metadata like which model is being used.

`ConversationContext`
- Builds the final prompt sent to the model.
- Injects system instructions.
- Adds recent history.
- Optionally injects retrieved external context (RAG, file snippets).
- Enforces token limits and triggers summarization.

`RagClient`
- (Optional) Fetches contextual snippets from external docs / embeddings / file store.
- Returns text chunks that `ConversationContext` can stitch in.
    
`MessageThread`
- Assigns stable IDs (conversationId, parent/child message relationships).
- Knows how to represent edits, regenerations, etc.
    
`TokenAccounting`
- Counts tokens for messages/prompts/responses.
- Used both for context trimming and for billing.

`UsageRecorder`
- Enforces per-user budgets / quotas using `BalanceAdapter`.
- Records usage (prompt tokens, completion tokens, model ID) for billing/metrics.

`BalanceAdapter`
- Source of truth for “is this user allowed to spend right now?” (credits, plan, etc.).

`TransportAdapter`
- Low-level HTTP / streaming against model APIs.
- Handles retries, streaming chunks, backoff.

`<Provider>Client` (ex: `ExampleProvider`, `OpenAIProvider`, etc.)
- Extends `BaseClient`.
- Knows how to translate our internal prompt into that provider’s request format.
- Knows how to parse that provider’s response (including streaming).
- Supplies model metadata via `getSaveOptions()` and `getModelId()`.
- Applies provider-specific config via `setOptions()`.

Think of it like this:
User input  
→ BaseClient  → PersistenceAdapter (load history)  → MessageThread (attach IDs)  
→ ConversationContext (build prompt using RagClient, Summarizer, TokenAccounting)  → UsageRecorder (check budget)  
→ ProviderClient.sendCompletion(...) using TransportAdapter  
→ PersistenceAdapter + UsageRecorder (save reply + usage)

# Submodules

## Core orchestration
[[librechat baseclient]]
[[librechat model provider]]

## Conversation context pipeline
[[librechat context]]
[[librechat instructions]]
[[librechat truncation policy]]
[[librechat summarizer]]
[[librechat attachments]]
[[librechat Ragclient]]
[[librechat persistence]]

## Threading and message structure
[[librechat messagethread]]

[[librechat token accounting]]
[[librechat usage recorder]]
[[librechat balanceadapter]]

## Io and integration
[[librechat transportadapter]]


