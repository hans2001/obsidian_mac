2025-05-23 13:25

Link: https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/description/

Problem: 
Given a weighted undirected connected graph with `n` vertices numbered from `0` to `n - 1`, and an array `edges` where `edges[i] = [ai, bi, weighti]` represents a bidirectional and weighted edge between nodes `ai` and `bi`. A minimum spanning tree (MST) is a subset of the graph's edges that connects all vertices without cycles and with the minimum possible total edge weight.

Find _all the critical and pseudo-critical edges in the given graph's minimum spanning tree (MST)_. An MST edge whose deletion from the graph would cause the MST weight to increase is called a _critical edge_. On the other hand, a pseudo-critical edge is that which can appear in some MSTs but not all.

Note that you can return the indices of the edges in any order.

**Example 1:**

![](https://assets.leetcode.com/uploads/2020/06/04/ex1.png)

**Input:** n = 5, edges = [[0,1,1],[1,2,1],[2,3,2],[0,3,2],[0,4,3],[3,4,3],[1,4,6]]
**Output:** [[0,1],[2,3,4,5]]
**Explanation:** The figure above describes the graph.
The following figure shows all the possible MSTs:
![](https://assets.leetcode.com/uploads/2020/06/04/msts.png)
Notice that the two edges 0 and 1 appear in all MSTs, therefore they are critical edges, so we return them in the first list of the output.
The edges 2, 3, 4, and 5 are only part of some MSTs, therefore they are considered pseudo-critical edges. We add them to the second list of the output.

**Example 2:**

![](https://assets.leetcode.com/uploads/2020/06/04/ex2.png)

**Input:** n = 4, edges = [[0,1,1],[1,2,1],[2,3,1],[0,3,1]]
**Output:** [[],[0,1,2,3]]
**Explanation:** We can observe that since all 4 edges have equal weight, choosing any 3 edges from the given 4 will yield an MST. Therefore all 4 edges are pseudo-critical.

Intuition:
To identify critical edge, if used_edge == n - 1 and the edge was skipped ,the cost of the new ""MST""(given limited edges) will be higher than the original MST ( all edges is availability ), which means that the skipped edge is essential for the actual MST. 

Otherwise, we identify is this edge is pseudo critical by force adding it to the tree in the second round. if the cost computed at second round is the same, that mean the skipped edge was replaced by a same weight edge, so the skipped edge will appear in some MST with the limited edges! otherwise the new cost is higher, which mean the skipped edge is actually not necessary at all, and it is skipped properly! 

Solution:
Kruskal
```python
def findCriticalAndPseudoCriticalEdges(self, n: int, edges: List[List[int]]) -> List[List[int]]:
    # Add original indices
    for i in range(len(edges)):
        edges[i].append(i)
    
    def find_mst(skip_idx=-1, force_idx=-1):
        dsu = DSU(n)
        cost = 0
        edges_used = 0
        
        # Force include edge if specified
        if force_idx != -1:
            src, dst, weight, _ = edges[force_idx]
            if dsu.union(src, dst):
                cost += weight
                edges_used += 1
        
        # Sort and process remaining edges
        sorted_edges = sorted(edges, key=lambda x: x[2])
        for i, (src, dst, weight, orig_idx) in enumerate(sorted_edges):
            if orig_idx == skip_idx:  # Skip this edge
                continue
            if dsu.union(src, dst):
                cost += weight
                edges_used += 1
                if edges_used == n - 1:  # MST complete
                    break
        
        # Check if graph is connected (MST has n-1 edges)
        return cost if edges_used == n - 1 else float('inf')
    
    original_cost = find_mst()
    critical, pseudo = [], []
    
    for i in range(len(edges)):
        # Test 1: Remove edge (critical test)
        cost_without = find_mst(skip_idx=i)
        if cost_without > original_cost:
            critical.append(edges[i][3])  # Original index
            continue
            
        # Test 2: Force include edge (pseudo-critical test)
        cost_with = find_mst(force_idx=i)
        if cost_with == original_cost:
            pseudo.append(edges[i][3])  # Original index
    
    return [critical, pseudo]
```

Complexity:
Time: 
given m as the number of edges
pushing original_index into edges takes O(n) time
sorting takes O(m log m) time
the DSU operation can be measured by a(n) where n is number of nodes (but we approximate it as O(1))

once n-1 edges being used, we stop the process of build the MST, so building process take O(N-1) time , and we build 2 MST for each edge , which is O(m * 2 * n-1) which is O(m^2), where n -1 is nearly m but smaller! 

**Total time complexity: O(m^2 * a(n))**

Space: 
DSU takes O(2n)  space where n is number of nodes
if the original edges count, 

**Space complexity: O(m + n)**

Tags: #kruskal #MST #sorting #disjoin_set_union 

RL: 

Considerations:
