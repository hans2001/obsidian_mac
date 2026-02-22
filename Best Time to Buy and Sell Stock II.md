2025-05-03 16:12

Link: https://neetcode.io/problems/best-time-to-buy-and-sell-stock-ii

Problem: 
You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

On each day, you may decide to buy and/or sell the stock. However, you can buy it then immediately sell it on the **same day**. Also, you are allowed to perform any number of transactions but can hold **at most one**share of the stock at any time.

Find and return the **maximum** profit you can achieve.

Intuition:
first, figure out all the branch require , we can only buy stock if we dont hold stock, and we can sell stock if we holding a stock ,so the hold position should be maintained! then we take the maximum among the 2 ,each memoized state can be the maximum profit on that day! so we can get get maximum profit at the final day! 

optimized: 
to reduce the overhead brought by the dict and recursion, we can use only 2 variable to maintain our position . the hold is the min buying price, and cash is maximum profit we can reach! 

Solution:
comprehensive: 
```python
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        n = len(prices)
        dp = {}

        def dfs(i, hold):
            if i >= n:
                return 0
            if (i, hold) in dp:
                return dp[(i, hold)]

            if not hold:
                holding = dfs(i + 1, False)
                buy = dfs(i + 1, True) - prices[i]
                dp[(i, hold)] = max(buy, holding)
            else:
                holding = dfs(i + 1, True)
                sell = dfs(i + 1, False) + prices[i]
                dp[(i, hold)] = max(sell, holding)

            return dp[(i, hold)]

        return dfs(0, False)
```

11optimized! 
```python
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        cash = 0                     # profit when not holding stock
        hold = -10**18               # profit when holding stock (start impossible)

        for p in prices:
            prev_cash = cash
            # Either keep not holding, or sell stock today
            cash = max(cash, hold + p)
            # Either keep holding, or buy stock today
            hold = max(hold, prev_cash - p)

        return cash
```

Complexity: O(n) / O(1)

Tags: #recursion  #greedy 1

RL: 

Considerations:
