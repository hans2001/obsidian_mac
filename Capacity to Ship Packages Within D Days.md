2025-05-07 10:57

Link: https://neetcode.io/problems/capacity-to-ship-packages-within-d-days

Problem: 
A conveyor belt has packages that must be shipped from one port to another within `days` days.

The `ith` package on the conveyor belt has a weight of `weights[i]`. Each day, we load the ship with packages on the conveyor belt (in the order given by `weights`). It is not allowed to load weight more than the **maximum weight capacity** of the ship.

Return the **least weight capacity** of the ship that will result in all the packages on the conveyor belt being shipped within `days` days.

Intuition:
since we would like to know the maximum capacity needed for all items to be exported within days! we perform binary search on the capacity. the lower bound would be the max weight, since at least we have to export that in a single day , so cap must be bigger or equal to that! the upper bound would be sum of all weights, assume we want to export all items within one day! 
for finding the day required with certain cap, we assemble items in a greedy way, since the order of items cannot be changed, there is no need in considering the best combination etc!  
!important: as we included if day <= days, we can exclude mid since mid might be the cap we need, as we implicitly handled the equal side here!  

Solution:
binary search: 
```python
class Solution:
    def shipWithinDays(self, weights: List[int], days: int) -> int:
        l, r = max(weights), sum(weights)
        res = r

        def canShip(cap):
            ships, currCap = 1, cap
            for w in weights:
                if currCap - w < 0:
                    ships += 1
                    if ships > days:
                        return False
                    currCap = cap

                currCap -= w
            return True

        while l <= r:
            cap = (l + r) // 2
            if canShip(cap):
                res = min(res, cap)
                r = cap - 1
            else:
                l = cap + 1

        return res
```

Complexity:
Time:
O(n) for nested lp
O(log n) for binary search
total :O(n * log n)

Space:
O(1)

Tags: #binary_search 

RL: 

Considerations:
