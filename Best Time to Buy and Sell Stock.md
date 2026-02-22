2025-05-29 12:31

Link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/?envType=company&envId=optiver&favoriteSlug=optiver-all

Problem: 
You are given an array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.

Return _the maximum profit you can achieve from this transaction_. If you cannot achieve any profit, return `0`.

**Example 1:**
**Input:** prices = [7,1,5,3,6,4]
**Output:** 5
**Explanation:** Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 
**Example 2:**
**Input:** prices = [7,6,4,3,1]
**Output:** 0
**Explanation:** In this case, no transactions are done and the max profit = 0.

Intuition:
use stack to record the holding stock, we abandon the stock if we found a stock with lower price we can hold, then we maintain the lowest buying prices. we update profit if prices is higher than our buying prices
we can drop higher buying prices prematurely, since for any future occasions that we met higher prices, we would always yield a higher profit since we hold a lower buying prices

Solution:
```python
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        min_price = float("inf")
        max_profit = 0
        for i in range(len(prices)):
            if prices[i] < min_price:
                min_price = prices[i]
            elif prices[i] - min_price > max_profit:
                max_profit = prices[i] - min_price

        return max_profit
```

Complexity:
O(n)

Tags: #stack

RL: 

Considerations:
