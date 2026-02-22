2025-03-04 15:05

Link:

Problem: 
![[Screenshot 2025-03-04 at 3.05.41 PM.png]]
![[Screenshot 2025-03-04 at 3.05.54 PM.png]]Intuition:

Solution:
```python
def odd_even_list(head):
	if not head or not head.next:
		return head
	od = head
	ev = head.next
	ev_head= ev
	while ev and ev.next:
		od.next = ev.next
		od = od.next
		ev.next = od.next
		ev = ev.next
	# od at the last group member
	od .next =ev_head
	return head
```
O(n) / O(1)

Tags: #linked_list #2_pointer 

RL: 

Considerations:
