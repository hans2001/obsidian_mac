2025-03-07 16:00

Link:

Problem:
You are given the head of a linked list `contestant_scores` with `n` nodes where each node represents the current score of a contestant in the game.

For each node in the list, find the value of the contestant with the next highest score. That is, for each score, find the value of the first node that is next to it and has a strictly larger value than it.

Return an integer array `answer` where `answer[i]` is the value of the next greater node of the `ith` node (1-indexed). If the `ith` node does not have a next greater node, set `answer[i] = 0`.

Evaluate the time and space complexity of your solution. Define your variables and provide a rationale for why you believe your solution has the stated time and space complexity.

Intuition:

Solution:
```python
class Node:
    def __init__(self, value, next=None):
        self.value = value
        self.next = next

def next_highest_scoring_contestant(head):
    cur =head
    sk = []
    c = 0
    while cur :
        cur = cur.next
        c += 1
    r =[0] * c
    i = 0
    ncur = head
    while ncur: 
        if not sk: 
            pass
        else: 
            while sk and ncur.value > sk[-1 ][1]: 
                index, val = sk.pop ( )
                r [index ] = ncur.value 
        sk.append(( i ,ncur.value))
        i +=1 
        ncur =ncur. next 
    return r
```

Tags: #linked_list #stack 

RL: 

Considerations:
