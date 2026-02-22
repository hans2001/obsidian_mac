2025-12-18 16:24

Link:

Problem: 
Given an array of integers `nums` and an integer `k`. A continuous subarray is called **nice** if there are `k` odd numbers on it.

Return _the number of **nice** sub-arrays_.

**Example 1:**
**Input:** nums = [1,1,2,1,1], k = 3
**Output:** 2
**Explanation:** The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

**Example 2:**
**Input:** nums = [2,4,6], k = 1
**Output:** 0
**Explanation:** There are no odd numbers in the array.

**Example 3:**
**Input:** nums = [2,2,2,1,2,2,1,2,2,2], k = 2
**Output:** 16

**Constraints:**
- `1 <= nums.length <= 50000`
- `1 <= nums[i] <= 10^5`
- `1 <= k <= nums.length`

Intuition:
since the subarr has to include exactly k odd number, we can slid window on the odd number directly , can compute the number of subarrs we can form with that sub portions! 

Solution:
```python
class Solution:
    def numberOfSubarrays(self, nums: List[int], k: int) -> int:
        n = len(nums)
        odds = []
        for i, num in enumerate(nums):
            if num % 2 == 1:
                odds.append(i)
        l = 0
        res = 0
        m = len(odds)

        while l + k - 1 < m:
            prev = -1 if l == 0 else odds[l - 1]
            nxt = n if (l + k) >= m else odds[l + k]

            res += (odds[l] - prev) * (nxt - odds[l + k - 1])
            l += 1
        return res
```

Tags: #subarray #sliding_window 

RL: 

Considerations:
