#### A schema builder for GraphQL that leverages TypeScript to enforce type safety throughout your schema definitions and resolvers. It allows you to build complex schemas with reliable, auto-inferred types.

## Concepts
Code first approach 
pothos leverage typescript's type inference so resolvers, arguments and return types are check at compile time

zero runtime overhead: 
no code generation like typeORM 

separation of concerns: 
separation between graphql api and internal data models

**Basic**
```typescript
import SchemaBuilder from '@pothos/core';
import { createYoga } from 'graphql-yoga';
import { createServer } from 'node:http';

// Create a new SchemaBuilder instance
const builder = new SchemaBuilder({});

// Define a root Query type with one field "hello"
builder.queryType({
  fields: (t) => ({
    hello: t.string({
      // Define an optional argument "name"
      args: {
        name: t.arg.string(),
      },
      // Resolver returns a greeting message:
      resolve: (_parent, { name }) => `Hello, ${name || 'World'}`,
    }),
  }),
});

// Convert your schema builder into a GraphQL schema
const schema = builder.toSchema();

// Create a GraphQL server using GraphQL Yoga
const yoga = createYoga({ schema });
const server = createServer(yoga);

server.listen(3000, () => {
  console.log('Visit http://localhost:3000/graphql');
});
```

**Defining object types and using refs**
```typescript
// Define an interface for your data model:
interface Giraffe {
  name: string;
  birthday: Date;
  heightInMeters: number;
}

// Create an ObjectRef for Giraffe:
const GiraffeRef = builder.objectRef<Giraffe>('Giraffe');

// Implement the object type with fields:
GiraffeRef.implement({
  description: 'Long necks, cool patterns, taller than you.',
  fields: (t) => ({
    name: t.exposeString('name'),
    height: t.exposeFloat('heightInMeters'),
    age: t.int({
      resolve: (giraffe) => {
        const ageDifMs = Date.now() - giraffe.birthday.getTime();
        const ageDate = new Date(ageDifMs);
        return Math.abs(ageDate.getUTCFullYear() - 1970);
      },
    }),
  }),
});

// Add a query that returns a Giraffe:
builder.queryType({
  fields: (t) => ({
    giraffe: t.field({
      type: GiraffeRef,
      resolve: () => ({
        name: 'James',
        birthday: new Date(Date.UTC(2012, 11, 12)),
        heightInMeters: 5.2,
      }),
    }),
  }),
});

```

 **Mutations, Subscriptions, and Advanced Field Definitions**
```typescript
 builder.mutationType({
  fields: (t) => ({
    addItem: t.field({
      type: 'String',
      args: {
        itemName: t.arg.string({ required: true }),
      },
      resolve: (_parent, { itemName }) => {
        // Add logic to insert an item and return a message.
        return `Item "${itemName}" added successfully!`;
      },
    }),
  }),
});
```

**Context**
```typescript
const yoga = createYoga({
  schema: builder.toSchema(),
  context: async ({ req }) => ({
    // Example: attach a current user (fetched from headers or auth token)
    currentUser: await getUserFromAuthHeader(req.headers.authorization),
  }),
});
```
The context object in Pothos is passed to every resolver. It’s a great place to store per-request data like the current user, database connections, or data loaders

**Advanced** - Defining a custom dfirective plugin
```typescript
import SchemaBuilder from '@pothos/core';

class AuthPlugin<Types> extends SchemaBuilder.BasePlugin<Types> {
  wrapResolve(resolver, fieldConfig) {
    return async (parent, args, context, info) => {
      if (!context.currentUser) {
        throw new Error('Unauthorized');
      }
      return resolver(parent, args, context, info);
    };
  }
}

// Register the plugin when initializing the builder:
const builder = new SchemaBuilder({
  plugins: [new AuthPlugin()],
});
```
This plugin wraps resolvers to enforce that a `currentUser` exists in the context before resolving a field.