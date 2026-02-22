# Flow: 
initialization (retrieve files attached to the session)
```js
// From BaseClient.js - addPreviousAttachments()
const files = await getFiles(
  { file_id: { $in: fileIds } },  // Query MongoDB for file metadata
  {},
  {}
);
```
fetch metadata form mongo, check files with embedded tag as true

file search tool
```js
// From createContextHandlers.js
const query = async (file) => {
  if (useFullContext) {
    // Get entire document content
    return axios.get(`${process.env.RAG_API_URL}/documents/${file.file_id}/context`);
  } else {
    // Get semantically relevant chunks via pgvector
    return axios.post(`${process.env.RAG_API_URL}/query`, {
      file_id: file.file_id,
      query: userMessageContent,  // User's question
      k: 4,                       // Top 4 most relevant chunks
    });
  }
};
```
either we perform semantic search or keyword search

3. The rag api backend performs
```sql
   SELECT content, metadata, 
          embedding <-> query_embedding AS distance
   FROM document_chunks 
   WHERE file_id = $1 
   ORDER BY embedding <-> query_embedding 
   LIMIT $2;
```
receive file id ,generate embeddings using the same model as we parse the file, and 
retrieve relevant text chunks!

the rag api returned text chunks in JSON ,adn we parse the into our current message histories for context

payload now includes :
previous chats
file content 
users new question

## Flow diagram
User Query → MongoDB (file metadata) → RAG API → PostgreSQL (vector search) → Text chunks → Message context → AI Provider
     ↓              ↓                    ↓           ↓                        ↓              ↓              ↓
"Tell me about X" → Find embedded files → Query → Vector similarity → Relevant text → Add to context → Generate response

## Flow Service arch
User: "What's our Q3 revenue?"

1. Meilisearch: "Find files with 'revenue' or 'Q3' keywords"
   → Returns: ["budget.pdf", "financial_report.xlsx"]

2. MongoDB: "Get metadata for these files"
   → Returns: File permissions, embedded flags

3. PostgreSQL: "Find content about Q3 revenue in these files"
   → Returns: "Q3 revenue was $2.5M, up 15% from Q2..."

4. Context: Add relevant chunks to message history

5. AI Provider: Generate response based on enriched context

we use keywords in user prompt to search relevant files with milisearch ,then those file ids were used to fetch metadata form mongodb ,and then the user prompt along with the file metadata were used to semantic search the postgressql, and the text chunks  response was parsed as system messages into the current chat histories , and sent to the ai provider for a completion request


focus on how i communicate with the service here! and some basic understanding on the repo?