## principle
for a key value pair, we hash the key with a hash function to determine the insertion index
index: hash (key) % array_size
% mod ensure our insertion index is valid within the array

## hash function 
is ideal to be
1. fast: O(1) lookup, insertions and deletions
2. deterministic: produce same results for the same input
3. uniform: mapped such that no cluster

## resize and rehashing
when hash_map reach half capacity, it will resize itself to double capacity! 
to avoid collisions
the mod number will change to the new cap! 
after resizing, we perform: 
1. rehash key value pairs
2. insert new key value pairs

## collisions
when insertion index is occupied
### chaining:
storing the colliding elements in a linked list
store multiple key-value pairs
### open addressing
continuously find next index for insertion 
### 1. Linear Probing
If position h(key) is occupied, try position (h(key) + 1) % table_size, then (h(key) + 2) % table_size, and so on.

For example, if you're trying to insert a value at position 5, but it's occupied, you'd check position 6, then 7, then 8...

This is simple to implement but suffers from "clustering"—once a few adjacent slots are filled, they tend to grow into longer clusters.

### 2. Quadratic Probing
Instead of checking consecutive slots, quadratic probing uses a quadratic function: (h(key) + 1²) % table_size, (h(key) + 2²) % table_size, (h(key) + 3²) % table_size, etc.

This helps reduce clustering but may not probe all possible positions if the table size is not chosen carefully.

### 3. Double Hashing
Uses a second hash function to determine the step size: (h1(key) + i * h2(key)) % table_size, where i is the probe number.

This provides a more uniform distribution and reduces clustering significantly.
