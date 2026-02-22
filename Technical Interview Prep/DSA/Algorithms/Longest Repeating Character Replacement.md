2025-04-19 20:46

Link: https://neetcode.io/problems/longest-repeating-substring-with-replacement

Problem: 
You are given a string `s` consisting of only uppercase english characters and an integer `k`. You can choose up to `k`characters of the string and replace them with any other uppercase English character.

After performing at most `k` replacements, return the length of the longest substring which contains only one distinct character.

Intuition:

Solution:
```python
class Solution:
    def characterReplacement(self, s: str, k: int) -> int:
        count = {}
        res = 0
        
        l = 0
        maxf = 0
        for r in range(len(s)):
            count[s[r]] = 1 + count.get(s[r], 0)
            maxf = max(maxf, count[s[r]])

            while (r - l + 1) - maxf > k:
                count[s[l]] -= 1
                l += 1
            res = max(res, r - l + 1)

        return res
```

Tags: 

RL: 

Considerations:
