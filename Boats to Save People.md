2025-05-05 20:16

Link: https://neetcode.io/problems/boats-to-save-people

Problem: 
You are given an integer array `people` where `people[i]` is the weight of the `ith` person, and an **infinite number of boats** where each boat can carry a maximum weight of `limit`. Each boat carries **at most** two people at the same time, provided the sum of the weight of those people is at most `limit`.

Return the **minimum** number of boats to carry every given person.

Intuition:
to greedily form boat pairs, we should combine the people with larger weight to the small weight, so that we best utilize space left on the boat, just like filling up a jar to different size of Stones! (we should fill the larger one first, then fill rest of the space with the smaller stones, so that we come up with the best weight (given that different stone has different weight !))

Solution:
```python
class Solution:
    def numRescueBoats(self, people: List[int], limit: int) -> int:
        m = max(people)
        count = [0] * (m + 1)
        for p in people:
            count[p] += 1
        
        idx, i = 0, 1
        while idx < len(people):
            while count[i] == 0:
                i += 1
            people[idx] = i
            count[i] -= 1
            idx += 1

        res, l, r = 0, 0, len(people) - 1
        while l <= r:
            remain = limit - people[r]
            r -= 1
            res += 1
            if l <= r and remain >= people[l]:
                l += 1
        return res
```

Complexity:
Time: 
O(n + k)
n is length or people array ,and k is the range between the min element and the max element, good for when range of element value is small or fixed! 

Space:
O(k)

Tags: #counting_sort #2_pointer 

RL: 

Considerations:
