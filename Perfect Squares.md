2025-05-28 17:39

Link: https://neetcode.io/problems/perfect-squares?list=neetcode250

Problem: 
You are given an integer `n`, return the least number of perfect square numbers that sum to `n`.

A **perfect square** is an integer that is the square of an integer. For example, 1, 4, 9, 16, 25... are perfect squares.

**Example 1:**
```java
Input: n = 13

Output: 2
```
Explanation: 13 = 4 + 9.

**Example 2:**
```java
Input: n = 6

Output: 3
```
Explanation: 6 = 4 + 1 + 1.

Intuition:
this is a unbounded knapsack problem, where each perfect squares can be used multiples times, to reach the amount n. so we can first find out the number available number to select, then inherit the idea from coin change, by selecting the minimum amount of squares to achieve the target! 

Solution:
top down dp: 
```python
class Solution:
    def numSquares(self, n: int) -> int:
        memo = {}

        def dfs(target):
            if target == 0:
                return 0
            if target in memo:
                return memo[target]

            res = target
            for i in range(1, target + 1):
                if i * i > target:
                    break
                res = min(res, 1 + dfs(target - i * i))

            memo[target] = res
            return res
        
        return dfs(n)
```

bottom up: 
```python
class Solution:
    def numSquares(self, n: int) -> int:
        dp = [n] * (n + 1)
        dp[0] = 0
        
        for target in range(1, n + 1):
            for s in range(1, target + 1):
                square = s * s
                if target - square < 0:
                    break
                dp[target] = min(dp[target], 1 + dp[target - square])
        
        return dp[n]
```

Complexity:
Time: 
O(n * sqrt(n))
for each recursion stack, we try up to sqrt(n) numbers (number of perfect squares), since we stop once i * i >= target! and we have n unique subproblems, and for each one we loop over sqrt (n) amount of numbers

Space: 
O(n) memoization table and recursion stack
O(sqrt(n)) number of perfect squares

Tags: #unbounded_knapsack 

RL: [[Coin Change]]

Considerations:
