2025-02-03 16:31

Link: https://neetcode.io/problems/binary-tree-maximum-path-sum

Problem: 
Given the `root` of a _non-empty_ binary tree, return the maximum **path sum** of any _non-empty_ path.

A **path** in a binary tree is a sequence of nodes where each pair of adjacent nodes has an edge connecting them. A node can _not_ appear in the sequence more than once. The path does _not_ necessarily need to include the root.

The **path sum** of a path is the sum of the node's values in the path.

Motivation:
The updated maximum value is different from the result return by the recursive function. For the maxPathSum, it can be any path from nested subtrees without passing the root, therefore, any node can be seen as the root node, and the max path sum can be seen as left subtree sum + root.val + right subtree sum, for subtree with the maximum path value. 
As for the recursive function, since we are using post-order traversal, the parent can only take result from either the left child or right child to formulate a valid path, therefore, it should be root.val + max (left path sum , right path sum , 0) (where 0 indicate we don't do down the path of either child, since they don't help!)

Solution:
```python
    def maxPathSum(self, root: Optional[TreeNode]) -> int:
        res = [root.val]

        def dfs(root):
            if not root:
                return 0

            leftMax = dfs(root.left)
            rightMax = dfs(root.right)
            leftMax = max(leftMax, 0)
            rightMax = max(rightMax, 0)

            res[0] = max(res[0], root.val + leftMax + rightMax)
            return root.val + max(leftMax, rightMax)

        dfs(root)
        return res[0]
```

Tags: #binary_tree #dfs #recursive #post_order_traversal 

RL: 