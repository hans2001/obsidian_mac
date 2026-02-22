2025-04-10 13:54

Link:

Problem: 
just Dijkstra algorithm (for finding shortest path from starting node to any node )

Intuition:
initialize the distance array for all node to infinity, which mean the min weight from start node to current node is infinity( or unreachable) . we also maintain a previous list to store parent node of current node in the shortest path, then we can reconstruct the shortest path from any node to the starting node in a backtrack way
initialize a visit set to record nodes that has been processed! 
append the starting node to the queue
for every neighbor, if weight to neighbor node + weight to current node < weight from start node to neighbor node, we update neighbor node total weight, otherwise ignore! 

we combine dfs with priority queue, where heap will return the minimum edge for u s to explore the next neighbor? since order by weigh ,it should be the first key in the tuple
parameter graph should be a dictionary

Solution:
```python
import heapq
def dijkstra(graph, start_node):
    # Number of nodes in the graph
    num_nodes = len(graph)
    
    # Step 1: Initialize distances list with infinity for all nodes except start_node
    distances = [float('infinity')] * num_nodes
    distances[start_node] = 0
    
    # Step 3: Initialize visited set to track visited nodes
    visited = set()
    
    # Step 4: Initialize priority queue and add start_node
    # Using a min-heap priority queue where items are (distance, node)
    priority_queue = [(0, start_node)]
    
    # Step 5: Process nodes in order of minimum distance
    while priority_queue:
        # Get node with minimum distance from priority queue
        current_distance, current_node = heapq.heappop(priority_queue)
        
        # Skip if we've already processed this node
        if current_node in visited:
            continue
        
        # Mark current node as visited
        visited.add(current_node)
        
        # Process all neighbors of the current node
        for neighbor, cost in graph[current_node]:
            # Skip already visited neighbors
            if neighbor in visited:
                continue
            
            # Calculate distance to neighbor through current node
            distance_through_current = distances[current_node] + cost
            
            # If we found a shorter path to neighbor
            if distance_through_current < distances[neighbor]:
                # Update distance to neighbor
                distances[neighbor] = distance_through_current
                # Update previous node for neighbor
                previous[neighbor] = current_node
                # Add neighbor to priority queue
                heapq.heappush(priority_queue, (distance_through_current, neighbor))
    
    # Step 6: Return results
    return distances, previous
```

Tags: #dijkstra #heap 

RL: 

Considerations:
**Generally, since the distance in longest path problem increases as each iteration, you cannot apply dijkstra algorithm.**
(since dijkstra greedily computes the path, once we reach the end node, we can stop the search, since we ensure eachpath we built is the shortest. but in a longest path problem, once there is an additional edge that in another path builds to the end node, the solution might be completely different, that is why we the longest path problem is NP-hard)

however, we can solve longest path problem in DAG with topological sort, since the algo ensure correct processing sequence, every edge is relaxed and the weight is stored properly in the distance array! 

**Why the non-negative requirement matters**: When Dijkstra processes a node, it assumes we've found the shortest path to that node. With negative edges, this assumption breaks because a later path through a negative edge could yield an even shorter total distance.