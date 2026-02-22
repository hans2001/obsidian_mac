2025-01-27 14:57

Link:https://neetcode.io/problems/reverse-a-linked-list

Problem: 
Given the beginning of a singly linked list `head`, reverse the list, and return the new beginning of the list.

Motivation:
use temp node to store cur.next, change cur.next to prev node, change prev node to current, then change cur to tmp (order matters!)

Solution:
![[Screenshot 2025-01-27 at 3.12.06 PM.png]]

Tags: #linked_list #reverse_linked_list

RL: [[Reverse Linked List II]]

Time complexity: O(n)

Space complexity: O(1)