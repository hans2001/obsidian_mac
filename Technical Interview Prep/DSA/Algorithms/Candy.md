2025-07-22 14:07

Link: https://neetcode.io/problems/candy?list=neetcode250

Problem: 
There are `n` children standing in a line. Each child is assigned a rating value given in the integer array `ratings`.

You are giving candies to these children subjected to the following requirements:

- Each child must have at least one candy.
- Children with a higher rating get more candies than their neighbors.

Return the **minimum** number of candies you need to have to distribute the candies to the children.

**Example 1:**
```java
Input: ratings = [4,3,5]

Output: 5
```
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.

**Example 2:**
```java
Input: ratings = [2,3,3]

Output: 4
```
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.   The third child gets 1 candy because it satisfies the above two conditions.

**Constraints:**
- `1 <= ratings.length <= 20,000`
- `0 <= ratings[i] <= 20,000`

Intuition:

Solution:
```python
class Solution:
    def candy(self, ratings: List[int]) -> int:
        n = len(ratings)
        arr = [1] * n

        for i in range(1, n):
            if ratings[i - 1] < ratings[i]:
                arr[i] = arr[i - 1] + 1
        
        for i in range(n - 2, -1, -1):
            if ratings[i] > ratings[i + 1]:
                arr[i] = max(arr[i], arr[i + 1] + 1)
        
        return sum(arr)
```

Tags: #greedy #2_pass 

RL: 

Considerations: