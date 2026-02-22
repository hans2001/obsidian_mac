2026-01-08 15:21

Link: https://leetcode.com/problems/unique-binary-search-trees-ii/description/

Problem: 
Given an integer `n`, return _all the structurally unique **BST'**s (binary search trees), which has exactly_ `n` _nodes of unique values from_ `1` _to_ `n`. Return the answer in **any order**.

**Example 1:**

![](https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg)

**Input:** n = 3
**Output:** [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]

**Example 2:**

**Input:** n = 1
**Output:** [[1]]

**Constraints:**
- `1 <= n <= 8`

failure: 

Intuition:
for each subtree, there are possibilities that each node can be root node or not , therefore, in each iteration ,we will have a for loop that test that ,and within each iteration ,the left and right portion can only be divided from the current node i, where left portion nodes can only be smaller ,and  right portion can only be larger(BST properties) that is why we recursively build a subarr that is returned to form the different combinations at parent level
sub problem  -> problem ( dp )
## **为什么不会漏？**
	因为：
	- 所有合法左子树都在 left_trees
	- 所有合法右子树都在 right_trees
	- BST 要求 **必须选一个左、一个右**
	你枚举了 **所有可能的配对**。

## **为什么不会多？**
	因为：
	- 不同 (left, right) 组合 → 结构必然不同
	- 根 i 固定，左右子树结构只要有一个不同，整棵树就不同

## **“子结构”在这里到底是什么意思？**
	**子结构不是共享的，而是组合出来的。**
	- left_trees 提供 **所有可能的左结构**
	- right_trees 提供 **所有可能的右结构**
	- 当前层负责 **把它们粘到一个新根上**
    
这是一个**自底向上的结构组合过程**。

![[Screenshot 2026-01-08 at 3.27.29 PM.png]]
Solution:
```python
# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution(object):
    def generateTrees(self, n):
        """
        :type n: int
        :rtype: List[Optional[TreeNode]]
        """
        if n == 0:
            return []

        def dfs(l, r):
            if l > r:
                return [None]

            res = []

            for i in range(l, r + 1):
                left_trees = dfs(l, i - 1)
                right_trees = dfs(i + 1, r)

                for left in left_trees:
                    for right in right_trees:
                        root = TreeNode(i)
                        root.left = left
                        root.right = right
                        res.append(root)

            return res

        return dfs(1, n)
```

Tags: #binary_search_tree #dfs #recursion #dp 

RL: 

Considerations:
