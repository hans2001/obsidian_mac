2025-05-10 18:02

Link: https://neetcode.io/problems/house-robber-iii

Problem: 
The thief has found himself a new place for his thievery again. There is only one entrance to this area, called `root`.

In this new place, there are houses and each house has its only one parent house. All houses in this place form a **binary tree**. It will automatically contact the police if **two directly-linked houses were broken**.

You are given the `root` of the binary tree, return the **maximum**amount of money the thief can rob **without alerting the police**.

**Example 1:**

![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/9cbe7429-7c26-4527-8e52-4209021b5300/public)

```java
Input: root = [1,4,null,2,3,3]

Output: 7
```
Explanation: Maximum amount of money the thief can rob = 4 + 3 = 7

**Example 2:**

![](https://imagedelivery.net/CLfkmk9Wzy8_9HRyug4EVA/7fb5c7a2-ebd5-410f-c79f-5eddcace2600/public)

```java
Input: root = [1,null,2,3,5,4,2]

Output: 12
```
Explanation: Maximum amount of money the thief can rob = 1 + 4 + 2 + 5 = 12

Intuition:
this is a post-order traversal, where we compute the result for nested subtrees first, then we deliver those results to parent!

- **`rob_current`**: This represents the maximum money we can get if we **include** (rob) the current node in our solution. When we rob the current node, we must skip its children (to avoid robbing adjacent houses). So we add:
    - The value of the current node (`node.val`)
    - The maximum money when we skip the left child (`skip_left`)
    - The maximum money when we skip the right child (`skip_right`)
- **`skip_current`**: This represents the maximum money we can get if we **exclude** (skip) the current node from our solution. When we skip the current node, we're free to either rob or skip each of its children, depending on which gives more money. So we add:
    - The best option for the left subtree: `max(rob_left, skip_left)`
    - The best option for the right subtree: `max(rob_right, skip_right)`

Solution:
recursion: 
```python
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def rob(self, root: Optional[TreeNode]) -> int:
        if not root:
            return 0
        
        res = root.val
        if root.left:
            res += self.rob(root.left.left) + self.rob(root.left.right)
        if root.right:
            res += self.rob(root.right.left) + self.rob(root.right.right)
        
        res = max(res, self.rob(root.left) + self.rob(root.right))
        return res
```
we take the best form grandchildren, that is why we add both left and right grandchildren's result! then we compare either rob this node with grandchild or rob child nodes only, and return the maximum!( recalculation of result occur, include memoization is better! )

dp (optimal)
```python
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:****
    def rob(self, root: TreeNode) -> int:
        def dfs(root):
            if not root:
                return [0, 0]

            leftPair = dfs(root.left)
            rightPair = dfs(root.right)

            withRoot = root.val + leftPair[1] + rightPair[1]
            withoutRoot = max(leftPair) + max(rightPair)

            return [withRoot, withoutRoot]

        return max(dfs(root))
```

Complexity:
Time: 
O(n) each node is visited exactly once 

Space: O(h) ( balanced tree ) / O(n) skewed tree ( each node is visited )
h: height of the tree (recursion stack)

Tags: #dp #tree #recursion #dfs #post_order_traversal 

RL: 

Considerations:
### Why Return Both Values?
We return both values because the decision at each node depends on both scenarios for its children:

1. **Information Propagation**: When we're at a node, we need to know both possible outcomes for its subtrees to make the optimal decision. The decision at the parent depends on both the "rob" and "skip" scenarios of its children.
2. **Interdependence**: The "rob" value for a node depends on the "skip" values of its children. Similarly, the "skip" value for a node depends on both the "rob" and "skip" values of its children.
3. **Avoiding Information Loss**: If we only returned the maximum of the two values at each step, we would lose the critical information about whether that maximum came from robbing or skipping the node, which we need to enforce the "no adjacent robberies" constraint.

which mean in order to compute whether we rob parent node or not, we would need to know if our child node has been robbed or not! if we just returned the max value, not only we ignore this information, though reach maximum result, might resulted from state that is not possible (robbing neighbors and current!)

**DP on trees**
This approach is a form of dynamic programming on trees, where we're computing and storing optimal solutions to subproblems (the subtrees) to build up the solution to the original problem.