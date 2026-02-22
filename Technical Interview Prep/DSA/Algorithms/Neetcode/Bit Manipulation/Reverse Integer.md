2025-02-25 12:08

Link: https://neetcode.io/problems/reverse-integer

Problem: 
You are given a signed 32-bit integer `x`.

Return `x` after reversing each of its digits. After reversing, if `x` goes outside the signed 32-bit integer range `[-2^31, 2^31 - 1]`, then return `0` instead.

Solve the problem without using integers that are outside the signed 32-bit integer range.

Intuition:
cannot apply mask, since the result can be bigger or smaller than the mask. ensure the computation is correct by using modules such as math.fmod( ) and turning the x/10 result to int, since -1 // 10 = -1 

**python is not good with bit manipulation related problems!** 
math.fmod(x,y): compute floating point remainder of dividing x by y

% operator in python (a%b):
a=(a//b)×b+(a%b)
`print(-5.3 % 2.0)  # Output: 0.7`
limitations: the result of a%b has the same sign as b
so we use fmod modue to keep the sign!

int (x/10 ) instead of x//10:
−1//10 gives −1−1 because −1/10−1/10 is −0.1−0.1 and the floor of −0.1−0.1 is −1−1.
int ( x/10) should be 0

Solution:
Mine:
```python
class Solution:
    def reverse(self, x: int) -> int:   
        r = 0
        sign = 1 if x > 0 else -1 
        x = abs( x)
        while x != 0 : 
            d= x%10 
            r = r * 10 + d 
            x //= 10
        return sign * r if -2**31 <= sign * r <= 2**31 - 1 else 0
        
```
keep the sign before computation, check the range at the end

editorial:
```python
class Solution:
    def reverse(self, x: int) -> int:   
        mx  =  2 ** 31 - 1
        mn  = -2 ** 31 
        rs =0 
        while x: 
            d = int ( math.fmod( x, 10  )  )
            x = int ( x /10 )
            # keep the sign

            if rs > mx//10 or (rs== mx//10 and d >mx %10) : 
                return 0
            if rs < mn//10 or (rs== mn//10 and d < mn %10) : 
                return 0
            rs = rs * 10 + d
        return rs 
```
continuously check if the rs is bigger or smaller than the range, actually, it is check at the second last loop!

Tags: #google #math

RL: 

Considerations:
