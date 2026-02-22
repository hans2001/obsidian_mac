2025-03-10 15:16

Link:

Problem: 
A poltergeist has been causing mischief and reversed the order of rooms on odd level floors. Given the root of a binary tree `hotel` where each node represents a room in the hotel and the root, restore order by reversing the node values at each odd level in the tree.

For example, suppose the rooms on level 3 have values `[308, 307, 306, 305, 304, 303, 302, 301]`. It should become `[301, 302, 303, 304, 305, 306, 307, 308]`.

Return the root of the altered tree.

A binary tree is perfect if all parent nodes have two children and all leaves are on the same level.
The level of a node is the number of edges along the path between it and the root node.

Evaluate the time complexity of your function. Define your variables and provide a rationale for why you believe your solution has the stated time complexity. Assume the input tree is balanced when calculating time complexity.

Intuition:
use dfs to match the symmetric child nodes ,and swap their value if level is odd!

Solution:
```python
from collections import deque
class Room():
     def __init__(self, value, left=None, right=None):
        self.val = value
        self.left = left
        self.right = right

def reverse_odd_levels(hotel):
    def dfs( left,right , le  ) :
        if not left or not right: 
            return
        if le %2 ==1:
            left.val,right.val  = right.val,left.val
        dfs( left.left ,right.right ,le +1 )
        dfs( left.right ,right.left ,le +1 )
        return
    dfs( hotel.left, hotel.right, 1 )
    return hotel
    
def print_tree( head ) : 
    q =deque(  [head])
    while q: 
        node = q.popleft ( )
        if not node : 
            continue
        print (node .val,end= ' ')
        q.append( node .left)
        q.append( node .right )
    return
```

Tags: #tree #dfs

RL: 

Considerations:
