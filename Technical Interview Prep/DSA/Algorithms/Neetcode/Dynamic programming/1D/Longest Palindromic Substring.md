2025-02-10 11:08

Link:https://neetcode.io/problems/longest-palindromic-substring

Problem: 
Given a string `s`, return the longest substring of `s` that is a _palindrome_.

A **palindrome** is a string that reads the same forward and backward.

If there are multiple palindromic substrings that have the same length, return any one of them.

Motivation:
check palindrome with 2 pointer by expanding outwards from the center of the substring. Though we have to check for even and odd length.

For the DP approach, we use a 2d array to indicate if substring s[i:j+1] is palindromic. 
for substring length j-i <=2, if first and last character is the same, the substring is palindromic. if length is longer, we need to check if insider substring is valid, and we check dp \[i+1\]\[j-1\] in this case, to inform us about the validity in O(1) time.

Recurrence:
![[Screenshot 2025-02-10 at 4.37.39 PM.png]]
Solution:
DP:
```python
class Solution:
    def longestPalindrome(self, s: str) -> str:
        resIdx, resLen = 0, 0
        n = len(s)
        dp = [[False] * n for _ in range(n)]
        for i in range(n - 1, -1, -1):
            for j in range(i, n):
                if s[i] == s[j] and (j - i <= 2 or dp[i + 1][j - 1]):
                    dp[i][j] = True
                    if resLen < (j - i + 1):
                        resIdx = i
                        resLen = j - i + 1

        return s[resIdx : resIdx + resLen]
```
O(n^2) / O(n^2)

2 pointers
```python
class Solution:
    def longestPalindrome(self, s: str) -> str:
        resIdx = 0
        resLen = 0
        for i in range(len(s)):
            # odd length
            l, r = i, i
            while l >= 0 and r < len(s) and s[l] == s[r]:
                if (r - l + 1) > resLen:
                    resIdx = l
                    resLen = r - l + 1
                l -= 1
                r += 1
            # even length
            l, r = i, i + 1
            while l >= 0 and r < len(s) and s[l] == s[r]:
                if (r - l + 1) > resLen:
                    resIdx = l
                    resLen = r - l + 1
                l -= 1
                r += 1
        return s[resIdx : resIdx + resLen]
```
O(n^2) / O(1)

Tags: #stirng #2_pointer #2d_dp #palindrome 

RL: [[Palindromic Substrings]]