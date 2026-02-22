2025-02-06 11:45

Link:https://neetcode.io/problems/design-word-search-data-structure

Problem: 
Design a data structure that supports adding new words and searching for existing words.

Implement the `WordDictionary` class:
- `void addWord(word)` Adds `word` to the data structure.
- `bool search(word)` Returns `true` if there is any string in the data structure that matches `word` or `false`otherwise. `word` may contain dots `'.'` where dots can be matched with any letter.

Motivation:
for dot character, we go through all child values for current node (it means current node can start with any char), then recursively find if there is a path that matches the word string. we increment j to i +1 to indicate we are checking the next char in the word!

Solution:
```python
class TrieNode:
    def __init__(self):
        self.children = {}
        self.word = False

class WordDictionary:
    def __init__(self):
        self.root = TrieNode()

    def addWord(self, word: str) -> None:
        cur = self.root
        for c in word:
            if c not in cur.children:
                cur.children[c] = TrieNode()
            cur = cur.children[c]
        cur.word = True

    def search(self, word: str) -> bool:
        def dfs(j, root):
            cur = root
            for i in range(j, len(word)):
                c = word[i]
                if c == ".":
                    for child in cur.children.values():
                        if dfs(i + 1, child):
                            return True
                    return False
                else:
                    if c not in cur.children:
                        return False
                    cur = cur.children[c]
            return cur.word

        return dfs(0, self.root)
```
Time: O(n) for addWord(), O(n) for search().
Space: O(t+n)
(n: length of the string, t: total number of TrieNodes in the Tree)

Tags: #trie  #dfs

RL: [[Implement Trie (Prefix Tree)]]