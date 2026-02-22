2025-02-05 11:21

Link:https://neetcode.io/problems/combinations-of-a-phone-number

Problem: 
You are given a string `digits` made up of digits from `2` through `9` inclusive.

Each digit (not including 1) is mapped to a set of characters as shown below:

A digit could represent any one of the characters it maps to.

Return all possible letter combinations that `digits` could represent. You may return the answer in **any order**.

Motivation:
instead of building local array ,we build a string in this case. For the iterative loop, we should try all characters that are mapped to the current digit, so we loop until len(items) ,where items is the list of characters. For recursive function index, we increment i to indicate we are trying out the next digit in the digits string!

Solution:
Editorial :
```python
class Solution:
    def letterCombinations(self, digits: str) -> List[str]:
        if not digits:
            return []

        res = [""]
        digitToChar = {
            "2": "abc",
            "3": "def",
            "4": "ghi",
            "5": "jkl",
            "6": "mno",
            "7": "qprs",
            "8": "tuv",
            "9": "wxyz",
        }

        for digit in digits:
            tmp = []
            for curStr in res:
                for c in digitToChar[digit]:
                    tmp.append(curStr + c)
            res = tmp
        return res
```

Mine:
![[Screenshot 2025-02-05 at 11.22.06 AM.png]]

Tags: #backtracking 

RL: 