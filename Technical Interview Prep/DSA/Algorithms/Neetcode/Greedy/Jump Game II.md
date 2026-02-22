2025-02-18 19:36

Link: https://neetcode.io/problems/jump-game-ii

Problem: 
You are given an array of integers `nums`, where `nums[i]` represents the maximum length of a jump towards the right from index `i`. For example, if you are at `nums[i]`, you can jump to any index `i + j` where:

- `j <= nums[i]`
- `i + j < nums.length`

You are initially positioned at `nums[0]`.

Return the minimum number of jumps to reach the last position in the array (index `nums.length - 1`). You may assume there is always a valid answer.

Motivation:
the memoized state represents minimum number of steps to reach the end. and each new state is updated by incrementing the minimum remaining steps (at index j) by 1 (checking the valid steps form index i)

The greedy solution took a sliding window ish approach, where each segment is defined by left and right pointer! the right pointer is defined as the furthest element we can reach from previous segments indexes ,and the left pointer is the next element of previous right pointer!

Solution:
top-down dp:
```python
class Solution:
    def jump(self, nums: List[int]) -> int:
        memo = {}

        def dfs(i):
            if i in memo:
                return memo[i]
            if i == len(nums) - 1:
                return 0
            if nums[i] == 0:
                return 1000000
            
            res = 1000000
            end = min(len(nums), i + nums[i] + 1)
            for j in range(i + 1, end):
                res = min(res, 1 + dfs(j))
            memo[i] = res
            return res

        return dfs(0)
```
O(n^2) / O(n)
for each index i, worst case e ned to check every step form i to len(nums) if step is big! practical complexity is much smaller due to memoization!

bottom-up dp:
```python
class Solution:
    def jump(self, nums: List[int]) -> int:
        n = len(nums)
        dp = [1000000] * n
        dp[-1] = 0

        for i in range(n - 2, -1, -1):
            end = min(n, i + nums[i] + 1)
            for j in range(i + 1, end):
                dp[i] = min(dp[i], 1 + dp[j])
        return dp[0]
```
O(n^2) / o(n)

Greedy:
```python
class Solution:
    def jump(self, nums: List[int]) -> int:
        res = 0
        l = r = 0

        while r < len(nums) - 1:
            farthest = 0
            for i in range(l, r + 1):
                farthest = max(farthest, i + nums[i])
            l = r + 1
            r = farthest
            res += 1
        return res
```
O(n) / O(1)
always choose the farthest position we can reach from each step, then the number of steps we took will be the minimum!

Tags: #recursion #dp #greedy

RL: [[Jump Game]]

Considerations:
