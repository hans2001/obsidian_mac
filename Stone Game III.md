2025-07-22 15:20

Link: https://neetcode.io/problems/stone-game-iii?list=neetcode250

Problem: 
Alice and Bob are playing a game with piles of stones. There are several stones arranged in a row, and each stone has an associated value which is an integer given in the array `stoneValue`.

Alice and Bob take turns, with Alice starting first. On each player's turn, that player can take `1`, `2`, or `3` stones from the **first** remaining stones in the row.

The score of each player is the sum of the values of the stones taken. The score of each player is `0` initially.

The objective of the game is to end with the highest score, and the winner is the player with the highest score and there could be a tie. The game continues until all the stones have been taken.

Assume Alice and Bob play optimally.

Return `"Alice"` if Alice will win, `"Bob"` if Bob will win, or `"Tie"` if they will end the game with the same score.
  
**Example 1:**
```java
Input: stoneValue = [2,4,3,1]

Output: "Alice"
```

Explanation: In first move, Alice will pick the first three stones (2,4,3) and in the second move Bob will pick the last remaining stone (1). The final score of Alice is (2 + 4 + 3 = 9) which is greater than the Bob's score (1).

**Example 2:**
```java
Input: stoneValue = [1,2,1,5]

Output: "Bob"
```

Explanation: In first move, Alice will pick the first three stones (1,2,1) and in the second move Bob will pick the last remaining stone (5). The final score of Alice is (1 + 2 + 1 = 4) which is lesser than the Bob's score (5).

**Example 3:**
```java
Input: stoneValue = [5,-3,3,5]

Output: "Tie"
```

Explanation: In first move, Alice will pick the first three stones (5,-3,3) and in the second move Bob will pick the last remaining stone (5). The final score of Alice is (5 + -3 + 3 = 5) which is equal to the Bob's score (5).

**Constraints:**
- `1 <= stoneValue.length <= 50,000`
- `-1000 <= stoneValue[i] <= 1000`

Intuition:
subproblem, when alice turn, we find out the balance of alice - bob score to maximize the net balance at alice turn, same for bob turn.  so the function recursively maximize the net balance for whoever payer taking stone i! at the starting point, we know that alice plays first, and we check the if max net balance is positive or 0? 

why does it work ? think in terms of decision tree
when i =0, Alice either choose first ,2 or third stone. afterwards, for each position ,bob can start taking either the next sone, the next next stone ... we are taking the path that would help the player at index i to maximize his balance! (player i max sm- opponent max sm) ()

Solution:
top down
```python
class Solution:
    def stoneGameIII(self, stoneValue: List[int]) -> str:
        n = len(stoneValue)
        dp = {}

        def dfs(i):
            if i >= n:
                return 0
            if i in dp:
                return dp[i]

            res, total = float("-inf"), 0
            for j in range(i, min(i + 3, n)):
                total += stoneValue[j]
                res = max(res, total - dfs(j + 1))

            dp[i] = res
            return res

        result = dfs(0)
        if result == 0:
            return "Tie"
        return "Alice" if result > 0 else "Bob"
```

bottom up 
```python
class Solution:
    def stoneGameIII(self, stoneValue: List[int]) -> str:
        n = len(stoneValue)
        dp = [float("-inf")] * (n + 1)
        dp[n] = 0

        for i in range(n - 1, -1, -1):
            total = 0
            for j in range(i, min(i + 3, n)):
                total += stoneValue[j]
                dp[i] = max(dp[i], total - dp[j + 1])

        result = dp[0]
        if result == 0:
            return "Tie"
        return "Alice" if result > 0 else "Bob"
```

Tags: #dp #bounded_knapsack 

RL: 

Considerations:
