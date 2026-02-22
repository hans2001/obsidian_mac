2025-01-24 14:23

Link: https://neetcode.io/problems/string-encode-and-decode

Problem: 
![[Screenshot 2025-01-24 at 2.24.21 PM.png]]
Motivation:
during decoding ,we need the information of substring length and start of the substring(since the string contain number, we cannot use number to indicate the start of the substring, must be some symbol that does not exist in the symbol)

Solution:
![[Screenshot 2025-01-24 at 2.24.28 PM.png]]
Tags: #array

RL: 

Time complexity: O(n)