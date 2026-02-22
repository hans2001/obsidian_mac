2025-02-18 12:28

Link: https://neetcode.io/problems/maximum-subarray

Problem: 
Given an array of integers `nums`, find the subarray with the largest sum and return the sum.

A **subarray** is a contiguous non-empty sequence of elements within an array.

Motivation:
instead of brute forcing the sum for each subarray, we memoized the sum for previous subarray, when the aggregated sum is negative, we abandon it and reset the local sum for the "sliding window" as 0, and reset the left pointer to the first positive number after the last number of the previous negative segment! since the previous sum will contribute negatively to future sum, the previous segment is safe to abandon! (the global sum stay as the maximum local sum until this point). The Kadane's algorithm solve this problem elegantly! 

The recursion idea adopts the dynamic programming paradigm, where we either start the subarray from this index or not, and take the maximum sum between the recursive stack. the base case would be when the index reach the end, we either return 0 if the subarray has started, or negative infinity to indicate an empty array if the flag is False(cannot not be selected),while subarray sum can be negative! 

Solution:
Memoized recursion:
```python
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        memo = [[None] * 2 for _ in range(len(nums) + 1)]

        def dfs(i, flag):
            if i == len(nums):
                return 0 if flag else -1e6
            if memo[i][flag] is not None:
                return memo[i][flag]
            if flag:
                memo[i][flag] = max(0, nums[i] + dfs(i + 1, True))
            else:
                memo[i][flag] = max(dfs(i + 1, False), 
                                    nums[i] + dfs(i + 1, True))
            return memo[i][flag]
        return dfs(0, False)
```
O(n) / O(n)

Kadane's algo
```python
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        maxSub, curSum = nums[0], 0
        for num in nums:
            if curSum < 0:
                curSum = 0
            curSum += num
            maxSub = max(maxSub, curSum)
        return maxSub
```
O(n) / O(1)

DP space optimized
```python
class Solution:
    def maxSubArray(self, nums):
        dp = [*nums]
        for i in range(1, len(nums)):
            dp[i] = max(nums[i], nums[i] + dp[i - 1])
        return max(dp)
```
O(n) / O(n)

Tags: #greedy #dp #recursion #kadane 

RL: 

Considerations:
