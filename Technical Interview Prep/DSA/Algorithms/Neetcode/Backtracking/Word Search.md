2025-02-04 15:44

Link:https://neetcode.io/problems/search-for-word

Problem: 
Given a 2-D grid of characters `board` and a string `word`, return `true` if the word is present in the grid, otherwise return `false`.

For the word to be present it must be possible to form it with a path in the board with horizontally or vertically neighboring cells. The same cell may not be used more than once in a word.

Motivation:
base case: boundary check, current element == word[ k ], or revisit cell or not !
explore 4 directions (all neighboring cells!)
if all element matched, return True

Solution:
```python
class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        ROWS, COLS = len(board), len(board[0])

        def dfs(r, c, i):
            if i == len(word):
                return True
            if (r < 0 or c < 0 or r >= ROWS or c >= COLS or
                word[i] != board[r][c] or board[r][c] == '#'):
                return False

            board[r][c] = '#'
            res = (dfs(r + 1, c, i + 1) or
                   dfs(r - 1, c, i + 1) or
                   dfs(r, c + 1, i + 1) or
                   dfs(r, c - 1, i + 1))
            board[r][c] = word[i]
            return res

        for r in range(ROWS):
            for c in range(COLS):
                if dfs(r, c, 0):
                    return True
        return False
```

set based
```python
class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        R, C = len(board), len(board[0])
        n = len(word)
        dirs = [(1,0), (-1,0), (0,1), (0,-1)]
        vis = set()

        def dfs(i: int, j: int, k: int) -> bool:
            # k is index in word we must match at (i, j)
            if board[i][j] != word[k]:
                return False
            if k == n - 1:              # matched last character
                return True

            vis.add((i, j))
            for di, dj in dirs:
                ni, nj = i + di, j + dj
                if 0 <= ni < R and 0 <= nj < C and (ni, nj) not in vis:
                    if dfs(ni, nj, k + 1):
                        vis.remove((i, j))
                        return True
            vis.remove((i, j))
            return False

        for i in range(R):
            for j in range(C):
                if dfs(i, j, 0):
                    return True
        return False
```

Tags: #backtracking #complete_search #set 

RL: 