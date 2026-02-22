2025-01-28 22:28

Link:https://neetcode.io/problems/find-duplicate-integer

Problem: 
You are given an array of integers `nums` containing `n + 1` integers. Each integer in `nums` is in the range `[1, n]` inclusive.

Every integer appears **exactly once**, except for one integer which appears **two or more times**. Return the integer that appears more than once.

Motivation:
Negative marking: 
use the original array as hash map and flipped values at its corresponding index. Since we have to go through every value, if we encounter a value where its corresponding index has been flipped, we have a duplicate number!

Floyd cycle detection:
due to the one-to-one mapping nature with n+1 numbers for range [1,n], we detect if there is a cycle with fast/slow pointers (duplicate number contributes to a cycle). Then we reinitialize a second pointer to catch up with the first one to find the entrance of the cycle ( the duplicate number )

Solution:
Negative marking:
```python
class Solution:
    def findDuplicate(self, nums: List[int]) -> int:
        for num in nums :
            idx = abs(num) - 1 
            if nums[idx] < 0 :
                return abs(num)
            nums[idx] *= -1
        return -1
```

Floyd cycle detection algorithm:
```python
class Solution:
    def findDuplicate(self, nums: List[int]) -> int:
        slow, fast = 0, 0
        while True:
            slow = nums[slow]
            fast = nums[nums[fast]]
            if slow == fast:
                break

        slow2 = 0
        while True:
            slow = nums[slow]
            slow2 = nums[slow2]
            if slow == slow2:
                return slow
```

Tags: #linked_list #cycle_detection

RL: [[Linked List Cycle Detection]] [[Floyd cycle detection Algorithm]]

Time complexity: O(n)

Space complexity: O(1)