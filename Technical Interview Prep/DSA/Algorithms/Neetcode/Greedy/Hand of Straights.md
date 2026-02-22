2025-02-19 14:40

Link: https://neetcode.io/problems/hand-of-straights

Problem: 
You are given an integer array `hand` where `hand[i]` is the value written on the `ith` card and an integer `groupSize`.

You want to rearrange the cards into groups so that each group is of size `groupSize`, and card values are consecutively increasing by `1`.

Return `true` if it's possible to rearrange the cards in this way, otherwise, return `false`.

Motivation:
the group would start from the smallest element that is available! use hash-map to record the number of elements that is available to form the group, if a supposed number that should be used to form the group does not exist in the dictionary, we can return False!

Solution:
Hash map
```python
class Solution:
    def isNStraightHand(self, hand: List[int], groupSize: int) -> bool:
        if len(hand) % groupSize != 0:
            return False
        count = Counter(hand)
        for num in hand:
            start = num
            while count[start - 1]:
                start -= 1
            while start <= num:
                while count[start]:
                    for i in range(start, start + groupSize):
                        if not count[i]:
                            return False
                        count[i] -= 1
                start += 1
        return True
```

Tags: #dict

RL: 

Considerations:
