2025-07-17 15:35

Link:

Problem: 
You are given an undirected connected graph with n nodes labeled from 0 to n - 1 and a 2D integer array edges where edges[i] = [ui, vi, wi] denotes an undirected edge between node ui and node vi with weight wi, and an integer k.

You are allowed to remove any number of edges from the graph such that the resulting graph has at most k connected components.

The cost of a component is defined as the maximum edge weight in that component. If a component has no edges, its cost is 0.

Return the minimum possible value of the maximum cost among all components after such removals.

Constraints:
1 <= n <= 5 * 104
0 <= edges.length <= 105
edges[i].length == 3
0 <= ui, vi < n
1 <= wi <= 106
1 <= k <= n
The input graph is connected

Intuition:

Solution:
```python
class DSU:
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank = [0] * n

    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]

    def union(self, x, y):
        px, py = self.find(x), self.find(y)
        if px == py:
            return False  # Already connected!

        if self.rank[px] < self.rank[py]:
            px, py = py, px
        self.parent[py] = px
        if self.rank[px] == self.rank[py]:
            self.rank[px] += 1
        return True

class Solution:
    def minCost(self, n: int, edges: List[List[int]], k: int) -> int:
        if not edges or n == k: 
            return 0
        edges. sort ( key = lambda x : x[ 2] ) 
        dsu = DSU( n )
        used_edges = 0
        tobeused = n-k
        for u,v,weight in edges: 
            if dsu.union ( u,v): 
                used_edges += 1
                if used_edges == tobeused: 
                    return weight
        # otherwise, return the max edge weight
        return edges[-1][2]©leetcode
```

Tags: #minimum_spanning_tree #binary_search #disjoin_set_union #weekly_contest_458 #q2

RL: 

Considerations:
