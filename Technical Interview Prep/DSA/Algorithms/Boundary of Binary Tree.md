2025-07-10 10:29

## Link:
https://leetcode.com/problems/boundary-of-binary-tree/description/?envType=company&envId=blackrock&favoriteSlug=blackrock-more-than-six-months

## Problem: 
The **boundary** of a binary tree is the concatenation of the **root**, the **left boundary**, the **leaves** ordered from left-to-right, and the **reverse order** of the **right boundary**.

The **left boundary** is the set of nodes defined by the following:

- The root node's left child is in the left boundary. If the root does not have a left child, then the left boundary is **empty**.
- If a node in the left boundary and has a left child, then the left child is in the left boundary.
- If a node is in the left boundary, has **no** left child, but has a right child, then the right child is in the left boundary.
- The leftmost leaf is **not** in the left boundary.

The **right boundary** is similar to the **left boundary**, except it is the right side of the root's right subtree. Again, the leaf is **not** part of the **right boundary**, and the **right boundary** is empty if the root does not have a right child.

The **leaves** are nodes that do not have any children. For this problem, the root is **not** a leaf.

Given the `root` of a binary tree, return _the values of its **boundary**_.

**Example 1:**

![](https://assets.leetcode.com/uploads/2020/11/11/boundary1.jpg)

**Input:** root = [1,null,2,3,4]
**Output:** [1,3,4,2]
**Explanation:**
- The left boundary is empty because the root does not have a left child.
- The right boundary follows the path starting from the root's right child 2 -> 4.
  4 is a leaf, so the right boundary is [2].
- The leaves from left to right are [3,4].
Concatenating everything results in [1] + [] + [3,4] + [2] = [1,3,4,2].

**Example 2:**

![](https://assets.leetcode.com/uploads/2020/11/11/boundary2.jpg)

**Input:** root = [1,2,3,4,5,6,null,null,null,7,8,9,10]
**Output:** [1,2,4,7,8,9,10,6,3]
**Explanation:**
- The left boundary follows the path starting from the root's left child 2 -> 4.
  4 is a leaf, so the left boundary is [2].
- The right boundary follows the path starting from the root's right child 3 -> 6 -> 10.
  10 is a leaf, so the right boundary is [3,6], and in reverse order is [6,3].
- The leaves from left to right are [4,7,8,9,10].
Concatenating everything results in [1] + [2] + [4,7,8,9,10] + [6,3] = [1,2,4,7,8,9,10,6,3].

## Intuition:
define 3 function to get the left, right boundary and the leaves, dont include root i nthis process, add root at the end! 

## Solution:
```python
def boundaryOfBinaryTree(self, root: Optional[TreeNode]) -> List[int]:
	def find_left( root , ar ):
		%% pre order %%
		if not root or not root.left and not root.right:
			return
		ar.append( root.val )
		if root.left :
			find_left(root.left,ar)
		else:
			find_left(root.right,ar)
	
	def find_right( root , ar ):
		%% post order %%
		if not root or not root.left and not root.right:
			return
		if root.right :
			find_right(root.right,ar)
		else:
			find_right(root.left,ar)
		ar.append( root.val )
	
	def find_leaves( the_root, root , ar ):
		if not root:
			return
		if not root.left and not root.right and root != the_root:
			ar.append( root.val )
			return
		if root.left:
			find_leaves (the_root , root.left ,ar )
		if root.right:
			find_leaves (the_root , root.right ,ar )
	
	left_bound = []
	right_bound = []
	leaves = []
	find_left ( root.left , left_bound )
	find_right ( root.right , right_bound )
	find_leaves ( root ,root, leaves )
	res= [ root.val ]
	res.extend(left_bound)
	res.extend(leaves)
	res.extend(right_bound)
	return res
```

Tags: #pre_order #post_order #binary_tree 

RL: 

Considerations:
