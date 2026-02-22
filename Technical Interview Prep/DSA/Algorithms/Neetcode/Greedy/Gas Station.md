2025-02-18 19:36

Link: https://neetcode.io/problems/gas-station

Problem: 
There are `n` gas stations along a circular route. You are given two integer arrays `gas` and `cost` where:

- `gas[i]` is the amount of gas at the `ith` station.
- `cost[i]` is the amount of gas needed to travel from the `ith` station to the `(i + 1)th` station. (The last station is connected to the first station)

You have a car that can store an unlimited amount of gas, but you begin the journey with an empty tank at one of the gas stations.

Return the starting gas station's index such that you can travel around the circuit once in the clockwise direction. If it's impossible, then return `-1`.

It's guaranteed that at most one solution exists.

Motivation:
Greedy:
if a starting index works, we keep checking if we can maintain a positive net balance.

First, we check if the total gas is bigger or equal to the total cost, if yes, that mean we have a unique starting position that allow us to complete a cycle! to find the starting position, we compute a net balance from each position to the end. if the net balance is positive or 0 for the right portion, we don't need to check the remaining left portion, since the remaining cost and gas will cancel out!

2 pointer: 
We keep track of the net balance as fuel in tank, and by extending the sliding window, we can locate the starting position! the right pointer starts at the end, if fuel is insufficient (tank<0) ,we try a new starting position by moving the pointer to the left. if the fuel is sufficient, we check if the tank can cover the remaining portion by updating the tank and increment the left pointer!

Solution:
greedy
```python
class Solution:
    def canCompleteCircuit(self, gas: List[int], cost: List[int]) -> int:
        if sum(gas) < sum(cost):
            return -1

        total = 0
        res = 0
        for i in range(len(gas)):
            total += (gas[i] - cost[i])

            if total < 0:
                total = 0
                res = i + 1
        
        return res
```
O(n) / O(1)

2-pointer
```python
class Solution:
    def canCompleteCircuit(self, gas: List[int], cost: List[int]) -> int:
        n = len(gas)
        start, end = n - 1, 0
        tank = gas[start] - cost[start]
        while start > end:
            if tank < 0:
                start -= 1
                tank += gas[start] - cost[start]
            else:
                tank += gas[end] - cost[end]
                end += 1
        return start if tank >= 0 else -1
```
O(n) / O(1)

Tags: #greedy #2_pointer #sliding_window 

RL: 

Considerations:
