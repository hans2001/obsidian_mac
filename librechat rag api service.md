The core idea: ingest files, chunk and embed them into a vector database (pgvector), then answer user “query text” via vector similarity search filtered by metadata like file_id.
### Key parts:
- Embedding pipelines: /embed, /local/embed
- Retrieval: /query (single file_id), /query_multiple (multiple file_ids)
- Management: /ids, /documents (get/delete), /documents/{id}/context, /health
### Under the hood:
- Loaders parse many formats into Document[]
- Chunking uses configured CHUNK_SIZE/CHUNK_OVERLAP
- Embeddings created on the backend
- Searches are KNN using cosine similarity in pgvector, filtered by metadata
- LRU cache only for query-text → embedding to avoid recomputation (DB search still runs)


## Embedding a file (upload)

- POST /embed with form file_id, file, optional entity_id.

- Save file to RAG_UPLOAD_DIR/<user_id>/....

- Detect loader via get_loader(...), loader.load() → Document[].

- Chunk with RecursiveCharacterTextSplitter(CHUNK_SIZE, CHUNK_OVERLAP).

- Attach metadata per chunk: file_id, user_id, digest, plus loader metadata.

- Store in vector DB: aadd_documents (pgvector async) or add_documents.

- Remove temp file, return {status, file_id, filename, known_type}.

## Query by single file_id (semantic search)

- POST /query with QueryRequestBody { file_id, query, k }.

- “Query text” = the prompt string in query.

- LRU cache embeds identical query strings once: embed_query(query) → cached vector.

- Similarity search: similarity_search_with_score_by_vector(embedding, k, filter={"file_id": file_id}).

- Auth: compare doc.metadata.user_id vs request.state.user.id or entity_id or "public".

## Fetch stored documents

- Validate IDs; fetch get_documents_by_ids(ids).
## Fetch processed context (single file)

- GET /documents/{id}/context
- Fetch docs by id; format with process_documents(...) into readable context.
## Delete documents

- DELETE /documents with body ["id1","id2", ...]
- Validate IDs; delete(ids=...).
## Loader purpose

- Chooses the correct parser for file type (PDF/CSV/DOCX/PPTX/XLSX/XML/Markdown/EPUB/Text).
- Handles PDF image extraction (PDF_EXTRACT_IMAGES) with safe fallback.
- For CSV, detects encoding; may create a temporary UTF‑8 copy for robust loading.
## Temp files

- Needed because loaders expect file paths and to avoid large in-memory buffers.
- Upload saved to disk, processed, then removed in finally.
## Caching behavior

- LRU cache only for query-text → embedding vector (identical strings reuse the same embedding).
- No caching of DB search results; every query performs a DB similarity search.
- Chunks/embeddings are persisted in the vector DB; not cached in memory.
## Vector search details (pgvector)

- Retrieval: KNN (top‑k nearest) using cosine similarity on pgvector.
- Filtering: by file_id (and $in for multiple).
- Indexes ensured for custom_id and JSON file_id; no ANN index configured here by default.


writing to local path!
- We first write the incoming binary stream to a temp local path under RAG_UPLOAD_DIR/<user_id>/....
- Then we load → chunk → embed → store vectors in the DB.
- Finally, we delete the temp file.