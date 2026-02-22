2025-03-11 12:56

Link:

Problem: 
There's a tapestry hanging up on the wall with the family tree of the cursed family who owns the hotel. Given the `root` of the binary tree where each node represents a member in the family, return the value of the lowest common ancestor of the youngest children in the family. The youngest children in the family are the deepest leaves in the tree.

Recall that:

- The node of a binary tree is a leaf if and only if it has no children
- The depth of the root of the tree is `0`. If the depth of a node is `d`, the depth of each of its children is `d + 1`.
- The lowest common ancestor of a set `S` of nodes, is the node `A` with the largest depth such that every node in `S` is in the subtree with root `A`.

Intuition:
dfs: when subtree depth is same, we know the current node is the common ancestor(both subtrees have deepest leaves at the same level) , otherwise, we take the ancestor for the deeper subtree (deepest leaves at deeper subtree: propagate LCA to the top) 
Solution:
BFS + dictionary;
```python
from collections import deque ,defaultdict
class Node ():
     def __init__(self, key, value, left=None, right=None):
        self.key = key
        self.val = value
        self.left = left
        self.right = right

def lca_youngest_children(root):
    deeps = []
    parent_mp  = {root: None } 
    q =deque([root])
    while q:
        nodes =[ ]
        for _ in range (len (q )): 
            node = q.popleft( )
            nodes.append ( node )
            if node.left:
                parent_mp[ node.left] = node 
                q.append ( node.left )
            if node.right:
                parent_mp[ node.right] = node 
                q.append ( node.right )
        deeps = nodes
    if len (deeps) ==1 :
        return deeps[0].val
        
    def lca_two (a,b) :
        parents = set ( )
        while a :
            parents.add( a)
            a =parent_mp[a]
        
        while b: 
            if b in parents:
                return b
            b = parent_mp[b]
        return None
    first_child= deeps[ 0]
    
    for node in deeps[ 1: ]: 
        first_child = lca_two( first_child ,node )
        if first_child is  None:
            break 
    return first_child.val if first_child else None
    
```

DFS: 
```python
def lca_youngest_children(root):
    def dfs ( node ) :
        if not node: 
            return ( None , 0)
        left, left_d = dfs ( node.left)
        right, right_d = dfs ( node.right)
        if left == right :
            return (node, left_d +1 )
        elif left_d > right_d: 
            return ( left, left_d+ 1)
        else:
            return ( right, right_d+ 1)
    lca ,  _ = dfs(root)
    return lca.val if lca else None
```

Tags: #bfs #dfs #tree 

RL: 

Considerations:
