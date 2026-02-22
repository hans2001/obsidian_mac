2025-01-27 18:29

Link:https://neetcode.io/problems/remove-node-from-end-of-linked-list

Problem: 
You are given the beginning of a linked list `head`, and an integer `n`.

Remove the `nth` node from the end of the list and return the beginning of the list.

Motivation:
use dummy node technique to ensure we end up in previous node of the node to be deleted. Greedily traverse the linked list by maintaining a n-stepped fast pointer, who implicitly derived slow pointers's position as the nth node from the end when fast is None

Solution:
```python
class Solution:
    def removeNthFromEnd(self, head: Optional[ListNode], n: int) -> Optional[ListNode]:
        dummy = ListNode(0, head)
        left = dummy
        right = head

        while n > 0:
            right = right.next
            n -= 1

        while right:
            left = left.next
            right = right.next

        left.next = left.next.next
        return dummy.next
```

Tags: #linked_list #greedy #fast_slow_pointer 

RL: 

Time complexity: O(n)

Space complexity: O(1)