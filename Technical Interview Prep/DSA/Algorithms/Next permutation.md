2025-04-16 14:27

Link:https://leetcode.com/problems/next-permutation/editorial/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem:   
A **permutation** of an array of integers is an arrangement of its members into a sequence or linear order.

- For example, for `arr = [1,2,3]`, the following are all the permutations of `arr`: `[1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1]`.

The **next permutation** of an array of integers is the next lexicographically greater permutation of its integer. More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the **next permutation** of that array is the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).

- For example, the next permutation of `arr = [1,2,3]` is `[1,3,2]`.
- Similarly, the next permutation of `arr = [2,3,1]` is `[3,1,2]`.
- While the next permutation of `arr = [3,2,1]` is `[1,2,3]` because `[3,2,1]` does not have a lexicographical larger rearrangement.

Given an array of integers `nums`, _find the next permutation of_ `nums`.

The replacement must be **[in place](http://en.wikipedia.org/wiki/In-place_algorithm)** and use only constant extra memory.

Intuition:
find the pivot: the first element that breaks the descending order!
find the immediate larger element than pivot to achieve the smallest upgrade! 
we can scan form the right to find the first element that is larger, since right subarray is already in descending order! 
swap the 2 element
minimize the tail by reversing the right subarray, since we have swap the leader, the tail should be smallest after the upgrade! (ascending order)
if pivot is not found: we return the lowest possible order

Solution:
![[Screenshot 2025-04-16 at 2.29.10 PM.png]]

Tags: 

RL: 

Considerations:
