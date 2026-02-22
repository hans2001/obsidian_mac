purpose
- TruncationPolicy.truncate() selects which messages fit the token budget and which should be candidates for summarization.

Interfaces
- TruncationResult:
	- context: kept messages.
	
	- messagesToRefine: leftover/pruned messages (usually older).
	
	- remainingContextTokens: budget left after chosen messages and priming.

- TruncationPolicy:
	- truncate({ messages, tokensPerMessage, maxContextTokens, instructionsTokens }) → TruncationResult

- SlidingWindowTruncation (provided):
	- Newest-first keep:
		- Starts with a fixed cost of 3 tokens (assistant priming label).
		
		- Computes remainingBudget = maxContextTokens - instructionsTokens.
		
		- Pops from the end (newest), keeps while it fits, then stops when the next doesn’t.

How it fits the flow

- ConversationContext.build():
- Inserts instructions, then calls truncate(...).
- Uses context as payload base, forwards messagesToRefine to Summarizer, and passes remainingContextTokens for budget-aware summaries.

Why newest-first

- Recency typically boosts response quality for chat.
- Older content isn’t lost—eligible for summarization.

Alternative strategies

- Role-weighted retention (always keep the last user+assistant pair).
- Semantic importance scoring (keep high-value turns).
- Time-decay + pinning for critical instructions or domain facts.

Edge cases and guardrails

- If only instructions fit: context may be empty; upstream throws InputLengthError.
- Ensure tokensPerMessage aligns with your tokenizer to avoid under/overflows.