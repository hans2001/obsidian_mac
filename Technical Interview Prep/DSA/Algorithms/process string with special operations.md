2025-07-17 16:27

Link:

Problem: 
You are given a string s consisting of lowercase English letters and the special characters: '*', '#', and '%'.

You are also given an integer k.
Build a new string result by processing s according to the following rules from left to right:

If the letter is a lowercase English letter append it to result.
A '*' removes the last character from result, if it exists.
A '#' duplicates the current result and appends it to itself.
A '%' reverses the current result.
Return the kth character of the final string result. If k is out of the bounds of result, return '.'.

Example 1:
Input: s = "a#b%*", k = 1
Output: "a"
Explanation:
i	s[i]	Operation	Current result
0	'a'	Append 'a'	"a"
1	'#'	Duplicate result	"aa"
2	'b'	Append 'b'	"aab"
3	'%'	Reverse result	"baa"
4	'*'	Remove the last character	"ba"
The final result is "ba". The character at index k = 1 is 'a'.

Example 2:
Input: s = "cd%#*#", k = 3

Explanation:
i	s[i]	Operation	Current result
0	'c'	Append 'c'	"c"
1	'd'	Append 'd'	"cd"
2	'%'	Reverse result	"dc"
3	'#'	Duplicate result	"dcdc"
4	'*'	Remove the last character	"dcd"
5	'#'	Duplicate result	"dcddcd"
The final result is "dcddcd". The character at index k = 3 is 'd'.

Example 3:
Input: s = "z*#", k = 0
Output: "."
Explanation:
i	s[i]	Operation	Current result
0	'z'	Append 'z'	"z"
1	'*'	Remove the last character	""
2	'#'	Duplicate the string	""
The final result is "". Since index k = 0 is out of bounds, the output is '.'.©leetcode

Intuition:
the kth character to be returned is on the final string, given that we cannot build the actual string( exceed space complexity) , we could reverse the string building process from final string to original string, and all operation will be done on the number k ,so that we know which character in the original string does k refer to !

Solution:
```python
class Solution:
    def processStr(self, s: str, k: int) -> str:
        n = len(s)
        prev_len = [0]*n
        L = 0
        for i,ch in enumerate(s):
            prev_len[i] = L
            if   'a' <= ch <= 'z':    L += 1
            elif ch == '*':           L = max(0, L-1)
            elif ch == '#':           L = L*2
            elif ch == '%':           L = L
        # print( prev_len , new_len , L )
        if k < 0 or k >= L:
            return "."
        for i in range( n-1,-1,-1 ):
            pre = prev_len [i]
            ch =s[ i ]
            if 'a' <= ch <= 'z': 
                if k == pre: 
                    return ch 
            elif ch == '*':
                pass
            elif ch =='#': 
                if k >= pre: 
                    k -= pre
            elif ch== '%': 
                k = pre - 1 -k
        return '.'
            ©leetcode
```
O(n)

Tags: #weekly_contest_458 #q3

RL: 

Considerations:
