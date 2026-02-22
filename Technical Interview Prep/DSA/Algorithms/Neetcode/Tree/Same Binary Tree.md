2025-01-31 11:08

Link:https://neetcode.io/problems/same-binary-tree

Problem: 
Given the roots of two binary trees `p` and `q`, return `true` if the trees are **equivalent**, otherwise return `false`.

Two binary trees are considered **equivalent** if they share the exact same structure and the nodes have the same values.

Motivation:
traverse both tree at the same time, and compare each node. return false when difference occur!

Solution:
DFS: 
![[Screenshot 2025-01-31 at 11.18.54 AM.png]]

iterative DFS: 
```python
class Solution:
    def isSameTree(self, p: Optional[TreeNode], q: Optional[TreeNode]) -> bool:
        stack = [(p, q)]
        
        while stack:
            node1, node2 = stack.pop()
            
            if not node1 and not node2:
                continue
            if not node1 or not node2 or node1.val != node2.val:
                return False
                
            stack.append((node1.right, node2.right))
            stack.append((node1.left, node2.left))
        
        return True
```

Tags: #dfs

RL: [[Subtree of Another Tree]]

Time complexity: O(n)

Space complexity: O(n)