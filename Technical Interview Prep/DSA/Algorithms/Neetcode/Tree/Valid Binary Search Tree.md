2025-01-31 14:10

Link:https://neetcode.io/problems/valid-binary-search-tree

Problem: 
Given the `root` of a binary tree, return `true` if it is a **valid binary search tree**, otherwise return `false`.

A **valid binary search tree** satisfies the following constraints: 

- The left subtree of every node contains only nodes with keys **less than** the node's key.
- The right subtree of every node contains only nodes with keys **greater than** the node's key.
- Both the left and right subtrees are also binary search trees.

Motivation:
maintain the min-max range for each node!

Solution:
iterative BFS: ![[Screenshot 2025-01-31 at 2.32.12 PM.png]]

Recursive DFS:
```python
class Solution:
    def isValidBST(self, root: Optional[TreeNode]) -> bool:
        def valid(node, left, right):
            if not node:
                return True
            if not (left < node.val < right):
                return False

            return valid(node.left, left, node.val) and valid(
                node.right, node.val, right
            )
        return valid(root, float("-inf"), float("inf"))
```

BFS: 
```python
class Solution:
    def isValidBST(self, root: Optional[TreeNode]) -> bool:
        if not root:
            return True

        q = deque([(root, float("-inf"), float("inf"))])

        while q:
            node, left, right = q.popleft()
            if not (left < node.val < right):
                return False
            if node.left:
                q.append((node.left, left, node.val))
            if node.right:
                q.append((node.right, node.val, right))

        return True
```

Tags: #binary_search_tree  

RL: 

Time complexity: O(n)

Space complexity: O(n)