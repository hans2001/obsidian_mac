2025-04-29 23:00

Link: https://leetcode.com/problems/permutations-ii/

Problem: 
Given a collection of numbers, `nums`, that might contain duplicates, return _all possible unique permutations **in any order**._

Intuition:
keep track if each element is used or not, in order to mess up the order and add later elements to the front
base case: when the length of sequence equals to the length of original array
skip duplicate element by following inserted order! so if previous was not used and this element is the same as the previous one, we should not use it! (given that we are not working on the first element!)

Solution:
```python
from collections import Counter
from math import factorial
from typing import List

class Solution:
    def permuteUnique(self, nums: List[int]) -> List[List[int]]:
        """
        Generate unique permutations of nums
        """
        n = len(nums)
        rs = []
        used = [False] * n
        nums.sort()  # Sort to get duplicates adjacent
        
        def backtrack(cur):
            # base case
            if len(cur) == n:
                rs.append(cur.copy())
                return
                
            for i in range(n):
                if used[i]:
                    continue
                    
                # Skip duplicates
                if i > 0 and nums[i] == nums[i-1] and not used[i-1]:
                    continue
                    
                used[i] = True
                cur.append(nums[i])
                backtrack(cur)
                used[i] = False
                cur.pop()
                
        backtrack([])
        return rs
```

Complexity: 
Time: O(n! * n) 
there are n! number of permutations (in recursion stack), and for each stack, we take O(n) to iterate through each one of them! 

Tags: #backtracking #set 

RL: 

Considerations:
