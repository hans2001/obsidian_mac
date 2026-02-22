2025-05-07 11:44

Link: https://neetcode.io/problems/search-in-rotated-sorted-array-ii

Problem: You are given an array of length `n` which was originally sorted in non-decreasing order (not necessarily with **distinct** values). It has now been **rotated** between `1` and `n` times. For example, the array `nums = [1,2,3,4,5,6]` might become:

- `[3,4,5,6,1,2]` if it was rotated `4` times.
- `[1,2,3,4,5,6]` if it was rotated `6` times.

Given the rotated sorted array `nums` and an integer `target`, return `true` if `target` is in `nums`, or `false` if it is not present.

You must decrease the overall operation steps as much as possible.

Intuition:
despite search in sorted arr, we need to handle duplicate values 
same as problem I, we should locate the sorted portion by checking left or right boundary against the element at mid!  then we check if target actually lies in the boundary define by boundary and mid, if not, it can only be at the other portion! 

Solution:
```python
class Solution:
    def search(self, nums: List[int], target: int) -> bool:
        l, r = 0, len(nums) - 1
        while l <= r:
            m = l + (r - l) // 2
            if nums[m] == target:
                return True

            if nums[l] < nums[m]:  # Left portion
                if nums[l] <= target < nums[m]:
                    r = m - 1
                else:
                    l = m + 1
            elif nums[l] > nums[m]:  # Right portion
                if nums[m] < target <= nums[r]:
                    l = m + 1
                else:
                    r = m - 1
            else:
                l += 1

        return False
```

Complexity:
Time: 
O(log n) where n is the length of nums

Space : 
O(1)

Tags: #binary_search 

RL: [[Search in Rotated Sorted Array]]

Considerations:
