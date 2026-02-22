2025-05-18 11:01

Link: https://leetcode.com/problems/path-with-minimum-effort/

Problem: 
You are a hiker preparing for an upcoming hike. You are given `heights`, a 2D array of size `rows x columns`, where `heights[row][col]` represents the height of cell `(row, col)`. You are situated in the top-left cell, `(0, 0)`, and you hope to travel to the bottom-right cell, `(rows-1, columns-1)` (i.e., **0-indexed**). You can move **up**, **down**, **left**, or **right**, and you wish to find a route that requires the minimum **effort**.

A route's **effort** is the **maximum absolute difference** in heights between two consecutive cells of the route.

Return _the minimum **effort** required to travel from the top-left cell to the bottom-right cell._

Intuition:
for dijkstra, we always initialized an array that tracks the weight form source node to any node, and the initial value is infinity! the root node would be the source node, and as we explore, we neighbor node with corresponding weight into the priority queue , this ensure we process the path that takes the least weight first, which is the fundamental of Dijkstra! 
Additionally ,we could implement pruning when current weight so far exceed the weight recorded in the distance array, but it is most likely not needed if we early return the result once we reach the destination! 

Solution:
pseudo code :(Dijkstra) 
```
function MinimumEffortPath(grid):
    rows = grid.length
    cols = grid[0].length
    
    // Initialize efforts array
    effort = new Array(rows, cols)
    for each cell (i, j) in grid:
        effort[i][j] = INFINITY
    effort[0][0] = 0
    
    // Priority queue: (effort, row, col)
    pq = new PriorityQueue()
    pq.insert((0, 0, 0))  // (effort, row, col)
    
    // Directions for adjacent cells
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    while pq is not empty:
        (currentEffort, r, c) = pq.extractMin()
        
        // If reached destination
        if r == rows - 1 and c == cols - 1:
            return currentEffort
            
        // Skip if we've found a better path already
        if currentEffort > effort[r][c]:
            continue
            
        // Check all adjacent cells
        for (dr, dc) in directions:
            newR = r + dr
            newC = c + dc
            
            // Check if valid cell
            if 0 <= newR < rows and 0 <= newC < cols:
                // Calculate new effort
                newEffort = max(currentEffort, abs(grid[r][c] - grid[newR][newC]))
                
                // If we found a path with less effort
                if newEffort < effort[newR][newC]:
                    effort[newR][newC] = newEffort
                    pq.insert((newEffort, newR, newC))
    
    return effort[rows-1][cols-1]
```

Complexity:
m is number of rows in heights, n is number of columns! 
Time: 
O(m*n*log(mn)) in worst case we push same cell into the queue multiple times for a dense graph, for a heaps size of m * n, it will take log(m * n) to heapify the queue after new cell is pushed into the queue! which gives us the total time complexity! 

Space: 
O(mn) where we track the distance array from source node to any node! 

Tags: #dijkstra 

RL: 

Considerations:
