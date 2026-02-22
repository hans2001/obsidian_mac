2025-02-19 14:40

Link: https://leetcode.com/problems/valid-parenthesis-string/description/

Problem: 
You are given a string `s` which contains only three types of characters: `'('`, `')'` and `'*'`. 

Return `true` if `s` is **valid**, otherwise return `false`.

A string is valid if it follows all of the following rules:
- Every left parenthesis `'('` must have a corresponding right parenthesis `')'`.
- Every right parenthesis `')'` must have a corresponding left parenthesis `'('`.
- Left parenthesis `'('` must go before the corresponding right parenthesis `')'`.
- A `'*'` could be treated as a right parenthesis `')'` character or a left parenthesis `'('` character, or as an empty string `""`.

Motivation:
Stack: Even though we know * can be treated as anything, we need to keep track of the ordering where star should appear after (, if it is treated as ). so we use 2 stack to keep track of their indexes, and resolve at the end if have any!

Greedy: 
both leftmin and leftmax mean the number of left bracket, but by treating the wild card as different bracket, we maintain a range of possibilities that inform us of result from both case(worst and best case), and by checking the validity of both case, we know if we have a valid parenthesis string! 

either too much left or too much right! 
when mx <0, even at best case, the number of lefties is not enough to resolve the righties!

at the end, if every * treated as righties, still cannot resolve number of lefties, we know we dont have a valid string!

Recursion:
for ch == left, right bracket, we have one option only, for ch == * ,we explore the 3 path, and obtain the result at the end of each recursion chain!

Solution:
Stack:
```python
class Solution:
    def checkValidString(self, s: str) -> bool:
        left = []
        star = []
        for i, ch in enumerate(s):
            if ch == '(':
                left.append(i)
            elif ch == '*':
                star.append(i)
            else:
                if not left and not star:
                    return False
                if left:
                    left.pop()
                else:
                    star.pop()
        while left and star:
            if left.pop() > star.pop():
                return False
        return not left
```
O(n) / O(n)

Greedy:
```python
class Solution:
    def checkValidString(self, s: str) -> bool:
        leftMin, leftMax = 0, 0
        for c in s:
            if c == "(":
                leftMin, leftMax = leftMin + 1, leftMax + 1
            elif c == ")":
                leftMin, leftMax = leftMin - 1, leftMax - 1
            else:
                leftMin, leftMax = leftMin - 1, leftMax + 1
            if leftMax < 0:
                return False
            if leftMin < 0:
                leftMin = 0
        return leftMin == 0
```
O(n) / O(1)

Recursion:
```python
class Solution:
    def checkValidString(self, s: str) -> bool:
        def dfs(i, open):
            if open < 0:
                return False
            if i == len(s):
                return open == 0
            
            if s[i] == '(':
                return dfs(i + 1, open + 1)
            elif s[i] == ')':
                return dfs(i + 1, open - 1)
            else:
                return (dfs(i + 1, open) or
                        dfs(i + 1, open + 1) or
                        dfs(i + 1, open - 1))
        return dfs(0, 0)
```
treat * as (, empty string or ). if one function returned True, we are good!
O(3^n) / O(n)
memoized version: O(n^2) / O(n^2)

Tags: #stack #greedy #dp

RL: 

Considerations:
