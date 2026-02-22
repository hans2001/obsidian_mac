2025-01-31 13:51

Link:https://neetcode.io/problems/count-good-nodes-in-binary-tree

Problem: 
Within a binary tree, a node `x` is considered **good** if the path from the root of the tree to the node `x`contains no nodes with a value greater than the value of node `x`

Given the root of a binary tree `root`, return the number of **good** nodes within the tree.

Motivation:
keep track of the maximum value along the way! 

Solution:
DFS:![[Screenshot 2025-01-31 at 2.02.24 PM.png]]

BFS: 
```python
class Solution:
    def goodNodes(self, root: TreeNode) -> int:
        res = 0
        q = deque()
		
        q.append((root,-float('inf')))

        while q:
            node,maxval = q.popleft()
            if node.val >= maxval:  
                res += 1
				
            if node.left:    
                q.append((node.left,max(maxval,node.val)))
            
            if node.right:
                q.append((node.right,max(maxval,node.val)))
                
        return res
```

Tags: #dfs #bfs

RL: 

Time complexity: O(n)

Space complexity: O(n)