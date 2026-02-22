2025-03-05 16:26

Link:

Problem: 
Contestants, today's challenge is to sort a linked list of items the fastest! The catch - you have to follow a certain technique or you're disqualified from the round. You’ll start with an unsorted lineup, and with each step, you’ll move one item at a time into its proper position until the entire lineup is perfectly ordered.

Given the `head` of a linked list, sort the items using the following procedure:

- Start with the first item: The sorted section initially contains just the first item. The rest of the items await their turn in the unsorted section.
- Pick and Place: For each step, pick the next item from the unsorted section, find its correct spot in the sorted section, and place it there.
- Repeat: Continue until all items are in the sorted section.

Return the head of the sorted linked list.

As a preview, here is a graphical example of the required technique (also known as the insertion sort algorithm). The partially sorted list (black) initially contains only the first element in the list. One element (red) is removed from the input data and inserted in-place into the sorted list with each iteration.

Intuition:
use dummy node method to assign a new sorted list! while traversing the original list, we locate a position in the new list to insert the current node .

Solution:
```python
def sort_list(head):
    if not head:
        return head
    tmp = Node( 0)
    cur = head
    while cur: 
        node_next = cur.next
        # we reassign cur positon
        dum  = tmp
        # what if dum.next is none
        while dum.next and dum.next.value < cur.value: 
            dum = dum.next
        # print( dum.next.value ,cur.value )
        if not dum.next:
            dum.next = cur 
            cur.next = None 
        else:
            temp = dum.next
            dum.next = cur
            cur.next = temp
        
        cur = node_next 
    return tmp.next
```

Tags: #linked_list  #insertion_sort

RL: 

Considerations:
