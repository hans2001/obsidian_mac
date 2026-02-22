2025-02-23 11:57

Link: https://neetcode.io/problems/spiral-matrix

Problem: 
Given an `m x n` matrix of integers `matrix`, return a list of all elements within the matrix in _spiral order_.

Intuition:
always define boundaries for problems such as this, and for each iteration ,we go through all cells within the boundary(may be!) ,and update the boundary accordingly! 
the additional check on boundary concerns the validness of sub matrix with single number of row or columns!

if t < b:
The guards prevent **double-counting** and even **indexing the wrong row** when a layer collapses to a single row or single column.

Solution:
```python
class Solution:
    def spiralOrder(self, matrix: List[List[int]]) -> List[int]:
        if not matrix or not matrix[0]:
            return []

        l, r = 0, len(matrix[0])   # r is exclusive
        t, b = 0, len(matrix)      # b is exclusive
        res = []

        while l < r and t < b:
            # top row
            for i in range(l, r):
                res.append(matrix[t][i])
            t += 1

            # right column
            for j in range(t, b):
                res.append(matrix[j][r - 1])
            r -= 1

            # bottom row (guard)
            if t < b:
                for i in range(r - 1, l - 1, -1):
                    res.append(matrix[b - 1][i])
                b -= 1

            # left column (guard)
            if l < r:
                for j in range(b - 1, t - 1, -1):
                    res.append(matrix[j][l])
                l += 1

        return res
```
O(m * n) / O(1)
m is number of row, n is number of column (iterate once for each row and column)
total number of cells: m * n

Tags: #matrix  #math #geometry #boundary

RL: [[Rotate Image]]

Considerations:
