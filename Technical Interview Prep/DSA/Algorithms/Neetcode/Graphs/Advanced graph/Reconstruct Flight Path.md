2025-02-16 15:41

Link: https://neetcode.io/problems/reconstruct-flight-path

Problem: 
You are given a list of flight tickets `tickets` where `tickets[i] = [from_i, to_i]` represent the source airport and the destination airport. 
Each `from_i` and `to_i` consists of three uppercase English letters.
Reconstruct the itinerary in order and return it.

All of the tickets belong to someone who originally departed from `"JFK"`. Your objective is to reconstruct the flight path that this person took, assuming each ticket was used exactly once.

If there are multiple valid flight paths, return the lexicographically smallest one.
- For example, the itinerary `["JFK", "SEA"]` has a smaller lexical order than `["JFK", "SFO"]`.

You may assume all the tickets form at least one valid flight path.

Motivation:
DFS:
Sort the tickets first by lexicographic order, then we build the mapping from the tickets and run dfs from the source node! to prevent end up in node without outgoing edges, we implement backtracking to take an alternative route if such case happened!
base case: when length of the response == len(tickets) +1 (an element in response mean we have exhaust one edge) ,we know we reach all node when node is not in the dictionary : node has no outgoing edges (while terminate case not reached!)

Solution:
**DFS**
```python
class Solution:
    def findItinerary(self, tickets: List[List[str]]) -> List[str]:
        adj = {src: [] for src, dst in tickets}
        tickets.sort()
        for src, dst in tickets:
            adj[src].append(dst)

        res = ["JFK"]
        def dfs(src):
            if len(res) == len(tickets) + 1:
                return True
            if src not in adj:
                return False

            temp = list(adj[src])
            for i, v in enumerate(temp):
                adj[src].pop(i)
                res.append(v)
                if dfs(v): return True
                adj[src].insert(i, v)
                res.pop()
            return False
            
        dfs("JFK")
        return res
```
O(E∗V) / O(E)
worst case: we have to explore E^2 number of edges (backtracking) (undirected)
E dominate number of vertices

**Hierholzer Algo**
```python
class Solution:
    def findItinerary(self, tickets: List[List[str]]) -> List[str]:
        adj = defaultdict(list)
        for src, dst in sorted(tickets)[::-1]:
            adj[src].append(dst)

        res = []
        def dfs(src):
            while adj[src]:
                dst = adj[src].pop()
                dfs(dst)
            res.append(src)
            
        dfs('JFK')
        return res[::-1]
```
sorting takes O(ElogE) where E is number of edges, dfs takes O(E) time where each edge is processed one, removal operation takes constant time, reversal operation takes O(V) time where V is number of vertices, which is essentially O(E)

Space complexity :
1. **Adjacency List:**
    - Uses O(E) space where E is the number of tickets.
2. **Recursion Stack:**
    - In the worst-case, the recursion depth could be O(E).
3. **Result List (`res`):**
    - This holds O(E) elements in the worst-case (since it stores the itinerary of length E+1).

Tags: #dfs #lexicographic #graph #hierholzer #eulerian_path 

RL: 

Considerations:
