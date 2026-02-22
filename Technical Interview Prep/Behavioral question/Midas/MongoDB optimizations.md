- Midas ( high throughput system ) -> used protobuf.js + mongo native driver to eliminate the overhead from Mongoose 
- Created the optimized protobuf file for the data team who handles the database management! 
- Validate queried data with the protobuf (protocol buffer) file in backend !

**protobuf.js**: ensure data consistency at serialization level, turn in memory objects( js objects) to binary format( smaller payload !)
## **What Are Indexes?**

- **Definition:**  
Indexes are data structures that store a small portion of the collection’s data in an easily traversable form. They allow MongoDB to quickly locate and retrieve documents without scanning the entire collection (work with match and sort keyword).

- **Faster Query Performance:** By narrowing the search space, indexes speed up queries, especially on large datasets.
- **Efficient Sorting:** MongoDB can use indexes to sort query results without an extra in-memory sort step.

## **Index types**
**Compound**
```js
db.orders.createIndex({ customerId: 1, orderDate: -1 });
```
Create indexes that span multiple fields to cover queries that filter on several conditions.

**Partial index**
```js
db.users.createIndex(
  { email: 1 },
  { partialFilterExpression: { email: { $exists: true } } }
);
```
Build indexes that only cover documents meeting certain criteria, reducing index size and improving performance for queries that match that condition.

## **What Is Protobuf?**

- **Definition:**  
Protocol Buffers (protobuf) are a language-neutral, platform-neutral mechanism developed by Google for serializing structured data. They provide a compact binary format that is both efficient and faster to parse compared to text-based formats like JSON.
### **Why Optimize Protobuf Schemas?**
**Efficiency:** A well-optimized protobuf schema reduces the size of the data sent between services and speeds up serialization/deserialization.
```proto
syntax = "proto3";

message User {
  string id = 1;
  string username = 2;
  int32 age = 3;
  // Consider removing rarely used fields or restructuring nested messages
}
```

**Optimization Tips:**
- **Field Order & Type:**  
Order your fields in the schema for efficient encoding, and use the most compact type that fits your data.
- **Optional Fields:**  
Remove or mark fields as optional if they are not always needed to reduce the payload size.

## **What is Serialization?**

- **Definition:**  
Serialization converts complex objects into a simpler format (often a byte stream) so they can be stored (in a database or file) or sent over a network. Deserialization reverses this process.

**Encoding**
```js
const protobuf = require('protobufjs');

protobuf.load("product.proto", (err, root) => {
  if (err) throw err;

  const Product = root.lookupType("Product");

  const productPayload = {
    id: "123",
    name: "Widget",
    price: 19.99,
    category: "Gadgets"
  };

  const errMsg = Product.verify(productPayload);
  if (errMsg) throw Error(errMsg);

  const message = Product.create(productPayload);
  const buffer = Product.encode(message).finish();

  // Now, store 'buffer' in MongoDB (using the native driver)
  // db.collection("products").insertOne({ data: buffer });
  console.log("Encoded product buffer:", buffer);
});
```
Encode your data using protobufjs into a binary format before storing it in MongoDB.

**Decoding**
```js
protobuf.load("product.proto", (err, root) => {
  if (err) throw err;

  const Product = root.lookupType("Product");

  // Simulate retrieved buffer (normally retrieved from MongoDB)
  const retrievedBuffer = /* buffer retrieved from MongoDB */;

  const decodedMessage = Product.decode(retrievedBuffer);
  const productObject = Product.toObject(decodedMessage, {
    longs: String,
    enums: String,
    bytes: String,
  });

  console.log("Decoded product object:", productObject);
});
```
When you retrieve the binary data, decode it back into a structured object using the same protobuf schema.

## Schema Design Optimization

- **Embedding vs. Referencing:**  
Carefully design your schema based on your query patterns. Embedding related data in one document can reduce the need for joins, while referencing may be preferred when dealing with large or highly variable subdocuments.
- **Data Denormalization:**  
Sometimes duplicating data across documents (denormalization) can reduce the need for expensive joins or aggregation pipelines during reads.
- **Example:**  
Instead of storing user orders in a separate collection and joining at runtime, you might embed a summary of orders in the user document if the order history is relatively static.

## Aggregation Pipeline Enhancements
- **Pipeline Stages:**
- **$project:** Limit fields to only what you need, reducing data transfer and memory usage.
- **$facet:** Run multiple aggregations in parallel within a single query.
- **$bucket or $bucketAuto:** Group documents into ranges, which is useful for histograms or summaries.
- **Optimization Tip:**  Start your pipeline with a well-indexed `$match` stage to filter out unnecessary documents early.

```js
db.sales.aggregate([
  { $match: { status: "completed" } }, // Use index on 'status' if available
  { $group: { _id: "$category", totalSales: { $sum: "$amount" } } },
  { $sort: { totalSales: -1 } }
]);
```

### **Facet**
Runs multiple independent aggregations on the same set of documents in parallel. Each sub-pipeline under a facet produces a separate output field.
```js
db.orders.aggregate([
  { $match: { status: "completed" } },
  {
    $facet: {
      // One facet for summarizing revenue
      revenueStats: [
        { $group: { _id: null, totalRevenue: { $sum: "$total" } } }
      ],
      // Another facet for categorizing orders by region
      ordersByRegion: [
        { $group: { _id: "$region", count: { $sum: 1 } } },
        { $sort: { count: -1 } }
      ]
    }
  }
]);
```
- This query returns a document with two fields, `revenueStats` and `ordersByRegion`, each containing the result of its respective sub-pipeline.

## **Bucket**
Groups documents into a specified number of buckets based on a given expression and explicit boundaries.
```js
db.products.aggregate([
  {
    $bucket: {
      groupBy: "$price",  // Field to group by
      boundaries: [0, 50, 100, 150, 200], // Boundaries for buckets
      default: "Other",   // Bucket for values outside boundaries
      output: { count: { $sum: 1 } }
    }
  }
]);
```
This query groups products into buckets by price ranges (0–50, 50–100, etc.) and counts how many fall into each bucket.

# Query and Performance Analysis

## **Explain Plans:**  

- Use the `explain()` method to review the query plan, check for index usage, and identify potential performance bottlenecks.
- `db.users.find({ age: { $gt: 25 } }).explain("executionStats");`

## **Metadata:**

- **Winning Plan:** The strategy MongoDB chose for executing the query.
- **Stages:** A breakdown of stages such as COLLSCAN (collection scan) or IXSCAN (index scan).
- **Execution Stats:** Metrics like `nReturned` (documents returned), `totalDocsExamined`, `totalKeysExamined`, and execution time.
- **Index Usage:** Information on which indexes (if any) were used.

## **Optimizations**

- **Add or Adjust Indexes:**  
    If your explain output shows a COLLSCAN on a frequently queried field, consider adding an index.
- **Rewrite Queries:**  
    Restructure queries to be more selective or to better leverage indexes (e.g., use more specific $match conditions).

## Sharding and Replication

- **Sharding:**  Scale horizontally by partitioning your data across multiple servers (shards). Sharding is effective for large datasets or high throughput workloads.
- **Example:** Use a shard key that evenly distributes data across shards.
- **Considerations:** Ensure the shard key is chosen based on your query patterns and data distribution.
- **Replication:**  Set up replica sets for high availability and to offload read operations from the primary node. Secondary nodes can serve read queries, thus distributing load.
## **Connection Pooling:**  

Efficiently manages multiple connections to the database. This is crucial in high-load environments where maintaining a pool of active connections can significantly reduce connection overhead.

# Why the Native Driver Is Faster (compare to Mongoose)

- **Minimal Abstraction:**  
The native driver exposes MongoDB’s core functionalities directly. It doesn't add extra layers for schema definitions, validations, or middleware, which can introduce overhead in Mongoose.

- **Direct API Access:**  
You work directly with the MongoDB commands and queries. This means there’s less processing between your code and the database engine, resulting in lower latency.

- **Lower Memory Footprint:**  
Without the extra features (like document middleware, built-in validations, etc.), the native driver consumes fewer resources and can handle high-throughput scenarios more efficiently.

## MongoDB native + protobuf.js
```js
const protobuf = require('protobufjs');

protobuf.load("awesome.proto", (err, root) => {
  if (err) throw err;

  const AwesomeMessage = root.lookupType("awesomepackage.AwesomeMessage");
  const payload = { awesomeField: "AwesomeString" };

  // Verify the payload
  const errMsg = AwesomeMessage.verify(payload);
  if (errMsg) throw Error(errMsg);

  // Create a message instance (object)
  const message = AwesomeMessage.create(payload);
  
  // Optionally convert to a plain object
  const object = AwesomeMessage.toObject(message, {
    longs: String,
    enums: String,
    bytes: String,
  });
  
  // Now, 'object' is a regular JavaScript object that adheres to the Protobuf schema
  // You can then store it directly into MongoDB as a JSON/BSON document.
  console.log("Verified and converted object:", object);
});
```