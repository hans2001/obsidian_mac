2025-02-07 19:37

Link:https://neetcode.io/problems/valid-tree

Problem: 
Given `n` nodes labeled from `0` to `n - 1` and a list of **undirected** edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.

Motivation:
for a valid tree with n nodes, it must have at most n-1 edges, for it to not have cycles, and have at least n-1 edges to connect all the nodes. so for length of edges that is not n-1, we know that it is not a valid tree already. Second, for an undirected edges, we need to set up the connection in both ways! Therefore, we need to avoid revisiting parent during the traversal, but that does not necessarily mean a cycle! so bypassing the parent node prevent false cycle detection!

Solution:
DFS:
```python
class Solution:
    def validTree(self, n: int, edges: List[List[int]]) -> bool:
        if len(edges) > (n - 1):
            return False
        
        adj = [[] for _ in range(n)]
        for u, v in edges:
            adj[u].append(v)
            adj[v].append(u)
        
        visit = set()
        def dfs(node, par):
            if node in visit:
                return False
            
            visit.add(node)
            for nei in adj[node]:
                if nei == par:
                    continue
                if not dfs(nei, node):
                    return False
            return True
        
        return dfs(0, -1) and len(visit) == n
```

Tags: #dfs #valid_tree #union_set

RL: 