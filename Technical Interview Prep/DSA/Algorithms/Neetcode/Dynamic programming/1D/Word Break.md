2025-02-10 19:48

Link:https://neetcode.io/problems/word-break

Problem: 
Given a string `s` and a dictionary of strings `wordDict`, return `true` if `s` can be segmented into a space-separated sequence of dictionary words.

You are allowed to reuse words in the dictionary an unlimited number of times. You may assume all dictionary words are unique.

Motivation:
try matching the substring from the end(bottom-up) and memoized the result with a array. For each starting index i, we try matching a word in the dictionary, since length of dictionary is smaller than length of the string. given that current word matched, if the remaining string also matched ,we set the memoized state to True, otherwise it remained false! the state represents a valid segmentation for s[i:]!

Solution:
DP(bottom-up)
```python
class Solution:
    def wordBreak(self, s: str, wordDict: List[str]) -> bool:
        dp = [False] * (len(s) + 1)
        dp[len(s)] = True

        for i in range(len(s) - 1, -1, -1):
            for w in wordDict:
                if (i + len(w)) <= len(s) and s[i : i + len(w)] == w:
                    dp[i] = dp[i + len(w)]
                if dp[i]:
                    break

        return dp[0]
```
O(n∗m∗t) / O(t)
(n is length of the string, m is the number of words in wordDict, and t is the maximum length of word in the dict)

DP (top-down):
```python
class Solution:
    def wordBreak(self, s: str, wordDict: List[str]) -> bool:
        memo = {len(s) : True}
        def dfs(i):
            if i in memo:
                return memo[i]
            
            for w in wordDict:
                if ((i + len(w)) <= len(s) and 
                     s[i : i + len(w)] == w
                ):
                    if dfs(i + len(w)):
                        memo[i] = True
                        return True
            memo[i] = False
            return False
        
        return dfs(0)
```
O(n∗m∗t) / O(n)

Tags: #dp #dict #substring 

RL: 