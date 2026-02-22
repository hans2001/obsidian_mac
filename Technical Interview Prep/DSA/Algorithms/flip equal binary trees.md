2026-01-08 16:13

Link: https://leetcode.com/problems/flip-equivalent-binary-trees/description/

Problem: 
For a binary tree **T**, we can define a **flip operation** as follows: choose any node, and swap the left and right child subtrees.

A binary tree **X** is _flip equivalent_ to a binary tree **Y** if and only if we can make **X** equal to **Y** after some number of flip operations.

Given the roots of two binary trees `root1` and `root2`, return `true` if the two trees are flip equivalent or `false` otherwise.

**Example 1:**

![Flipped Trees Diagram](https://assets.leetcode.com/uploads/2018/11/29/tree_ex.png)

**Input:** root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
**Output:** true
**Explanation:** We flipped at nodes with values 1, 3, and 5.

**Example 2:**
**Input:** root1 = [], root2 = []
**Output:** true

**Example 3:**
**Input:** root1 = [], root2 = [1]
**Output:** false

**Constraints:**
- The number of nodes in each tree is in the range `[0, 100]`.
- Each tree will have **unique node values** in the range `[0, 99]`.

failure: 

Intuition:
at each level(base case), we compare if the node values are equal(true), or if we only have a single node (false), or we are missing both node( true ). then from this level, we determine how we progress to the next level, we can either flip the sequence of entry, or stay the same. for example, for tree1, we decide to go to the left child, and we do the same for tree2, this is no flipping. however, there might be case that flipping is required so that the node can match, so we go left for tree1, but go right for tree2 (flipping). if either of the strategy works, which mean for the 2 subtree starting from this node, the 2 subtree is flip equal, and we populate the result to parent. if at some higher level, both strat does not work, the 2 tree are considered not to be flip equal, otherwise they are! 

Solution:
```python
# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right

class Solution(object):
    def flipEquiv(self, root1, root2):
        """
        :type root1: Optional[TreeNode]
        :type root2: Optional[TreeNode]
        :rtype: bool
        """
        def eq(r1, r2):
            # Both empty => equivalent
            if r1 is None and r2 is None:
                return True

            # One empty, one not => not equivalent
            if r1 is None or r2 is None:
                return False

            # Different values => not equivalent
            if r1.val != r2.val:
                return False

            # Option 1: no flip at this node
            no_flip = eq(r1.left, r2.left) and eq(r1.right, r2.right)

            # Option 2: flip children at this node
            flip = eq(r1.left, r2.right) and eq(r1.right, r2.left)

            return no_flip or flip

        return eq(root1, root2)
```

Tags: #binary_tree #recursion  

RL: 

Considerations:
