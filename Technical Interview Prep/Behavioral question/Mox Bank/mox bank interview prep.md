**
Hi, My name is Hans, I recently obtained Bachelor's in Electronic Engineering with a minor in information technology at Hong Kong University of Science and Technology

**Experience Overview**  
currently, I have around 4 years of software development experience, primarily centered around

- languages: JavaScript, TypeScript, and Python,
- cloud technologies like AWS, Azure
- and have experience with both SQL databases and NoSQL solutions

My internship experience spans 4 industries: (optional) financial, transportation, telecommunication, and EdTech. Throughout these roles, I've built SaaS platforms, mobile applications, AI-powered systems, and Windows desktop application

**Career Journey**  
My career began at startups like SOCIF and Midas, where I built flagship product such as market intelligence web platform handling millions of financial records, which helped them secured their first batch of clients and around a million hkd in funding 

I later transitioned to large corporations, like CICC, where I developed a real-time monitoring dashboard with technologies such as Vue.js/ Node.js for Bloomberg data usages. Most recently, i interned at HKT, where I built an internal GenAI platform that interacts with LLM endpoints like OpenAI, Gemini, and Llama3, and utilized workflows to improve response accuracy using technologies such as LangChain and OPENAI SDK

Financial technologies have always been my passion, I believe my expertise in full stack development will allow me to contribute greatly to your mox bank’s mission!
**

review graphql paigination and pothos 
review docker compose and github actions for HKT

[[Docker]]
[[Docker Compose]]
```yml
version: '3'
services:
  api:
    build: ./api
    environment:
      - NODE_ENV=production
  llm:
    image: ollama/ollama
    volumes:
      - ./models:/models
```

[[Typescript]]
```typescript
interface ApiResponse<T> { data: T; error?: Error; } async function fetchData<T>(url: string): Promise<ApiResponse<T>> { // Your implementation }
```

**[[SOCIF]]**
**1. Signed URLs:**
prevents unauthorized access to statement 
```javascript
// Time-limited access
const signedUrl = generateSignedUrl(resource, expiresIn: 3600);
```

**2. Multipart Upload Security:**
- Client-side validation
- Server-side verification
- Encrypted transmission "Critical for KYC document uploads"

[[TDD principles]]
[[DDD principles]]
## [[Tap  & Go]]

## React / React Native
### **React Performance Patterns You've Used:**
1. **Memoization**
    - `React.memo()` for expensive components
    - `useMemo()` for heavy computations (store and reuse computation results( memoization))
    - `useCallback()` for stable function references (function are created every render(with out use callback))
    - only recreate when dependencies changed!
```
Without useCallback: Every parent render = all children re-render

With useCallback: Children only re-render when their data changes
```

## API protocols
```graphql
# Your GraphQL - Single request, precise data
query {
  financialNews(limit: 100) {
    id
    title
    sentiment
  }
}

# vs REST - Multiple requests, overfetching
GET /api/news
GET /api/news/1/sentiment
```
**Restful**
while modular, can result in over fetching unnecessary round trips between client and server
## [[GraphQL]]
1review graphql features! and production experience with grpahql 
N+1 problem! 
multi layer caching? 
why use graphql over restful
custom resolvers made the backend codebase can be more complicated!
[[Pothos]]

Defend design decision
"At Midas, we chose GraphQL because our financial dashboard had varied data needs. Different clients needed different fields. The multi-layer caching reduced our API latency by 40%." (client able to customize their dashboard) ( multi tenant architecture )

Performance optimizations with Graphql
- **DataLoader pattern** - Batch database queries
- **Query complexity analysis** - Prevent expensive queries
- **Caching layers** - TTL, session-based, mutation invalidation
## [[Kafka]]
connectors between producer and consumer applications, where event are stored by topic( partitions )
can be replayed in the order event are generated (queue) (first in first out)

**CAP theorem**
distributed systems can only guarantee **2 out of 3**
**C**onsistency
**A**vailability
**P**artition Tolerance


[[CICC]]