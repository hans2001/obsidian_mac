2025-12-24 15:28

Link:

Problem: 
You are given two integer arrays `inorder` and `postorder` where `inorder` is the inorder traversal of a binary tree and `postorder` is the postorder traversal of the same tree, construct and return the binary tree.

**Example 1:**
![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/938c14d3-6669-47ab-924b-a1a08640f200/public)

```java
Input: inorder = [2,1,3,4], postorder = [2,4,3,1]

Output: [1,2,3,null,null,null,4]
```

**Example 2:**
```java
Input: inorder = [1], postorder = [1]

Output: [1]
```

**Constraints:**
- `1 <= postorder.length == inorder.length <= 3000`.
- `-3000 <= inorder[i], postorder[i] <= 3000`
- `inorder` and `postorder` consist of **unique** values.
- Each value of `postorder` also appears in `inorder`.
- `inorder` is **guaranteed** to be the inorder traversal of the tree.
- `postorder` is **guaranteed** to be the postorder traversal of the tree.

Intuition:
postorder logs the root node the last, so u know u can get the root node from the end of the post order. then to maintain the shape of the desired tree, inorder tell us important info on how the subtree is divided. 

so we should build a hashmap that map value to index with the inorder arr, then as we build node by consuming fomr the back of the postorder arr, we identify  the node position in the inorder arr with the hashamp, and we call recursive func with the info from the left and right pointer, which tell us whether we should put this node (pointer by p) here or not. if not, this node will be build in later recursive calls instead of this call

the if lo>hi: 
return None help us do exactly that! 

Solution:
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right

class Solution:
    def buildTree(
        self,
        inorder: List[int],
        postorder: List[int]
    ) -> Optional[TreeNode]:
        """
        Inorder traversal tells us how the tree is split:
        left subtree | root | right subtree.

        Postorder traversal tells us the construction order:
        left -> right -> root.
        When consuming postorder backwards, we see:
        root -> right -> left.

        Strategy:
        - Use a pointer `p` to consume postorder from the end.
        - Use a hashmap on inorder to locate the root index quickly.
        - Recursively construct the right subtree first, then the left.
        - `l` and `r` define the valid inorder range for the current subtree.
        """

        # Map each value to its index in inorder
        idx = {v: i for i, v in enumerate(inorder)}

        # Pointer to the current root in postorder
        p = len(postorder) - 1

        def dfs(l: int, r: int) -> Optional[TreeNode]:
            nonlocal p

            # No valid nodes in this inorder range
            if l > r:
                return None

            # The current root comes from postorder
            val = postorder[p]
            p -= 1
            node = TreeNode(val)

            # Split inorder range
            mid = idx[val]

            # IMPORTANT: build right subtree first
            node.right = dfs(mid + 1, r)
            node.left = dfs(l, mid - 1)

            return node

        return dfs(0, len(inorder) - 1)
```

Time: **O(n)**
- Each node is **created exactly once**
- Each node’s index in inorder is looked up in **O(1)** via the hashmap
- Each recursive call does constant work

Space: **O(n)**
idx = {value: index}

Tags: #post_order #in_order #recursion 

RL: 

Considerations:
