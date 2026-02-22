2025-10-01 20:59

Link:

Problem: 
“Given a list of values, build a complete binary tree in level-order (left to right) and return the root node.”

Constraints:

Intuition:
given a order traversal list, u should build the binary tree from the list to the binary tree, and return the root of the new tree

Solution:
```python
class Node: 
    def __init__ ( self,val )  :
        self.val = val
        self.left  = None
        self.right  = None
class Solution: 
    def __init__ (self) : 
        pass

    def solve( self , input ): 
        if not input: 
            return None
        i = 1 
        n = len( input )
        root = Node(input[0])
        q = deque([root])
        while i < n and q :
            node =  q.popleft( )
            if i < n : 
                node.left  = Node (input[i])
                q.append(node.left)
                i += 1
            if i < n : 
                node.right  = Node (input[i])
                q.append(node.right)
                i += 1
        return root
```

space / time : O(N) , O(N) where n is the length of the input list

Tags: #bfs #pointer

RL: 

Considerations:
