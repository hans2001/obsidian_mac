2025-05-05 20:52

Link:https://neetcode.io/problems/minimum-size-subarray-sum

Problem: 
You are given an array of positive integers `nums` and a positive integer `target`, return the **minimal length** of a subarray whose sum is greater than or equal to `target`. If there is no such subarray, return `0` instead.

A **subarray** is a contiguous non-empty sequence of elements within an array.

Intuition:
determine changes of the window size by comparing subarray sum with target! 

Solution:
sliding window: 
```python
class Solution:
    def minSubArrayLen(self, target: int, nums: List[int]) -> int:
        l, total = 0, 0
        res = float("inf")

        for r in range(len(nums)):
            total += nums[r]
            while total >= target:
                res = min(r - l + 1, res)
                total -= nums[l]
                l += 1

        return 0 if res == float("inf") else res
```
O(n) / O(1) space 
prefix sm + binary search: 
```python
class Solution:
    def minSubArrayLen(self, target: int, nums: List[int]) -> int:
        n = len(nums)
        prefixSum = [0] * (n + 1)
        for i in range(n):
            prefixSum[i + 1] = prefixSum[i] + nums[i]
        
        res = n + 1
        for i in range(n):
            l, r = i, n
            while l < r:
                mid = (l + r) // 2
                curSum = prefixSum[mid + 1] - prefixSum[i]
                if curSum >= target:
                    r = mid
                else:
                    l = mid + 1
            if l != n:
                res = min(res, l - i + 1)

        return res % (n + 1)
```
O(nlogn) / O(n)

Complexity:

Tags: #sliding_window #binary_search 

RL: 

Considerations:
