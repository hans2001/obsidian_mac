2025-07-10 10:56
## Link:

## Problem: 

## Intuition:
Why Greedy Works Here

**Key insight:** When both arrays are sorted, the greedy choice is always optimal because:
1. **Monotonicity:** If `a > b` and `c > d`, then pairing `(a,b)` and `(c,d)` is at least as good as `(a,d)` and `(c,b)`

- Sort both arrays (original and target)
- Use two pointers to greedily match elements
- Greedy choice: Always take the first available match
## Solution:
```python
class Solution:
	def maximizeGreatness(self, nums: List[int]) -> int:
		nums.sort( )
		left ,count = 0,0
		for num in nums:
			if num > nums[left] :
			left += 1
			count += 1
		return count
```

Tags: #greedy #2_pointer 
