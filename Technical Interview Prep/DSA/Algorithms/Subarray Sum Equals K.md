 2025-04-18 12:41

Link: https://leetcode.com/problems/subarray-sum-equals-k/description/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem: 
Given an array of integers `nums` and an integer `k`, return _the total number of subarrays whose sum equals to_ `k`.

A subarray is a contiguous **non-empty** sequence of elements within an array.

Intuition:
we just check if complement( curr_sum - k ) exist in dict. if yes, we have that amount of subarray that can reach the sum! 
### How the code uses that
- `cnt[p]` stores how many times a prefix sum value `p` has occurred so far.
- At each step:
    - `run += x`
    - Add `cnt[run - k]` to the answer (that’s all valid `l`).
    - Then increment `cnt[run]` (so future positions can use this prefix).
        
`cnt[0] = 1` seeds the empty prefix so subarrays starting at index `0` are counted when `run == k`.

### Why it catches _all_ subarrays
Every subarray ending at the current index corresponds bijectively to a previous prefix `S[l]` with value `run - k`. By summing `cnt[run - k]` at each step, you count **every** such `l` once—no misses, no duplicates.

![[Screenshot 2025-08-25 at 11.34.38 AM.png]]

Solution:
```python
from collections import defaultdict
	class Solution(object):
		def subarraySum(self, nums, k):
			if not nums:
				return 0
			n = len(nums)
			count = 0
			d = defaultdict( int )
			d[0] = 1
			total = 0
			for i in range( n) :
				total += nums[i]
				count += d[total-k]
				d[total] += 1
			return count
```

Tags: #prefix_sum #dict 

RL: 

Considerations:
