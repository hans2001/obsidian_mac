2025-05-12 14:14

Link: https://neetcode.io/problems/delete-leaves-with-a-given-value

Problem: 
You are given a binary tree `root` and an integer `target`, delete all the **leaf nodes** with value `target`.

Note that once you delete a leaf node with value `target`, if its parent node becomes a leaf node and has the value `target`, it should also be deleted (you need to continue doing that until you cannot).

Intuition:
we should determine if child node has been removed first before working on the current node, so we use post order traversal!

Solution:
```python
def removeLeafNodes(self, root: Optional[TreeNode], target: int) -> Optional[TreeNode]:
    def dfs(node):
        if not node:
            return None
            
        # Process children first (post-order traversal)
        node.left = dfs(node.left)
        node.right = dfs(node.right)
        
        # After processing children, check if current node is a leaf with target value
        if not node.left and not node.right and node.val == target:
            return None  # Remove this leaf node
        
        return node  # Keep this node
        
    return dfs(root)
```

Complexity:
Time: 
O(n) each node is visited once

Space: 
O(n) recursion stack 

Tags: #post_order_traversal #recursion #tree 

RL: 

Considerations:
