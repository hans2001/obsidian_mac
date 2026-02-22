2025-12-08 11:45

Link: https://neetcode.io/problems/single-element-in-a-sorted-array/question

Problem: 
You are given a **sorted array** consisting of only integers where every element appears exactly twice, except for one element which appears exactly once.
Return the single element that appears only once.

Your solution must run in `O(log n)` time and `O(1)` space.

**Example 1:**
```java
Input: nums = [1,1,2,3,3,4,4,8,8]

Output: 2
```

**Example 2:**
```java
Input: nums = [3,3,7,7,10,11,11]

Output: 10
```

**Constraints:**
- `1 <= nums.length <= 1,00,000`.
- `0 <= nums[i] <= 1,00,000`

Intuition:
check the position where pattern was break in the arr. normally, pairs should start from even indicies, and the next num should be neighbor .and for odd index, previous num should be neighbors in normal case. when the single num inserted, the pattern would changed! that is where we spot whether we should move left or right, and here it shows monotonicity

Solution:
```python
class Solution:
    def singleNonDuplicate(self, nums: List[int]) -> int:
        """
        Use binary search on the sorted array.
        Check whether mid is even or odd and compare with neighbors.
        """
        n = len(nums)
        l, r = 0, n - 1
        while l <= r:
            md = (l + r) // 2
            lower = max(0, md - 1)
            upper = min(n - 1, md + 1)
            if md % 2 == 0:
                if nums[md] == nums[upper] and upper != md:
                    l = md + 1
                elif nums[md] == nums[lower] and lower != md:
                    r = md - 1
                else:
                    return nums[md]
            else:
                if nums[md] == nums[upper] and upper != md:
                    r = md - 1
                elif nums[md] == nums[lower] and lower != md:
                    l = md + 1
                else:
                    return nums[md]
        return -1
```

Tags: #binary_search 

RL: 

Considerations:
