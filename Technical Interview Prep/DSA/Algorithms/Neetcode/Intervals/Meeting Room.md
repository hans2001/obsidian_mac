2025-02-20 12:13

Link: https://neetcode.io/problems/meeting-schedule

Problem: 
Given an array of meeting time interval objects consisting of start and end times `[[start_1,end_1],[start_2,end_2],...] (start_i < end_i)`, determine if a person could add all meetings to their schedule without any conflicts.

Intuition:
sorted by primary key first ,then we dont need to check if they are arranged by the starting time. then we check if the ending time of previous meeting is later than the starting time of current meeting!

Solution:
```python
class Solution:
    def canAttendMeetings(self, intervals: List[Interval]) -> bool:
        intervals.sort(key=lambda i: i.start)

        for i in range(1, len(intervals)):
            i1 = intervals[i - 1]
            i2 = intervals[i]

            if i1.end > i2.start:
                return False
        return True
```
O(nlog⁡n) / O(1)

Tags: #intervals #sorting

RL: 

Considerations:
