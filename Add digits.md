2025-04-15 12:25

Link:

Problem: 
Given an integer `num`, repeatedly add all its digits until the result has only one digit, and return it.

Intuition:
- The digital root of a multiple of 9 is always 9 (except for 0)
- For other numbers, the digital root equals the remainder when the number is divided by 9
- This gives us the mathematical shortcut: digital root = 1 + ((num - 1) % 9) for non-zero numbers

Solution:
```python
class Solution(object):

def addDigits(self, num):
	if num ==0 :
		return 0
	elif num % 9 ==0 :
		return 9
	return num % 9
```

Tags: #number_theory

RL: 

Considerations:
