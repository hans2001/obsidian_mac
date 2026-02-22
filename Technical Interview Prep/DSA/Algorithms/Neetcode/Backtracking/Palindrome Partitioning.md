2025-02-05 10:31

Link:https://neetcode.io/problems/palindrome-partitioning

Problem: 
Given a string `s`, split `s` into substrings where every substring is a palindrome. Return all possible lists of palindromic substrings.

You may return the solution in **any order**.

Motivation:
Diff: Added condition before backtracking. Ensure the substring we sliced is a palindrome, before trying to build the local array! 

Base case: we should have a index indicating the element we are currently partition up to, for which when it exceed the last element index, we should know that the local array finished(this path reach an end) then check if all ele in this path is valid before pushing that into result! 

Solution:
backtracking: 
```python
class Solution:
    def partition(self, s: str) -> List[List[str]]:
        res, part = [], []
        def dfs(i):
            if i >= len(s):
                res.append(part.copy())
                return
            for j in range(i, len(s)):
                if self.isPali(s, i, j):
                    part.append(s[i : j + 1])
                    dfs(j + 1)
                    part.pop()
        dfs(0)
        return res

    def isPali(self, s, l, r):
        while l < r:
            if s[l] != s[r]:
                return False
            l, r = l + 1, r - 1
        return True
```
O(n * 2 ^ n) / O(n)

DP:
```python
class Solution:
    def partition(self, s: str) -> List[List[str]]:
        n = len(s)
        dp = [[False] * n for _ in range(n)]
        for l in range(1, n + 1):
            for i in range(n - l + 1):
                dp[i][i + l - 1] = (s[i] == s[i + l - 1] and
                                    (i + 1 > (i + l - 2) or
                                    dp[i + 1][i + l - 2]))
        res, part = [], []
        def dfs(i):
            if i >= len(s):
                res.append(part.copy())
                return
            for j in range(i, len(s)):
                if dp[i][j]:
                    part.append(s[i : j + 1])
                    dfs(j + 1)
                    part.pop()
        dfs(0)
        return res
```
O(n * 2 ^ n) / O(n)

Tags: #backtracking  #dp #palindrome

RL: 