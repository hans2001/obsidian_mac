2025-02-23 13:09

Link: https://neetcode.io/problems/pow-x-n

Problem: 
`Pow(x, n)` is a mathematical function to calculate the value of `x` raised to the power of `n` (i.e., `x^n`).

Given a floating-point value `x` and an integer value `n`, implement the `myPow(x, n)` function, which calculates `x` raised to the power `n`.

You may **not** use any built-in library functions.

Intuition:
2^10  = 2^5 * 2^5
2^5 = 2 * 2^2 * 2^2
base case : when x is 0 or when n is 0

Solution:
divide and conquer (recursive)
```python
class Solution:
    def myPow(self, x: float, n: int) -> float:
        if x == 0:
            return 0
        if n == 0:
            return 1
        
        res = 1
        power = abs(n)
        
        while power:
            if power & 1:
                res *= x
            x *= x
            power >>= 1
        
        return res if n >= 0 else 1 / res
```
O(log n ) / O(log n)

or 

Mine 
```python
class Solution:
    def myPow(self, x: float, n: int) -> float:
		return x ** n 
```

Tags: #exponentiation

RL: 

Considerations:
definition of logarithm with base 2:  how many times we can divided n by 2 