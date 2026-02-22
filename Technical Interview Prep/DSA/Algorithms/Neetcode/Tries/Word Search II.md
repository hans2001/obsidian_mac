2025-02-06 13:58

Link:https://neetcode.io/problems/search-for-word-ii

Problem: 
Given a 2-D grid of characters `board` and a list of strings `words`, return all words that are present in the grid.

For a word to be present it must be possible to form the word with a path in the board with horizontally or vertically neighboring cells. The same cell may not be used more than once in a word.

Motivation:
declare trie node to efficiently compute solution.  first we build the Trie by inserting each word in the target list. Then as we traverse through the board, children in each Trie node and the visit set serve as a guidance for us to prune our search(early return). for valid paths, we build a local string, and when the current node's flag indicate the end of an word, we append the local string to the result set, indicate a potential path in the board that matches a target in the word list!

Solution:
```python
class TrieNode:
    def __init__(self):
        self.children = {}
        self.isWord = False

    def addWord(self, word):
        cur = self
        for c in word:
	        ## create node by default! otherwise get the existing node! 
            cur = cur.children[c]
        cur.isWord = True

class Solution:
    def findWords(self, board: List[List[str]], words: List[str]) -> List[str]:
        root = TrieNode()
        for w in words:
            root.addWord(w)

        ROWS, COLS = len(board), len(board[0])
        res, visit = set(), set()

        def dfs(r, c, node, word):
            if (r < 0 or c < 0 or r >= ROWS or 
                c >= COLS or (r, c) in visit or 
                board[r][c] not in node.children
            ):
                return

            visit.add((r, c))
            node = node.children[board[r][c]]
            word += board[r][c]
            if node.isWord:
                res.add(word)

            dfs(r + 1, c, node, word)
            dfs(r - 1, c, node, word)
            dfs(r, c + 1, node, word)
            dfs(r, c - 1, node, word)
            visit.remove((r, c))

        for r in range(ROWS):
            for c in range(COLS):
                dfs(r, c, root, "")

        return list(res)
```
 O(m∗n∗4∗3t−1+s) / O(s)

complexity: 
to optimize time complexity; we store the word at the last character node, and we set the board cell as # instead of using a additional visited set ! 

time: 
The worst-case time complexity remains O(M×N×4^L) where:

- M×N is the board size
- L is the maximum word length
- 4 is the number of directions

Tags: #dfs #trie #prune #meta

RL: 