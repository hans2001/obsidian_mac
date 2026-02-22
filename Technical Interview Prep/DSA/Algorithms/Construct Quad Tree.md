2025-05-10 18:02

Link: https://neetcode.io/problems/construct-quad-tree

**Problem**: 
You are given a `n * n` binary matrix `grid`. We want to represent `grid` with a Quad-Tree.
Return the root of the Quad-Tree representing `grid`.

A Quad-Tree is a tree data structure in which each internal node has exactly four children. Besides, each node has two attributes:

- `isLeaf`: True if the node is a leaf node on the tree or False if the node has four children.
- `val`: True if the node represents a grid cell of `1's` or False if the node represents a grid cell of `0's`. Notice that you can assign the `val` to True or False when `isLeaf` is False, and both are accepted in the answer.
    

```java
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;
}
```

We can construct a Quad-Tree from a two-dimensional grid using the following steps:

1. If the current grid has the same value (i.e all `1's` or all `0's`), set `isLeaf` True and set `val` to the value of the grid and set the four children to Null and stop.
2. If the current grid has different values, set `isLeaf` to False and set `val` to any value and divide the current grid into four sub-grids representing the four childrens of the current node.
3. Recurse the steps for every children of the current node.

Intuition:
divide the grid into sections, starting from the 4 borders! with the top down approach ,we request result for the child section by checking if values within the subsections are the same, if yes we can prune the result by returning as a leaf node, if not, we have to resolve from the most nested section, until we reach a leaf node! 

Solution:
naive recursion (top - down): 
```python
"""
# Definition for a QuadTree node.
class Node:
    def __init__(self, val, isLeaf, topLeft, topRight, bottomLeft, bottomRight):
        self.val = val
        self.isLeaf = isLeaf
        self.topLeft = topLeft
        self.topRight = topRight
        self.bottomLeft = bottomLeft
        self.bottomRight = bottomRight
"""

class Solution:
    def construct(self, grid: List[List[int]]) -> 'Node':
        def dfs(n, r, c):
            allSame = True
            for i in range(n):
                for j in range(n):
                    if grid[r][c] != grid[r + i][c + j]:
                        allSame = False
                        break
            if allSame:
                return Node(grid[r][c], True)
            
            n = n // 2
            topleft = dfs(n, r, c)
            topright = dfs(n, r, c + n)
            bottomleft = dfs(n, r + n, c)
            bottomright = dfs(n, r + n, c + n)
            
            return Node(0, False, topleft, topright, bottomleft, bottomright)
        
        return dfs(len(grid), 0, 0)
```

optimized recursion (bottom-up) 
```python
"""
# Definition for a QuadTree node.
class Node:
    def __init__(self, val, isLeaf, topLeft, topRight, bottomLeft, bottomRight):
        self.val = val
        self.isLeaf = isLeaf
        self.topLeft = topLeft
        self.topRight = topRight
        self.bottomLeft = bottomLeft
        self.bottomRight = bottomRight
"""

class Solution:
    def construct(self, grid: List[List[int]]) -> 'Node':
        def dfs(n, r, c):
            if n == 1:
                return Node(grid[r][c] == 1, True)

            mid = n // 2
            topLeft = dfs(mid, r, c)
            topRight = dfs(mid, r, c + mid)
            bottomLeft = dfs(mid, r + mid, c)
            bottomRight = dfs(mid, r + mid, c + mid)

            if (topLeft.isLeaf and topRight.isLeaf and 
                bottomLeft.isLeaf and bottomRight.isLeaf and
                topLeft.val == topRight.val == bottomLeft.val == bottomRight.val):
                return Node(topLeft.val, True)

            return Node(False, False, topLeft, topRight, bottomLeft, bottomRight)

        return dfs(len(grid), 0, 0)
```

The main difference is the naive solution iterate through each element within the subsection, checking if all element are the same within. 
While the optimized one build recursively form size = 1, even though the recursion stack might be longer, the time needed to check if element are the same within subsections is constant,(by checking if 4 child node is leaf node and compare their value! ,if yes, we have same values within the section!)
The second approach went through each cell exactly once, hence we can reduce the complexity to O(n^2)

Complexity:
Time: 
naive: O(log n * n^2)
optimized : O(n^2)

Space: 
O(log n) for recursion stack

Tags: #recursion #tree #quad_tree #dfs 

RL: 

Considerations:
