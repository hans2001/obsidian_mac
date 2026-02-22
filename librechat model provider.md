Purpose

- Provider subclasses connect BaseClient to actual model APIs and optional RAG.

Responsibilities

- Implement sendCompletion(payload, opts) to call the model and/or RAG services.

- Implement setOptions(opts) for runtime config (e.g., model).

- Implement getSaveOptions() and getModelId() for metadata.

- Translate internal messages to the provider’s format (and back).

- Optionally implement RAG logic (e.g., RealRAGIntegration formats file-based context; ExampleProvider demonstrates router chains and prompt templates).

Integration

- Inherits orchestration from BaseClient, replaces provider-specific behavior.