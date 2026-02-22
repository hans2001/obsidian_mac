2025-01-27 15:13

Link:https://neetcode.io/problems/merge-two-sorted-linked-lists

Problem: 
You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists into one **sorted** linked list and return the head of the new sorted linked list.

The new list should be made up of nodes from `list1` and `list2`.

Motivation:
by comparing current node value, redirect the new iterator to node with smaller value is sufficient, can reuse the original node (shallow copy!)

Solution:
```python
class Solution:
    def mergeTwoLists(self, list1: ListNode, list2: ListNode) -> ListNode:
        dummy = node = ListNode()

        while list1 and list2:
            if list1.val < list2.val:
                node.next = list1
                list1 = list1.next
            else:
                node.next = list2
                list2 = list2.next
            node = node.next

        node.next = list1 or list2

        return dummy.next
```

Tags: #linked_list 

RL: 

Time complexity: O(n + m)

Space complexity: O(1)