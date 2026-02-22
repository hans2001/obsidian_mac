2025-02-25 11:33

Link: https://neetcode.io/problems/sum-of-two-integers

Problem: 
Given two integers `a` and `b`, return the sum of the two integers without using the `+` and `-`operators.

Intuition:
mask & max_int
Since number in python can grow arbitrary large, we need  masks to limit the number within 32 bits. the max_int is useful when we need to handle negative number. since numbers are represented in 2's complement system, if the number is bigger than the max_int, the number is actually a negative number, and we can obtain that by flipping all bits! 

Algorithm: (Adding without carry) / (calculating the carry)
the xor operation adds 2 bits without considering the carry, and we use & operation plus left shifting to find out the carry!(and assign to b) make sure both the sum and carry is within 32 bit by applying the mask, and we repeat the process until there is no carry left! Last, we check if the result is smaller than the maximum integer representable by python, if not, we flip the bits by result ^ mask, and applies python's bitwise NOT(~), to represent that the result is negative number! 

Solution:
```python
class Solution:
    def getSum(self, a: int, b: int) -> int:
        mask = 0xFFFFFFFF
        max_int = 0x7FFFFFFF

        while b != 0:
            carry = (a & b) << 1
            a = (a ^ b) & mask
            b = carry & mask

        return a if a <= max_int else ~(a ^ mask)
```
O(1) / O(1)
mask: ( 1 << 32 ) - 1 
max_int: ( 1 << 31 ) - 1 

Tags: #bit_manipulation #xor #and 

RL: 