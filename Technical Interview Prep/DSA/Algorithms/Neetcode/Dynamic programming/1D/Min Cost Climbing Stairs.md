2025-02-09 14:44

Link:https://neetcode.io/problems/min-cost-climbing-stairs

Problem: 
You are given an array of integers `cost` where `cost[i]` is the cost of taking a step from the `ith` floor of a staircase. After paying the cost, you can step to either the `(i + 1)th` floor or the `(i + 2)th` floor.

You may choose to start at the index `0` or the index `1` floor.

Return the minimum cost to reach the top of the staircase, i.e. just past the last index in `cost`.

Motivation:
we start from cost 0, we should memoized the cost to reach level i, so for level ,we know the minimum value to reach the top of the staircase

Solution:
Memo + DFS:
```python
class Solution:
    def minCostClimbingStairs(self, cost: List[int]) -> int:
        memo = [-1] * len(cost)
        
        def dfs(i):
            if i >= len(cost):
                return 0
            if memo[i] != -1:
                return memo[i]
            memo[i] = cost[i] + min(dfs(i + 1), dfs(i + 2))
            return memo[i]
        
        return min(dfs(0), dfs(1))
```
O(N) / O(N)

DP(bottom-up)
```python
class Solution:
    def minCostClimbingStairs(self, cost: List[int]) -> int:
        n = len(cost)
        dp = [0] * (n + 1)
        for i in range(2, n + 1):
            dp[i] = min(dp[i - 1] + cost[i - 1],
                        dp[i - 2] + cost[i - 2])
        return dp[n]
```
O(N) / O(N)

DP(top-bottom) + space optimized
```python
class Solution:
    def minCostClimbingStairs(self, cost: List[int]) -> int:
        for i in range(len(cost) - 3, -1, -1):
            cost[i] += min(cost[i + 1], cost[i + 2])
        return min(cost[0], cost[1])
```
O(N) / O(1)

Tags: #dp #dfs

RL: [[Climbing Stairs]]