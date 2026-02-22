2025-07-06 15:08

Link: https://neetcode.io/problems/maximum-sum-circular-subarray?list=neetcode250

Problem: 
You are given a circular integer array `nums` of length `n`, return the maximum possible sum of a non-empty **subarray** of `nums`.

A circular array means the end of the array connects to the beginning of the array. Formally, the next element of `nums[i]` is `nums[(i + 1) % n]` and the previous element of `nums[i]` is `nums[(i - 1 + n) % n]`.

A **subarray** may only include each element of the fixed buffer `nums` at most once. Formally, for a subarray `nums[i], nums[i + 1], ..., nums[j]`, there does not exist `i <= k1, k2 <= j` with `k1 % n == k2 % n`.

**Example 1:**
```java
Input: nums = [-2,4,-5,4,-5,9,4]

Output: 15
```
Explanation: Subarray [-2,4,9,4] has maximum sum 15.

**Example 2:**
```java
Input: nums = [2,3,-4]

Output: 5
```
**Constraints:**
- `n == nums.length`
- `1 <= n <= 3 * 10,000`
- `-30,000 <= nums[i] <= 30,000`

Intuition:
in a linear way, we can solve the problem with kadane's algo to find the subarray that reach the max sum ,however, we should also consider min case, where if wrapper around case exists, the middle portion is to be skipped! that is why keeping the global min sum can do. finally we compare the maximum linear case (wrapped min case is ignored!) with the wrapped case(where the min subarray in middle is skipped) to find out the global maximum!

edge case: 
if linear max is negative, total - global min can be positive, resulting a wrong response(either a positive result is returned or 0 is returned( none of the element was taken)). so we only use the max comparison func only if global max is positive! 
Solution:

Tags: #kadane  #greedy

RL: 

Considerations:
