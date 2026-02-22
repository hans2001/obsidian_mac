Purpose

- Summarizer.summarize() condenses pruned messages into a compact system message when there is token headroom, preserving long-term context.

Interfaces

- Summarizer: summarize(messagesToRefine, remainingTokens) → Promise<{ summaryMessage?, summaryTokenCount }>

- SimpleSummarizer (example implementation):

- Approximates 1 token ≈ 4 chars.

- Concatenates old message texts up to (remainingTokens - 4) * 4 chars.

- Wraps as a system message prefixed with [summary].

How it fits the flow

- ConversationContext.build():

- Calls truncation first; if messages were pruned and there’s remainingContextTokens > 0, it calls summarize(...).

- If a summary is returned, it is prepended as a system message and promptTokens is incremented by summaryTokenCount.

Design considerations

- Summarizer should:

- Respect remainingTokens strictly to avoid context overflow.

- Preserve important entities, tasks, and decisions.

- Avoid leaking sensitive data if your redaction policies require it.

Extension points

- Replace SimpleSummarizer with:

- LLM-based abstractive summarization gated by a tighter budget.

- Extractive key-phrase + bullet list approaches for predictability.

- Domain-aware templates (tickets, incidents, meeting minutes).

Edge cases

- If messagesToRefine is empty or remainingTokens is zero, return { summaryTokenCount: 0 } (no summary).

- If the computed summary is empty, omit summaryMessage.