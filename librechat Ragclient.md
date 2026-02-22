Purpose

- Centralize RAG queries against external document sources.

Typical Responsibilities (future/optional in this codepath)

- For each attached/reference file, fetch relevant chunks.

- Support “full context” vs “semantic query” modes.

- Return merged context blocks for prompting.

Integration

- In this test client setup, RAG is handled inside provider subclasses (e.g., ExampleProvider simulates; RealRAGIntegration shows a production-style flow).

- If introduced, RagClient would be called by providers or by an extended ConversationContext.