2025-02-08 13:14

Link: https://neetcode.io/problems/word-ladder

Problem: 
You are given two words, `beginWord` and `endWord`, and also a list of words `wordList`. All of the given words are of the same length, consisting of lowercase English letters, and are all distinct.

Your goal is to transform `beginWord` into `endWord` by following the rules:

- You may transform `beginWord` to any word within `wordList`, provided that at exactly one position the words have a different character, and the rest of the positions have the same characters.
- You may repeat the previous step with the new word that you obtain, and you may do this as many times as needed.

Return the **minimum number of words within the transformation sequence** needed to obtain the `endWord`, or `0` if no such sequence exists.

Motivation:
given that only one character can be changed, to any word in the wordList. we use this information to generate nodes for the graph, where each character is replaced by " * "(any character),and connect to words in the word list that has the same pattern, as edges. ( this is the graph building process, where each word can be the stepping stone, but u dont know which character in the word would be so u have to change  all ch in each word to be a unique state in the graph ,avoid create the same pattern)

Then we can traverse the graph from beginWord to endWord through BFS to find the shortest path. from each level, we prepare nodes for the next level, which are words that has the same pattern as current word, with the extra " * ", but not exactly the same word!

The neighbors should not have been visited as well, into the queue, for future processing. If the word is the end word, we return the current level we are at, otherwise, we go to the next level! 

Solution:
BFS:
```python
class Solution:
    def ladderLength(self, beginWord: str, endWord: str, wordList: List[str]) -> int:
        if endWord not in wordList:
            return 0

        nei = collections.defaultdict(list)
        wordList.append(beginWord)
        for word in wordList:
            for j in range(len(word)):
                pattern = word[:j] + "*" + word[j + 1 :]
                nei[pattern].append(word)

        visit = set([beginWord])
        q = deque([beginWord])
        res = 1
        while q:
            for i in range(len(q)):
                word = q.popleft()
                if word == endWord:
                    return res
                for j in range(len(word)):
                    pattern = word[:j] + "*" + word[j + 1 :]
                    for neiWord in nei[pattern]:
                        if neiWord not in visit:
                            visit.add(neiWord)
                            q.append(neiWord)
            res += 1
        return 0
```

Tags: #google #bfs #dict #deque

RL: 