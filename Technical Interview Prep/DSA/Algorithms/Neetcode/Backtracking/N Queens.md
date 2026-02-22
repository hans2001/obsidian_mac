2025-02-05 12:35

Link:https://neetcode.io/problems/n-queens

Problem: 
The **n-queens** puzzle is the problem of placing `n` queens on an `n x n` chessboard so that no two queens can attack each other.

A **queen** in a chessboard can attack horizontally, vertically, and diagonally.

Given an integer `n`, return all distinct solutions to the **n-queens puzzle**.

Each solution contains a unique board layout where the queen pieces are placed. `'Q'` indicates a queen and `'.'` indicates an empty space.

You may return the answer in **any order**.

Motivation:
check if we can place a queen in current cell. if yes, we continue build the local solutions, otherwise we forbid. the base case is when column index reach the end. The problem implicitly implies a possible Queen in each column, so we recursively find the queen's position in each column, by checking which row we can place!

Solution:
Mine:
![[Screenshot 2025-02-05 at 12.36.03 PM.png]]

Editorial: 
```python
class Solution:
    def solveNQueens(self, n: int) -> List[List[str]]:
        res = []
        board = [["."] * n for i in range(n)]

        def backtrack(r):
            if r == n:
                copy = ["".join(row) for row in board]
                res.append(copy)
                return
            for c in range(n):
                if self.isSafe(r, c, board):
                    board[r][c] = "Q"
                    backtrack(r + 1)
                    board[r][c] = "."

        backtrack(0)
        return res

    def isSafe(self, r: int, c: int, board):
        row = r - 1
        while row >= 0:
            if board[row][c] == "Q":
                return False
            row -= 1
            
        row, col = r - 1, c - 1
        while row >= 0 and col >= 0:
            if board[row][col] == "Q":
                return False
            row -= 1
            col -= 1

        row, col = r - 1, c + 1
        while row >= 0 and col < len(board):
            if board[row][col] == "Q":
                return False
            row -= 1
            col += 1
        return True
```

Tags: #backtracking 

RL: [[Letter Combinations of a Phone Number]]