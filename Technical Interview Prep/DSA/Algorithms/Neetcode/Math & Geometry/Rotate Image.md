2025-02-23 11:57

Link: https://neetcode.io/problems/rotate-matrix

Problem: 
Given a square `n x n` matrix of integers `matrix`, rotate it by 90 degrees _clockwise_.

You must rotate the matrix _in-place_. Do not allocate another 2D matrix and do the rotation.

Intuition:
intuitively, we can come up with rotation by cells algo, but it is too complicated. With a more in-depth understanding of matrixes, we should think about the problem in terms of performing reverse and transpose , and their relationship with rotating the matrix

Solution:
Reverse & Transpose: 
```python
class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        # Reverse the matrix vertically
        matrix.reverse()

        # Transpose the matrix
        for i in range(len(matrix)):
            for j in range(i + 1, len(matrix)):
                matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
```
O(n^2) / O(1)
or 
`rotated_90_cw = list(map(list, zip(*matrix[::-1])))`

Rotation by cells
```python
class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        l, r = 0, len(matrix) - 1
        while l < r:
            for i in range(r - l):
                top, bottom = l, r

                # save the topleft
                topLeft = matrix[top][l + i]

                # move bottom left into top left
                matrix[top][l + i] = matrix[bottom - i][l]

                # move bottom right into bottom left
                matrix[bottom - i][l] = matrix[bottom][r - i]

                # move top right into bottom right
                matrix[bottom][r - i] = matrix[top + i][r]

                # move top left into top right
                matrix[top + i][r] = topLeft
            r -= 1
            l += 1
```
O(n^2) / O(1)

Tags: #matrix #math #geometry #transpose #reverse 

RL: 

Considerations:
Transpose of a matrix is when rows become columns and columns became rows! 
can use **zip** in python module to help us perform this
zip (matrix[::-1]) : grab the first element form each row and group them together
![[Screenshot 2025-02-23 at 12.21.31 PM.png]]

Rotate by 180 degree:
```python
rotated_180 = [row[::-1] for row in matrix[::-1]]
```

Rotate 90 degree counter-clockwise :
transpose and then reversed ,or reverse each column and then transpose
```python
# Option 1: Transpose first, then reverse each row
rotated_90_ccw = [row[::-1] for row in list(map(list, zip(*matrix)))]

# Option 2: Reverse columns first, then transpose
rotated_90_ccw_alternative = list(map(list, zip(*[row[::-1] for row in matrix])))

```