2025-01-24 14:27

Link:https://neetcode.io/problems/search-2d-matrix

Problem: ![[Screenshot 2025-01-24 at 2.44.33 PM.png]]

Motivation:
find the row that might contain target first, then search that row
make sure to update md based on i and j, and in the loop

1.Solution:![[Screenshot 2025-01-24 at 2.44.43 PM.png]]

2.class Solution:
    def searchMatrix(self, matrix: List[List[int]], target: int) -> bool:
        ROWS, COLS = len(matrix), len(matrix[0])

        l, r = 0, ROWS * COLS - 1
        while l <= r:
            m = l + (r - l) // 2
            row, col = m // COLS, m % COLS
            if target > matrix[row][col]:
                l = m + 1
            elif target < matrix[row][col]:
                r = m - 1
            else:
                return True
        return False
flattened array

3.![[Screenshot 2025-01-24 at 3.06.48 PM.png]]
bisect module

Tags: #binary_search 

RL: [[Binary Search]]

Time complexity: O(log (m*n))

Space complexity: O(1)