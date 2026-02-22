2025-02-03 15:25

Link:https://neetcode.io/problems/find-median-in-a-data-stream

Problem: 
The **[median](https://en.wikipedia.org/wiki/Median)** is the middle value in a sorted list of integers. For lists of _even_ length, there is no middle value, so the median is the [mean](https://en.wikipedia.org/wiki/Mean)of the two middle values.

For example:
- For `arr = [1,2,3]`, the median is `2`.
- For `arr = [1,2]`, the median is `(1 + 2) / 2 = 1.5`

Implement the MedianFinder class:
- `MedianFinder()` initializes the `MedianFinder` object.
- `void addNum(int num)` adds the integer `num` from the data stream to the data structure.
- `double findMedian()` returns the median of all elements so far.

Motivation:
addNum / Init:
use 2 heap to divide the dynamic array into 2 segments. the max heap will be storing elements in the left segments, and min heap for the other half. after insertion, make sure to perform rebalance on both heaps, so that the offset of their sizes are at most 1, before any further insertion!. 

FindMedian:
let m and n be the size of the heaps respectively. if m == n (m + n is even number if m == n), median will be the mean of first element in both heap. otherwise, median is at the top of the heap that has more elements ( the left alone! )

Solution:
Editorial:
```python
class MedianFinder:
    def __init__(self):
        # two heaps, large, small, minheap, maxheap
        # heaps should be equal size
        self.small, self.large = [], []  

    def addNum(self, num: int) -> None:
        if self.large and num > self.large[0]:
            heapq.heappush(self.large, num)
        else:
            heapq.heappush(self.small, -1 * num)

        if len(self.small) > len(self.large) + 1:
            val = -1 * heapq.heappop(self.small)
            heapq.heappush(self.large, val)
        if len(self.large) > len(self.small) + 1:
            val = heapq.heappop(self.large)
            heapq.heappush(self.small, -1 * val)

    def findMedian(self) -> float:
        if len(self.small) > len(self.large):
            return -1 * self.small[0]
        elif len(self.large) > len(self.small):
            return self.large[0]
        return (-1 * self.small[0] + self.large[0]) / 2.0
```
O(m∗logn) for addNum() / O(m) for findMedian()
m is the number of function calls, and n is length of array

review: 
![[Screenshot 2025-10-17 at 2.08.05 PM.png]]

Tags: #find_the_running_median #heap

RL: 