2025-12-12 15:24

Link: https://neetcode.io/problems/string-compression/question?list=neetcode250

Problem: 
You are given an array of characters `chars`, compress it using the following algorithm:

Begin with an empty string `s`. For each group of **consecutive repeating characters** in `chars`:
- If the group's length is `1`, append the character to `s`.
- Otherwise, append the character followed by the group's length.

The compressed string `s` should not be **returned separately**, but instead, be stored in the input character array `chars`. Note that group lengths that are `10` or longer will be split into multiple characters in `chars`. For example, `10` is represented as `["1","0"]`.

Let `k` be the length of the compressed string `s`. You must modify the first `k` characters of `chars` array and return `k`.

You must write an algorithm that uses only constant extra space.

**Example 1:**
```java
Input: chars = ["a","a","a","a","a","a","a","a","a","a","a"]

Output: 3
```
Explanation: The compressed string is "a11" and the first 3 characters of the input array should be ["a","1","2"].

**Example 2:**
```java
Input: chars = ["A"]

Output: 1
```
Explanation: The compressed string is "A" and the first 1 characters of the input array should be ["A"].

**Example 3:**
```java
Input: chars = ["1","1","2"]

Output: 3
```
Explanation: The compressed string is "122" and the first 3 characters of the input array should be ["1","2","2"].

**Constraints:**
- `1 <= chars.length <= 2000`
- `chars[i]` is a lowercase English letter, uppercase English letter, digit, or symbol.

Intuition:
make sure that u flush the last grp (when read pointer read the end!) (no distinct char in the future anymore!) (this grp has to be considered! )
maintain 3 pointer to modify in place, the write pointer is maintained by +1 only
and the read pointer should increment each iteration until meeting a char that is not the previous char! 

Solution:
```python
from typing import List

class Solution:
    def compress(self, chars: List[str]) -> int:
        cnt_pointer, cur_pointer, write_pointer = 0, 0, 0
        n = len(chars)

        while cnt_pointer <= n:  # <= so we can flush last group
            if cnt_pointer == n or chars[cnt_pointer] != chars[cur_pointer]:
                diff = cnt_pointer - cur_pointer

                chars[write_pointer] = chars[cur_pointer]
                write_pointer += 1

                if diff > 1:
                    for digit in str(diff):
                        chars[write_pointer] = digit
                        write_pointer += 1

                cur_pointer = cnt_pointer  # start new group

            cnt_pointer += 1

        return write_pointer
```

Tags: #2_pointer 

RL: 

Considerations:
