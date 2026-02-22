2025-04-19 21:11

Link: https://neetcode.io/problems/daily-temperatures

Problem: 
You are given an array of integers `temperatures` where `temperatures[i]` represents the daily temperatures on the `ith`day.

Return an array `result` where `result[i]` is the number of days after the `ith` day before a warmer temperature appears on a future day. If there is no day in the future where a warmer temperature will appear for the `ith` day, set `result[i]` to `0`instead.

Intuition:
maintain a monotonic decreasing stack, pop elements when elements from stack smaller than current element! and update the result arr with the index difference! 

Solution:
Stack
```python
class Solution:
    def dailyTemperatures(self, temperatures: List[int]) -> List[int]:
        res = [0] * len(temperatures)
        stack = []  # pair: [temp, index]

        for i, t in enumerate(temperatures):
            while stack and t > stack[-1][0]:
                stackT, stackInd = stack.pop()
                res[stackInd] = i - stackInd
            stack.append((t, i))
        return res
```

Tags: #stack  #mono_stack

RL: 

Considerations:
