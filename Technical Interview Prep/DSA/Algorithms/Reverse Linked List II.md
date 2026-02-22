2025-05-07 11:54

Link: https://leetcode.com/problems/reverse-linked-list-ii/description/

Problem: 
Given the `head` of a singly linked list and two integers `left` and `right` where `left <= right`, reverse the nodes of the list from position `left` to position `right`, and return _the reversed list_.

**Example 1:**
**Input:** head = [1,2,3,4,5], left = 2, right = 4
**Output:** [1,4,3,2,5]

**Example 2:**
**Input:** head = [5], left = 1, right = 1
**Output:** [5]

Intuition:
we should locate reference to node just before the reversal list, head of the reversal list for the in place head insertion, while right - left number of times, we always connect cur.next to prev.next, and set the new prev.next to cur, then we advanced cur pointer to the original next! 

Solution:
editorial
```python
class Solution:
    def reverseBetween(
        self, head: Optional[ListNode], m: int, n: int
    ) -> Optional[ListNode]:
        # Empty list
        if not head:
            return None

        # Move the two pointers until they reach the proper starting point
        # in the list.
        cur, prev = head, None
        while m > 1:
            prev = cur
            cur = cur.next
            m, n = m - 1, n - 1

        # The two pointers that will fix the final connections.
        tail, con = cur, prev

        # Iteratively reverse the nodes until n becomes 0.
        while n:
            third = cur.next
            cur.next = prev
            prev = cur
            cur = third
            n -= 1

        # Adjust the final connections as explained in the algorithm
        if con:
            con.next = prev
        else:
            head = prev
        tail.next = cur
        return head
```

in place head insertion
```python
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next

class Solution:
    def reverseBetween(self, head: Optional[ListNode], left: int, right: int) -> Optional[ListNode]:
        # edge‐cases
        if not head or left == right:
            return head

        # 1) use a dummy to simplify head handling
        dummy = ListNode(0)
        dummy.next = head
        prev = dummy
        # move `prev` to the node just before position `left`
        for _ in range(left - 1):
            prev = prev.next

        # 2) reverse the sublist from left to right
        #    reverse_tail will end up as the tail of the reversed segment
        reverse_tail = prev.next
        cur = reverse_tail.next
        # perform in-place head-insertion for (right-left) nodes
        for _ in range(right - left):
            nxt = cur.next
            cur.next = prev.next
            prev.next = cur
            cur = nxt

        # 3) hook the tail of reversed segment back to the remainder
        reverse_tail.next = cur

        # 4) return the real head (might have changed if left==1)
        return dummy.next
```
take next node and put in front! 

Complexity:
Time:
O(n) process each node once 

Space: 
O(1)

Tags: #linked_list #dummy_node 

RL: [[Reverse Linked List]]

Considerations:
