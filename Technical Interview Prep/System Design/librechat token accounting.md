Purpose

- TokenAccounting measures token usage for budgeting and summarization.

Responsibilities

- tokensForMessage(message) to count tokens per message.

- Expose maxContextTokens as the budget limit.

- Used to compute promptTokens and remaining headroom for summarization.

Integration

- ConversationContext relies on it for truncation and counts.

- Indirectly supports UsageRecorder via promptTokens.