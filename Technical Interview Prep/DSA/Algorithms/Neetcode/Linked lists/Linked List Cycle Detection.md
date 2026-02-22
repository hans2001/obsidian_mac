2025-01-27 15:22

Link:https://neetcode.io/problems/linked-list-cycle-detection

Problem: 
Given the beginning of a linked list `head`, return `true` if there is a cycle in the linked list. Otherwise, return `false`.

There is a cycle in a linked list if at least one node in the list that can be visited again by following the `next` pointer.

Internally, `index` determines the index of the beginning of the cycle, if it exists. The tail node of the list will set it's `next` pointer to the `index-th` node. If `index = -1`, then the tail node points to `null` and no cycle exists.

**Note:** `index` is **not** given to you as a parameter.

Motivation:
evaluate after slow and fast node is changed, otherwise initial case will be evaluated to True (Fast/ slow pointer !)

Solution:
![[Screenshot 2025-01-27 at 3.22.40 PM.png]]

Tags: #linked_list #fast_slow_pointer

RL: 

Time complexity: O(n)

Space complexity: O(1)