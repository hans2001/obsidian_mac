2025-04-30 12:29

Link:https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/

Problem: 
Given an integer array `nums` and an integer `k`, return `true` if it is possible to divide this array into `k` non-empty subsets whose sums are all equal.

**Example 1:**
**Input:** nums = [4,3,2,3,5,2,1], k = 4
**Output:** true
**Explanation:** It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.

**Example 2:**
**Input:** nums = [1,2,3,4], k = 3
**Output:** false

Intuition:
base case checking: 
if the total sum can be divided to k parts (modulation)
if the largest element is smaller than target? otherwise the element would not appear in any subset! 

**base case:**
when we found k number of subset with sum == target

Recursive call: 
if current_sum == target, we should try building additional subset if we haven't build k number of subset! 

Backtrack: 
Including this element in the current subset didn't lead to a valid overall solution,

we mark elements as used or not, prevent us from including the same element in 2 subset! 
by sorting the nums array, we know we can stop exploring if adding the next element would exceed the target! (optimization)

Solution:
```python
class Solution:
    def canPartitionKSubsets(self, arr: List[int], k: int) -> bool:
        n = len(arr)
    
        # If the total sum is not divisible by k, we can't make subsets.
        total_array_sum = sum(arr)
        if total_array_sum % k != 0:
            return False
        target_sum = total_array_sum // k

        # Sort in decreasing order.
        arr.sort(reverse=True)

        taken = [False] * n
        
        def backtrack(index, count, curr_sum):
            n = len(arr)
      
            # We made k - 1 subsets with target_sum and the last subset must also have target_sum.
            if count == k - 1:
                return True
            
            # No need to proceed further.
            if curr_sum > target_sum:
                return False
                
            # When curr sum reaches target then one subset is made.
            # Increment count and reset current sum.
            if curr_sum == target_sum:
                return backtrack(0, count + 1, 0)
                
            # Try not picked elements to make some combinations.
            for j in range(index, n):
                if not taken[j]:
                    # Include this element in current subset.
                    taken[j] = True
                    
                    # If using current jth element in this subset leads to make all valid subsets.
                    if backtrack(j + 1, count, curr_sum + arr[j]):
                        return True
        
                    # Backtrack step.
                    taken[j] = False
    
            # We were not able to make a valid combination after picking 
            # each element from the array, hence we can't make k subsets.
            return False
        
        return backtrack(0, 0, 0)
```

```python
from typing import List

class Solution:
    def canPartitionKSubsets(self, nums: List[int], k: int) -> bool:
        total = sum(nums)
        if total % k != 0:
            return False

        target = total // k
        nums.sort(reverse=True)                 
        # big numbers first = huge speedup
        if nums[0] > target:
            return False

        groups = [0] * k
        n = len(nums)

        def dfs(idx: int) -> bool:
            if idx == n:
                return True  # if we never exceed target, sums must all be target
            x = nums[idx]
            for i in range(k):
                if groups[i] + x <= target:
                    groups[i] += x
                    if dfs(idx + 1):
                        return True
                    groups[i] -= x

                # If we tried putting x into an empty bucket and it didn't work,
                # no need to try other empty buckets (they're symmetric).
                if groups[i] == 0:
                    break

            return False

        return dfs(0)
```

Complexity:
Time: 
k * 2^n
we have k number of subset to form, and for each one, we have 2^n possible ways to choose subsets of elements

Space: 
We have used an extra array of size N to mark the already used elements.  
And the recursive tree makes at most N calls at one time, so the recursive stack also takes O(N) space. (only explores not used elements, which is less or equal to n )

Tags: #backtracking #sorting #subarray 

RL: 

Considerations:
