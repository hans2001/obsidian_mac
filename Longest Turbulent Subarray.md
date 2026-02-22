2025-07-07 10:27

Link: https://neetcode.io/problems/longest-turbulent-subarray?list=neetcode250

Problem: 
You are given an integer array `arr`, return the length of a maximum size turbulent subarray of `arr`.

A subarray is **turbulent** if the comparison sign flips between each adjacent pair of elements in the subarray.

More formally, a subarray `[arr[i], arr[i + 1], ..., arr[j]]` of `arr` is said to be turbulent if and only if:

- For `i <= k < j`:
    
    - `arr[k] > arr[k + 1]` when `k` is odd, and
    - `arr[k] < arr[k + 1]` when `k` is even.

- Or, for `i <= k < j`:

    - `arr[k] > arr[k + 1]` when `k` is even, and
    - `arr[k] < arr[k + 1]` when `k` is odd.

**Example 1:**
```java
Input: arr = [2,4,3,2,2,5,1,4]

Output: 4
```
Explanation: The longest turbulent subarray is [2,5,1,4].

**Example 2:**
```java
Input: arr = [1,1,2]

Output: 2
```

Intuition:
for finding longest subarray ,we could use sliding window and make sure the elements in the sliding window fulfilled the condition, which we can use the length of that to update the max length of subarray found! if invalid pair is found, we should increment the left pointer to a position where valid pair would start, we could continue increment the right pointer ( enlarge window size ), if the new pair found is a valid component for the turbulent subarray!
greedy because we dont backtrack decisions

Solution:
```python
class Solution:
    def maxTurbulenceSize(self, arr: List[int]) -> int:
        l, r, res, prev = 0, 1, 1, ""

        while r < len(arr):
            if arr[r - 1] > arr[r] and prev != ">":
                res = max(res, r - l + 1)
                r += 1
                prev = ">"
            elif arr[r - 1] < arr[r] and prev != "<":
                res = max(res, r - l + 1)
                r += 1
                prev = "<"
            else:
                r = r + 1 if arr[r] == arr[r - 1] else r
                l = r - 1
                prev = ""

        return res
```

Tags: #sliding_window  #dp #greedy 

RL: 

Considerations:
