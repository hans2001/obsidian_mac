2025-03-14 14:00

Link:

Problem: 
![[Screenshot 2025-03-14 at 2.00.58 PM.png]]
Intuition:
build a reverse graph and start exploring from the terminal node! then as we iterate through the reverse path, we noted node as safe if its reverse outgoing path is reduced to zero.
For example, if a node A has two outgoing edges to nodes B and C, neither B nor C has to be terminal right away. If both B and C eventually become safe (i.e., all their outgoing paths have been pruned), then A' s outdegree will eventually reduce to zero, and A will be marked safe.

So, the safety of AA is ensured as long as every path starting from AA leads—directly or indirectly—to a terminal state.

Solution:
```python 
def eventual_safe_nodes(graph):
    from collections import deque, defaultdict
    
    n = len(graph)
    # Build reverse graph and outdegree count for each node.
    reverse_graph = defaultdict(list)
    outdegree = [0] * n
    for u, neighbors in enumerate(graph):
        outdegree[u] = len(neighbors)
        for v in neighbors:
            reverse_graph[v].append(u)
    
    # Start with terminal nodes (outdegree 0).
    queue = deque([i for i in range(n) if outdegree[i] == 0])
    safe = [False] * n
    
    while queue:
        node = queue.popleft()
        safe[node] = True
        # For each predecessor in the reverse graph:
        for pred in reverse_graph[node]:
            outdegree[pred] -= 1
            if outdegree[pred] == 0:
                queue.append(pred)
    
    # The safe nodes are those that are marked True.
    return [i for i, is_safe in enumerate(safe) if is_safe]
```

Tags: #graph #bfs #out_degree 

RL: 

Considerations:
