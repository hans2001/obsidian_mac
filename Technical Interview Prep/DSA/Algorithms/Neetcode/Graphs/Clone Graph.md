2025-02-06 15:51

Link: https://neetcode.io/problems/clone-graph

Problem: 
Given a node in a connected undirected graph, return a deep copy of the graph.

Each node in the graph contains an integer value and a list of its neighbors.

```java
class Node {
    public int val;
    public List<Node> neighbors;
}
```
The graph is shown in the test cases as an adjacency list. **An adjacency list** is a mapping of nodes to lists, used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.

For simplicity, nodes values are numbered from 1 to `n`, where `n` is the total number of nodes in the graph. The index of each node within the adjacency list is the same as the node's value (1-indexed).

The input node will always be the first node in the graph and have `1` as the value.

Motivation:
DFS:
use a dictionary to map old node to new node. for each new node, we recursively build it neighbors, if its neighbors is not built!  otherwise the neighbor is immediately returned, if it is found in the dictionary! and append to the new node's neighbors list!

BFS:
create a new node for root first, then store it in hash map. use bfs to traverse all neighbors for each original node. if neighbor node is not created(not in hash map) ,create it immediately and put it into the hash map, then push the old neighbor node into queue to build its neighbors later! 
All the new node is now in the hash map, we get the graph by simply returning the head of the new node, which is dict[root]

Solution:
DFS: 
```python
class Solution:
    def cloneGraph(self, node: Optional['Node']) -> Optional['Node']:
        oldToNew = {}

        def dfs(node):
            if node in oldToNew:
                return oldToNew[node]

            copy = Node(node.val)
            oldToNew[node] = copy
            for nei in node.neighbors:
                copy.neighbors.append(dfs(nei))
            return copy

        return dfs(node) if node else None
```
O(V+E) / O(V)

BFS:
```python
class Solution:
    def cloneGraph(self, node: Optional['Node']) -> Optional['Node']:
        if not node:
            return None

        oldToNew = {}
        oldToNew[node] = Node(node.val)
        q = deque([node])

        while q:
            cur = q.popleft()
            for nei in cur.neighbors:
                if nei not in oldToNew:
                    oldToNew[nei] = Node(nei.val)
                    q.append(nei)
                oldToNew[cur].neighbors.append(oldToNew[nei])

        return oldToNew[node]
```
O(V+E) / O(V)

V+E: process a node, and iterate over its neighbors! 

Tags: #dfs #bfs #graph

RL: 