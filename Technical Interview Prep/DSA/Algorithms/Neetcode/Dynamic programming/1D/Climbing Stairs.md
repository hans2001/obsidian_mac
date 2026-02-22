2025-02-09 14:16

Link: https://neetcode.io/problems/climbing-stairs

Problem: 
You are given an integer `n` representing the number of steps to reach the top of a staircase. You can climb with either `1` or `2` steps at a time.

Return the number of distinct ways to climb to the top of the staircase.

Motivation:
Since number of ways to climb to current level is either by 1 or 2 steps, we notice a dependency on previous result by either 1 to 2 steps. 
Similar to the #fibonacci_sequence  f(n) = f(n-1) + f(n-2), the ways to reach current level depend on ways to reach previous 2 level, since both of them can reach current level, by taking either 1 or 2 step! 
Recurrence: F(n) = F(n-1) +F(n-2)

Solution:
DFS: 
```python
class Solution:
    def climbStairs(self, n: int) -> int:
        def dfs(i):
            if i >= n:
                return i == n
            return dfs(i + 1) + dfs(i + 2)
        return dfs(0)
```
O(2^n) / O(n) (n recursion stack)

Memoization + DFS
```python
class Solution:
    def climbStairs(self, n: int) -> int:
        cache = [-1] * n
        def dfs(i):
            if i >= n:
                return i == n
            if cache[i] != -1:
                return cache[i]
            cache[i] = dfs(i + 1) + dfs(i + 2)
            return cache[i]
        return dfs(0)
```
O(n) / O(n)

Bottom-up DP:
```python
class Solution:
    def climbStairs(self, n: int) -> int:
        if n <= 2:
            return n
        dp = [0] * (n + 1)
        dp[1], dp[2] = 1, 2
        for i in range(3, n + 1):
            dp[i] = dp[i - 1] + dp[i - 2]
        return dp[n]
```
O(n) / O(n)

DP space optimized:
```python
class Solution:
    def climbStairs(self, n: int) -> int:
        one, two = 1, 1
        for i in range(n - 1):
            temp = one
            one = one + two
            two = temp
        
        return one
```
O(n) / O(1)

Tags: #dfs #1d_dp #memoization #fibonacci_sequence 

RL: [[Min Cost Climbing Stairs]]