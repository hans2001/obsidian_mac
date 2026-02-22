2025-02-28 13:51

Link: https://neetcode.io/problems/decode-string

Problem: 
Given an encoded string, return its decoded string.

The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets is being repeated exactly `k` times. Note that `k` is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, `k`. For example, there will not be input like `3a` or `2[4]`.

The test cases are generated so that the length of the output will never exceed `105`.

Intuition:
cs: current string being build
cn: current number being build 
sk: we can use a single stack to store the previous substring and previous number ( but they serve differently )
when encounter char being a digit, we add it to the building number( range: 1 - 300)
when encounter a open bracket, it mean we will be building for a new and nested substring, so we temporarily push the pair ( cs ,cn ) to the stack
when encounter closing bracket, it mean the nested current substring is finished, we should resolve it by multiplying with the number in front of the opening bracket, and add it to the previous substring. 
then we make this our new current string, as we resolve from the innermost string! it would be multiplying previous number if any in the stack ,with addition to the previous string! .. 

"2[a3[bc]]d"
Process character by character:
- '2': current_num = 2
- '[': Push ("", 2) onto stack. Reset current_string = "" and current_num = 0
- 'a': current_string = "a"
- '3': current_num = 3
- '[': Push ("a", 3) onto stack. Reset current_string = "" and current_num = 0
- 'b': current_string = "b"
- 'c': current_string = "bc"
- ']': Pop ("a", 3) from stack.
    - Repeat current_string 3 times: "bc" * 3 = "bcbcbc"
    - Append to previous string: "a" + "bcbcbc" = "abcbcbc"
    - This becomes our new current_string = "abcbcbc"
- ']': Pop ("", 2) from stack.
    - Repeat current_string 2 times: "abcbcbc" * 2 = "abcbcbcabcbcbc"
    - Append to previous string: "" + "abcbcbcabcbcbc" = "abcbcbcabcbcbc"
    - This becomes our new current_string = "abcbcbcabcbcbc"
- 'd': current_string = "abcbcbcabcbcbcd"

Solution:
```python
def decode_string(s):
	cn = 0 
	cs = ""
	nums = []
	string = []
	for ch in s:
		if ch .isdigit ( ) :
			cn = cn * 10 + int (ch)
		elif ch == '[' :
			string.append ( cs )
			nums.append ( cn )
			cn =0
			cs =""
		elif ch==']':
			ct = nums.pop ()
			prev = string.pop ( )
			cs = prev + (cs * ct )
		else:
			cs += ch
	return cs
```

Tags: #stack 

RL: 

Considerations:
