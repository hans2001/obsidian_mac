| Aspect                  | Kruskal's Algorithm                           | Prim's Algorithm                                              |
| ----------------------- | --------------------------------------------- | ------------------------------------------------------------- |
| **Basic Approach**      | Starts with an empty graph and adds edges     | Starts with a single vertex and grows a tree                  |
| **Selection Criteria**  | Selects edges by global minimum weight        | Selects the minimum-weight edge connected to the current tree |
| **Component Growth**    | Grows a forest of trees that eventually merge | Grows a single tree from the start                            |
| **Main Data Structure** | Disjoint-Set Union (DSU)                      | Priority Queue (Min-Heap)                                     |
| **Best For**            | Sparse graphs (fewer edges)                   | Dense graphs (many edges)                                     |
Kruskal
builds and sort all edges, then we select the one with min weight among all edges by processing them in order, the process stops when we included V-1 edges, which is number of edges required for the MST
**note:** 
make sure we dont add edge that create a ccyle, so we implement a DSU class to check that ,and skip the edge that creates a cycle! 

Time complexity: 
O(ElogE) dominated by the edge sorting step
DSU operation takes near constant time

Prim:
we start from a random vertex, and relax the neighboring edge with the min weight (taht connect to a new vertex)! stop the process when all nodes are included! 
**note:** 
requires priority queue for edge selection
track visited vertices

Time complexity: 
O(ElogV)) for a heap size of V