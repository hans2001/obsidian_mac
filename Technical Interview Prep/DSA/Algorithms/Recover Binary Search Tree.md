2025-12-19 13:45

Link:

Problem: 
You are given the `root` of a binary search tree (BST), where the values of **exactly two** nodes of the tree were swapped by mistake. Recover the tree without changing its structure.

**Example 1:**
![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/da4adadd-34ee-4edf-dc3e-278c975c8300/public)

![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/96e06c58-5c7b-41dc-b437-b5149b510400/public)

```java
Input: root = [1,3,null,null,2]

Output: [3,1,null,null,2]
```

**Example 2:**
![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/b31f94d3-7ed1-4589-6059-2f7f18335000/public)

![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/d0eb2f44-1931-49b3-4e26-03c7ffebae00/public)

```java
Input: root = [2,3,1]

Output: [2,1,3]
```

**Constraints:**

- `2 <= The number of nodes in the tree <= 1000`.
- `-(2^31) <= Node.val <= ((2^31)-1)`

**Follow up:** A solution using `O(n)` space is pretty straight-forward. Could you devise a constant `O(1)` space solution?

Intuition:
find the node that is too large too early and  to small too late. the first violation expose the node that is too large too early ,(not compliant to the BST structure)  and if there are second violation the too small too late variable should be updated, since it should be the smallest misplaced node in all violations, so it should be placed the earliest .which is to replace with the first violation( for more context, refer to tree drawn from examples! )


Solution:
```python
class Solution:
    def recoverTree(self, root: Optional[TreeNode]) -> None:
        first = second = prev = None
        def inorder(node):
            nonlocal first, second, prev
            if not node:
                return
            inorder(node.left)
            # detect inversion
            if prev and prev.val > node.val:
                if not first:
                    first = prev          # too-big, too-early
                second = node             # too-small, too-late (keep updating)
            prev = node
            inorder(node.right)

        inorder(root)
        if first and second:
            first.val, second.val = second.val, first.val
```

Tags: #in_order  #binary_search_tree 

RL: 

Considerations:
