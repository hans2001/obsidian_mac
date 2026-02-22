2025-01-31 11:26

Link:https://neetcode.io/problems/subtree-of-a-binary-tree

Problem: 
Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a subtree of `root` with the same structure and node values of `subRoot` and `false` otherwise.

A subtree of a binary tree `tree` is a tree that consists of a node in `tree` and all of this node's descendants. The tree `tree` could also be considered as a subtree of itself.

Motivation:
use the helper function to sameTree to check if subRoot belong to root ,when node with same value is found. Note: node does not have unique value! 

Solution:
```python
class Solution:
    def isSubtree(self, root: Optional[TreeNode], subRoot: Optional[TreeNode]) -> bool:
        if not subRoot:
            return True
        if not root:
            return False

        if self.sameTree(root, subRoot):
            return True
        return (self.isSubtree(root.left, subRoot) or 
               self.isSubtree(root.right, subRoot))

    def sameTree(self, root: Optional[TreeNode], subRoot: Optional[TreeNode]) -> bool:
        if not root and not subRoot:
            return True
        if root and subRoot and root.val == subRoot.val:
            return (self.sameTree(root.left, subRoot.left) and 
                   self.sameTree(root.right, subRoot.right))
        return False
```

Tags: #dfs #binary_tree 

RL: [[Same Binary Tree]]

Time complexity: O(m * n)
m is number of nodes in subRoot and n is number of nodes in root

Space complexity:  O(m+ n)