Purpose

- MessageThread ensures consistent conversation/message IDs and builds assistant messages.

Responsibilities

- Generates/normalizes IDs: conversationId, parentMessageId, responseMessageId.

- materialize(history, ids): derive the working message list from stored history + current turn.

- createUserMessage(ids, text): constructs the user message with correct threading.

- buildAssistantMessage(ids, completion, modelId): builds assistant reply (supports string or structured content).

- Enables edits/regenerations by maintaining correct parent/child relationships and ordering.