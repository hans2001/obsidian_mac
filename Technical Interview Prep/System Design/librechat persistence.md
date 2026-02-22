Purpose

- PersistenceAdapter abstracts storage and message history.

Responsibilities

- Load conversation history: loadHistory(conversationId).

- Save messages: saveMessage(message, saveOptions).

- Accept provider SaveOptions (e.g., model, endpoint) from getSaveOptions() for metadata.

Integration

- Used by BaseClient to load history and persist user/assistant messages.

Notes

- Storage backing (e.g., MongoDB) is abstracted; exact collections are not referenced here by the test clients.