Purpose

- Orchestrates the chat lifecycle: builds context, checks budget, calls provider, and persists messages. Subclassed by providers like ExampleProvider or RealRAGIntegration.

Responsibilities

- Expose sendMessage() (edit/regenerate flows are handled via MessageThread IDs, not a separate editMessage()).

- Manage conversation/thread IDs via MessageThread.

- Load/save messages via PersistenceAdapter.

- Backfill attachments via Attachments.

- Build the model payload via ConversationContext.

- Check prompt budget via UsageRecorder.checkPromptBalance().

- Call sendCompletion() implemented by subclass.

- Persist the assistant message and record prompt token usage via UsageRecorder.recordUserMessageTokenCount().

Key Methods

- sendMessage(userText, opts) – orchestrates the request:

- thread.prepareIds() → persistence.loadHistory() → thread.materialize() → user msg creation/persist → attachments backfill → context.build() → usage.checkPromptBalance() → sendCompletion() → thread.buildAssistantMessage() → persist assistant → record user msg token count.

- sendCompletion(payload, opts) – abstract provider call.

- getSaveOptions() / getModelId() / setOptions(opts) – provider hooks for metadata and runtime config.