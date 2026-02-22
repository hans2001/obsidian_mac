
| Data Structure         | Access     | Search     | Insertion  | Deletion   | Find Min/Max    | Space      |
| ---------------------- | ---------- | ---------- | ---------- | ---------- | --------------- | ---------- |
| **Array**              | O(1)       | O(n)¹      | O(n)²      | O(n)²      | O(n)            | O(n)       |
| **Linked List**        | O(n)       | O(n)       | O(1)³      | O(1)³      | O(n)            | O(n)       |
| **Stack**              | O(n)       | O(n)       | O(1)       | O(1)       | O(1)⁴           | O(n)       |
| **Queue**              | O(n)       | O(n)       | O(1)       | O(1)       | O(1)⁵           | O(n)       |
| **Hash Map**           | N/A        | O(1)⁶      | O(1)⁶      | O(1)⁶      | N/A             | O(n)       |
| **Binary Tree**        | O(n)⁷      | O(n)⁷      | O(n)⁷      | O(n)⁷      | O(n)            | O(n)       |
| **BST**                | O(n)⁸      | O(n)⁸      | O(n)⁸      | O(n)⁸      | O(n)⁸           | O(n)       |
| **Balanced BST**       | O(log n)   | O(log n)   | O(log n)   | O(log n)   | O(log n)        | O(n)       |
| **Binary Heap**        | N/A        | O(n)       | O(log n)   | O(log n)   | O(1)            | O(n)       |
| **Trie**               | O(m)⁹      | O(m)⁹      | O(m)⁹      | O(m)⁹      | N/A             | O(n·m)⁹    |
| **Graph (Adj List)**   | O(n)¹⁰     | O(V+E)     | O(1)       | O(V+E)     | N/A             | O(V+E)     |
| **Graph (Adj Matrix)** | O(1)       | O(V)       | O(V²)¹¹    | O(V²)¹¹    | N/A             | O(V²)      |
| **B-Tree**             | O(log n)   | O(log n)   | O(log n)   | O(log n)   | O(log n)        | O(n)       |
| **Skip List**          | O(log n)¹² | O(log n)¹² | O(log n)¹² | O(log n)¹² | O(1)/O(log n)¹³ | O(n log n) |
## Key Operations by Data Structure Type:

### Arrays/Lists:
- Use when: Fixed size, frequent random access, rare insertions/deletions
- Avoid when: Frequent insertions/deletions in the middle

### Linked Lists:
- Use when: Frequent insertions/deletions, unknown size
- Avoid when: Random access is common

### Stacks/Queues:
- Use when: LIFO/FIFO processing, backtracking, scheduling
- Avoid when: Random access or searching is needed

### Hash Maps:
- Use when: Fast lookups by key, caching
- Avoid when: Ordered data is required

### Trees:
- Use when: Hierarchical data, ordered operations, searching
- Avoid when: Flat data structures are sufficient

### Heaps:
- Use when: Priority processing, finding min/max frequently
- Avoid when: Searching for arbitrary elements

### Graphs:
- Use when: Relationship modeling, pathfinding
- Avoid when: Data doesn't have natural connections

### Tries:
- Use when: Prefix searching, autocomplete, dictionaries
- Avoid when: Not dealing with string/sequence keys