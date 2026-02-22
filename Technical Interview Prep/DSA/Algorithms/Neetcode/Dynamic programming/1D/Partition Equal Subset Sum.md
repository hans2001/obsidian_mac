2025-02-11 19:55

Link: https://neetcode.io/problems/partition-equal-subset-sum

Problem: 
You are given an array of positive integers `nums`.

Return `true` if you can partition the array into two subsets, `subset1` and `subset2` where `sum(subset1) == sum(subset2)`. Otherwise, return `false`.

Motivation:
identify that for subset sum to be the same, the total sum is even, and the target subset sum is total sum divided by 2!

optimal solution:
a backward iteration ensure we don't use DP values computed in current iteration(each element can only be used one).However, if each number can be used more than once, we can consider a approach of forward iteration.

top-down: 
2 variables determine the unique state of each subset sum, being index in original list and current sum! so the recursive function based on these 2 variables, compute the validness of current state, by requesting solutions from future state( starting from a later index! )

bottom up: 
for each state from 1 to target + 1 , we check if current element can be included (nums[i-1]). if after inclusion, we found a complement that is also true (valid state using previous elements ), that mean we have a combination that contribute to the desired sum ,otherwise, we cannot reach the desired sum even provided with this element!

Recurrence: 
![[Screenshot 2025-02-13 at 12.33.27 PM.png]]
Solution:
brute force(recursion):
```python
class Solution:
    def canPartition(self, nums: List[int]) -> bool:
        if sum(nums) % 2:
            return False
        
        def dfs(i, target):
            if i >= len(nums):
                return target == 0
            if target < 0:
                return False
            
            return dfs(i + 1, target) or dfs(i + 1, target - nums[i])
        
        return dfs(0, sum(nums) // 2)
```
O(2^n) / O(n)

DP top-down:
```python
class Solution:
    def canPartition(self, nums: List[int]) -> bool:
        total = sum(nums)
        if total % 2 != 0:
            return False
        
        target = total // 2
        n = len(nums)
        memo = [[-1] * (target + 1) for _ in range(n + 1)]

        def dfs(i, target):
            if target == 0:
                return True
            if i >= n or target < 0:
                return False
            if memo[i][target] != -1:
                return memo[i][target]
            
            memo[i][target] = (dfs(i + 1, target) or 
                               dfs(i + 1, target - nums[i]))
            return memo[i][target]

        return dfs(0, target)
```
O(n * target) / O(n * target)

Dp bottom up
```python
class Solution:
    def canPartition(self, nums: List[int]) -> bool:
        total = sum(nums)
        if total % 2 != 0:
            return False

        target = total // 2
        n = len(nums)
        dp = [[False] * (target + 1) for _ in range(n + 1)]

        for i in range(n + 1):
            dp[i][0] = True

        for i in range(1, n + 1):
            for j in range(1, target + 1):
                if nums[i - 1] <= j:
                    dp[i][j] = (dp[i - 1][j] or 
                                dp[i - 1][j - nums[i - 1]])
                else:
                    dp[i][j] = dp[i - 1][j]

        return dp[n][target]
```

DP optimal:
```python
class Solution:
    def canPartition(self, nums: list[int]) -> bool:
        if sum(nums) % 2:
            return False

        target = sum(nums) // 2
        dp = [False] * (target + 1)

        dp[0] = True
        for num in nums:
            for j in range(target, num - 1, -1):
                dp[j] = dp[j] or dp[j - num]
                
        return dp[target]
```
O(n * target) / O(target)

Tags: #dp #subset #bounded_knapsack 

RL: 