2025-12-12 20:52

Link:

Problem: 
You are given an array of integers `nums` and an integer `target`.
Return the number of **non-empty** subsequences of `nums` such that the sum of the minimum and maximum element on it is less or equal to `target`. Since the answer may be too large, return it modulo `(10^9) + 7`.

**Example 1:**
```java
Input: nums = [3,5,6,7], target = 9

Output: 4
```
Explanation: There are 4 subsequences that satisfy the condition.  
[3] -> Min value + max value <= target (3 + 3 <= 9)  
[3,5] -> (3 + 5 <= 9)  
[3,5,6] -> (3 + 6 <= 9)  
[3,6] -> (3 + 6 <= 9)

**Example 2:**
```java
Input: nums = [3,3,6,8], target = 10

Output: 6
```
Explanation: There are 6 subsequences that satisfy the condition. (nums can have repeated numbers).  
[3] , [3] , [3,3], [3,6] , [3,6] , [3,3,6]

**Constraints:**
- `1 <= nums.length <= 100,000`
- `1 <= nums[i], target <= 1,000,000`

Intuition:
use left right to compute valid windwo where all subseq would work ,adn the number of subsequence of arr n would be (2 ^ n )- 1. but in this case since
We count subsequences where **min is exactly nums[l]** and max is ≤ nums[r].
- We must include index l.
- For each of the (r-l) elements after l, choose include/exclude independently.
    
- That’s 2^(r-l) subsequences.
    No “-1” because the “choose none of them” option corresponds to the subsequence [nums[l]], which is non-empty and valid if nums[l]+nums[l] <= target (and if it isn’t, then the nums[l]+nums[r] <= target condition would fail anyway when r==l).

Therefore, we aggregate all subseq with valid windows until all valid windwo are considered! 

example
nums = [2, 3, 4, 5]
target = 7
pow2 = [1, 2, 4, 8, 16]

[2]
[2,3]
[2,4]
[2,5]
[2,3,4]
[2,3,5]
[2,4,5]
[2,3,4,5]
l = 0 (nums[l] = 2)
r = 3 (nums[r] = 5)
ans += 2^(r - l) = 2^(3 - 0) = 8

[3]
[3,4]
l = 0 (nums[l] = 3)
r = 2 (nums[r] = 4)
3 + 5 > 7
3 + 4 = 7 <= target
ans += 2^(r - l) = 2^(2 - 1) = 2

Solution:
```python
from typing import List

class Solution:
    def numSubseq(self, nums: List[int], target: int) -> int:
        MOD = 10**9 + 7
        nums.sort()
        n = len(nums)

        # precompute powers of 2 up to n
        pow2 = [1] * (n + 1)
        for i in range(1, n + 1):
            pow2[i] = (pow2[i - 1] * 2) % MOD

        l, r = 0, n - 1
        ans = 0

        while l <= r:
            if nums[l] + nums[r] <= target:
                ans = (ans + pow2[r - l]) % MOD
                l += 1
            else:
                r -= 1

        return ans
```

Tags: #2_pointer #subsequence 

RL: 

Considerations:
