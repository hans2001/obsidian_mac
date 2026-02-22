2025-05-12 14:36

Link:

Problem: 
counter number of wasy to use any coin any number of timse to reach amount( unique combinations)

Intuition:
prune the path that tries all coins ,that made remain smaller than 0 or that do not have a past state
Solution:
```python
class Solution:
    def change(self, amount: int, coins: List[int]) -> int:
        def numberOfWays(i: int, amount: int) -> int:
            if amount == 0:
                return 1
            if i == len(coins):
                return 0
            if memo[i][amount] != -1:
                return memo[i][amount]

            if coins[i] > amount:
                memo[i][amount] = numberOfWays(i + 1, amount)
            else:
                memo[i][amount] = numberOfWays(i, amount - coins[i]) + numberOfWays(i + 1, amount)
            
            return memo[i][amount]

        memo = [[-1] * (amount + 1) for _ in range(len(coins))]
        return numberOfWays(0, amount)
```
Complexity:

Tags: 

RL: 

Considerations:
