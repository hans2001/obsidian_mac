2025-02-02 13:00

Link:https://neetcode.io/problems/last-stone-weight

Problem: 
You are given an array of integers `stones` where `stones[i]` represents the weight of the `ith`stone.

We want to run a simulation on the stones as follows:

- At each step we choose the **two heaviest stones**, with weight `x` and `y` and smash them togethers
- If `x == y`, both stones are destroyed
- If `x < y`, the stone of weight `x` is destroyed, and the stone of weight `y` has new weight `y - x`.

Continue the simulation until there is no more than one stone remaining.

Return the weight of the last remaining stone or return `0` if none remain.

Motivation:
bucket sort: 
when range of stone value is limited, we can use bucket to reduce the Algo time complexity to O(n+w). We locate the maximum value with the first pointer, if the number of stones is aone with value abs(first-second), otherwise we decrement j to valid second stone. if no valid second stone ,the stone pointed by first will be the unpaired stone! 

Solution:
Heap: 
```python
class Solution:
    def lastStoneWeight(self, stones: List[int]) -> int:
        stones = [-s for s in stones]
        heapq.heapify(stones)

        while len(stones) > 1:
            first = heapq.heappop(stones)
            second = heapq.heappop(stones)
            if second > first:
                heapq.heappush(stones, first - second)

        stones.append(0)
        return abs(stones[0])
```
O(n log n) / O(n)

Bucket Sort:
```python
class Solution:
    def lastStoneWeight(self, stones: List[int]) -> int:

        maxStone = max(stones)
        bucket = [0] * (maxStone + 1)
        for stone in stones:
            bucket[stone] += 1
        
        first = second = maxStone
        while first > 0:
            if bucket[first] % 2 == 0:
                first -= 1
                continue
            
            j = min(first - 1, second)
            while j > 0 and bucket[j] == 0:
                j -= 1
            
            if j == 0:
                return first
            second = j
            bucket[first] -= 1
            bucket[second] -= 1
            bucket[first - second] += 1
            first = max(first - second, second)
        return first
```
O (m +w) (w is the maximum value in the stones array) / O(w)

Tags: #heap

RL: 