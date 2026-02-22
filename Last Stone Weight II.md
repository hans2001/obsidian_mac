2025-05-29 12:32

Link: https://neetcode.io/problems/last-stone-weight-ii?list=neetcode250

Problem: 
You are given an array of integers `stones` where `stones[i]` is the weight of the `ith`stone.

We are playing a game with the stones. On each turn, we choose any two stones and smash them together. Suppose the stones have weights `x` and `y` with `x <= y`. The result of this smash is:

- If `x == y`, both stones are destroyed, and
- If `x != y`, the stone of weight `x` is destroyed, and the stone of weight `y` has new weight `y - x`.

At the end of the game, there is **at most one** stone left.

Return the **smallest** possible weight of the left stone. If there are no stones left, return `0`.

**Example 1:**
```java
Input: stones = [2,4,1,5,6,3]

Output: 1
```

Explanation: 
1. We smash 2 and 1 which makes the array [1,4,5,6,3].
2. We smash 4 and 3 which makes the array [1,1,5,6].
3. We smash 5 and 6 which makes the array [1,1,1].
4. We smash 1 and 1 which makes the array [1].

**Example 2:**
```java
Input: stones = [4,4,1,7,10]

Output: 2
```

**Constraints:**
- `1 <= stones.length <= 30`
- `1 <= stones[i] <= 100`

Intuition:
This is a 0/1 knapsack problem where we decide to include or exclude each stone. The key insight is that the stone smashing process ultimately assigns each stone a + or - sign, which is equivalent to partitioning stones into two subsets. The final stone weight equals the minimum possible |sum(S1) - sum(S2)|.

In our recursive solution, `total` tracks one subset's sum. When we don't include a stone, it implicitly belongs to the other subset. We stop exploring when `total >= ceiling(sum/2)` because `total` is now the larger subset, or when we've processed all stones.

The DFS explores all 2^n possible partitions by making binary decisions at each stone, and returns the minimum difference found across all valid partitions.

Solution:
top down + memoization
```python
class Solution:
    def lastStoneWeightII(self, stones: List[int]) -> int:
        stoneSum = sum(stones)
        target = (stoneSum + 1) // 2
        dp = {}

        def dfs(i, total):
            if total >= target or i == len(stones):
                return abs(total - (stoneSum - total))
            if (i, total) in dp:
                return dp[(i, total)]

            dp[(i, total)] = min(dfs(i + 1, total), dfs(i + 1, total + stones[i]))
            return dp[(i, total)]

        return dfs(0, 0)
```
O(2^n) / O(min(n,m)) for recursion stack 

bottom-up 
```python
class Solution:
    def lastStoneWeightII(self, stones: List[int]) -> int:
        stoneSum = sum(stones)
        target = stoneSum // 2
        n = len(stones)

        dp = [[0] * (target + 1) for _ in range(n + 1)]

        for i in range(1, n + 1):
            for t in range(target + 1):
                if t >= stones[i - 1]:
                    dp[i][t] = max(dp[i - 1][t], dp[i - 1][t - stones[i - 1]] + stones[i - 1])
                else:
                    dp[i][t] = dp[i - 1][t]

        return stoneSum - 2 * dp[n][target]
```

Tags: #2d_dp  #memoization #bounded_knapsack 

RL: [[Partition Equal Subset Sum]]

Considerations:
