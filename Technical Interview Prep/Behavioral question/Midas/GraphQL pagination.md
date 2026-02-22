#### Cursor based pagination
An encoded reference to a record. When you fetch a page of results, each record comes with a cursor. The last record’s cursor in your result set can be used to fetch the next page.

**PageInfo:**  
Provides metadata about the current page, such as:
- **endCursor:** The cursor for the last item in the fetched page.
- **hasNextPage:** A boolean indicating if more data exists beyond the current page.

## Schema
```typescript
type NewsFeed {
  id: ID!
  title: String!
  publishedAt: String!
}

type NewsFeedEdge {
  cursor: String!
  node: NewsFeed!
}

type PageInfo {
  endCursor: String
  hasNextPage: Boolean!
}

type NewsFeedConnection {
  edges: [NewsFeedEdge!]!
  pageInfo: PageInfo!
}

type Query {
  newsFeeds(first: Int, after: String): NewsFeedConnection!
}
```

- **`newsFeeds`:**  
    The query accepts two parameters:
    - **`first`:** The number of items to return.
    - **`after`:** The cursor after which the data should be fetched.
        
- **`NewsFeedConnection`:**  
    Wraps the result in an array of `edges` (each with its cursor and node) along with pagination information.

## Query
```typescript
query GetNewsFeeds($first: Int, $after: String) {
  newsFeeds(first: $first, after: $after) {
    edges {
      cursor
      node {
        id
        title
        publishedAt
      }
    }
    pageInfo {
      endCursor
      hasNextPage
    }
  }
}
```
 
##  **Usage Scenario**

1. **Initial Request:**  

The client calls the query with `first: 10` and `after: null` (or omits `after`) to get the first 10 items.

2. **Subsequent Request:**  

If `pageInfo.hasNextPage` is true, the client uses `pageInfo.endCursor` from the previous response as the `after`value for the next query to fetch the next 10 items.

For after === null, we don't have endCursor, so we are basically fetching the first 10 items! (initial fetch!)

## **Example Scenario**

Imagine you have a list of news articles with IDs. After fetching a page of 10 items, the last article on that page might have an ID of `123`. The system might encode that ID into a cursor. For example:
- **Raw Identifier:** `123`
- **Encoded Cursor:** `"MTIz"` (which is Base64 for `123`)

So, your `pageInfo.endCursor` might look like this in the GraphQL response:
`{   "pageInfo": {     "endCursor": "MTIz",     "hasNextPage": true   } }`

If `hasNextPage` is `true`, you take the `endCursor` (e.g., `"MTIz"`) from the previous response and pass it as the `after`argument in the next query:
