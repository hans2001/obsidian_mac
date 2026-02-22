2025-05-31 17:04

Link: https://neetcode.io/problems/count-paths?list=neetcode250

Problem: 
There is an `m x n` grid where you are allowed to move either down or to the right at any point in time.

Given the two integers `m` and `n`, return the number of possible unique paths that can be taken from the top-left corner of the grid (`grid[0][0]`) to the bottom-right corner (`grid[m - 1][n - 1]`).

You may assume the output will fit in a **32-bit** integer.

Intuition:
we can either go left or right, base case should identify we have reach the end
for bottom up approach, we use row to represent exactly rows and cols for cols in the gird, and the answer lies at the last row and last cell!

Solution:
top down
```python
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        memo = [[-1] * n for _ in range(m)]
        def dfs(i, j):
            if i == (m - 1) and j == (n - 1):
                return 1
            if i >= m or j >= n:
                return 0
            if memo[i][j] != -1:
                return memo[i][j]
            
            memo[i][j] =  dfs(i, j + 1) + dfs(i + 1, j)
            return memo[i][j]
        
        return dfs(0, 0)
```

bottom up
```python
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        dp = [[0] * (n + 1) for _ in range(m + 1)]
        dp[m - 1][n - 1] = 1

        for i in range(m - 1, -1, -1):
            for j in range(n - 1, -1, -1):
                dp[i][j] += dp[i + 1][j] + dp[i][j + 1]

        return dp[0][0]
```

optimal: 
```python
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        dp = [1] * n
        for i in range(m - 2, -1, -1):
            for j in range(n - 2, -1, -1):
                dp[j] += dp[j + 1]
                
        return dp[0]
```
This initializes the last row of our conceptual 2D grid. Why all 1's? Because from any cell in the bottom row, there's exactly one way to reach the destination (keep moving right).

Complexity: #2d_dp 

Tags: 

RL: 

Considerations:

Optimal solution
- `dp[j]` = paths from `(i+1, j)` (the cell below current position)
- `dp[j+1]` = paths from `(i, j+1)` (the cell to the right of current position)

The magic is that `dp[j] += dp[j+1]` transforms `dp[j]` from representing the bottom cell to representing the current cell!