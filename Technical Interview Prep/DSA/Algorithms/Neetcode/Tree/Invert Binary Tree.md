2025-01-30 10:46

Link:https://neetcode.io/problems/invert-a-binary-tree

Problem: 
You are given the root of a binary tree `root`. Invert the binary tree and return its root.

Motivation:
traverse the tree using either method, make sure to perform swapping of child nodes at each level, then make sure all neighbor is considered! 

Solution:
Breath First Search:
```python
class Solution:
    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:
        if not root:
            return None
        queue = deque([root])
        while queue:
            node = queue.popleft()
            node.left, node.right = node.right, node.left
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
        return root
```

Depth First Search:
```python
class Solution:
    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:
        if not root: return None

        root.left, root.right = root.right, root.left
        
        self.invertTree(root.left)
        self.invertTree(root.right)
        
        return root
```

Iterative DFS:
```python
class Solution:
    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:
        if not root:
            return None
        stack = [root]
        while stack:
            node = stack.pop()
            node.left, node.right = node.right, node.left
            if node.left:
                stack.append(node.left)
            if node.right:
                stack.append(node.right)
        return root
```

Tags: #bfs #dfs #binary_tree 

RL: 

Time complexity: O(n)

Space complexity: O(n)