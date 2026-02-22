2025-04-18 11:44

Link:https://leetcode.com/problems/valid-triangle-number/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem: 
Given an integer array `nums`, return _the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle_.

Intuition:
- When we find `nums[left] + nums[right] > nums[i]`, we're saying that:
    - `nums[left]` can form a valid triangle with `nums[right]` and `nums[i]`
    - `nums[left+1]` can also form a valid triangle with `nums[right]` and `nums[i]` (since `nums[left+1] > nums[left]` in a sorted array)
    - And so on for all elements between `left` and `right-1`

- That's why we add `(right - left)` to our count - it represents all these valid triangles with `nums[i]` as the largest side.

- We exclude `right` from the count formula because we're already using the element at index `right` as our middle side.

- When we decrement `right`, we're essentially looking at a new set of combinations with a smaller middle element.

- And exactly as you said, we don't need to decrement `left` when we find a valid triangle because:
    - If `nums[left] + nums[right] > nums[i]`, then any larger left element will also work
    - If we couldn't form a valid triangle with the smallest possible left element, we need to increase left to find a larger value

Solution:
2 pointer
```python
class Solution(object):
	def triangleNumber(self, nums):
		nums .sort( )
		count = 0
		n = len(nums)
		for i in range( n-1, 1,-1):
			l,r = 0, i - 1
			while l < r:
				sm = nums[l] + nums[r]
				if sm <= nums[i] :
					l += 1
				else :
					count += (r - l)
					r -= 1
		return count
```

Tags: #2_pointer #triangle 

RL: 

Considerations:
