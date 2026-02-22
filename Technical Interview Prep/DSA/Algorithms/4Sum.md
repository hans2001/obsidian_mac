2025-04-17 18:19

Link: https://leetcode.com/problems/4sum/description/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem: 
Given an array `nums` of `n` integers, return _an array of all the **unique** quadruplets_ `[nums[a], nums[b], nums[c], nums[d]]` such that:

- `0 <= a, b, c, d < n`
- `a`, `b`, `c`, and `d` are **distinct**.
- `nums[a] + nums[b] + nums[c] + nums[d] == target`

You may return the answer in **any order**.

Intuition:
we used 2 for loops to locate the first 2 elements, and use the 2 pointer approach for the 2 sum function, which we find out the position of the rest elements ! we passed down the complement value to be an indicator of how the pointer should move, and we skip duplicate values if consecutive is the same! (for the second for loop, after the first element, we should not encounter duplicate element as we progress the array, so if (i > k +1 and nums[i] == nums[i-1]), we should skip this value 

Solution:
2 pointer 
```python
from collections import defaultdict
class Solution(object):
	def fourSum(self, nums, target):
		if not nums or len(nums) < 4:
			return []
		nums.sort( )
		res = []
		def threeSum( k, complement) :
			for i in range( k +1 ,len( nums )):
				if i > k+1 and nums[ i ] == nums[ i-1 ]:
					continue
				l,r =i +1 , len ( nums ) - 1
				while l < r:
					sm = nums[i] + nums[l] + nums[r]
					if sm < complement:
						l +=1
					elif sm > complement:
						r -=1
					else:
						res.append([nums[k],nums[i],nums[l],nums[r]])
						l +=1
						r -=1
						while l < r and nums[l] == nums[l-1] :
							l +=1
						while l < r and nums[r] == nums[r+1]:
							r -= 1
			return
	for k in range( len (nums )) :
		if target > 0 and nums[k] > target:
			break
		if k == 0 or nums[k - 1] != nums[k]:
			threeSum(k, target - nums[k])
	return res
```
Time complexity: O(n^3)
Space complexity: O(1)

Tags: #bloomberg #fundamentals

RL: [[3Sum]]

Considerations: