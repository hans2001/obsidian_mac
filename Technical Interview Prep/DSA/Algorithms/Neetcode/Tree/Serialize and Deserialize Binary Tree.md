2025-02-03 16:31

Link:https://neetcode.io/problems/serialize-and-deserialize-binary-tree

Problem: 
Implement an algorithm to serialize and deserialize a binary tree.

Serialization is the process of converting an in-memory structure into a sequence of bits so that it can be stored or sent across a network to be reconstructed later in another computer environment.

You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure. There is no additional restriction on how your serialization/deserialization algorithm should work.

**Note:** The input/output format in the examples is the same as how NeetCode serializes a binary tree. You do not necessarily need to follow this format.

Motivation:
use the DFS to serialize and deserialize the string, so that we can use a global index to 
indicate the node we are working on, by just incrementing it by 1 in each recursive stack, and the way they go through/ or build the tree will be the same! 

BFS: 
we can also format the string to level order, so that when we decompose, the problem becomes the one that we are trying to solve in the tiktok interview, which is to build the tree from a order-level sorted list ,and we need to make sure value are separated by comma, so that when we decode, we decode negative value or multi-digit properly! we can set null as some random char, for example '#'

Solution:
```python
class Codec:
    # Encodes a tree to a single string.
    def serialize(self, root: Optional[TreeNode]) -> str:
        res = []

        def dfs(node):
            if not node:
                res.append("N")
                return
            res.append(str(node.val))
            dfs(node.left)
            dfs(node.right)

        dfs(root)
        return ",".join(res)
        
    # Decodes your encoded data to tree.
    def deserialize(self, data: str) -> Optional[TreeNode]:
        vals = data.split(",")
        self.i = 0

        def dfs():
            if vals[self.i] == "N":
                self.i += 1
                return None
            node = TreeNode(int(vals[self.i]))
            self.i += 1
            node.left = dfs()
            node.right = dfs()
            return node

        return dfs()
```

my solution with order level traversal
```python
from collections import deque

# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Codec:
    def serialize(self, root):
        """
        Encodes a tree to a single string.
        :type root: TreeNode
        :rtype: str
        """
        if not root:
            return ""
        
        q = deque([root])
        ans = []
        empty = TreeNode(float('inf'))
        
        while q:
            node = q.popleft()
            if node == empty:
                ans.append("#")
                continue
            
            ans.append(str(node.val))
            q.append(node.left if node.left else empty)
            q.append(node.right if node.right else empty)
        
        return ",".join(ans)

    def deserialize(self, data):
        """
        Decodes your encoded data to tree.
        :type data: str
        :rtype: TreeNode
        """
        if not data:
            return None
        
        data = data.split(",")
        root = TreeNode(int(data[0]))
        i = 1
        n = len(data)
        q = deque([root])
        
        while i < n and q:
            cur = q.popleft()
            
            # Left child
            if i < n:
                if data[i] == "#":
                    cur.left = None
                else:
                    node = TreeNode(int(data[i]))
                    cur.left = node
                    q.append(node)
                i += 1
            
            # Right child
            if i < n:
                if data[i] == "#":
                    cur.right = None
                else:
                    node = TreeNode(int(data[i]))
                    cur.right = node
                    q.append(node)
                i += 1
        
        return root
```

Tags: #binary_tree #dfs #bfs 

RL: 