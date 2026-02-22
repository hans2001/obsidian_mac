2025-07-07 16:15

Link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/description/?envType=company&envId=optiver&favoriteSlug=optiver-all

Problem: 
You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `ith` day, and an integer `k`.

Find the maximum profit you can achieve. You may complete at most `k` transactions: i.e. you may buy at most `k` times and sell at most `k` times.

**Note:** You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

**Example 1:**

**Input:** k = 2, prices = [2,4,1]
**Output:** 2
**Explanation:** Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.

**Example 2:**

**Input:** k = 2, prices = [3,2,6,5,0,3]
**Output:** 7
**Explanation:** Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.

**Constraints:**

- `1 <= k <= 100`
- `1 <= prices.length <= 1000`
- `0 <= prices[i] <= 1000`

Intuition:
similar to buy stock with cooldown, we can either buy the stock or not at each state, make sure to keep track of number of transactions we have process , we should prune the path if all transactions is used! without the cool down requirement,  we can buy immediately the next day without hesitation! for 2^n number of possibilities, we can reduce it to O(n) with memoization! for n is the length of the prices!

Solution:
```python
class Solution(object):
    def maxProfit(self, k, prices):
        dp = {}
        
        def dfs(i, canBuy, transactions_left):
            # Base cases
            if i >= len(prices) or transactions_left == 0:
                return 0
            
            if (i, canBuy, transactions_left) in dp:
                return dp[(i, canBuy, transactions_left)]
            
            # Option 1: Do nothing (skip this day)
            skip = dfs(i + 1, canBuy, transactions_left)
            
            if canBuy:
                # Option 2: Buy stock
                buy = dfs(i + 1, False, transactions_left) - prices[i]
                dp[(i, canBuy, transactions_left)] = max(buy, skip)
            else:
                # Option 2: Sell stock (completes one transaction)
                sell = dfs(i + 1, True, transactions_left - 1) + prices[i]
                dp[(i, canBuy, transactions_left)] = max(sell, skip)
            
            return dp[(i, canBuy, transactions_left)]
        
        return dfs(0, True, k)
```

Tags: #dp #bounded_knapsack 

RL: [[Best Time to Buy and Sell Stock With Cooldown]]

Considerations:
