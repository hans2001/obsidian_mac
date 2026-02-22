2025-01-31 15:49

Link:https://neetcode.io/problems/binary-tree-from-preorder-and-inorder-traversal

Problem: 
You are given two integer arrays `preorder` and `inorder`.
- `preorder` is the preorder traversal of a binary tree
- `inorder` is the inorder traversal of the same tree
- Both arrays are of the same size and consist of unique values.

Rebuild the binary tree from the preorder and inorder traversals and return its root.

Motivation:
use preorder array to locate the root node in subtrees. Locate the index of root node in the inorder array, so that we can identify left and right segment for the target root node.

The hash map approach build subtrees for each node exactly once(use pre_idx to indicate the node to be built, instead of a loop) , and uses left and right variable to determine the boundary of the subtrees in the in_order array. when l > r: no node for the subtree. otherwise include all recursively build nodes from inorder[ l : r +1 ] until both child node is None.
pre_i indicate the root for the recursive subtree as we move along the process ,the pre_i is maintain at the global scope

Solution:
DFS:
```python
class Solution:
    def buildTree(self, preorder: List[int], inorder: List[int]) -> Optional[TreeNode]:
        if not preorder or not inorder:
            return None

        root = TreeNode(preorder[0])
        mid = inorder.index(preorder[0])
        root.left = self.buildTree(preorder[1 : mid + 1], inorder[:mid])
        root.right = self.buildTree(preorder[mid + 1 :], inorder[mid + 1 :])
        return root
```
O(n^2) / O(n)

Hash Map + DFS:
```python
class Solution:
    def buildTree(self, preorder: List[int], inorder: List[int]) -> Optional[TreeNode]:
        indices = {val: idx for idx, val in enumerate(inorder)}
        
        self.pre_idx = 0
        def dfs(l, r):
            if l > r:
                return None

            root_val = preorder[self.pre_idx]
            self.pre_idx += 1
            root = TreeNode(root_val)
            mid = indices[root_val]
            root.left = dfs(l, mid - 1)
            root.right = dfs(mid + 1, r)
            return root

        return dfs(0, len(inorder) - 1)
```
O(n) / O(n)

simplify
```python
# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def buildTree(preorder, inorder):
    idx = {v:i for i,v in enumerate(inorder)}  # value -> inorder index
    pre_i = 0                                   # pointer in preorder

    def helper(l, r):
        nonlocal pre_i
        if l > r:                               
            return None
        root_val = preorder[pre_i]
        pre_i += 1
        k = idx[root_val]                       # split point in inorder
        root = TreeNode(root_val)
        root.left  = helper(l, k - 1)           # left subtree uses inorder[l..k-1]
        root.right = helper(k + 1, r)           # right subtree uses inorder[k+1..r]
        return root

    return helper(0, len(inorder) - 1)
```

Tags: #dfs #dict #binary_tree 

RL: 
