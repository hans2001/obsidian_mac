2025-02-04 14:48

Link: https://neetcode.io/problems/permutations

Problem: 
Given an array `nums` of **unique** integers, return all the possible permutations. You may return the answer in **any order**.

Motivation:
Rule of thumb:
Either building the sublist or swapping nums[i] with element at idx, ensure we have backtrack element( restore to previous state ), base case(when to terminate the dfs) ,and append copy of the local result to the global one

Solution:
optimal:
```python
class Solution:
    def permute(self, nums: List[int]) -> List[List[int]]:
        self.res = []
        self.backtrack(nums, 0)
        return self.res

    def backtrack(self, nums: List[int], idx: int):
        if idx == len(nums):
            self.res.append(nums[:])
            return
        for i in range(idx, len(nums)):
            nums[idx], nums[i] = nums[i], nums[idx]
            self.backtrack(nums, idx + 1)
            nums[idx], nums[i] = nums[i], nums[idx]
```
O(n * n!) / O(1)

Backtrack:
![[Screenshot 2025-02-04 at 2.49.03 PM.png]]

Tags: #backtracking #dfs

RL: 