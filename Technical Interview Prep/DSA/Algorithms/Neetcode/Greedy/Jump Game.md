2025-02-18 19:36

Link:https://neetcode.io/problems/jump-game

Problem: 
You are given an integer array `nums` where each element `nums[i]` indicates your maximum jump length at that position.

Return `true` if you can reach the last index starting from index `0`, or `false` otherwise.

Motivation:
our intuition is initially wrong, thinking about taking the maximum step from the start, guessing we can reach the end! Turns out, we can start from the end, and inform the process of a valid path by updating the goal along the way! the goal will be updated to index that could reach the end, so we can reverse the valid path to the starting point, and inform us if we could reach the end!

Solution:
Greedy:
```python
class Solution:
    def canJump(self, nums: List[int]) -> bool:
        goal = len(nums) - 1

        for i in range(len(nums) - 2, -1, -1):
            if i + nums[i] >= goal:
                goal = i
        return goal == 0
```
O(n) / O(1)

DP top-down:
```python
class Solution:
    def canJump(self, nums: List[int]) -> bool:
        memo = {}
        def dfs(i):
            if i in memo:
                return memo[i]
            if i == len(nums) - 1:
                return True
            if nums[i] == 0:
                return False
            
            end = min(len(nums), i + nums[i] + 1)
            for j in range(i + 1, end):
                if dfs(j):
                    memo[i] = True
                    return True
            memo[i] = False
            return False

        return dfs(0)
```
O(n^2) / O(n)

for each index i, the jump length as be as large as n. Therefore, the worst case complexity is O(n^2)
The memo dict can store up to n index, and the recursion stack could be up to O(n) (case where we advanced one step at a time)

DP bottom-up:
```python
class Solution:
    def canJump(self, nums: List[int]) -> bool:
        n = len(nums)
        dp = [False] * n
        dp[-1] = True

        for i in range(n - 2, -1, -1):
            end = min(n, i + nums[i] + 1)
            for j in range(i + 1, end):
                if dp[j]:
                    dp[i] = True
                    break
        return dp[0]
```
same complexity as above!

Tags: #greedy #dp 

RL: 

Considerations:
