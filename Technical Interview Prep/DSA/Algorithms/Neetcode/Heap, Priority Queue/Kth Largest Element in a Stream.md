2025-02-02 12:29

Link:https://neetcode.io/problems/kth-largest-integer-in-a-stream

Problem: 
Design a class to find the `kth` largest integer in a stream of values, including duplicates. E.g. the `2nd` largest from [1, 2, 3, 3] is `3`. The stream is not necessarily sorted.

Implement the following methods:

- `constructor(int k, int[] nums)` Initializes the object given an integer `k` and the stream of integers `nums`.
- `int add(int val)` Adds the integer `val` to the stream and returns the `kth` largest integer in the stream.

Motivation:
maintain a min-heap with capacity of k. Then the K-th largest element will be at top of the heap naturally! 

Solution:
```python
class KthLargest:
    
    def __init__(self, k: int, nums: List[int]):
        self.minHeap, self.k = nums, k
        heapq.heapify(self.minHeap)
        while len(self.minHeap) > k:
            heapq.heappop(self.minHeap)

    def add(self, val: int) -> int:
        heapq.heappush(self.minHeap, val)
        if len(self.minHeap) > self.k:
            heapq.heappop(self.minHeap)
        return self.minHeap[0]
```

Tags: #min-heap #heap

RL: 