2025-01-24 15:58

Link:https://neetcode.io/problems/find-minimum-in-rotated-sorted-array

Problem: 
You are given an array of length `n` which was originally sorted in ascending order. It has now been **rotated** between `1` and `n` times. For example, the array `nums = [1,2,3,4,5,6]` might become:

- `[3,4,5,6,1,2]` if it was rotated `4` times.
- `[1,2,3,4,5,6]` if it was rotated `6` times.

Notice that rotating the array `4` times moves the last four elements of the array to the beginning. Rotating the array `6` times produces the original array.

Assuming all elements in the rotated sorted array `nums` are **unique**, return the minimum element of this array.

A solution that runs in `O(n)` time is trivial, can you write an algorithm that runs in `O(log n) time`?

Motivation:
by comparing mid and right element, we know that if mid and right element are in the same sorted subarray. The result should be the starting point of the right sorted subarray, so for the case of nums[md] < nums[j],we should consider the chance that nums[md] is actually the result, so we turn j = md. As for while loop condition (i < j), we know if nums[i] will be the answer if i == j ,so we dont have to go through an additional loop.

Solution:
![[Screenshot 2025-01-26 at 11.15.53 AM.png]]

Tags: #binary_search 

RL: [[Binary Search]]

Time complexity: O(n)

Space complexity: O(1)