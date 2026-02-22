2025-12-26 16:38

Link: https://neetcode.io/problems/campus-bikes/question?list=allNC

Problem: 
On a campus represented on the X-Y plane, there are `n` workers and `m` bikes, with `n <= m`.

You are given an array `workers` of length `n` where `workers[i] = [xᵢ, yᵢ]` is the position of the `iᵗʰ` worker. You are also given an array `bikes` of length `m` where `bikes[j] = [xⱼ, yⱼ]` is the position of the `jᵗʰ` bike. All the given positions are **unique**.

Assign a bike to each worker. Among the available bikes and workers, we choose the `(workerᵢ, bikeⱼ)` pair with the **shortest Manhattan distance** between each other and assign the bike to that worker.

If there are multiple `(workerᵢ, bikeⱼ)` pairs with the same shortest **Manhattan distance**, we choose the pair with **the smallest worker index**. If there are multiple ways to do that, we choose the pair with **the smallest bike index**. Repeat this process until there are no available workers.

Return _an array_ `answer` _of length_ `n`, _where_ `answer[i]` _is the index (**0-indexed**) of the bike that the_ `iᵗʰ` _worker is assigned to_.

The **Manhattan distance** between two points `p1` and `p2` is `Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|`.

**Example 1:**
```java
Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]

Output: [1,0]
```

Explanation:  
Worker 1 grabs Bike 0 as they are closest (without ties), and Worker 0 is assigned Bike 1. So the output is [1, 0].

**Example 2:**
```java
Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]

Output: [0,2,1]
```

Explanation:  
Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2, thus Worker 1 is assigned to Bike 2, and Worker 2 will take Bike 1. So the output is [0,2,1].

**Constraints:**
- `n == workers.length`
- `m == bikes.length`
- `1 <= n <= m <= 1000`
- `workers[i].length == bikes[j].length == 2`
- `0 <= xᵢ, yᵢ < 1000`
- `0 <= xⱼ, yⱼ < 1000`
- All worker and bike locations are **unique**.

failure: 
dont know how we should make a proper select, and did not think resolving it in a global manner, and did not think of computing future knowledge! (test approach does not work without global knowledge! )

Intuition:
find globally smallest pair for manhanttan index, if same dis exist ,then u sort by worker index and then by index ,this can be easily sorted by the global pair of tuples order per the requirements( (distance, worker_index, bike_index) )

When you pop the smallest (d, i, j):
- If i and j are both free → this **must** be the correct next assignment
    (because nothing smaller exists anywhere)
- If not → that pair is invalid forever, and skipping it cannot affect correctness

- the problem statement **forbids** reconsidering it
- future choices only get worse or equal in distance

So the decision is **locally optimal and globally forced**.
This is not a matching problem — it’s a **deterministic greedy simulation**.

Solution:
```python
from heapq import heappush, heappop
from typing import List

class Solution:
    def assignBikes(self, workers: List[List[int]], bikes: List[List[int]]) -> List[int]:
        hp = []
        m, n = len(workers), len(bikes)

        for i in range(m):
            wx, wy = workers[i]
            for j in range(n):
                bx, by = bikes[j]          # <-- fix: bikes[j], not bikes[i]
                dist = abs(wx - bx) + abs(wy - by)
                heappush(hp, (dist, i, j)) # tie-break works via tuple order

        bike_used = [False] * n
        worker_done = [False] * m
        ans = [-1] * m
        remaining = m

        while remaining:
            dist, i, j = heappop(hp)
            if worker_done[i] or bike_used[j]:
                continue
            worker_done[i] = True
            bike_used[j] = True
            ans[i] = j
            remaining -= 1

        return ans
```

- Building heap of all pairs: m*n pushes → **O(mn log(mn))**
- Space: heap size mn → **O(mn)**

Tags: #heap  #greedy 

RL: 

Considerations:
