2025-01-22 14:06

[Link](https://www.hackerrank.com/challenges/separate-the-numbers/problem?isFullScreen=true)

Problem: ![[Pasted image 20250122160316.png]]

Motivation:
Slice the array in different sub length (trying out different possibilities for the first number). for each first number, try building a ascending sequence with incrementation of 1. Once the sequence is built, we compare it with the original string. 
Since the built sequence is concatenated from integer, no leading zero is allowed, we implicitly check for this condition. If the sequence equals to the original string, we return YES and the first number, otherwise, return NO (not beautiful)

Solution:
![[Screenshot 2025-01-22 at 4.04.06 PM.png]]

Tags: #string

RL: 