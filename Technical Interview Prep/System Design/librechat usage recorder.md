Purpose

- UsageRecorder validates budget and records usage.

Responsibilities

- checkPromptBalance(promptTokens, { user, endpoint, modelOptions }) before model call.

- recordUserMessageTokenCount(userMsg, promptTokens) after building payload.

Integration

- Invoked inside BaseClient.sendMessage() before and after the provider call.