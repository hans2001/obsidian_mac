|                      |                                                                                 |                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| -------------------- | ------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Retrieval design** | _How would you design a system to retrieve videos similar to a given video ID?_ | Describe building embeddings (visual + audio + text), using a two‑tower model for similarity, storing embeddings in a vector DB (e.g., FAISS), computing similarity scores and using heuristics (trending, user history) to filter candidates[engineering.fb.com](https://engineering.fb.com/2023/08/09/ml-applications/scaling-instagram-explore-recommendations-system/#:~:text=The%20basic%20idea%20behind%20retrieval,from%20a%20general%20media%20distribution). |

|   |   |   |
|---|---|---|
|**Multi‑modal RAG**|_What approaches exist for multi‑modal RAG? Which would you pick for Vine?_|Explain embedding all modalities together, grounding into text, or having separate stores[developer.nvidia.com](https://developer.nvidia.com/blog/an-easy-introduction-to-multimodal-retrieval-augmented-generation/#:~:text=,ranker)[developer.nvidia.com](https://developer.nvidia.com/blog/an-easy-introduction-to-multimodal-retrieval-augmented-generation/#:~:text=Ground%20all%20modalities%20into%20one,primary%20modality). Discuss trade‑offs (simplicity vs nuance, cost, re‑ranking).|

|                          |                                                                  |                                                                                                                                                                                     |
| ------------------------ | ---------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Rule‑based filtering** | _How would you design rule‑based filters for retrieving videos?_ | Describe heuristics such as video length, category, profanity filters; talk about updating rules and monitoring false positives/negatives. Mention combining rules with ML signals. |

|   |   |   |
|---|---|---|
|**Fine‑tuning**|_How would you fine‑tune an MLLM to better recall relevant videos?_|Discuss collecting labelled pairs (prompt–video), training with contrastive or triplet loss, using PEFT/LoRA to reduce compute, and evaluating improvement.|

|   |   |   |
|---|---|---|
|**Prompt engineering**|_What makes a good prompt for video recall?_|Use clear instructions, include the video ID and context, provide examples, and specify desired output format.|

|   |   |   |
|---|---|---|
|**Evaluation**|_How do you measure retrieval quality?_|Mention precision@k, recall@k, MRR, and user‑level metrics like click‑through rate; emphasize feedback loops[arxiv.org](https://arxiv.org/pdf/2507.01066#:~:text=The%20Feedback%20Loop%20continuously%20monitors,videos%20and%20similarity%20thresholds%20based).|

# Resources
With only two days before your interview, focus on materials that give high return:

1. **TikTok EBR paper** – read “Embedding‑based Retrieval in Multimodal Content Moderation” to understand how TikTok designs retrieval systems[arxiv.org](https://arxiv.org/pdf/2507.01066#:~:text=The%20Feedback%20Loop%20continuously%20monitors,videos%20and%20similarity%20thresholds%20based)[arxiv.org](https://arxiv.org/pdf/2507.01066#:~:text=introduce%20an%20embedding,risk%20examples.%20This%20approach%20offers).
    
2. **NVIDIA’s multimodal RAG blog** – study the three approaches to building multi‑modal RAG and think about how Vine could apply them[developer.nvidia.com](https://developer.nvidia.com/blog/an-easy-introduction-to-multimodal-retrieval-augmented-generation/#:~:text=,ranker)[developer.nvidia.com](https://developer.nvidia.com/blog/an-easy-introduction-to-multimodal-retrieval-augmented-generation/#:~:text=Ground%20all%20modalities%20into%20one,primary%20modality).
    
3. **Instagram/Meta retrieval article** – note the multi‑stage retrieval architecture and Two‑Tower model details[engineering.fb.com](https://engineering.fb.com/2023/08/09/ml-applications/scaling-instagram-explore-recommendations-system/#:~:text=The%20basic%20idea%20behind%20retrieval,from%20a%20general%20media%20distribution)[engineering.fb.com](https://engineering.fb.com/2023/08/09/ml-applications/scaling-instagram-explore-recommendations-system/#:~:text=Two%20Tower%20NN).
    
4. **Your own projects** – review your code and design notes from HKT and HKUST. Be ready to discuss architecture, embedding choices, vector database operations, and performance improvements.
    
5. **Recent multi‑modal retrieval research** – skim the abstract of “Bidirectional Generative Retrieval with Multi‑Modal LLMs for Text‑Video Retrieval” to understand generative retrieval frameworks[openreview.net](https://openreview.net/forum#:~:text=text,art).
    
6. **Prompt engineering primers** – practise writing prompts for various tasks and reading guidelines from open‑source communities.

# Summary
To impress the Vine team, demonstrate that you can **hit the ground running on retrieval‑heavy tasks**. Show that you understand the **theoretical underpinnings** (embedding models, multi‑modal RAG, evaluation metrics) and have **practical experience** building GenAI platforms and multi‑modal applications.

Prepare to articulate how your past projects solved similar problems,
illustrate your problem‑solving process, 
and display curiosity about TikTok’s challenges


HKT exp 
[[HKT]]