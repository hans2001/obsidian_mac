2025-05-31 17:04

Link: https://neetcode.io/problems/unique-paths-ii?list=neetcode250

Problem: 
You are given an `m x n` integer array `grid`. There is a robot initially located at the **top-left** corner (i.e., `grid[0][0]`). The robot tries to move to the **bottom-right** corner (i.e., `grid[m - 1][n - 1]`). The robot can only move either down or right at any point in time.

An obstacle and space are marked as `1` or `0` respectively in `grid`. A path that the robot takes cannot include any square that is an obstacle.

Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

The test cases are generated so that the answer will be less than or equal to `2 * (10^9)`.

Intuition:
Similar to [[Unique Path]] , but path that led to obstacle should be terminated, since there are no paths possible from obstacle that reach the destination, so the number of paths it contribute should be 0.

Solution:
bottom up
```python
class Solution:
    def uniquePathsWithObstacles(self, grid: List[List[int]]) -> int:
        M, N = len(grid), len(grid[0])
        if grid[0][0] == 1 or grid[M - 1][N - 1] == 1:
            return 0
        dp = [[0] * (N + 1) for _ in range(M + 1)]


        dp[M - 1][N - 1] = 1

        for r in range(M - 1, -1, -1):
            for c in range(N - 1, -1, -1):
                if grid[r][c] == 1:
                    dp[r][c] = 0
                else:
                    dp[r][c] += dp[r + 1][c]
                    dp[r][c] += dp[r][c + 1]

        return dp[0][0]
```

optimal:
```python
class Solution:
    def uniquePathsWithObstacles(self, grid: List[List[int]]) -> int:
        M, N = len(grid), len(grid[0])
        dp = [0] * (N + 1)
        dp[N - 1] = 1

        for r in range(M - 1, -1, -1):
            for c in range(N - 1, -1, -1):
                if grid[r][c]:
                    dp[c] = 0
                else:
                    dp[c] += dp[c + 1]
        
        return dp[0]
```

Complexity:
Time : O(m * n)

Space: O(n)

Tags: #2d_dp 

RL: [[Unique Path]]

Considerations:

Setting `dp[i][j] = 0` for obstacles ensures that:

1. No paths are counted as passing through the obstacle
2. Adjacent cells only count valid (non-obstacle) paths in their calculations
3. The final answer automatically excludes all blocked routes