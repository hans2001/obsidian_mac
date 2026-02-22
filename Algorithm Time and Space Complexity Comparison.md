## Sorting Algorithms

| Algorithm      | Time Complexity (Best) | Time Complexity (Average) | Time Complexity (Worst) | Space Complexity | Stability | Notes                                                       |
| -------------- | ---------------------- | ------------------------- | ----------------------- | ---------------- | --------- | ----------------------------------------------------------- |
| Bubble Sort    | O(n)                   | O(n²)                     | O(n²)                   | O(1)             | Stable    | Simple implementation; inefficient for large datasets       |
| Selection Sort | O(n²)                  | O(n²)                     | O(n²)                   | O(1)             | Unstable  | Performs poorly with all input sizes                        |
| Insertion Sort | O(n)                   | O(n²)                     | O(n²)                   | O(1)             | Stable    | Efficient for small or nearly sorted data                   |
| Merge Sort     | O(n log n)             | O(n log n)                | O(n log n)              | O(n)             | Stable    | Consistent performance; requires extra space                |
| Quick Sort     | O(n log n)             | O(n log n)                | O(n²)                   | O(log n)         | Unstable  | Fast in practice; poor worst case with bad pivots           |
| Heap Sort      | O(n log n)             | O(n log n)                | O(n log n)              | O(1)             | Unstable  | In-place sorting; slower than quick sort in practice        |
| Counting Sort  | O(n+k)                 | O(n+k)                    | O(n+k)                  | O(n+k)           | Stable    | Efficient when range of input values (k) is small           |
| Radix Sort     | O(n·k)                 | O(n·k)                    | O(n·k)                  | O(n+k)           | Stable    | Good for fixed-length integers; k is number of digits       |
| Bucket Sort    | O(n+k)                 | O(n+k)                    | O(n²)                   | O(n+k)           | Stable    | Efficient when input uniformly distributed                  |
| Tim Sort       | O(n)                   | O(n log n)                | O(n log n)              | O(n)             | Stable    | Hybrid of merge and insertion sort; used in Python and Java |

## Searching Algorithms

| Algorithm            | Time Complexity (Best) | Time Complexity (Average) | Time Complexity (Worst) | Space Complexity | Notes                                                              |
| -------------------- | ---------------------- | ------------------------- | ----------------------- | ---------------- | ------------------------------------------------------------------ |
| Linear Search        | O(1)                   | O(n)                      | O(n)                    | O(1)             | Works on unsorted arrays; simple                                   |
| Binary Search        | O(1)                   | O(log n)                  | O(log n)                | O(1)             | Requires sorted data                                               |
| Jump Search          | O(1)                   | O(√n)                     | O(√n)                   | O(1)             | Works on sorted arrays; block-based                                |
| Interpolation Search | O(1)                   | O(log log n)              | O(n)                    | O(1)             | Good for uniformly distributed sorted arrays                       |
| Exponential Search   | O(1)                   | O(log n)                  | O(log n)                | O(1)             | Good for unbounded/infinite arrays                                 |
| Ternary Search       | O(1)                   | O(log₃ n)                 | O(log₃ n)               | O(1)             | Divides range into three parts; slightly slower than binary search |

## Graph Algorithms

| Algorithm        | Time Complexity | Space Complexity | Use Cases                                             | Limitations                              |
| ---------------- | --------------- | ---------------- | ----------------------------------------------------- | ---------------------------------------- |
| BFS              | O(V+E)          | O(V)             | Shortest path (unweighted), level-order traversal     | Memory intensive for wide graphs         |
| DFS              | O(V+E)          | O(V)             | Maze generation, topological sorting, cycle detection | Not optimal for shortest paths           |
| Dijkstra's       | O((V+E)log V)   | O(V)             | Shortest path (positive weights)                      | Doesn't work with negative weights       |
| Bellman-Ford     | O(V·E)          | O(V)             | Shortest path (allows negative weights)               | Slower than Dijkstra's                   |
| Floyd-Warshall   | O(V³)           | O(V²)            | All-pairs shortest paths                              | High time and space complexity           |
| Prim's           | O(E log V)      | O(V)             | Minimum spanning tree                                 | Works only on undirected graphs          |
| Kruskal's        | O(E log E)      | O(V)             | Minimum spanning tree                                 | Union-find structure required            |
| Topological Sort | O(V+E)          | O(V)             | Scheduling, dependency resolution                     | Only works on DAGs                       |
| A* Search        | O(E)            | O(V)             | Path finding with heuristics                          | Performance depends on heuristic quality |

## String Algorithms

|Algorithm|Time Complexity (Best)|Time Complexity (Average)|Time Complexity (Worst)|Space Complexity|Use Cases|
|---|---|---|---|---|---|
|Naive String Matching|O(n)|O(m·n)|O(m·n)|O(1)|Simple pattern matching|
|KMP|O(n)|O(n)|O(n)|O(m)|Efficient pattern matching|
|Rabin-Karp|O(n)|O(n)|O(m·n)|O(1)|Multiple pattern matching|
|Z Algorithm|O(n)|O(n)|O(n)|O(n)|Pattern matching and variants|
|Boyer-Moore|O(n)|O(n)|O(m·n)|O(m)|Practical string searching|
|Aho-Corasick|O(n+m+z)|O(n+m+z)|O(n+m+z)|O(m)|Multiple pattern matching|
|Suffix Tree|O(n)|O(n)|O(n)|O(n)|Complex string operations|
|Manacher's|O(n)|O(n)|O(n)|O(n)|Finding palindromic substrings|

## Dynamic Programming Algorithms

| Algorithm                      | Time Complexity     | Space Complexity | Use Cases                          | Optimization Techniques     |
| ------------------------------ | ------------------- | ---------------- | ---------------------------------- | --------------------------- |
| Fibonacci                      | O(n)                | O(n) or O(1)     | Sequence calculation               | Memoization, tabulation     |
| Longest Common Subsequence     | O(m·n)              | O(m·n)           | String similarity, diff tools      | Space optimization possible |
| 0/1 Knapsack                   | O(n·W)              | O(n·W)           | Resource allocation                | Space optimization to O(W)  |
| Matrix Chain Multiplication    | O(n³)               | O(n²)            | Optimizing series of operations    | -                           |
| Longest Increasing Subsequence | O(n²) or O(n log n) | O(n)             | Sequence analysis                  | Binary search optimization  |
| Edit Distance                  | O(m·n)              | O(m·n)           | Spell checking, DNA analysis       | Space optimization possible |
| Coin Change                    | O(n·amount)         | O(amount)        | Currency systems, vending machines | -                           |

## Key Algorithm Selection Criteria
### Sorting Algorithm Selection

- **Use Quick Sort**: General purpose, in-memory sorting
- **Use Merge Sort**: When stability is required or external sorting
- **Use Heap Sort**: When guaranteed O(n log n) and minimal space is needed
- **Use Insertion Sort**: For small arrays or nearly sorted data
- **Use Radix/Counting Sort**: For integers with limited range

### Searching Algorithm Selection

- **Use Binary Search**: When data is sorted and random access is available
- **Use Hash-based Search**: When O(1) lookups are critical and preparation time is available
- **Use BFS**: For shortest paths in unweighted graphs or level order traversal
- **Use DFS**: For maze problems, topological sorting, or connected components
- **Use A* : When a good heuristic is available to guide graph search

### Dynamic Programming vs Greedy Algorithms

- **Use DP**: When problem has optimal substructure and overlapping subproblems
- **Use Greedy**: When local optimal choices lead to global optimal solution