2025-02-10 16:42

Link: https://neetcode.io/problems/maximum-product-subarray

Problem: 
Given an integer array `nums`, find a **subarray** that has the largest product within the array and return it.

A **subarray** is a contiguous non-empty sequence of elements within an array.

You can assume the output will fit into a **32-bit** integer.

Motivation:
starting from the first element is crucial, since we need to include at least one element for a valid subarray! moreover, this is a multiplication problem, where infinity or 0 times any element cannot result in proper product! 1 might work, but what if 1 was used as the lo_max or lo_min, but 1 is not actually in the array, which resulted in wrong results! 

We use 2 variable to keep track of the maximum value in the positive and negative case. if cur_min and num is negative, after parity is flipped, it might become the maximum product. Same for when cmx is positive, but num is negative, the minimum value can be potentially updated, which might contribute the maximum product later!

The point is we need to keep track of the minProduct, which might help us locate the correct max product in the subarray when a new element is introduced. and we update the glo_max in every round with the lo_max( since new lo_max considered previous lo_min times current element ) it accounted for all cases! 

Solution:
Kadane
```python
class Solution:
    def maxProduct(self, nums: List[int]) -> int:
        res = nums[0]
        curMin, curMax = 1, 1

        for num in nums:
            tmp = curMax * num
            curMax = max(num * curMax, num * curMin, num)
            curMin = min(tmp, num * curMin, num)
            res = max(res, curMax)
        return res
```
O(n)/O(1)

Tags: #kadane #dp #subarray #product

RL: 