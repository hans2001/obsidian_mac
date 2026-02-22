2025-02-04 12:55

Link:https://neetcode.io/problems/combination-target-sum-ii

Problem: 
You are given an array of integers `candidates`, which may contain duplicates, and a target integer `target`. Your task is to return a list of all **unique combinations** of `candidates` where the chosen numbers sum to `target`.

Each element from `candidates` may be chosen **at most once** within a combination. The solution set must not contain duplicate combinations.

You may return the combinations in **any order** and the order of the numbers in each combination can be in **any order**.

Motivation:
initially sort the array to ensure early return works! when same element encountered or sum + current element is bigger than target, we early return. For case 1, since we would already handled subset that sums to target and include this element, any element that is the same later should not be considered! 


Solution:
optimal(early return):
```python
class Solution:
    def combinationSum2(self, candidates: List[int], target: int) -> List[List[int]]:
        res = []
        candidates.sort()

        def dfs(idx, path, cur):
            if cur == target:
                res.append(path.copy())
                return
            for i in range(idx, len(candidates)):
                if i > idx and candidates[i] == candidates[i - 1]:
                    continue
                if cur + candidates[i] > target:
                    break

                path.append(candidates[i])
                dfs(i + 1, path, cur + candidates[i])
                path.pop()

        dfs(0, [], 0)
        return res
```
O(n∗2^n) / O(n)

Tags: #backtracking 

RL: [[Combination Sum]]