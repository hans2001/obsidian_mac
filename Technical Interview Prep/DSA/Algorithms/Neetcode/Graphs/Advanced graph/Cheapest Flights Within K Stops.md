2025-02-16 12:36

Link: https://neetcode.io/problems/gcheapest-flight-path

Problem: 
There are `n` airports, labeled from `0` to `n - 1`, which are connected by some flights. You are given an array `flights` where `flights[i] = [from_i, to_i, price_i]` represents a one-way flight from airport `from_i` to airport `to_i` with cost `price_i`. You may assume there are no duplicate flights and no flights from an airport to itself.

You are also given three integers `src`, `dst`, and `k` where:

- `src` is the starting airport
- `dst` is the destination airport
- `src != dst`
- `k` is the maximum number of stops you can make (not including `src` and `dst`)

Return **the cheapest price** from `src` to `dst` with at most `k` stops, or return `-1` if it is impossible.

Motivation:
despite finding min cost to reach destination, we have an additional requirement of maximum k intermediate stops. Bellman ford algo is more suitable in this case, where we update the 2d table of costs to destination along the way. if previous node is reachable ,we update the cost for current node by previous_cost + cost_to_current, otherwise, we don't update. make sure we modify the copied prices list first, to ensure our result is based on previous iteration, instead of the current one(partial results!)

Solution:
Bellman_ford
```python
class Solution:
    def findCheapestPrice(self, n: int, flights: List[List[int]], src: int, dst: int, k: int) -> int:
        prices = [float("inf")] * n
        prices[src] = 0

        for i in range(k + 1):
            tmpPrices = prices.copy()

            for s, d, p in flights:  # s=source, d=dest, p=price
                if prices[s] == float("inf"):
                    continue
                if prices[s] + p < tmpPrices[d]:
                    tmpPrices[d] = prices[s] + p
            prices = tmpPrices
        return -1 if prices[dst] == float("inf") else prices[dst]
```
O(n+(m∗k)) / O(n) -> prices array of size n
n is number of cities / m is number of flights and k is number of stops! 
m * k, for each bfs layer (ith loop), we iterate all flights and check if the prices array can be updated, with the new reachable node! 
if prices[s]  is not  infinity, the node is reachable at previous lp! create the copy of the prices array take O(n) time in each lp

Dijkstra: 
```python
class Solution:
    def findCheapestPrice(self, n: int, flights: List[List[int]], src: int, dst: int, k: int) -> int:
        INF = float("inf")
        adj = [[] for _ in range(n)]
        dist = [[INF] * (k + 5) for _ in range(n)]
        for u, v, cst in flights:
            adj[u].append([v, cst])
        
        dist[src][0] = 0
        minHeap = [(0, src, -1)] # cost, node, stops
        while len(minHeap):
            cst, node, stops = heapq.heappop(minHeap)
            if dst == node: return cst
            if stops == k or dist[node][stops + 1] < cst:
                continue
            for nei, w in adj[node]:
                nextCst = cst + w
                nextStops = 1 + stops
                if dist[nei][nextStops + 1] > nextCst:
                    dist[nei][nextStops + 1] = nextCst
                    heapq.heappush(minHeap, (nextCst, nei, nextStops))
        return -1
```
O(m * k * log(n * k)) / O(n * k)
number of states:
each state is a pair (node, stops),worst case each node is visited with up to k+1 different stop counts!

Relaxations: 
worst case, each edge(m) has to be relaxed for each stop count(k) -> O(m * k)
heap operations take log(n * k) -> heap size of (n * k) (heap operation cost per relaxation)
total cost -> O(m* k * log(n* k))

Tags: #bellman_ford #dijkstra #graph

Considerations:
**Why did you use `k + 2` when allocating the `dist` table, and can you think of a more precise way to allocate this table?**

**Answer:**  
The use of `k + 2` is a safety margin to ensure that the table is large enough to avoid index errors when you access `dist[node][stops + 1]`. However, if we reason about the actual requirement:

- You start with `stops = -1` in the heap, meaning that after one flight, `stops` becomes `0`.
- In the worst-case scenario, if you are allowed up to `k` stops, you are effectively taking k + 1 flights (because stops count intermediate stops between flights).

Thus, you only need to store costs for states from `0` (or after adjusting for the starting value) up to `k+1` flights.

A more precise allocation would be to create a table with `k + 2` columns, which covers indices `0` through `k+1`.