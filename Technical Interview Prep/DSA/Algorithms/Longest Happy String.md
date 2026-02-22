2025-05-13 16:00

Link: https://neetcode.io/problems/longest-happy-string

Problem: 
A string `s` is called **happy** if it satisfies the following conditions:

- `s` only contains the letters `'a'`, `'b'`, and `'c'`.
- `s` does not contain any of `"aaa"`, `"bbb"`, or `"ccc"` as a **substring**.
- `s` contains at most `a` occurrences of the letter `'a'`.
- `s` contains at most `b` occurrences of the letter `'b'`.
- `s` contains at most `c` occurrences of the letter `'c'`.

You are given three integers `a`, `b`, and `c`, return the **longest possible happy** string. If there are multiple longest happy strings, return any of them. If there is no such string, return the empty string `""`.

A **substring** is a contiguous sequence of characters within a string.

Intuition:
instead of making sure char are alternating ,we just need to know that the last 2 chars in res is not the same as the current char, so that we can use the current char, otherwise we should use another most frequent char among the remaining chars! 

Solution:
max_heap
```python
class Solution:
    def longestDiverseString(self, a: int, b: int, c: int) -> str:
        res = ""
        maxHeap = []
        for count, char in [(-a, "a"), (-b, "b"), (-c, "c")]:
            if count != 0:
                heapq.heappush(maxHeap, (count, char))
        
        while maxHeap:
            count, char = heapq.heappop(maxHeap)
            if len(res) > 1 and res[-1] == res[-2] == char:
                if not maxHeap:
                    break
                count2, char2 = heapq.heappop(maxHeap)
                res += char2
                count2 += 1
                if count2:
                    heapq.heappush(maxHeap, (count2, char2))
                heapq.heappush(maxHeap, (count, char))
            else:
                res += char
                count += 1
                if count:
                    heapq.heappush(maxHeap, (count, char))

        return res
```

Complexity:
Time: 
O(n * log k) for total of k node in the heap -> O(n)

Space: 
O(1)

Tags: #heap #microsoft

RL: [[Reorganize String]]

Considerations:
