2025-02-23 16:46

Link: https://neetcode.io/problems/multiply-strings

Problem: 
You are given two strings `num1` and `num2` that represent non-negative integers. 

Return the product of `num1` and `num2` in the form of a string.

Assume that neither `num1` nor `num2` contain any leading zero, unless they are the number `0`itself.

**Note**: You can not use any built-in library to convert the inputs directly into integers.

Intuition:
build the digit at the i+j position, handle the carry at the i+j+1 position, make sure we have a single digit at each position! at the end, remove any remaining zeros! (since we set the default values for all digits to 0, but for a valid number, the starting digit is not 0)

Solution:
```python
class Solution:
    def multiply(self, num1: str, num2: str) -> str:
        if "0" in [num1, num2]:
            return "0"

        res = [0] * (len(num1) + len(num2))
        num1, num2 = num1[::-1], num2[::-1]
        for i1 in range(len(num1)):
            for i2 in range(len(num2)):
                digit = int(num1[i1]) * int(num2[i2])
                res[i1 + i2] += digit
                res[i1 + i2 + 1] += res[i1 + i2] // 10
                res[i1 + i2] = res[i1 + i2] % 10

        res, beg = res[::-1], 0
        while beg < len(res) and res[beg] == 0:
            beg += 1
        res = map(str, res[beg:])
        return "".join(res)
```
O(n * m) / O(n+m)

Tags: #math #multiplication 

RL: 

Considerations:
