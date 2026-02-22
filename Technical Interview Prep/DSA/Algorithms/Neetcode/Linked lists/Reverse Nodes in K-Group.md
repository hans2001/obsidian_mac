2025-02-03 11:07

Link:https://neetcode.io/problems/reverse-nodes-in-k-group

Problem: 
You are given the head of a singly linked list `head` and a positive integer `k`.

You must reverse the first `k` nodes in the linked list, and then reverse the next `k` nodes, and so on. If there are fewer than `k` nodes left, leave the nodes as they are.

Return the modified list after reversing the nodes in each group of `k`.

You are only allowed to modify the nodes' `next` pointers, not the values of the nodes.

Motivation:
use a list to store the original heads and tails of each grp in the first pass. Then connect the original head of current group to the original tail of the next group in the second pass. count the length of each group as well ,if the last group does not have length k, we reverse it in the second pass! otherwise ,proceed with the connection

Solution:
Mine:
![[Screenshot 2025-02-03 at 2.35.08 PM.png]]
O(n) / O(n/k)

Editorial:
```python
class Solution:
    def reverseKGroup(self, head: Optional[ListNode], k: int) -> Optional[ListNode]:
        dummy = ListNode(0, head)
        groupPrev = dummy

        while True:
            kth = self.getKth(groupPrev, k)
            if not kth:
                break
            groupNext = kth.next

            prev, curr = kth.next, groupPrev.next
            while curr != groupNext:
                tmp = curr.next
                curr.next = prev
                prev = curr
                curr = tmp

            tmp = groupPrev.next
            groupPrev.next = kth
            groupPrev = tmp
        return dummy.next

    def getKth(self, curr, k):
        while curr and k > 0:
            curr = curr.next
            k -= 1
        return curr
```
O(n) / O(1)

Tags: #linked_list #reverse_linked_list 

RL: [[Reverse Linked List]]