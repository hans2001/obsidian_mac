2025-01-27 14:52

Link:https://neetcode.io/problems/sliding-window-maximum

Problem: 
You are given an array of integers `nums` and an integer `k`. There is a sliding window of size `k` that starts at the left edge of the array. The window slides one position to the right until it reaches the right edge of the array.

Return a list that contains the maximum element in the window at each step.

Motivation:
just make sure the maximum element falls within the current window, we can just store tuple (value, index) as heap node to achieve that! 
what is the res for? 

Solution:
![[Screenshot 2025-01-27 at 2.53.14 PM.png]]
Tags: #sliding_window #heap

RL: 

Time complexity: O(n log k) 
Inserting and removing elements in a heap takes O(log⁡k) (k:window size)

Space complexity: O(n)