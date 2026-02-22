strongly typed, define a schema that outlines the types( scalars, objects, enums, interfaces, unions, and input types) and fields available

[[GraphQL Yoga]]
[[GraphQL pagination]]

SDL
```typescript
type Query {
  hello(name: String): String!
}
```
**Query Type:** The entry point for all read operations.
**Fields:** Define what data clients can request.
**Scalars:** Basic types like `String`, `Int`, `Boolean`.

**Query**
```typescript
query {
  hello(name: "Alice")
}
```

**Mutations** 
modify server-side data( create, update or deletion )
```typescript
type Mutation {
  addMessage(text: String!): Message!
}

type Message {
  id: ID!
  text: String!
}
```

```typescript
mutation {
  addMessage(text: "Hello, GraphQL!") {
    id
    text
  }
}
```
takes an input and returns a newly created Message object 

**Resolvers**
are functions that connect schema to data, determine how each field in schema is populated when a query or mutation is executed 
```typescript
const resolvers = {
  Query: {
    hello: (_, { name }) => `Hello, ${name || 'World'}`,
  },
  Mutation: {
    addMessage: (_, { text }, { dataSources }) => dataSources.messageAPI.createMessage(text),
  },
};
```

**Context**
an object shared by all resolvers in a single request
authentication, database connections and DataLoader instances
```typescript
const server = new ApolloServer({
  schema,
  context: ({ req }) => ({
    user: getUserFromAuthHeader(req.headers.authorization),
    db: myDatabaseConnection,
  }),
});
```

**Introspection**
query the schema itself
This allows tools (like GraphQL Playground or Apollo Studio) to generate documentation and assist with auto-completion.
```typescript
{
  __schema {
    types {
      name
    }
  }
}
```

**DataLoader**
allow nested queries where fields on objects can themselves be queried
```typescript
import DataLoader from 'dataloader';

const userLoader = new DataLoader(async (ids) => {
  const users = await getUsersByIds(ids); // Fetch users in a single query
  return ids.map(id => users.find(user => user.id === id));
});

const resolvers = {
  Post: {
    author: (post, _, { userLoader }) => userLoader.load(post.authorId),
  },
};
```
DataLoader batches requests for the same type of data so that multiple nested field requests (like fetching an author for each post) are combined into a single query, reducing overhead.

## **Federation & Schema Stitching**
**Concept:**  
Federation allows you to compose multiple GraphQL services into a single, unified API. Schema stitching is a similar approach that merges different GraphQL schemas.

**Usage:**  
This pattern is common in microservices architectures, where each team maintains its own GraphQL service that is later combined into a gateway.

**Example Tools:**  
Apollo Federation, GraphQL Mesh.

## **Subscriptions for Real-Time Data**
**Concept:**  
Subscriptions enable real-time updates by allowing the server to push data to clients over WebSockets. They’re ideal for live feeds, chat apps, or any scenario where data changes frequently.

**Example:**
```typescript
subscription {
  messageAdded {
    id
    content
    author {
      name
    }
  }
}
```
In your resolvers, you’ll typically set up a publisher-subscriber model (using tools like Redis Pub/Sub or Apollo Server’s built-in subscriptions).

## **Custom Directives & Middleware**
**Concept:**  
Custom directives let you extend GraphQL’s behavior. You might use directives to enforce authorization, validate inputs, or transform data.

**Example:**  
A custom `@auth` directive can be used on fields to restrict access based on user roles:
```typescript 
type Query {
  secretData: String @auth(requires: ADMIN)
}
```
Middleware can wrap resolvers to add logging, error handling, or performance tracing.

## **Persisted Queries & Caching**
**Concept:**  
    Persisted queries allow clients to send a query hash instead of the full query text, which improves performance and reduces bandwidth. Coupled with server-side caching, this can drastically improve efficiency.

**Usage:**  
    Tools like Apollo Server support automatic persisted queries, and you can combine them with HTTP caching headers.