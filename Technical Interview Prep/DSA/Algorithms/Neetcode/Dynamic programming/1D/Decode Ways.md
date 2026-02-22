2025-02-10 12:18

Link: https://neetcode.io/problems/decode-ways

Problem: 
A string consisting of uppercase english characters can be encoded to a number using the following mapping:

```java
'A' -> "1"
'B' -> "2"
...
'Z' -> "26"
```

To **decode** a message, digits must be grouped and then mapped back into letters using the reverse of the mapping above. There may be multiple ways to decode a message. For example, `"1012"` can be mapped into:

- `"JAB"` with the grouping `(10 1 2)`
- `"JL"` with the grouping `(10 12)`

The grouping `(1 01 2)` is invalid because `01` cannot be mapped into a letter since it contains a leading zero.

Given a string `s` containing only digits, return the number of ways to **decode** it. You can assume that the answer fits in a **32-bit** integer.

Motivation:
dfs( i ): number of valid decodings starting at index i
dp = { n : 1 } reached the end of the string, only one way to decode an empty string
wo
path pruning: if s[i] is '0', no way to decode this string

dfs (i+1): s[i] is decoded as an single digit, continue the recursion
dfs (i+2): if condition is met, we can treat s[i:i+2] as an single digit , we continue recursion at i = i + 2.
return dfs(0): number of ways to decode the entire string 

Recurrence:
either decode a digit from one number or two number!
![[Screenshot 2025-02-10 at 4.35.50 PM.png]]
Solution:
DP top-down
```python
class Solution:
    def numDecodings(self, s: str) -> int:
        dp = {len(s) : 1}

        def dfs(i):
            if i in dp:
                return dp[i]
            if s[i] == "0":
                return 0

            res = dfs(i + 1)
            if i + 1 < len(s) and (
                s[i] == "1" or s[i] == "2" and
                s[i + 1] in "0123456"
            ):
                res += dfs(i + 2)
            dp[i] = res
            return res

        return dfs(0)
```
O(n) / O(n) (n is length of the input string)

DP bottom-up:
```python
class Solution:
    def numDecodings(self, s: str) -> int:
        dp = {len(s): 1}
        for i in range(len(s) - 1, -1, -1):
            if s[i] == "0":
                dp[i] = 0
            else:
                dp[i] = dp[i + 1]

            if i + 1 < len(s) and (s[i] == "1" or
               s[i] == "2" and s[i + 1] in "0123456"
            ):
                dp[i] += dp[i + 2]
        return dp[0]
```

Tags: #dp #dfs 

RL: 