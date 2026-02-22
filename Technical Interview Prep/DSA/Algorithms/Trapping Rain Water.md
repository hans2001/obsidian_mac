2025-04-23 11:42

Link: https://leetcode.com/problems/trapping-rain-water/description/?envType=company&envId=bloomberg&favoriteSlug=bloomberg-thirty-days

Problem: 
Given `n` non-negative integers representing an elevation map where the width of each bar is `1`, compute how much water it can trap after raining.

Intuition:
we maintain a monotonic decreasing stack, if current height is larger than previous height in stack, we compute if there is water trapper. so we popped previous height from stack, which is the index of the bottom bar, and now we have the left bar at top of the stack! we compute the water trapped by computing width( spaces within left_bar to right_bar ) * height_diff (lowest bar to bottom_bar). and we increment local water to global water volume!

Solution:
```python
class Solution:
    def trap(self, height):
        ans = 0
        current = 0
        st = []
        while current < len(height):
            while len(st) != 0 and height[current] > height[st[-1]]:
                top = st[-1]
                st.pop()
                if len(st) == 0:
                    break
                distance = current - st[-1] - 1
                bounded_height = (
                    min(height[current], height[st[-1]]) - height[top]
                )
                ans += distance * bounded_height
            st.append(current)
            current += 1
        return ans
```

Complexity:
Time: O(n)
Single iteration of O(n), bar can be touched at most twice! 
insertion and deletion of stack element takes O(1) time! 

Space: O(n)
stack can take up to O(n) space at worst case! 

Tags: #stack

RL: 

Considerations:
