2025-12-08 14:00

Link: https://neetcode.io/problems/find-peak-element/question

Problem: 
A peak element is an element that is **strictly greater** than its neighbors.

You are given a **0-indexed** integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to **any of the peaks**.

You may imagine that `nums[-1] = nums[n] = -∞`. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.

You must write an algorithm that runs in `O(log n)` time.

**Example 1:**
```java
Input: nums = [1,2,3,1]

Output: 2
```
Explanation: 3 is a peak element and your function should return the index number 2.

**Example 2:**
```java
Input: nums = [1,2,1,3,4,5,0]

Output: 5
```
Explanation: 5 is a peak element and your function should return the index number 5.

**Constraints:**
- `1 <= nums.length <= 1000`.
- `-(2^31) <= nums[i] <= ((2^31)-1)`
- `nums[i] != nums[i + 1]` for all valid `i`.

Intuition:
the insight is that we should use the slope to find local peak. the peak is guarantee base on this property: `nums[i] != nums[i + 1]` for all valid `i`. therefore, as we discover neighbor element that lead to positive slop , we know peak is on the right, otherwise for negative slop , we know that peak might be on the left!  return peak index when both neighbor element is smaller than current element! 

Solution:
```python
class Solution:
    def findPeakElement(self, nums: List[int]) -> int:
        """
        Use the idea of local monotonicity:
        - If the right neighbor is larger, move right (ascending slope).
        - If the left side is larger, move left (descending slope).
        - If both neighbors are smaller, we've found a peak.
        """
        n = len(nums)
        neg_inf = float('-inf')

        l, r = 0, n - 1
        while l <= r:
            md = (l + r) // 2

            lower_val = neg_inf if md - 1 < 0 else nums[md - 1]
            upper_val = neg_inf if md + 1 >= n else nums[md + 1]

            if lower_val < nums[md] and upper_val < nums[md]:
                return md
            elif upper_val < nums[md]:
                r = md - 1
            else:
                l = md + 1

        return -1
```


Tags: #binary_search 

RL: 

Considerations:
