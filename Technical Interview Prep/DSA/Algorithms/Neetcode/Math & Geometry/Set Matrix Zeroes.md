2025-02-23 11:57

Link: https://neetcode.io/problems/set-zeroes-in-matrix

Problem: 
Given an `m x n` matrix of integers `matrix`, if an element is `0`, set its entire row and column to `0`'s.

You must update the matrix _in-place_.

**Follow up:** Could you solve it using `O(1)` space?

Intuition:
optimized version: we ca integrate the O(m+n) indicator cell into the original grid. for column to be 0, we set the first row same column cell to be 0, for row to be 0, we set same row first column to be 0 (except the first row, since we used that cell to indicate the first column to be 0). we use an additional variable row_zero to indicate if the first row should be all zero or not!. in first pass, we update the indicator grid, in second pass, we handle all positions except the first row and first column ,since the indicators is being used to update other grid cell in current pass, if modified, will lead to misinterpretations!. in the final pass, we handle the first row and first column  

Solution:
Mine: 
![[Screenshot 2025-02-23 at 5.59.42 PM.png]]
O(m * n) / O(1)
O(m + n) for setting zeros, the outer loop takes O(m * n) time
total: O(min (m,n))

space optimized:
```python
class Solution:
    def setZeroes(self, matrix: List[List[int]]) -> None:
        ROWS, COLS = len(matrix), len(matrix[0])
        rowZero = False

        for r in range(ROWS):
            for c in range(COLS):
                if matrix[r][c] == 0:
                    matrix[0][c] = 0
                    if r > 0:
                        matrix[r][0] = 0
                    else:
                        rowZero = True

        for r in range(1, ROWS):
            for c in range(1, COLS):
                if matrix[0][c] == 0 or matrix[r][0] == 0:
                    matrix[r][c] = 0

        if matrix[0][0] == 0:
            for r in range(ROWS):
                matrix[r][0] = 0

        if rowZero:
            for c in range(COLS):
                matrix[0][c] = 0
```
O(m * n) / O(1)

Tags: #matrix #math

RL: 

Considerations:
