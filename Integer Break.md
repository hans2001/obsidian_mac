2025-05-28 17:38

Link: https://neetcode.io/problems/integer-break?list=neetcode250

Problem: 
You are given an integer `n`, break it into the sum of `k` **positive integers**, where `k >= 2`, and maximize the product of those integers.

Return the maximum product you can get.

Intuition:
knapsack nature: 
for numbers other than n, we should optimize our choice for getting the maximum product, and we can either use the number as itself or breaking it down to sum of other numbers that might result in a greater product! 

unbounded nature: 
while not a classic problem in this realm!, when breaking up numbers, for example 2, we can choose to break it down as 1 + 1, so here shows the unlimited number choice, it is not like we can select 1 as part of the addition only once! 

Solution:
top down
```python
class Solution:
    def integerBreak(self, n: int) -> int:
        dp = {1: 1}

        def dfs(num):
            if num in dp:
                return dp[num]

            dp[num] = 0 if num == n else num
            for i in range(1, num):
                val = dfs(i) * dfs(num - i)
                dp[num] = max(dp[num], val)
            return dp[num]
        
        return dfs(n)
```

bottom up
```python
class Solution:
    def integerBreak(self, n: int) -> int:
        dp = {}
        def dfs(num, i):
            if min(num, i) == 0:
                return 1
            if (num, i) in dp:
                return dp[(num, i)]
            if i > num:
                dp[(num, i)] = dfs(num, num)
                return dp[(num, i)]
            
            dp[(num, i)] = max(i * dfs(num - i, i), dfs(num, i - 1))
            return dp[(num, i)]
        
        return dfs(n, n - 1)
```

Complexity:
Time: 
O(n^2)
to compute the max product for n, we need max products for previous results, starting from num = 1 O(n). 
In each loop, we compare the number as itself or breaking it up into multiple numbers, and we record the max product for each !

Space: 
O(n)
we need O(n) space to store the max product for each number!

Tags: #unbounded_knapsack #dp 

RL: [[Coin Change]] [[Perfect Squares]]

Considerations:
**why only integer n has to be break into more than 2 number**

n = 6:
- **Must break the original 6:** Cannot return 6
- **Optimal breakdown:** 3 + 3 = 6, product = 3 × 3 = 9
- **Why not break the 3's?** Because 3 > 1+2, so keeping each 3 is better than breaking it

The constraint `k >= 2` only applies to the **original number**, not to every piece in the breakdown!