2025-02-06 20:54

Link:https://neetcode.io/problems/surrounded-regions

Problem: 
You are given a 2-D matrix `board` containing `'X'` and `'O'` characters.

If a continuous, four-directionally connected group of `'O'`s is surrounded by `'X'`s, it is considered to be **surrounded**. 

Change all **surrounded** regions of `'O'`s to `'X'`s and do so **in-place** by modifying the input board.

Motivation:
use the "reverse" idea, we start from the boarder 'O's which we should prevent, and turn the "O" cells that we can reach to some symbol that represent visited "#" etc. Then the rest of the "O" cell that cannot be reached mean they are surrounded by X. So in the last loop, we turn those remaining "O" cells to "X", and the visited "#" cell back to "O", meaning they are not surrounded.

Solution:
DFS:
```python
class Solution:
    def solve(self, board: List[List[str]]) -> None:
        ROWS, COLS = len(board), len(board[0])

        def capture(r, c):
            if (r < 0 or c < 0 or r == ROWS or 
                c == COLS or board[r][c] != "O"
            ):
                return
            board[r][c] = "T"
            capture(r + 1, c)
            capture(r - 1, c)
            capture(r, c + 1)
            capture(r, c - 1)

        for r in range(ROWS):
            if board[r][0] == "O":
                capture(r, 0)
            if board[r][COLS - 1] == "O":
                capture(r, COLS - 1)
        
        for c in range(COLS):
            if board[0][c] == "O":
                capture(0, c)
            if board[ROWS - 1][c] == "O":
                capture(ROWS - 1, c)

        for r in range(ROWS):
            for c in range(COLS):
                if board[r][c] == "O":
                    board[r][c] = "X"
                elif board[r][c] == "T":
                    board[r][c] = "O"
```
O(m∗n) / O(m∗n)

BFS:
```python
class Solution:
    def solve(self, board: List[List[str]]) -> None:
        ROWS, COLS = len(board), len(board[0])
        directions = [(1, 0), (-1, 0), (0, 1), (0, -1)]

        def capture():
            q = deque()
            for r in range(ROWS):
                for c in range(COLS):
                    if (r == 0 or r == ROWS - 1 or 
                        c == 0 or c == COLS - 1 and 
                        board[r][c] == "O"
                    ):
                        q.append((r, c))
            while q:
                r, c = q.popleft()
                if board[r][c] == "O":
                    board[r][c] = "T"
                    for dr, dc in directions:
                        nr, nc = r + dr, c + dc
                        if 0 <= nr < ROWS and 0 <= nc < COLS:
                            q.append((nr, nc))
        
        capture()
        for r in range(ROWS):
            for c in range(COLS):
                if board[r][c] == "O":
                    board[r][c] = "X"
                elif board[r][c] == "T":
                    board[r][c] = "O"
```
O(m∗n) / O(m∗n)

Tags: #dfs #bfs

RL: 