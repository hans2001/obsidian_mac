2025-02-04 10:09

Link:https://neetcode.io/problems/subsets

Problem: 
Given an array `nums` of **unique** integers, return all possible subsets of `nums`.

The solution set must **not** contain duplicate subsets. You may return the solution in **any order**.

Motivation:
for each subset, either include the num or not. the result at each level depend on previous results( previous subset built! ). so from the recursive solution we can see that, for each recursive stack at the same index, the state of the subset is different! and index i depend then element in nums that we are currently handling! when last element is handled, we append results at leaf of the tree! (binary tree)

Solution:
backtracking: 
```python
class Solution:
    def subsets(self, nums: List[int]) -> List[List[int]]:
        res = []

        subset = []

        def dfs(i):
            if i >= len(nums):
                res.append(subset.copy())
                return
            subset.append(nums[i])
            dfs(i + 1)
            subset.pop()
            dfs(i + 1)

        dfs(0)
        return res
```

iteration :
```python
class Solution:
    def subsets(self, nums: List[int]) -> List[List[int]]:
        res = [[]]
        
        for num in nums:
            res += [subset + [num] for subset in res]
        
        return res
```

Tags: #backtracking

RL: 