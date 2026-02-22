2025-05-13 18:14

Link: https://neetcode.io/problems/car-pooling

Problem: 
There is a car with `capacity` empty seats. The vehicle only drives east (i.e., it cannot turn around and drive west).

You are given the integer `capacity` and a integer array `trips` where `trips[i] = [numPassengers[i], from[i], to[i]]` indicates that the `ith` trip has `numPassengers[i]` passengers and the locations to pick them up and drop them off are `from[i]` and `to[i]` respectively. The locations are given as the number of kilometers due east from the car's initial location.

Return `true` if it is possible to pick up and drop off all passengers for all the given trips, or `false` otherwise.

Intuition:
maintain current passenger number, as we proceed with trips, we load passengers onto the bus, if the current trip_point( form_i ) exceed some previous's trip to_i, we could already drop the passenger off by reducing the current passenger number! if at some point, current passenger number exceed the capacity, the function should return False, otherwise when all trip finished without violation ,we return True

Solution:
min heap: 
```python
class Solution:
    def carPooling(self, trips: List[List[int]], capacity: int) -> bool:
        trips.sort(key=lambda t: t[1])
        
        minHeap = []  # pair of [end, numPassengers]
        curPass = 0
        
        for numPass, start, end in trips:
            while minHeap and minHeap[0][0] <= start:
                curPass -= heapq.heappop(minHeap)[1]
            
            curPass += numPass
            if curPass > capacity:
                return False
            
            heapq.heappush(minHeap, [end, numPass])
        
        return True
```

Complexity:
Time: 
O(n log n)
n iteration of heap operations! 

Space: 
O(n) where n is the size of the heap

Tags: #amazon #heap 

RL: 

Considerations:
