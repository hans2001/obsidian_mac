2025-05-13 15:47

Link: https://neetcode.io/problems/reorganize-string

Problem: 
You are given a string `s`, rearrange the characters of `s` so that any two **adjacent** characters are not the same.

You can return **any** possible rearrangement of `s` or return `""` if not posssible.

**Example 1:**
```java
Input: s = "axyy"
Output: "xyay"
```

**Example 2:**
```java
Input: s = "abbccdd"
Output: "abcdbcd"
```

**Example 3:**
```java
Input: s = "ccccd"
Output: ""
```

Intuition:
the most frequent char is the most constraint resource in the problem, so we should place them whenever we can, and create space with other chars! 
To do this, we should temporarily hold the placed char, and insert the most frequent char among remaining char. since if we dont hold the char, majority char might get placed multiple times and ruin the problem directly! 

Solution:
hash map + max heap
```python
class Solution:
    def reorganizeString(self, s: str) -> str:
        count = Counter(s)
        maxHeap = [[-cnt, char] for char, cnt in count.items()]
        heapq.heapify(maxHeap)
        
        prev = None
        res = ""
        while maxHeap or prev:
            if prev and not maxHeap:
                return ""
            
            cnt, char = heapq.heappop(maxHeap)
            res += char
            cnt += 1

            if prev:
                heapq.heappush(maxHeap, prev)
                prev = None
            
            if cnt != 0:
                prev = [cnt, char]
        
        return res
```

Complexity:
Time:
O(N) for building the hash map
O(N log k), we called n times heap operation ,where the heap can only store up to j unique characters (k <=26). so we can say that it is constant, and the heap operation takes O(n)

Space: 
O(k) extra space where k < 26, so constant space needed! 
O(n) for the output string! 

Tags: #heap #hash_map #greedy #tesla

RL: [[Longest Happy String]]

Considerations:
## 2. Critical Resource Management

The most frequent character is the most constrained resource in the reorganization:
- It needs the most spacing between occurrences
- It has the highest risk of ending up adjacent to itself
- It requires the most "other characters" to serve as separators

**3. Checking possibilities** 
If we have a character that appears f times in a string of length n:

- We need at least f-1 other characters to separate them
- This means we need n-f ≥ f-1
- Solving: n-f ≥ f-1 → n ≥ 2f-1 → f ≤ (n+1)/2

This is exactly our impossibility check! If any character appears more than (n+1)/2 times, there aren't enough other characters to separate them.