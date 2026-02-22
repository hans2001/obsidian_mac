2025-02-20 15:10

Link: https://neetcode.io/problems/meeting-schedule-ii

Problem: 
Given an array of meeting time interval objects consisting of start and end times `[[start_1,end_1],[start_2,end_2],...] (start_i < end_i)`, find the minimum number of days required to schedule all meetings without any conflicts.

Intuition:
2 pointer: 
use count to track concurrent meeting, if the start time is smaller than the meeting end time(end pointer) the count will increase as number of concurrent meeting increases. otherwise, reducing count mean the new meeting actually start after the previous meeting, decrement concurrent meeting count by one. so the result will be the maximum concurrent meeting, since other meeting can fit in one day, but the concurrent meetings cannot fitted into one day!
(similar to the greedy approach!)

min-heap: 
first sort the intervals based on start time (important!)
use end time to signal reach room' s characters, by comparing starting time of the following meeting to rooms with earliest end time, we know that if we need a new room. if not, we release the room that is compatible and assign a new room with the updated end time (just like the idea of reusing a room). if we need a new room, nothing is popped from the heap ,and a new room is pushed in 

Solution:
2 pointer
```python
class Solution:
    def minMeetingRooms(self, intervals: List[Interval]) -> int:
        start = sorted([i.start for i in intervals])
        end = sorted([i.end for i in intervals])
        
        res = count = 0
        s = e = 0
        while s < len(intervals):
            if start[s] < end[e]:
                s += 1
                count += 1
            else:
                e += 1
                count -= 1
            res = max(res, count)
        return res
```
O(n log n) / O(n)
heap operation takes log n time for heap of size n (for each number: nlogn)

min-heap:
```python
class Solution:
    def minMeetingRooms(self, intervals: List[Interval]) -> int:
        intervals.sort(key=lambda x: x.start)
        min_heap = []

        for interval in intervals:
            if min_heap and min_heap[0] <= interval.start:
                heapq.heappop(min_heap)
            heapq.heappush(min_heap, interval.end)

        return len(min_heap)
```
O(n log n) / O(n)
sorting: nlogn, O(n) time for the 2 pointer part! 

Greedy:
```python
class Solution:
    def minMeetingRooms(self, intervals: List[Interval]) -> int:
        time = []
        for i in intervals:
            time.append((i.start, 1))
            time.append((i.end, -1))
        
        time.sort(key=lambda x: (x[0], x[1]))
        
        res = count = 0
        for t in time:
            count += t[1]
            res = max(res, count)
        return res
```
O(n log n) / O(n)

Tags: #heap #greedy #intervals #2_pointer 

RL: [[Meeting Room]]

Considerations:
