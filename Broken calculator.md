2025-11-24 15:48

Link:https://leetcode.com/problems/broken-calculator/description/?envType=company&envId=millennium&favoriteSlug=millennium-all

Problem: 
There is a broken calculator that has the integer `startValue` on its display initially. In one operation, you can:

- multiply the number on display by `2`, or
- subtract `1` from the number on display.

Given two integers `startValue` and `target`, return _the minimum number of operations needed to display_ `target` _on the calculator_.

**Example 1:**
**Input:** startValue = 2, target = 3
**Output:** 2
**Explanation:** Use double operation and then decrement operation {2 -> 4 -> 3}.

**Example 2:**
**Input:** startValue = 5, target = 8
**Output:** 2
**Explanation:** Use decrement and then double {5 -> 4 -> 8}.

**Example 3:**
**Input:** startValue = 3, target = 10
**Output:** 3
**Explanation:** Use double, decrement and double {3 -> 6 -> 5 -> 10}.

**Constraints:**
- `1 <= startValue, target <= 109`

Intuition:
we use deploy greedy once we start form the target, because divide by 2 always takes more step than  +1 to reach a larger number, that is why it is one way comparison now. alue, we have to consider cases where -1 then * 2 might help us utilize smaller number of steps! 

Solution:
```python
class Solution:
    def brokenCalc(self, startValue: int, target: int) -> int:
        ans = 0
        while target > startValue:
            ans += 1
            if target % 2: target += 1
            else: target //= 2

        return ans + startValue - target
```

Tags: #greedy 

RL: 

Considerations:
