2025-12-10 14:01

Link: https://neetcode.io/problems/grumpy-bookstore-owner/question

Problem: 
There is a bookstore owner that has a store open for `n` minutes. You are given an integer array `customers` of length `n` where `customers[i]` is the number of the customers that enter the store at the start of the `ith` minute and all those customers leave after the end of that minute.

During certain minutes, the bookstore owner is grumpy. You are given a binary array `grumpy`where `grumpy[i]` is `1` if the bookstore owner is grumpy during the `ith` minute, and is `0`otherwise.

When the bookstore owner is grumpy, the customers entering during that minute are **not satisfied**. Otherwise, they are **satisfied**.

The bookstore owner knows a secret technique to remain **not grumpy** for `minutes`consecutive minutes, but this technique can only be used **once**.

Return the **maximum** number of customers that can be satisfied throughout the day.

**Example 1:**
```java
Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], minutes = 3

Output: 16
```

Explanation: 
- The bookstore owner keeps themselves not grumpy for the last 3 minutes.
- The maximum number of customers that can be satisfied = 1 + 1 + 1 + 1 + 7 + 5 = 16.
    
**Example 2:**
```java
Input: customers = [10,1,7], grumpy = [0,0,0], minutes = 2

Output: 18
```
Explanation: The bookstore owner is already not grumpy for all the customers.

**Constraints:**
- `n == customers.length == grumpy.length`
- `1 <= minutes <= n <= 20,000`
- `0 <= customers[i] <= 1000`
- `grumpy[i]` is either `0` or `1`.

Intuition:
u should return the total number of customers that are satisfied. so customer that are satisfied already should be all countede, and we use the sliding windwo to compute for extra customer that cna made satisfied after applying the technique for minutes! 

Solution:
```python
from typing import List

class Solution:
    def maxSatisfied(self, customers: List[int], grumpy: List[int], minutes: int) -> int:
        n = len(customers)

        # base: customers already satisfied without the technique
        base = 0
        for i in range(n):
            if grumpy[i] == 0:
                base += customers[i]

        # sliding window: extra customers we can "save" in a window of length `minutes`
        extra = 0
        cur = 0
        l = 0

        for r in range(n):
            if grumpy[r] == 1:
                cur += customers[r]

            # keep window length <= minutes
            if r - l + 1 > minutes:
                if grumpy[l] == 1:
                    cur -= customers[l]
                l += 1

            extra = max(extra, cur)
        return base + extra
```

Tags: #sliding_window 

RL: 

Considerations:
