2025-02-06 17:33

Link: https://neetcode.io/problems/islands-and-treasure

Problem: 
You are given a m×nm×n 2D `grid` initialized with these three possible values:

1. `-1` - A water cell that _can not_ be traversed.
2. `0` - A treasure chest.
3. `INF` - A land cell that _can_ be traversed. We use the integer `2^31 - 1 = 2147483647` to represent `INF`.

Fill each land cell with the distance to its nearest treasure chest. If a land cell cannot reach a treasure chest than the value should remain `INF`.

Assume the grid can only be traversed up, down, left, or right.
Modify the `grid` **in-place**.

Motivation:
brute force would be run bfs from each cell and return when we reach a chest( shortest path wit unweighted graph). However, the time complexity would be(m * n) ^ 2. Instead, we could consider multi source BFS, where we run BFS from the chest simultaneously, and update grid cell that is valid and not visited yet, until all valid grid cell has been modified! 

Solution:
```python
class Solution:
    def islandsAndTreasure(self, grid: List[List[int]]) -> None:
        ROWS, COLS = len(grid), len(grid[0])
        visit = set()
        q = deque()

        def addCell(r, c):
            if (min(r, c) < 0 or r == ROWS or c == COLS or
                (r, c) in visit or grid[r][c] == -1
            ):
                return
            visit.add((r, c))
            q.append([r, c])

        for r in range(ROWS):
            for c in range(COLS):
                if grid[r][c] == 0:
                    q.append([r, c])
                    visit.add((r, c))

        dist = 0
        while q:
            for i in range(len(q)):
                r, c = q.popleft()
                grid[r][c] = dist
                addCell(r + 1, c)
                addCell(r - 1, c)
                addCell(r, c + 1)
                addCell(r, c - 1)
            dist += 1
```
O(m * n) / O(m * n)
(m is number of rows, and n is number of columns in the grid)

Tags: #multi_source_bfs #walls_and_gates #amazon

RL: 