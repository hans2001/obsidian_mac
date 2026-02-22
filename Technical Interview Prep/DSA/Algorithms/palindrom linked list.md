2025-04-15 15:58

Link:https://leetcode.com/problems/palindrome-linked-list/description/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem: 
Given the `head` of a singly linked list, return `true` _if it is a_ _palindrome_ _or_ `false` _otherwise_.

Intuition:

Solution:
```python

class Solution(object):
def isPalindrome(self, head):
	slow ,fast = head ,head
	sk = []
	while fast and fast.next:
		sk.append( slow.val)
		slow = slow.next
		fast = fast.next.next
	if fast:
		slow= slow.next
	while sk and slow:
		if sk[-1] != slow.val :
			return False
		sk.pop ( )
		slow = slow.next
	return not sk
```

Tags: #stack #linked_list #fast_slow_pointer 

RL: 

Considerations:
