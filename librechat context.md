Purpose

- ConversationContext converts messages and optional instructions into the final payload sent to the model.

Responsibilities

- Insert system instructions via addInstructions().

- Enforce token budget via TruncationPolicy and TokenAccounting.

- Optionally summarize pruned messages using Summarizer.

- Return { payload, promptTokens }.

Integration

- Called by BaseClient.sendMessage() as context.build({ messages, instructions, summarizer }).

Depends On

- TruncationPolicy for sliding window/trimming.

- TokenAccounting for token counts and max context limit.

- Summarizer to compact history when needed.