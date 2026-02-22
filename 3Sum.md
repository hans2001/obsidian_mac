2025-04-16 13:06

Link: https://leetcode.com/problems/3sum/description/

Problem: 
Given an integer array nums, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must not contain duplicate triplets.

Intuition:
2 pointer: 
main function same as below, 2sum function we use 2 pointer approach to find the suitable combination, where when sum < 0, we increment the left pointer, and when sum is bigger than 0, we decrement the right pointer( sorted array! )
## hash set: 
main function:
we sorted the array -> easy for us to skip duplicates and terminate the search earlier
fix a first element at index i
if current value is > 0: loop can be ended
if current value equal to previous value, can be skipped -> skipping duplicates! 
else call 2sum function! 

**2Sum:** 
compute complement! 
fix a middle element at index j
if complement exist in hash set, we push triplet to response arr, and skipping the consecutive duplicate middle element! 
otherwise, we move on to try another middle element! 

Solution:
## 2 pointer
```python
class Solution:
    def threeSum(self, nums: List[int]) -> List[List[int]]:
        res = []
        nums.sort()
        for i in range(len(nums)):
            if nums[i] > 0:
                break
            if i == 0 or nums[i - 1] != nums[i]:
                self.twoSumII(nums, i, res)
        return res

    def twoSumII(self, nums: List[int], i: int, res: List[List[int]]):
        lo, hi = i + 1, len(nums) - 1
        while lo < hi:
            sum = nums[i] + nums[lo] + nums[hi]
            if sum < 0:
                lo += 1
            elif sum > 0:
                hi -= 1
            else:
                res.append([nums[i], nums[lo], nums[hi]])
                lo += 1
                hi -= 1
                while lo < hi and nums[lo] == nums[lo - 1]:
                    lo += 1
```
## hash set
```python
class Solution:
    def threeSum(self, nums: List[int]) -> List[List[int]]:
        res = []
        nums.sort()
        for i in range(len(nums)):
            if nums[i] > 0:
                break
            if i == 0 or nums[i - 1] != nums[i]:
                self.twoSum(nums, i, res)
        return res

    def twoSum(self, nums: List[int], i: int, res: List[List[int]]):
        seen = set()
        j = i + 1
        while j < len(nums):
            complement = -nums[i] - nums[j]
            if complement in seen:
                res.append([nums[i], nums[j], complement])
                while j + 1 < len(nums) and nums[j] == nums[j + 1]:
                    j += 1
            seen.add(nums[j])
            j += 1
```
time complexity: O(n^2)
space: O(n) for the hashset 

Tags: #3sum #bloomberg 

RL: [[4Sum]]

Considerations:
