2025-05-31 17:58

Link: https://neetcode.io/problems/longest-common-subsequence?list=neetcode250

Problem: 
Given two strings `text1` and `text2`, return the length of the _longest common subsequence_ between the two strings if one exists, otherwise return `0`.

A **subsequence** is a sequence that can be derived from the given sequence by deleting some or no elements without changing the relative order of the remaining characters.

- For example, `"cat"` is a subsequence of `"crabt"`.

A **common subsequence** of two strings is a subsequence that exists in both strings.

**Example 1:**
```java
Input: text1 = "cat", text2 = "crabt" 

Output: 3 
```

Explanation: The longest common subsequence is "cat" which has a length of 3.

**Example 2:**
```java
Input: text1 = "abcd", text2 = "abcd"

Output: 4
```

**Example 3:**
```java
Input: text1 = "abcd", text2 = "efgh"

Output: 0
```

Intuition:
- **If text1[i] == text2[j]**: Both characters match! We include this character in our LCS and solve for the remaining substrings.
    - LCS = 1 + LCS(text1[i+1:], text2[j+1:])
- **If text1[i] != text2[j]**: Characters don't match. The LCS must exclude at least one of them.
    - LCS = max(LCS(text1[i+1:], text2[j:]), LCS(text1[i:], text2[j+1:]))
    - We try both possibilities and take the maximum

Solution:
top-down 
```python
class Solution:
    def longestCommonSubsequence(self, text1: str, text2: str) -> int:
        memo = {}

        def dfs(i, j):
            if i == len(text1) or j == len(text2):
                return 0
            if (i, j) in memo:
                return memo[(i, j)]
            
            if text1[i] == text2[j]:
                memo[(i, j)] = 1 + dfs(i + 1, j + 1)
            else:
                memo[(i, j)] = max(dfs(i + 1, j), dfs(i, j + 1))
                
            return memo[(i, j)]
        
        return dfs(0, 0)
```

bottom up solution
```python
class Solution:
    def longestCommonSubsequence(self, text1: str, text2: str) -> int:
        dp = [[0 for j in range(len(text2) + 1)] 
                 for i in range(len(text1) + 1)]

        for i in range(len(text1) - 1, -1, -1):
            for j in range(len(text2) - 1, -1, -1):
                if text1[i] == text2[j]:
                    dp[i][j] = 1 + dp[i + 1][j + 1]
                else:
                    dp[i][j] = max(dp[i][j + 1], dp[i + 1][j])

        return dp[0][0]
```

Complexity: 
Time: 
O(m * n) we have the nested for loop that builds the dp table

Space: 
O(m * n)
spaced used by the dp table

Tags: #2d_dp #memoization 

RL: 

Considerations:
