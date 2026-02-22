2025-08-05 14:15

Link:

Problem: 
![[Pasted image 20250805141625.png]]

Constraints:

Intuition:
since we continously work with the prefix, we dont need sliding window , nothing is ejected from the window, along the step, we can check if the subsequence we have has the potential to be built as a valid one? or is it already a valid one
for a valid one, we should have less than k of 10 pair first, adn the remaining number of ones should be less than the number of 10 pair that we needed, since given that case, we can either fill a zero to get a valid pair first, then build 10 as we need, or filling zero already give us what we wanted! if number of one is more than number of 10 pair than we need, filling a zero will result 10 pair that is more than k, so the subsequence will never be valid, since we cannot delete shit form the sequence!

Solution:
```python
def calculateTotalPrefix(sequence, k):
    cnt_1 = 0
    cnt_10 = 0
    res = 0

    for i in range(len(sequence)):
        ch = sequence[i]
        if ch == '1':
            cnt_1 += 1
        elif ch == '0':
            cnt_10 += cnt_1
        # Check if current prefix is valid
        if cnt_10 > k: 
	        break
        if cnt_10 == k or k - cnt_10 <= cnt_1:
            res += 1

    return res
```
Tags: #subsequence #count 

RL: 

Considerations:
