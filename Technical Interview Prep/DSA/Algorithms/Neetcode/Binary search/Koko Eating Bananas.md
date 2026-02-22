2025-01-24 15:48

Link:https://neetcode.io/problems/eating-bananas

Problem: ![[Screenshot 2025-01-24 at 3.50.37 PM.png]]

Motivation: 
upper bound for k is max(piles),since h will be at least bigger number of piles, so as long as we consume one pile per hour, we are fine. So we binary search suitable value for k in the range of (1, max(piles)). If we found a rate k that allow us consume in time less than h, we record it as best case, and then try a smaller k. Otherwise we try a bigger k, until j < i 

Solution:
```python
    def minEatingSpeed(self, piles: List[int], h: int) -> int:
        l, r = 1, max(piles)
        res = r
        while l <= r:
            k = (l + r) // 2
            totalTime = 0
            for p in piles:
                totalTime += math.ceil(float(p) / k)
            if totalTime <= h:
                res = k
                r = k - 1
            else:
                l = k + 1
        return res
```

Tags: #binary_search 

RL: [[Binary Search]]

Time complexity: O(n *log m)

Space complexity: O(1)