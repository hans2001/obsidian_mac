2025-02-19 14:40

Link: https://neetcode.io/problems/partition-labels

Problem: 
You are given a string `s` consisting of lowercase english letters. 

We want to split the string into as many substrings as possible, while ensuring that each letter appears in at most one substring.

Return a list of integers representing the size of these substrings in the order they appear in the string.

Motivation:
create a hash-map that keep track of the last index of each element. Then as we iterate through the string, we maintain a end pointer that indicates the furthest position for all element in the current segment. if we reach the end pointer, the segment can be isolated and elements in it wont appear furthermore.
greedy nature: 
condition: smallest segment / last occurrence included

locally optimal: update last index if needed ,cut segment once reach! (no backtrack!)

Solution:
Greedy: 
```python
class Solution:
    def partitionLabels(self, s: str) -> List[int]:
        lastIndex = {}
        for i, c in enumerate(s):
            lastIndex[c] = i
        
        res = []
        size = end = 0
        for i, c in enumerate(s):
            size += 1
            end = max(end, lastIndex[c])

            if i == end:
                res.append(size)
                size = 0
        return res
```

Tags: #greedy #dict 

RL: 

Considerations:
