2025-04-15 17:10

Link:

Problem: 
You are given a **sorted unique** integer array `nums`.

A **range** `[a,b]` is the set of all integers from `a` to `b` (inclusive).

Return _the **smallest sorted** list of ranges that **cover all the numbers in the array exactly**_. That is, each element of `nums` is covered by exactly one of the ranges, and there is no integer `x` such that `x` is in one of the ranges but not in `nums`.

Each range `[a,b]` in the list should be output as:

- `"a->b"` if `a != b`
- `"a"` if `a == b`

Intuition:
mark each ele as starting point, we determine if we should start a new range from each point!, a new range should be start if next ele is not consecutive of current element ,or if the starting point is the last element! we mark the start of current range with a variable, then we find the end of this range with the for loop! . append the range to the res before a new range has to be started!!

Solution:
```python
class Solution(object):

def summaryRanges(self, nums):
	res= [ ]
	start= 0
	for i in range( len ( nums )):
		if i == len( nums ) -1 or nums[ i ] + 1 != nums[ i + 1]:
		if start == i:
			res .append( str( nums[ start ]))
		else:
			res.append( str( nums [start]) + "->" + str( nums[ i ]))
		if i < len( nums )-1:
			start = i+ 1
	return res
```

Tags: 

RL: 

Considerations:
