2025-02-21 16:05

Link: https://neetcode.io/problems/minimum-interval-including-query

Problem: 
You are given a 2D integer array `intervals`, where `intervals[i] = [left_i, right_i]` represents the `ith`interval starting at `left_i` and ending at `right_i` **(inclusive)**. 

You are also given an integer array of query points `queries`. The result of `query[j]` is the **length of the shortest interval** `i` such that `left_i <= queries[j] <= right_i`. If no such interval exists, the result of this query is `-1`.

Return an array `output` where `output[j]` is the result of `query[j]`.

Note: The length of an interval is calculated as `right_i - left_i + 1`.

Intuition:
it is important that we sort the intervals and the queries. Then, as we go through the sorted queries, if the interval cover current query, we push the interval length, along with end index as the second key, into the heap. to get the min length for each query, we remove interval ends in the heap that does not cover the query, since the queries are sorted, it wont cover future queries as well, so it is safe to remove! and we get the min length at the top of the heap!

Solution:
min heap:
```python
class Solution:
    def minInterval(self, intervals: List[List[int]], queries: List[int]) -> List[int]:
        intervals.sort()
        minHeap = []
        res = {}
        i = 0
        for q in sorted(queries):
            while i < len(intervals) and intervals[i][0] <= q:
                l, r = intervals[i]
                heapq.heappush(minHeap, (r - l + 1, r))
                i += 1

            while minHeap and minHeap[0][1] < q:
                heapq.heappop(minHeap)
            res[q] = minHeap[0][0] if minHeap else -1
        return [res[q] for q in queries]
```
O(nlogn+mlogm) / O(n + m)
sorting take most of the time, in the loop, we iterate queries in O(n) time and iterate over minHeap in O(m) time

Tags: #heap #intervals 

RL: 

Considerations:
