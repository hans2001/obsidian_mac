2025-02-10 12:14

Link: https://neetcode.io/problems/palindromic-substrings

Problem: 
Given a string `s`, return the number of substrings within `s` that are palindromes.

A **palindrome** is a string that reads the same forward and backward.

Motivation:
just build palindromic substring from the center, and increment the result when it is valid
Recurrence:
![[Screenshot 2025-02-10 at 4.37.39 PM.png]]
Solution:
2 pointer
```python
class Solution:
    def countSubstrings(self, s: str) -> int:
        res = 0
        
        for i in range(len(s)):
            # odd length
            l, r = i, i
            while l >= 0 and r < len(s) and s[l] == s[r]:
                res += 1
                l -= 1
                r += 1

            # even length
            l, r = i, i + 1
            while l >= 0 and r < len(s) and s[l] == s[r]:
                res += 1
                l -= 1
                r += 1
        
        return res
```
O(n^2) / O(1)

dp:
```python
class Solution:
    def countSubstrings(self, s: str) -> int:
        n, res = len(s), 0
        dp = [[False] * n for _ in range(n)]

        for i in range(n - 1, -1, -1):
            for j in range(i, n):
                if s[i] == s[j] and (j - i <= 2 or dp[i + 1][j - 1]):
                    dp[i][j] = True
                    res += 1
        return res
```

Tags: #2_pointer #dp #palindrome 

RL: [[Longest Palindromic Substring]]