2025-02-20 12:13

Link: https://neetcode.io/problems/insert-new-interval

Problem: 
You are given an array of non-overlapping intervals `intervals` where `intervals[i] = [start_i, end_i]`represents the start and the end time of the `ith` interval. `intervals` is initially sorted in ascending order by `start_i`.

You are given another interval `newInterval = [start, end]`.

Insert `newInterval` into `intervals` such that `intervals` is still sorted in ascending order by `start_i` and also `intervals` still does not have any overlapping intervals. You may merge the overlapping intervals if needed.

Return `intervals` after adding `newInterval`.

Note: Intervals are _non-overlapping_ if they have no common point. For example, [1,2] and [3,4] are non-overlapping, but [1,2] and [2,3] are overlapping.

Intuition: 
binary search: 
the input list is sorted by start_time, we just need to insert the new interval with binary_search. Then for each interval, we check if there is overlapping in one pass. we compare the last element in result with the first element in intervals, if previous end_time conflict with new start_time, we just change the old end_time to the maximum of either old or new end_time!

Greedy: 
Since the new interval is the one that will mess with the original interval, we could just update the new interval by spotting the interval that has a conflict with it, and save the merge ones to check if future interval overlaps. if not we just append the rest of the intervals to the result, if yes, we merge it by taking the minimum start _time and maximum end_time!

Solution:
Binary search: 
![[Screenshot 2025-02-21 at 12.02.39 PM.png]]
O(n) / O(1)

Greedy:
```python
class Solution:
    def insert(self, intervals: List[List[int]], newInterval: List[int]) -> List[List[int]]:
        res = []

        for i in range(len(intervals)):
            if newInterval[1] < intervals[i][0]:
                res.append(newInterval)
                return res + intervals[i:]
            elif newInterval[0] > intervals[i][1]:
                res.append(intervals[i])
            else:
                newInterval = [
                    min(newInterval[0], intervals[i][0]),
                    max(newInterval[1], intervals[i][1]),
                ]
        res.append(newInterval)
        return res
```
O(n) / O(1)

Tags: #greedy #binary_search #intervals 

RL: 

Considerations:
