2025-02-02 14:04

Link:https://neetcode.io/problems/k-closest-points-to-origin

Problem: 
You are given an 2-D array `points` where `points[i] = [xi, yi]` represents the coordinates of a point on an X-Y axis plane. You are also given an integer `k`.

Return the `k` closest points to the origin `(0, 0)`. 

The distance between two points is defined as the Euclidean distance (`sqrt((x1 - x2)^2 + (y1 - y2)^2))`.

You may return the answer in **any order**.

Motivation:
maintain a heap of size k

quick_select: 
partition subarrays until pivot returned is at the kth position, therefore quick_sorte can be stopped! 

Solution:
```python
class Solution:
    def kClosest(self, points: List[List[int]], k: int) -> List[List[int]]:
        maxHeap = []
        for x, y in points:
            dist = -(x ** 2 + y ** 2)
            heapq.heappush(maxHeap, [dist, x, y])
            if len(maxHeap) > k:
                heapq.heappop(maxHeap)
        
        res = []
        while maxHeap:
            dist, x, y = heapq.heappop(maxHeap)
            res.append([x, y])
        return res
```
O(n *log k)(k is number of points to be returned) / O(k)

Quick_select: 
```python
class Solution:
    def kClosest(self, points, k):
        euclidean = lambda x: x[0] ** 2 + x[1] ** 2
        def partition(l, r):
            pivotIdx = r
            pivotDist = euclidean(points[pivotIdx])
            i = l
            for j in range(l, r):
                if euclidean(points[j]) <= pivotDist:
                    points[i], points[j] = points[j], points[i]
                    i += 1
            points[i], points[r] = points[r], points[i]
            return i

        L, R = 0, len(points) - 1
        pivot = len(points)

        while pivot != k:
            pivot = partition(L, R)
            if pivot < k:
                L = pivot + 1
            else:
                R = pivot - 1
        return points[:k]
```
O(n) -> avg case O(n^2) -> worst case / O(1)

Tags: #max_heap #heap #quick_select

RL: [[Quick Sort]]