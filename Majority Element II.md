2025-05-05 16:25

Link: https://neetcode.io/problems/majority-element-ii

Problem: 
You are given an integer array `nums` of size `n`, find all elements that appear more than `⌊ n/3 ⌋` times. You can return the result in any order

Intuition:
k = 3, there will be at most k-1 candidate that can appear more than n/k times!
in first pass we can locate such candidate without error, in second pass, we check if the count is actually exceed n/3 to handle edge cases! 

Solution:
```python
class Solution:
    def majorityElement(self, nums: List[int]) -> List[int]:
        n = len(nums)
        num1 = num2 = -1
        cnt1 = cnt2 = 0

        for num in nums:
            if num == num1:
                cnt1 += 1
            elif num == num2:
                cnt2 += 1
            elif cnt1 == 0:
                cnt1 = 1
                num1 = num
            elif cnt2 == 0:
                cnt2 = 1
                num2 = num
            else:
                cnt1 -= 1
                cnt2 -= 1
        
        cnt1 = cnt2 = 0
        for num in nums:
            if num == num1:
                cnt1 += 1
            elif num == num2:
                cnt2 += 1
        
        res = []
        if cnt1 > n // 3:
            res.append(num1)
        if cnt2 > n // 3:
            res.append(num2)
        
        return res
```

Complexity:
Time: 
O(2* n)

Space: 
O(1)

Tags: #voting

RL: [[Boyer Voting Algo]]

Considerations:
