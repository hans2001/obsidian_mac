2025-02-23 07:54

Link: https://neetcode.io/problems/plus-one

Problem: 
You are given an integer array `digits`, where each `digits[i]` is the `ith` digit of a large integer. It is ordered from most significant to least significant digit, and it will not contain any leading zero.

Return the digits of the given integer after incrementing it by one.

Intuition:
think about the problem in terms of carry (on first principle), in what case do we need to increment 1 without or with carry, when do we need to change the whole number, or when does a particular number turn to 0? 

Solution:
```python
class Solution:
    def plusOne(self, digits: List[int]) -> List[int]:
        n = len(digits)
        for i in range(n - 1, -1, -1):
            if digits[i] < 9:
                digits[i] += 1
                return digits
            digits[i] = 0
        
        return [1] + digits
```
O(N) / O(1)

Tags: #math

RL: 

Considerations:
