2025-02-04 10:15

Link:https://neetcode.io/problems/combination-target-sum

Problem: 
You are given an array of **distinct** integers `nums` and a target integer `target`. Your task is to return a list of all **unique combinations** of `nums` where the chosen numbers sum to `target`.

The **same** number may be chosen from `nums` an **unlimited number of times**. Two combinations are the same if the frequency of each of the chosen numbers is the same, otherwise they are different.

You may return the combinations in **any order** and the order of the numbers in each combination can be in **any order**.

Motivation:
come up with a binary decision tree, where for subset, we can either include the current element or not, and use index i to determine the current element in the recursive stack. 

As for the iterative approach, the not include current element options is extracted to a for loop, and when current element is included, we recursively call the function!
The second approach adopted early return where we stop when current sum + current element exceeds target, which is practically more efficient than approach 1 (theoretically the same! ).

Solution:
```python
class Solution:
    def combinationSum(self, nums: List[int], target: int) -> List[List[int]]:
        res = []

        def dfs(i, cur, total):
            if total == target:
                res.append(cur.copy())
                return
            if i >= len(nums) or total > target:
                return

            cur.append(nums[i])
            dfs(i, cur, total + nums[i])
            cur.pop()
            dfs(i + 1, cur, total)

        dfs(0, [], 0)
        return res
```
O(2^(T/m)) / O(t/m)
T is value of target, and m is the minimum value in original array

Early Return + Iteration: 
```python
class Solution:
    def combinationSum(self, nums: List[int], target: int) -> List[List[int]]:
        res = []
        nums.sort()

        def dfs(i, cur, total):
            if total == target:
                res.append(cur.copy())
                return
            
            for j in range(i, len(nums)):
                if total + nums[j] > target:
                    return
                cur.append(nums[j])
                dfs(j, cur, total + nums[j])
                cur.pop()
        
        dfs(0, [], 0)
        return res
```
O(2^(T/m)) / O(t/m)

Tags: #backtracking 

RL: [[Combination Sum II]]