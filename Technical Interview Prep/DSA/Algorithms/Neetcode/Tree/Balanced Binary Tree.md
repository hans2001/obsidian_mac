2025-01-30 15:15

Link:https://neetcode.io/problems/balanced-binary-tree

Problem: 
Given a binary tree, return `true` if it is **height-balanced** and `false` otherwise.

A **height-balanced** binary tree is defined as a binary tree in which the left and right subtrees of every node differ in height by no more than 1.

Motivation:
Recursive (bottom - up approach):
post-order traversal, compute height for child subtree first before process parent node.
Maintain 2 value for each node, tree height and if subtree is balanced. For each parent node, if both child subtree is balanced and their height difference is at most 1, the tree is balanced from parent node. otherwise is not.

Iterative: 
post-order: left-right-root
The outer loop ensure we visit the leftmost node first. When leftmost node is None, we trigger backtracking by processing nodes at top of the stack. 
For each node, if right child is none or we have processed the right child, then we can process current node, otherwise, we should process the right child first. 
Use a dictionary to record height for each node ,then determine if current subtree is balanced, and update height for parent node. update the backtracked node and trigger backtrack

Solution:
DFS:
```python
class Solution:
    def isBalanced(self, root: Optional[TreeNode]) -> bool:
        def dfs(root):
            if not root:
                return [True, 0]

            left, right = dfs(root.left), dfs(root.right)
	            balanced = left[0] and right[0] and abs(left[1] - right[1]) <= 1
            return [balanced, 1 + max(left[1], right[1])]

        return dfs(root)[0]
```

Iterative DFS: 
```python
class Solution:
    def isBalanced(self, root):
        stack = []
        node = root
        last = None
        depths = {}

        while stack or node:
            if node:
                stack.append(node)
                node = node.left
            else:
                node = stack[-1]
                if not node.right or last == node.right:
                    stack.pop()
                    left = depths.get(node.left, 0)
                    right = depths.get(node.right, 0)

                    if abs(left - right) > 1:
                        return False

                    depths[node] = 1 + max(left, right)
                    last = node
                    node = None
                else:
                    node = node.right

        return True
```

Tags: #dfs #binary_tree

RL: 

Time complexity: O(n)

Space complexity: 
Recursive: 
O(h) (h :height of the tree)

Iterative:
O(n)