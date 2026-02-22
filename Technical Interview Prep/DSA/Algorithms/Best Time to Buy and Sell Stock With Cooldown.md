2025-06-05 19:55

Link:https://neetcode.io/problems/buy-and-sell-crypto-with-cooldown?list=neetcode250

Problem: 
You are given an integer array `prices` where `prices[i]` is the price of NeetCoin on the `ith` day.

You may buy and sell one NeetCoin multiple times with the following restrictions:

- After you sell your NeetCoin, you cannot buy another one on the next day (i.e., there is a cooldown period of one day).
- You may only own at most one NeetCoin at a time.

You may complete as many transactions as you like.
Return the **maximum profit** you can achieve. 

**Example 1:**
```java
Input: prices = [1,3,4,0,4]

Output: 6
```

Explanation: Buy on day 0 (price = 1) and sell on day 1 (price = 3), profit = 3-1 = 2. Then buy on day 3 (price = 0) and sell on day 4 (price = 4), profit = 4-0 = 4. Total profit is 2 + 4 = 6.

**Example 2:**
```java
Input: prices = [1]

Output: 0
```

Intuition:
this is a unbounded knapsack problem, however a additional buy state affects our action in each recursive call. while in buy state, we can choose to either buy this coin or not, this created 2 branch and we will choose the branch that provide the maximum amount of profit. same for the sold state, we can choose to either sold this coin today or not. we use the coin price when we actually bought or sold the coin, and we memoized the computation for duplicate queries! 

Solution:
top down
```python
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        dp = {}  # key=(i, buying) val=max_profit

        def dfs(i, buying):
            if i >= len(prices):
                return 0
            if (i, buying) in dp:
                return dp[(i, buying)]

            cooldown = dfs(i + 1, buying)
            if buying:
                buy = dfs(i + 1, not buying) - prices[i]
                dp[(i, buying)] = max(buy, cooldown)
            else:
                sell = dfs(i + 2, not buying) + prices[i]
                dp[(i, buying)] = max(sell, cooldown)
            return dp[(i, buying)]

        return dfs(0, True)
```

bottom up
```python
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        n = len(prices)
        dp = [[0] * 2 for _ in range(n + 1)]  

        for i in range(n - 1, -1, -1):
            for buying in [True, False]:
                if buying:
                    buy = dp[i + 1][False] - prices[i] if i + 1 < n else -prices[i]
                    cooldown = dp[i + 1][True] if i + 1 < n else 0
                    dp[i][1] = max(buy, cooldown)
                else:
                    sell = dp[i + 2][True] + prices[i] if i + 2 < n else prices[i]
                    cooldown = dp[i + 1][False] if i + 1 < n else 0
                    dp[i][0] = max(sell, cooldown)

        return dp[0][1]
```

Complexity:

Tags: #unbounded_knapsack #2d_dp 

RL: 

Considerations:
