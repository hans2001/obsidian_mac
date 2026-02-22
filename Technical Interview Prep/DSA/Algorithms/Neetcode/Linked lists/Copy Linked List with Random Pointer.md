2025-01-28 19:31

Link:https://neetcode.io/problems/copy-linked-list-with-random-pointer

Problem: 
![[Screenshot 2025-01-28 at 7.31.20 PM.png]]
Motivation:
in the first pass, we do not initialize next and random pointer, since the node to be point to has not been initialized. We use a hash map to identify the new node from current node(key). In second pass, we setup next and random pointer for each newly created node! 

Solution:
![[Screenshot 2025-01-28 at 8.03.15 PM.png]]
Tags: #linked_list #dict 

RL: 

Time complexity: O(n)

Space complexity: O(n)