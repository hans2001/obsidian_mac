2025-08-25 11:39

Link: https://leetcode.com/problems/longest-substring-without-repeating-characters/description/?envType=company&envId=tiktok&favoriteSlug=tiktok-thirty-days

Problem: 
standard prefix sm template

Intuition:
## 1. Why `pre = [0] * (n+1)` and `pre[0] = 0`
- We want `pre[i]` to represent the **sum of the first i elements** (not including index `i`).
- That means:
    pre[i]=nums[0]+nums[1]+⋯+nums[i−1]pre[i]=nums[0]+nums[1]+⋯+nums[i−1]
- So:
    - `pre[0] = 0` (sum of zero elements = 0).
    - `pre[1] = nums[0]`
    - `pre[2] = nums[0] + nums[1]`
    - `pre[n] = nums[0] + … + nums[n-1]`
        
This is why we allocate size **n+1** instead of n: we need that **extra 0 at the front** to make indexing clean.

## 2. Why `pre[r+1] - pre[l]`
Suppose we want sum of subarray `nums[l..r]`.

- By definition:
    pre[r+1]=nums[0]+…+nums[r]pre[r+1]=nums[0]+…+nums[r]pre[l]=nums[0]+…+nums[l−1]pre[l]=nums[0]+…+nums[l−1]

Subtracting:
	pre[r+1]−pre[l]=(nums[0]+…+nums[r])−(nums[0]+…+nums[l−1])pre[r+1]−pre[l]=(nums[0]+…+nums[r])−(nums[0]+…+nums[l−1])

All terms before `l` cancel out, leaving = nums[l]+…+nums[r]=nums[l]+…+nums[r]

Solution:
```python
from typing import List

def prefix_sum_array(nums: List[int]) -> List[int]:
    """
    Build a prefix sum array.
    pre[i] = sum of nums[0..i-1]
    """
    n = len(nums)
    pre = [0] * (n + 1)   # pre[0] = 0
    for i in range(n):
        pre[i+1] = pre[i] + nums[i]
    return pre

# Usage
nums = [2, 1, 3, 5]
pre = prefix_sum_array(nums)

# sum of nums[l..r] = pre[r+1] - pre[l]
print(pre[4] - pre[1])   # sum of [1,3,5] = 9
```

Tags: 

RL: 

Considerations:
