2025-02-09 17:05

Link: https://neetcode.io/problems/house-robber

Problem: 
You are given an integer array `nums` where `nums[i]` represents the amount of money the `i`th house has. The houses are arranged in a straight line, i.e. the `i`th house is the neighbor of the `(i-1)`th and `(i+1)`th house.

You are planning to rob money from the houses, but you cannot rob **two adjacent houses** because the security system will automatically alert the police if two adjacent houses were _both_ broken into.
 
Return the _maximum_ amount of money you can rob **without** alerting the police.

Motivation:
The substructure of this problem is to rob or not to rob current house. if current house is not robbed, next house will be robbed since profit has to be maximized and every value is non-negative. If current house is robbed, it mean robbing next house would not give us a better profit! 

Recurrence:
![[Screenshot 2025-02-10 at 4.40.04 PM.png]]

Solution:
DP(top-down)
```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        memo = [-1] * len(nums)

        def dfs(i):
            if i >= len(nums):
                return 0
            if memo[i] != -1:
                return memo[i]
            memo[i] = max(dfs(i + 1), nums[i] + dfs(i + 2))
            return memo[i]
        
        return dfs(0)
```
O(n) / O(n)

DP(bottom-up)
```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        if not nums:
            return 0
        if len(nums) == 1:
            return nums[0]
        
        dp = [0] * len(nums)
        dp[0] = nums[0]
        dp[1] = max(nums[0], nums[1])
        
        for i in range(2, len(nums)):
            dp[i] = max(dp[i - 1], nums[i] + dp[i - 2])
        
        return dp[-1]
```
O(n) / O(n)

Space optimized
```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        rob1, rob2 = 0, 0

        for num in nums:
            temp = max(num + rob1, rob2)
            rob1 = rob2
            rob2 = temp
        return rob2
```
O(n) / O(1)

Tags: #dp #google #dfs

RL: [[House Robber II]]