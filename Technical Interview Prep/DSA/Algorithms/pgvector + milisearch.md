## 1. Why fuse pgvector + Millisearch?

- **pgvector (semantic search)**  
    Finds text chunks by **meaning** (via embeddings). Example:
    - Query: “regulations about artificial intelligence”
    - Match: “EU AI Act draft” (no overlapping keywords, but conceptually related).
        
- **Millisearch (keyword search)**  
    Finds text chunks by **exact words / phrases / filters**. Example:
    - Query: “AI regulation”
    - Match: “AI regulation in China” (because “AI” and “regulation” literally appear).
        

👉 Neither is perfect alone:
- Semantic can miss exact matches if embeddings are fuzzy.
- Keyword can miss synonyms / paraphrases.

So we **combine (fuse) results** from both → hybrid retrieval.

---

## 2. What goes into both indexes?

Yes — the same **text chunks** (e.g. 300–800 tokens each).
- **pgvector** stores → `(chunk_text, embedding, metadata)`
- **Millisearch** stores → `(chunk_text, metadata)`
    
That way:
- pgvector gives **semantic similarity scores**.
- Millisearch gives **lexical ranks** (position in ranked list).
    
## 3. What is RRF (Reciprocal Rank Fusion)?

RRF = **Reciprocal Rank Fusion**.  
It’s a simple way to merge ranked lists from different retrievers (like pgvector + Millisearch).

Formula:
score(d)=∑r∈retrievers1k+rankr(d)score(d)=r∈retrievers∑​k+rankr​(d)1​
- `rank_r(d)` = position of document `d` in retriever `r`’s result list.
- `k` = constant (e.g. 60, prevents low-ranked docs from exploding).
- If a document appears in both lists, its scores add up → gets boosted.

    ![[Screenshot 2025-09-03 at 11.45.15 AM.png]]

**Example** (k=60):
Query: “AI regulation”

- **Millisearch results**:
    1. “AI regulation in China” → rank 1 → score = 1/(60+1) = 0.01639
    2. “AI regulation EU Act” → rank 2 → score = 1/(60+2) = 0.01613
        
- **pgvector results**:
    1. “EU AI Act draft” → rank 1 → score = 0.01639
    2. “China AI law” → rank 2 → score = 0.01613
        

Fusion:
- “EU AI Act draft” (pg rank 1 only) = 0.01639
- “AI regulation in China” (meili rank 1 only) = 0.01639
- If something shows up in **both lists**, its score doubles → moves up.
    

👉 This way, **overlapping hits get rewarded**, but unique hits from each retriever still have a chance.

---

## 4. End-to-End Flow

Here’s the whole pipeline:

### (A) Ingestion

1. Document → chunk into passages (e.g., 500 tokens each).
    
2. For each chunk:
    
    - Compute **embedding** (OpenAI/HuggingFace).
        
    - Insert into **pgvector table**.
        
    - Insert into **Millisearch index** (text + metadata).
        

---

### (B) Query

User asks: _“What are AI regulations in Europe?”_

1. **Millisearch search**
    
    - Input: `"AI regulations Europe"`
        
    - Output: ranked list by keyword match (`doc_id, chunk_text, rank`).
        
2. **pgvector search**
    
    - Input: embedding of query
        
    - Output: ranked list by cosine similarity (`doc_id, chunk_text, similarity`).
        

---

### (C) Fusion (RRF)

1. Normalize results into a common format: `(doc_id, rank, score)`.
    
2. Apply RRF:
    
    - Each doc gets a fused score from both lists.
        
    - If a doc appears in both → boosted.
        
    - Sort by fused score.
        
---

### (D) Context Assembly

1. Pick top-k chunks (e.g. 6).
    
2. Concatenate into a **context string**.
    
3. Pass into the LLM prompt:
    
    `Question: What are AI regulations in Europe? Context: [chunk1] [chunk2] ...`
    

---

### (E) LLM Answer

The LLM (e.g. GPT-4, Claude, etc.) generates a grounded answer using those chunks.

---

## 5. Visual Flow

 `┌───────────────┐  │ Document Store │  └──────┬────────┘         │ chunks  ┌──────▼───────┐         ┌────────────────┐  │ pgvector DB  │         │ Millisearch DB │  │ (semantic)   │         │ (lexical+facets)│  └──────┬───────┘         └─────────┬──────┘         │                           │   cosine sim                   keyword match         │                           │         └──────────┬────────────────┘                    ▼           Reciprocal Rank Fusion                    │             Top-k merged chunks                    │               LLM Prompt                    │                    ▼              Grounded Answer`