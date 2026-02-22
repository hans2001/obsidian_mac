2025-02-04 15:15

Link: https://neetcode.io/problems/subsets-ii

Problem: 
You are given an array `nums` of integers, which may contain duplicates. Return all possible subsets.

The solution must **not** contain duplicate subsets. You may return the solution in **any order**.

Motivation:
when nums[i] are not included, we skip duplicate elements that follows, otherwise, we are basically building same subset, as if nums[i] are included! 

Solution:
```python
class Solution:
    def subsetsWithDup(self, nums: List[int]) -> List[List[int]]:
        res = []
        nums.sort()

        def backtrack(i, subset):
            if i == len(nums):
                res.append(subset[::])
                return

            subset.append(nums[i])
            backtrack(i + 1, subset)
            subset.pop()

            while i + 1 < len(nums) and nums[i] == nums[i + 1]:
                i += 1
            backtrack(i + 1, subset)

        backtrack(0, [])
        return res
```
O(n * 2^n) / O(n)

Iteration:
```python
class Solution:
    def subsetsWithDup(self, nums: List[int]) -> List[List[int]]:
        nums.sort()
        res = [[]]
        prev_Idx = idx = 0
        for i in range(len(nums)):
            idx = prev_idx if i >= 1 and nums[i] == nums[i - 1] else 0
            prev_idx = len(res)
            for j in range(idx, prev_idx):
                tmp = res[j].copy()
                tmp.append(nums[i])
                res.append(tmp)
        return res
```
O(n * 2^n) / O(1)

Tags: #backtracking 

RL: [[Subsets]]