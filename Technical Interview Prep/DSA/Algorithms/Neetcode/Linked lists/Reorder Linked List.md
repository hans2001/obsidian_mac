2025-01-27 16:14

Link:https://neetcode.io/problems/reorder-linked-list

Problem: 
![[Screenshot 2025-01-27 at 6.29.56 PM.png]]
Motivation:
use fast/slow pointer to find the mid point of the list, then reverse second half of the list. 
Afterwards, we merge 2 list by saving the previous link first before modifying them, the update node to previously linked node!

Solution:
```python
class Solution:
    def reorderList(self, head: Optional[ListNode]) -> None:
        slow, fast = head, head.next
        while fast and fast.next:
            slow = slow.next
            fast = fast.next.next

        second = slow.next
        prev = slow.next = None
        while second:
            tmp = second.next
            second.next = prev
            prev = second
            second = tmp

        first, second = head, prev
        while second:
            tmp1, tmp2 = first.next, second.next
            first.next = second
            second.next = tmp1
            first, second = tmp1, tmp2
```

Tags: #linked_list #fast_slow_pointer 

RL: [[Linked List Cycle Detection]], [[Reverse Linked List]]

Time complexity: O(n)

Space complexity: O(1)