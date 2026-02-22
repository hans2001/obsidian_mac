2025-02-20 12:13

Link: https://neetcode.io/problems/non-overlapping-intervals

Problem: 
Given an array of intervals `intervals` where `intervals[i] = [start_i, end_i]`, return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

Note: Intervals are _non-overlapping_ even if they have a common point. For example, `[1, 3]` and `[2, 4]` are overlapping, but `[1, 2]` and `[2, 3]` are non-overlapping.

Intuition:
we should keep the one end first (largest change of not having a conflict with other intervals). so we sort the intervals based on both keys, and update prevEnd as the minimum end time(conflict happened or not). Even though conflict did not happened between previous end time and new start time, by taking the interval with earlier end_time and later start time is even greater(higher chance of no conflict!) (even though the end_time will be the only contributing factor in this case), otherwise, we remove the incoming interval that create conflicts( the idea ensure minimum removal! )

recursion: (complete search) ,we either take current interval or abandon. default we abandon current interval( as if this interval will have conflict with others). if current interval start time does not overlap with previous end_time, we keep current interval, as 1 + dfs(i+1 ,i) (update prev to current index). the state we are maintaining is the maximum interval we can keep, so that result will have the least deduction!

Solution:
```python
class Solution:
    def eraseOverlapIntervals(self, intervals: List[List[int]]) -> int:
        intervals.sort()
        res = 0
        prevEnd = intervals[0][1]
        
        for start, end in intervals[1:]:
            if start >= prevEnd:
                prevEnd = end
            else:
                res += 1
                prevEnd = min(end, prevEnd)
        return res
```
O(nlogn) / O(1)

DP:
```python
class Solution:
    def eraseOverlapIntervals(self, intervals: List[List[int]]) -> int:
        intervals.sort(key=lambda x: x[1])
        n = len(intervals)
        dp = [0] * n  

        for i in range(n):
            dp[i] = 1 
            for j in range(i):
                if intervals[j][1] <= intervals[i][0]:  
                    dp[i] = max(dp[i], 1 + dp[j])

        max_non_overlapping = max(dp)  
        return n - max_non_overlapping
```
O(n^2) / O(n)

Recursion:
```python
class Solution:
    def eraseOverlapIntervals(self, intervals: List[List[int]]) -> int:
        intervals.sort()
        def dfs(i, prev):
            if i == len(intervals):
                return 0
            res = dfs(i + 1, prev)
            if prev == -1 or intervals[prev][1] <= intervals[i][0]:
                res = max(res, 1 + dfs(i + 1, i))
            return res
        
        return len(intervals) - dfs(0, -1)
```
O(2 ^ n) / O(n)

Tags: #greedy #recursion #intervals 

RL: 

Considerations:
