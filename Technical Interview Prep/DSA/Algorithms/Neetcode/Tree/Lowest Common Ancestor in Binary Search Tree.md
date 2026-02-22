2025-01-31 12:15

Link:https://neetcode.io/problems/lowest-common-ancestor-in-binary-search-tree

Problem: 
Given a binary search tree (BST) where all node values are _unique_, and two nodes from the tree `p` and `q`, return the lowest common ancestor (LCA) of the two nodes.

The lowest common ancestor between two nodes `p` and `q` is the lowest node in a tree `T` such that both `p` and `q` as descendants. The ancestor is allowed to be a descendant of itself.

Motivation:
we determine the path to traverse based on BST properties
![[Screenshot 2025-01-31 at 12.16.15 PM.png]]
so by comparing the current node value, we know that which subtree we should traverse to. If none of the options matches, which mean current node value equals to either p or q, then current node is the common ancestor

Solution:
Recursion:
```python
class Solution:
    def lowestCommonAncestor(self, root: TreeNode, p: TreeNode, q: TreeNode) -> TreeNode:
        if not root or not p or not q:
            return None
        if (max(p.val, q.val) < root.val):
            return self.lowestCommonAncestor(root.left, p, q)
        elif (min(p.val, q.val) > root.val):
            return self.lowestCommonAncestor(root.right, p, q)
        else:
            return root
```

Iterative: 
```python
class Solution:
    def lowestCommonAncestor(self, root: TreeNode, p: TreeNode, q: TreeNode) -> TreeNode:
        cur = root

        while cur:
            if p.val > cur.val and q.val > cur.val:
                cur = cur.right
            elif p.val < cur.val and q.val < cur.val:
                cur = cur.left
            else:
                return cur
```

Tags: #dfs #binary_search_tree

RL: 

Time complexity: O(h) , O(n) -> iterative
for recursive approach, we don't need to visit every node, but 1 node for every single level, so time complexity is O(log n) which is the height of the tree! 

Space complexity: O(h) , O(1)