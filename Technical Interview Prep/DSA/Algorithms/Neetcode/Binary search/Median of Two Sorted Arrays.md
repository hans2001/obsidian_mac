2025-01-26 12:45

Link:https://neetcode.io/problems/median-of-two-sorted-arrays

Problem: 
You are given two integer arrays `nums1` and `nums2` of size `m` and `n` respectively, where each is sorted in ascending order. Return the [median](https://en.wikipedia.org/wiki/Median) value among all elements of the two arrays.

Your solution must run in O(log(m+n))O(log(m+n)) time.

Motivation: by comparing last element of the left segment, to the first element of the right segment, in the other array, we can find a valid partition!
Since size of the other array can be computed in O(1) if we know the partition point of one array(j = half - i - 2), we should perform binary search on the shorter array to optimize time complexity. 

A valid partition is found once the last element on left segment is smaller than first element of right segment for both array. Otherwise, we should reduce element of A, if last element is bigger than the starting element in B, or increase vice versa 

Solution:
![[Screenshot 2025-01-26 at 2.51.59 PM.png]]
Tags: #binary_search 

RL: [[Binary Search]]

Time complexity: O(log(min(n,m)))

Space complexity: O(1)