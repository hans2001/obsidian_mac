2025-07-10 14:50

Link:

## Problem: 
An arbitrage is the simultaneous purchase and sale of an asset in order to profit from a difference in the price. This type of trade exploits price differences between similar or identical financial instruments, either on different markets or in different forms.

You are a currency trader looking for arbitrage opportunities in the currency market using these three quotes:

1. The cost of USD per EUR (USD/EUR) for converting USD to EUR.
2. The cost of EUR per GBP (EUR/GBP) for converting EUR to GBP.
3. The cost of GBP per USD (GBP/USD) for converting GBP to USD.

You must use your USD to buy EUR, then use your EUR to buy GBP, and finally use your GBP to buy USD, resulting in some sort of profit or loss (i.e., USD to EUR to GBP to USD). Reverse trading is not allowed, so you are limited to the 3 exchanges in the direction shown above. For example, you can convert USD to EUR, which means selling US Dollars and buying Euros; you cannot invert the fraction to sell Euros and buy US Dollars.

Given 100,000 USD for each trade, calculate the arbitrage profit truncated to whole dollars (USD); otherwise, print 0 if there is no arbitrage opportunity.

**Input Format**

The locked stub code in your editor reads the following input from stdin and passes it to your function: The first line contains a single integer, N, denoting the number of quotes.

Each of the N subsequent lines describes a quote in the form of three space-separated floating-point numbers:

1. The first quote is a real number denoting the price quote for (USD/EUR).
2. The second quote is a real number denoting the price quote for (EUR/GBP).
3. The third quote is a real number denoting the price quote for (GBP/USD).

Your function takes a string array parameter containing each of the N lines as described above, in the same order.

## Intuition:

## Solution:
```python
class Solution:
    def solve(self, arr):
        budget = 100000  # Fixed: removed comma
        rs = []
        for query in arr:
            q1, q2, q3 = map(float, query.split())
            euros = budget * q1 
            gbp = euros * q2 
            usd = q3 * gbp
            profit = usd - budget 
            rs.append(max(0, int(profit)))
        return rs
```
Tags: 

RL: 

Considerations:
