2025-04-10 11:55

Link:

Problem: 
perform topological sort in a recursive way

Intuition:
we should maintain a visited and visiting set, where visited set record node that has been processed, and visiting set record the visited node in the current stack ,so to detect cycle when neighbor of next node being previous node! 

Since every node will be processed, we can start from a random node, and the recursive property of the algorithm will ensure us that ancestors will appear before the descendant in the result list, so starting form any node will result in a valid topological order! 

Solution:
```python
def topological_sort_dfs(graph):
    visited = set()    # Nodes that are completely processed
    visiting = set()   # Nodes currently in the recursion stack (helps detect cycles)
    result = []        # Stores the topological order
    
    def dfs(node):
        print(f"\nProcessing node: {node}")
        print(f"  visiting before: {visiting}")
        print(f"  visited before: {visited}")
        
        # Check for cycles
        if node in visiting:
            raise ValueError(f"Cycle detected involving node {node}")
            
        # Skip if already visited
        if node in visited:
            print(f"  Node {node} already visited, skipping")
            return
            
        # Mark as being visited (add to recursion stack)
        visiting.add(node)
        print(f"  Added {node} to visiting: {visiting}")
        
        # Visit all neighbors recursively
        if node in graph:  # Check if node has any neighbors
            print(f"  Neighbors of {node}: {graph[node]}")
            for neighbor in graph[node]:
                print(f"  Processing neighbor: {neighbor} of node {node}")
                dfs(neighbor)
        else:
            print(f"  Node {node} has no neighbors")
        
        # After all neighbors are processed:
        # 1. Mark node as fully visited
        # 2. Remove from recursion stack
        # 3. Add to result (at the beginning for correct order)
        visited.add(node)
        visiting.remove(node)
        result.insert(0, node)
        
        print(f"  All neighbors of {node} processed")
        print(f"  visiting after: {visiting}")
        print(f"  visited after: {visited}")
        print(f"  result now: {result}")
    
    # Start DFS from each unvisited node
    for node in graph:
        if node not in visited:
            print(f"\nStarting new DFS from node {node}")
            dfs(node)
            
    return result

# Example graph
graph = {
    'A': ['B', 'C'],
    'B': ['D'],
    'C': ['E'],
    'D': [],
    'E': []
}

print("Graph:", graph)
print("\nTracing DFS Topological Sort:")
result = topological_sort_dfs(graph)
print("\nFinal Topological Order:", result)
```

Tags: #topological_sort 

RL: 

Considerations:
