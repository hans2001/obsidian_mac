
2025-04-22 11:05

Link: https://leetcode.com/problems/two-city-scheduling/description/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem: 
A company is planning to interview `2n` people. Given the array `costs` where `costs[i] = [aCosti, bCosti]`, the cost of flying the `ith` person to city `a` is `aCosti`, and the cost of flying the `ith` person to city `b` is `bCosti`.

Return _the minimum cost to fly every person to a city_ such that exactly `n` people arrive in each city.

Intuition:
we can sort the array by difference between cost going to city vs to city B. For example, if person X costs $100 to send to A and $300 to send to B, while person Y costs $200 to send to A and $250 to send to B:

- Person X's difference is $100 - $300 = -$200 (much cheaper to send to A)
- Person Y's difference is $200 - $250 = -$50 (somewhat cheaper to send to A)
after that, we can send the first half to city A, and the second half to city B! 

Solution:
```python
class Solution:
    def twoCitySchedCost(self, costs: List[List[int]]) -> int:
        # Sort by a gain which company has 
        # by sending a person to city A and not to city B
        costs.sort(key = lambda x : x[0] - x[1])
        
        total = 0
        n = len(costs) // 2
        # To optimize the company expenses,
        # send the first n persons to the city A
        # and the others to the city B
        for i in range(n):
            total += costs[i][0] + costs[i + n][1]
        return total
```

Tags: #greedy 

RL: 

Considerations:
