2025-01-26 11:19

Link:https://neetcode.io/problems/find-target-in-rotated-sorted-array

Problem: 
You are given an array of length `n` which was originally sorted in ascending order. It has now been **rotated** between `1` and `n` times. For example, the array `nums = [1,2,3,4,5,6]` might become:

- `[3,4,5,6,1,2]` if it was rotated `4` times.
- `[1,2,3,4,5,6]` if it was rotated `6` times.

Given the rotated sorted array `nums` and an integer `target`, return the index of `target` within `nums`, or `-1` if it is not present.

You may assume all elements in the sorted rotated array `nums` are **unique**,
A solution that runs in `O(n)` time is trivial, can you write an algorithm that runs in `O(log n) time`?

Motivation:
custom binary search
we first ;find which portion is sorted, in order for us to apply the comparison logic. after we located sorted portion, we check if target really falls into this portion, or is  actually  at another portion! so comparing element at the 2 pointer (which pointer) to the target is essential to determine target's position! 
we can do binary search right way, but the condition diff, instead of the standard one
explain why we check if nums[i] < nums[md]

## 5. Your Question: Why Compare `nums[l]` and `nums[mid]`?

> "why we have to identify if `nums[l] <= nums[mid]`, and in this case we can actually find out that target is in the right portion — why?"

Because:
1. **We need to know which side is sorted**. Without this, we can't apply binary search correctly.
    - Binary search only works if the section is sorted.
2. If `nums[l] <= nums[mid]`, then `[l..mid]` is sorted.
    - If `target` is **inside that sorted range**, we move into it.
    - If not, we **discard it safely**, because if it's not in sorted left, it can only be in the right.
        
So when you find `nums[l] <= nums[mid]`, you’re **not automatically concluding target is in the right portion**.  
Instead:
- You’re concluding _left half is sorted_, and then decide whether the target falls inside or outside that half.
- If outside, you move to the right portion.

Solution:
```python
class Solution:
    def search(self, nums: List[int], target: int) -> int:
        n = len(nums)
        l, r = 0, n - 1
        while l <= r:
            md = (l + r) // 2
            # Found the target
            if nums[md] == target:
                return md
            # Check which portion is sorted
            if nums[l] <= nums[md]:
                # Left portion [l..md] is sorted
                if nums[l] <= target < nums[md]:
                    r = md - 1
                else:
                    l = md + 1
            else:
                # Right portion [md..r] is sorted
                # nums[md] == target was already handled above
                if nums[md] < target <= nums[r]:
                    l = md + 1
                else:
                    r = md - 1
        return -1
```

Tags: #binary_search 

RL: [[Binary Search]], [[Find Minimum in Rotated Sorted Array]]

Time complexity: O(n)

Space complexity: O(1)