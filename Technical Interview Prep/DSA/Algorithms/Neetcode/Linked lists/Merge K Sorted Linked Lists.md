2025-01-29 16:02

Link:https://neetcode.io/problems/merge-k-sorted-linked-lists

Problem: 
You are given an array of `k` linked lists `lists`, where each list is sorted in ascending order.

Return the **sorted** linked list that is the result of merging all of the individual linked lists.

Motivation:
Divide and conquer: 
Instead of merging a list and a node each time which takes O(k* N), we divide the problem into merging 2 lists and continuously to that until we have only 1 lists ( similar to #merge_sort )

Heap: 
we push the head of each list into the heap first, then we continuously consumes nodes in the heap and build the result list, until there are no nodes in the heap! the value of the node is ordered by (node.val ,list index, node) ,where the node here is for storing purpose only! 

Solution:
Iterative Divide and Conquer 
```python
class Solution:
    def mergeKLists(self, lists: List[Optional[ListNode]]) -> Optional[ListNode]:
        if not lists or len(lists) == 0:
            return None

        while len(lists) > 1:
            mergedLists = []
            for i in range(0, len(lists), 2):
                l1 = lists[i]
                l2 = lists[i + 1] if (i + 1) < len(lists) else None
                mergedLists.append(self.mergeList(l1, l2))
            lists = mergedLists
        return lists[0]

    def mergeList(self, l1, l2):
        dummy = ListNode()
        tail = dummy

        while l1 and l2:
            if l1.val < l2.val:
                tail.next = l1
                l1 = l1.next
            else:
                tail.next = l2
                l2 = l2.next
            tail = tail.next
        if l1:
            tail.next = l1
        if l2:
            tail.next = l2
        return dummy.next
```

Heap:
![[Screenshot 2025-01-29 at 5.01.56 PM.png]]

Tags: #linked_list #heap #merge_sort #divide_and_conquer #citadel #failure

RL: [[Merge Two Sorted Linked Lists]]

**Time complexity**:
Divide and conquer:
number of merge operations: log k
merge 2 lists with total size of N takes O(N)
total : O(N log k)

Heap : 
insert / remove operation: O (log k)
above operation done for N nodes: O(N log k)

**Space complexity**:  O(k)
Divide and conquer: O(K)
Heap stores at most K elements: O(K)
