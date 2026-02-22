2025-11-27 23:10

Link:

Problem: 
![[Screenshot 2025-11-27 at 11.10.48 PM.png]]
![[Screenshot 2025-11-27 at 11.10.54 PM.png]]Constraints:
round ans to 54 decimal place? 
return as string? 
the confusing  part is each segment come fomr a single circl , what does that even mean? (that u should derive the segment from single circl , but u can derive multiple of that! )

goal: 
we need to make n slices, and we can make as small as possible ,and thye can comr form a single circle adn taht is it! 
however, we are ask to find the max area for the single segment that we can reach ,and we know there are finite number of segment a circle cna make, if after all circle was cut ,and we did not make n slices , that mean we lost, however, if we do make n or more than  slice , that mean it works! 
o that segment until u find a sweetspot! 
for input we have n, radiis and the number of segments needed ~make it as k!
we pass by referecne 
we dont have to store that in our member instance ?
if we dotn modify our datambere 

```cpp
const long PI = 3.14159265359;
class Solution{ 
	int segments;
	vector<int> radiis{};
public: 
	Solution(const int segments, vector<int>& radiis):segments(segments),radiis(radiis) {}
# we cna just use lower bound here!
	const long compute ( ) {
		
	}
	
}
int main {
	int n{};int segments{};
	vector<int> radiis{};
	for (int num& : radiis) { 
		cin >> num;
	}
	Solution sol{};
	return 0;
}
```


Intuition:
first, compute areas for are for each circle
upper bound should be single segment from the largest circle!
we should loop to the upper bound of segment we can cut , and mid is the segment area, which is actually upper bound + lower bound / 2 (this is tricyk ,i dont know we can compute segment are like this!)
then we compare the amount of segment we can build based on this segment area, and see if we reach the totla amount of segments we need. if yes, we can increase the segment are that we use, otherwise we shoould make it smaller to ensure each area fits!
dont know how to format the thing to 4 decimal ! 

Solution:
```python
PI = 3.14159265359

def max_segment_area(radii, segments):
    areas = [PI * r * r for r in radii]

    low, high = 0.0, max(areas)

    for _ in range(70):  # enough to get 1e-4 precision
        mid = (low + high) / 2.0
        if mid == 0:
            break

        total = 0
        for a in areas:
            total += int(a // mid)   # how many slices of area mid from this circle

        if total >= segments:
            low = mid    # mid works, try bigger
        else:
            high = mid   # mid too big, try smaller

    # return formatted string
    return f"{low:.4f}"

# Test with given examples
print(max_segment_area([1, 1, 1, 2, 2, 3], 6))  # -> "7.0686"
print(max_segment_area([4, 3, 3], 3))           # -> "28.2743"
```

Tags: #math #binary_search 

RL: 

Considerations:
the result is the fking target (the thing that we binary search on), the target is the max segment area that we can get ,so we can just search on that, wtf are u thinking ? 