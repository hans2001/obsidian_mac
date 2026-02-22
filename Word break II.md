2025-05-02 11:15

Link:https://leetcode.com/problems/word-break-ii/description/

Problem: 
Given a string `s` and a dictionary of strings `wordDict`, add spaces in `s` to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in **any order**.

**Note** that the same word in the dictionary may be reused multiple times in the segmentation.

Intuition:
initialize the trie with the wordDict
as we work on the solution recursively, we find the splitting point of substring through the nested for loop, if we found one, we make this a possible combo by adding it to path, and call the function to split the next potential substring. However, there might be case that we can split this substring at a later point( word with same prefix! ). so we backtrack the path addition, and continue with the for loop!
if we cannot not find a splitting point in the for loop, the current substring cannot be split! and there is no possibilities for the current way of splitting strings! 

Solution:
trie
```python
class TrieNode:
    def __init__(self):
        self.isEnd = False
        self.children = [None] * 26  # For lowercase English letters


class Trie:
    def __init__(self):
        self.root = TrieNode()

    def insert(self, word):
        node = self.root
        for char in word:
            index = ord(char) - ord("a")
            if not node.children[index]:
                node.children[index] = TrieNode()
            node = node.children[index]
        node.isEnd = True


class Solution:
    def wordBreak(self, s: str, wordDict: List[str]) -> List[str]:
        # Build the Trie from the word dictionary
        trie = Trie()
        for word in wordDict:
            trie.insert(word)

        # Map to store results of subproblems
        dp = {}

        # Iterate from the end of the string to the beginning
        for start_idx in range(len(s), -1, -1):
            # List to store valid sentences starting from start_idx
            valid_sentences = []

            # Initialize current node to the root of the trie
            current_node = trie.root

            # Iterate from start_idx to the end of the string
            for end_idx in range(start_idx, len(s)):
                char = s[end_idx]
                index = ord(char) - ord("a")

                # Check if the current character exists in the trie
                if not current_node.children[index]:
                    break

                # Move to the next node in the trie
                current_node = current_node.children[index]

                # Check if we have found a valid word
                if current_node.isEnd:
                    current_word = s[start_idx : end_idx + 1]

                    # If it's the last word, add it as a valid sentence
                    if end_idx == len(s) - 1:
                        valid_sentences.append(current_word)
                    else:
                        # If it's not the last word, append it to each sentence formed by the remaining substring
                        sentences_from_next_index = dp.get(end_idx + 1, [])
                        for sentence in sentences_from_next_index:
                            valid_sentences.append(
                                current_word + " " + sentence
                            )

            # Store the valid sentences in dp
            dp[start_idx] = valid_sentences

        # Return the sentences formed from the entire string
        return dp.get(0, [])
```

Complexity:
**Time:** 
consider a decision tree ,each node follow by 2 child node. since we will be exploring the full depth and width of the tree, the time complexity is essentially exponential O(2^n)
and for each exploration, we take O(n) time to iterate the string to find the splitting point, so total time complexity is O(2^n * n )

**Space:**
- **Trie structure**: O(k) space for storing all characters from all words in the dictionary
- **Recursion stack**: O(n) in the worst case for the depth of recursion
- **Memoization table**: O(n) to store results for each starting position
- **Result storage**: O(2^n * n) in the worst case to store all possible sentences (dominant)

total: **O(n * k + 2^n * n)** where:

Tags: #backtracking  #trie 

RL: 

Considerations:
