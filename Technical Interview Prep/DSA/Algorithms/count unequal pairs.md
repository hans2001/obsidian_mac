2025-08-06 11:39

Link:

Problem: 
Given an array of integers, find the number of pairs (i, j) such that arr[i] != arr[j] and i < j.

Constraints:

Intuition:
seen we track total number of predecessor ,and unequal tacks number of predecessors that is not the same as the current element, which forms a pair with this element

Solution:
```python
unequal = 0
seen = 0
freq = {}

for x in arr:
    # of the 'seen' items, freq[x] were equal to x,
    # so the rest (seen - freq[x]) are unequal to x
    unequal += seen - freq.get(x, 0)

    # now include x in seen/freq for future iterations
    freq[x] = freq.get(x, 0) + 1
    seen += 1

# 'unequal' is our final answer
```

Tags: 

RL: 

Considerations:
