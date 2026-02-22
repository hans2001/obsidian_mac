2025-10-03 16:54

Link: https://leetcode.com/problems/restore-ip-addresses/description/?envType=company&envId=tiktok&favoriteSlug=tiktok-three-months

Problem: 
A **valid IP address** consists of exactly four integers separated by single dots. Each integer is between `0` and `255` (**inclusive**) and cannot have leading zeros.

- For example, `"0.1.2.201"` and `"192.168.1.1"` are **valid** IP addresses, but `"0.011.255.245"`, `"192.168.1.312"` and `"192.168@1.1"` are **invalid** IP addresses.

Given a string `s` containing only digits, return _all possible valid IP addresses that can be formed by inserting dots into_ `s`. You are **not** allowed to reorder or remove any digits in `s`. You may return the valid IP addresses in **any** order.

**Example 1:**
**Input:** s = "25525511135"
**Output:** ["255.255.11.135","255.255.111.35"]

**Example 2:**
**Input:** s = "0000"
**Output:** ["0.0.0.0"]

**Example 3:**
**Input:** s = "101023"
**Output:** ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]

**Constraints:**
- `1 <= s.length <= 20`
- `s` consists of digits only.

Intuition:
well u have no idea when to insert it to get a valid combo, so u could only try all option and verify at the end if it works, and prune the path that does not work early, that is the only way. so assume for each char, u can try inserting a dot, or not, and for each interval, u check if current number is vaild or not ,and we only proceed with the path that is valid. Then as u move on, u should start from the char that is not part of the last section u just built, and from this new char, u consider combo with at most 2 other char! ,and the process continues!
at the end, u only take paths that has at most 4 segment as a valid response! 

Solution:
```python
from typing import List

class Solution:
    def restoreIpAddresses(self, s: str) -> List[str]:
        n = len(s)
        res, path = [], []

        def valid(i: int, j: int) -> bool:
            # j is inclusive; assume 0 <= i <= j < n
            length = j - i + 1
            if length > 3:
                return False
            # no leading zeros unless the part is exactly "0"
            if s[i] == '0' and length > 1:
                return False
            # numeric range 0..255
            return int(s[i:j+1]) <= 255

        def dfs(i: int) -> None:
            seg_left = 4 - len(path)
            rem = n - i
            # prune: remaining chars must fit into remaining segments
            if rem < seg_left or rem > 3 * seg_left:
                return

            if len(path) == 4:
                if i == n:
                    res.append('.'.join(path))
                return

            # try parts of length 1..3 (bounded by string end)
            end_max = min(n - 1, i + 2)
            for j in range(i, end_max + 1):
                if valid(i, j):
                    path.append(s[i:j+1])
                    dfs(j + 1)
                    path.pop()
        dfs(0)
        return res

```

Tags: #string #backtracking 

RL: 

Considerations:
