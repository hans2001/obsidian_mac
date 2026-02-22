2025-02-10 15:19

Link:https://neetcode.io/problems/coin-change

Problem: 
You are given an integer array `coins` representing coins of different denominations (e.g. 1 dollar, 5 dollars, etc) and an integer `amount` representing a target amount of money.

Return the fewest number of coins that you need to make up the _exact_ target amount. If it is impossible to make up the amount, return `-1`.

You may assume that you have an unlimited number of each coin.

Motivation:
dp[i] represent minimum number of coins required to reach amount i. For the bottom up approach, we build up the dp array from 0 to amount + 1 , where amount uses coin value + remaining amount to compute the required coins. we set initial value as infinity, then use min function to update dp[i]. If result is infinity, there is no reach ways to target amount! 
![[Screenshot 2025-02-10 at 4.35.08 PM.png]]

## 3. Analogy: “unbounded knapsack”
This is the same as unbounded knapsack (infinite supply of each item).
- In **0/1 knapsack**: if you take an item, you move on (`i+1`).
- In **unbounded knapsack**: if you take an item, you **stay** (still available).

Solution:
DFS + Memoized (top-down)
```python
class Solution:
    def coinChange(self, coins: List[int], amount: int) -> int:
        memo = {}
        def dfs(amount):
            if amount == 0:
                return 0
            if amount in memo:
                return memo[amount]
            
            res = 1e9
            for coin in coins:
                if amount - coin >= 0:
                    res = min(res, 1 + dfs(amount - coin))
            
            memo[amount] = res
            return res
        minCoins = dfs(amount)
        return -1 if minCoins >= 1e9 else minCoins
```
O(n * t) / O(t)

DP (bottom-up)
```python
class Solution:
    def coinChange(self, coins: List[int], amount: int) -> int:
        dp = [amount + 1] * (amount + 1)
        dp[0] = 0

        for a in range(1, amount + 1):
            for c in coins:
                if a - c >= 0:
                    dp[a] = min(dp[a], 1 + dp[a - c])
        return dp[amount] if dp[amount] != amount + 1 else -1
```
O(n * t) / O(t)
(t is given amount, n is length of array coins)

Tags: #dp #dfs #unbounded_knapsack #memoization 

RL: 