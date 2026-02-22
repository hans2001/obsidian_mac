2025-02-23 08:11

Link: https://neetcode.io/problems/non-cyclical-number

Problem: 
A **non-cyclical number** is an integer defined by the following algorithm:

- Given a positive integer, replace it with the sum of the squares of its digits.
- Repeat the above step until the number equals `1`, or it **loops infinitely in a cycle** which does not include `1`.
- If it stops at `1`, then the number is a **non-cyclical number**.

Given a positive integer `n`, return `true` if it is a **non-cyclical number**, otherwise return `false`.

Intuition:
the naive method below need additional space to represent the number , what if we could replace the representation with pointers
check if we have infinite loop in the while loop ,by checking if we have seen the number before!

Solution:
```python
class Solution:
    def isHappy(self, n: int) -> bool:
        visit = set()

        while n not in visit:
            visit.add(n)
            n = self.sumOfSquares(n)
            if n == 1:
                return True
        return False

    def sumOfSquares(self, n: int) -> int:
        output = 0

        while n:
            digit = n % 10
            digit = digit ** 2
            output += digit
            n = n // 10
        return output
```
O(log n) / O(log n)

Time complexity analysis:  since 1 <= n<= 1000, any number will be reduced up t o 9^2+9^2+9^2=243. once a number reduced below, i can only map to number within 1 to 243! that mean the cycle at most need to check 243 different values before encounter a same value or reach 1. each number has at most O(log n) digits, the sumOfSquares function run through each digit in O(log n) time, so the total complexity will be O(243 * log n)

Space complexity: each number requires O(log n) space to represent (n is number of digits),  the set will store up to 243 integers, for each integer occupying log n, we have space complexity as O(241 * log n)

```python
class Solution:
    def isHappy(self, n: int) -> bool:
        slow, fast = n, self.sumOfSquares(n)

        while slow != fast:
            fast = self.sumOfSquares(fast)
            fast = self.sumOfSquares(fast)
            slow = self.sumOfSquares(slow)
        return True if fast == 1 else False
    
    def sumOfSquares(self, n: int) -> int:
        output = 0

        while n:
            digit = n % 10
            digit = digit ** 2
            output += digit
            n = n // 10
        return output
```
O(log n) / O(1)

Tags: #math 

RL: 

Considerations:
