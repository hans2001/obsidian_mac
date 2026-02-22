2025-05-07 11:54

Link: https://leetcode.com/problems/design-circular-queue/description/

Problem: 
Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle, and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".

One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

Implement the `MyCircularQueue` class:

- `MyCircularQueue(k)` Initializes the object with the size of the queue to be `k`.
- `int Front()` Gets the front item from the queue. If the queue is empty, return `-1`.
- `int Rear()` Gets the last item from the queue. If the queue is empty, return `-1`.
- `boolean enQueue(int value)` Inserts an element into the circular queue. Return `true` if the operation is successful.
- `boolean deQueue()` Deletes an element from the circular queue. Return `true` if the operation is successful.
- `boolean isEmpty()` Checks whether the circular queue is empty or not.
- `boolean isFull()` Checks whether the circular queue is full or not.

You must solve the problem without using the built-in queue data structure in your programming language.

Intuition:
The main reason for the circular queue concept is indeed to efficiently utilize the space in front of the linear queue - what would otherwise be "wasted" space after elements have been dequeued.

identify cases where we are removing the last element, and handle this differently
identify when we are inserting the first element! 
make sure we wrap the tail pointer around to the head of the list! 

Solution:
singly linked list
```python
class ListNode:
    def __init__(self, val):
        self.val = val
        self.next = None

class MyCircularQueue:
    def __init__(self, k: int):
        self.cap = k
        self.count = 0
        self.head = None
        self.tail = None
    
    def enQueue(self, value: int) -> bool:
        # Check if queue is full
        if self.count == self.cap:
            return False
        
        # Create new node
        node = ListNode(value)
        
        # If queue is empty
        if not self.head:
            self.head = node
        
        # If only one element exists
        if self.head and not self.head.next:
            self.head.next = node
        
        # Add to tail
        if self.tail:
            self.tail.next = node
        
        # Update tail pointer
        self.tail = node
        
        # Maintain circular structure - tail points back to head
        self.tail.next = self.head
        
        self.count += 1
        return True
    
    def deQueue(self) -> bool:
        # Check if queue is empty
        if not self.head or self.count == 0:
            return False
        
        # If this is the last node
        if self.head == self.tail:
            self.head.next = None
            self.head = None
            self.tail = None
        else:
            # Move head to next node
            nxt = self.head.next
            self.head.next = None
            self.head = nxt
        
        self.count -= 1
        return True
    
    def Front(self) -> int:
        if self.count == 0 or not self.head:
            return -1
        return self.head.val
    
    def Rear(self) -> int:
        if self.count == 0 or not self.tail:
            return -1
        return self.tail.val
    
    def isEmpty(self) -> bool:
        return self.count == 0
    
    def isFull(self) -> bool:
        return self.count == self.cap
```

Complexity:
Time: 
initialization takes O(1)
all methods takes O(1)

Space: 
O(n) where each node requires O(1) to store
we store up to k nodes

Tags: #linked_list #singly_linked_list #ood 

RL: 

Considerations:
