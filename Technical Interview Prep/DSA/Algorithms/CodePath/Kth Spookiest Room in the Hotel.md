2025-03-10 17:08

Link:

Problem: 
Over time, your hotel has gained a reputation for being haunted, and you now have customers coming specifically for a spooky experience. You are given the `root` of a binary search tree (BST) with `n` nodes where each node represents a room in the hotel and each node has an integer `key` representing the spookiness of the room (`1` being most spooky and `n` being least spooky) and `val` representing the room number. The tree is organized according to its keys.

Given the `root` of a BST and an integer `k` write a function `kth_spookiest()` that returns the **value** of the `kth` spookiest room (smallest `key`, 1-indexed) of all the rooms in the hotel.

Evaluate the time and space complexity of your function. Define your variables and provide a rationale for why you believe your solution has the stated time and space complexity. Assume the input tree is balanced when calculating time and space complexity.

Intuition:
go down either side with dfs by comparing node.key with target key

Solution:
```python
from collections import deque
class Node():
     def __init__(self, key, value, left=None, right=None):
        self.key = key
        self.val = value
        self.left = left
        self.right = right

def kth_spookiest(root, k):
    def dfs ( node, k) : 
        if not node: 
            return -1
        if node.key ==k :
            return node.val
        if k < node .key: 
            return dfs( node.left ,k )
        else:
            return dfs( node.right, k)
    return dfs( root,k )

# Using build_tree() function at the top of the page
def build_tree( rooms ) :
    head=  Node( rooms[ 0][0],rooms[ 0][1])
    q= deque( [head] )
    i =1 
    n = len(rooms)
    while q and i < n:
        node = q.popleft( )
        if not node:
            continue
        if i +1 < n: 
            if rooms[ i] == None: 
                node.left = None 
            else: 
                node .left = Node( rooms[ i][0],rooms[i][1])
                q.append( node.left )
            i+=1     
        if i +1 < n: 
            if rooms[ i] == None: 
                node.right = None 
            else: 
                node .right = Node( rooms[ i][0],rooms[i][1])
                q.append( node.right )
            i+=1    
    return head

rooms = [(3, "Lobby"), (1, 101), (4, 102), None, (2, 201)]
hotel1 = build_tree(rooms)

rooms = [(5, 'Lobby'), (3, 101), (6, 102), (2, 201), (4, 202), None, None, (1, 301)]
hotel2 = build_tree(rooms)

print(kth_spookiest(hotel1, 1))
print(kth_spookiest(hotel2, 3))
```

Tags: #binary_search_tree #dfs

RL: 

Considerations:
