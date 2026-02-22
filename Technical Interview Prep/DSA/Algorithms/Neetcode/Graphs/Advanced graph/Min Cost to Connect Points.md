2025-02-15 20:41

Link: https://neetcode.io/problems/min-cost-to-connect-points

Problem: 
You are given a 2-D integer array `points`, where `points[i] = [xi, yi]`. Each `points[i]`represents a distinct point on a 2-D plane.

The cost of connecting two points `[xi, yi]` and `[xj, yj]` is the **manhattan distance** between the two points, i.e. `|xi - xj| + |yi - yj|`.

Return the minimum cost to connect all points together, such that there exists exactly one path between each pair of points.

Motivation:
the min cost to connect all points together turns all to be the  minimum spanning tree. 
Prim:
a node-first relaxation ,starting form the source node ,we relax neighboring edges for the source node, and push all of them into the priority queue. as the nature of heap ,the min edge will be relaxed first, and we reach a new vertex though the relaxed edge! 
important!: we use a hash set to keep track of the node visited, which make sure we dont relax an edge that visited the node we visited before! 
Condition: as all node has been visited, the process can be stopped

Kruskal: 
we build out all edges in an array, and sort them by the weight. Then, we relax edges in ascending order, and skips the edge that will create cycles with the help of disjoint union set. after all edges has been processed, we should have only processed the minimum relaxation edges , which mean the final cost would be the weight to connect the MST! 

Solution:
Prims' Algo
```python
class Solution:
    def minCostConnectPoints(self, points: List[List[int]]) -> int:
        N = len(points)
        adj = {i: [] for i in range(N)}
        for i in range(N):
            x1, y1 = points[i]
            for j in range(i + 1, N):
                x2, y2 = points[j]
                dist = abs(x1 - x2) + abs(y1 - y2)
                adj[i].append([dist, j])
                adj[j].append([dist, i])

        res = 0
        visit = set()
        minH = [[0, 0]]
        while len(visit) < N:
            cost, i = heapq.heappop(minH)
            if i in visit:
                continue
            res += cost
            visit.add(i)
            for neiCost, nei in adj[i]:
                if nei not in visit:
                    heapq.heappush(minH, [neiCost, nei])
        return res
```
Time / Space:  O(n^2 log⁡n) / O(n^2) (the adj dictionary)

Graph construction takes O(n^2) time, and push, pop operation from heap takes O(log n^2) time. (heap of size n^2)

Assume we have n node, each node has potential n adjacent edges, worst case we perform heappush for the n edges (none of the node is visited), that is how we got the time O(n^2 log n) 

Kruskal :
```python
class DSU:
    def __init__(self, n):
        self.Parent = list(range(n + 1))
        self.Size = [1] * (n + 1)

    def find(self, node):
        if self.Parent[node] != node:
            self.Parent[node] = self.find(self.Parent[node])
        return self.Parent[node]

    def union(self, u, v):
        pu = self.find(u)
        pv = self.find(v)
        if pu == pv:
            return False
        if self.Size[pu] < self.Size[pv]:
            pu, pv = pv, pu
        self.Size[pu] += self.Size[pv]
        self.Parent[pv] = pu
        return True

class Solution:
    def minCostConnectPoints(self, points: List[List[int]]) -> int:
        n = len(points)
        dsu = DSU(n)
        edges = []
        for i in range(n):
            x1, y1 = points[i]
            for j in range(i + 1, n):
                x2, y2 = points[j]
                dist = abs(x1 - x2) + abs(y1 - y2)
                edges.append((dist, i, j))
        
        edges.sort()
        res = 0
        for dist, u, v in edges:
            if dsu.union(u, v):
                res += dist
        return res
```

Tags: #prims #min-heap #dict #manhattan_distance #graph #kruskal #greedy 

RL: 