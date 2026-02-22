2025-03-10 16:40

Link:

Problem: 

Intuition:

Solution:
```python
class Node():
     def __init__(self, value, left=None, right=None):
        self.val = value
        self.left = left
        self.right = right

def purge_hotel(hotel):
    r =[ ]
    def dfs( node ) : 
        if not node: 
            return -1
        lh = dfs( node.left)
        rh = dfs( node.right)
        ch = 1 + max( lh , rh )
        if ch == len(r ): 
            r .append( [])
        r [ch] .append( node.val )
        return ch 
    dfs ( hotel )
    return r 
    
guests = ["👻", "😱", "🧛🏾‍♀️", "💀", "😈"]

from collections import deque
def build_tree(guests): 
    n = len( guests)
    root = Node( guests[ 0 ] ) 
    q=deque ( [ root ])
    i=1
    while q and i < n :
        node = q.popleft( )
        if i< n: 
            node.left = Node ( guests[ i])
            q.append( node.left )
            i += 1
        if i < n: 
            node.right  = Node ( guests[ i])
            q.append( node.right) 
            i += 1
    return root
    
def print_tree( root) :
    q=deque( [root ])
    while q:
        node = q.popleft ()
        if not node: 
            continue
        print (node.val ,end= ' ')
        q.append( node.left )
        q.append( node.right )
    return

hotel = build_tree(guests)
print_tree(hotel)
print(purge_hotel(hotel))
```

Tags: 

RL: 

Considerations:
