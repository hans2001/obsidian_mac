2025-01-30 11:42

Link: https://neetcode.io/problems/binary-tree-diameter

Problem: 
The **diameter** of a binary tree is defined as the **length** of the longest path between _any two nodes within the tree_. The path does not necessarily have to pass through the root.

The **length** of a path between two nodes in a binary tree is the number of edges between the nodes.

Given the root of a binary tree `root`, return the **diameter** of the tree.

Motivation:
Since height is a bottom up property, we must compute heights for child nodes before parent node, that is why we use **post order traversal!**  
## Diameter = left_height + right_height
we need to compute the max height for each node, and record the maximum diameter computed along the way. Therefore, for the recursive method, we use a global variable to keep track of the maximum diameter, and the recursive function compute the maximum height for each node through post-order traversal. 

For the iterative approach, we store the tuple (max_height, max_diameter) as value for each node (key: node address) in the stack. For root, we are able to compute the maximum diameter as max( left_height + right_height, max_left_diameter, max_right_diameter)

A longer path might be fully contained within one of the subtrees, rather than passing through the current node, so we need to compare diameters and locate the maximum one.

Solution:
DFS: 
```python
class Solution:
    def diameterOfBinaryTree(self, root: Optional[TreeNode]) -> int:
        res = 0

        def dfs(root):
            nonlocal res

            if not root:
                return 0
            left = dfs(root.left)
            right = dfs(root.right)
            res = max(res, left + right)

            return 1 + max(left, right)

        dfs(root)
        return res
```

Iterative DFS:
```python
class Solution:
    def diameterOfBinaryTree(self, root: Optional[TreeNode]) -> int:
        stack = [root]
        mp = {None: (0, 0)}

        while stack:
            node = stack[-1]

            if node.left and node.left not in mp:
                stack.append(node.left)
            elif node.right and node.right not in mp:
                stack.append(node.right)
            else:
                node = stack.pop()

                leftHeight, leftDiameter = mp[node.left]
                rightHeight, rightDiameter = mp[node.right]

                mp[node] = (1 + max(leftHeight, rightHeight),
                           max(leftHeight + rightHeight, leftDiameter, rightDiameter))

        return mp[root][1]
```

Tags: #dfs #binary_tree #post_order_traversal 

RL: 

Time complexity: O(n)

Space complexity: O(n)