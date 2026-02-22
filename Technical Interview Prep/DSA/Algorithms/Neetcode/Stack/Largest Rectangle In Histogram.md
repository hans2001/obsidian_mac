2025-01-26 19:01

Link:https://neetcode.io/problems/largest-rectangle-in-histogram

Problem: 
You are given an array of integers `heights` where `heights[i]` represents the height of a bar. The width of each bar is `1`.

Return the area of the largest rectangle that can be formed among the bars.

Note: This chart is known as a [histogram](https://en.wikipedia.org/wiki/Histogram).

Motivation:
height of the rectangle form by multiple bars is defined by the shortest. By going through the test cases, we can observe that we cannot extend the right boundary once a shorter bar is met. So for each shorter bar (compare to bars in stack), we starting popping the longer bars in stack in descending order, and compute their area. Note: we should use a variable(start) to define the left boundary for current height. At the end, we compute areas that can be formed by the remaining bars in the stack! 

Solution:
class Solution:
    def largestRectangleArea(self, heights: List[int]) -> int:
        maxArea = 0
        stack = []  # pair: (index, height)

        for i, h in enumerate(heights):
            start = i
            while stack and stack[-1][1] > h:
                index, height = stack.pop()
                maxArea = max(maxArea, height * (i - index))
                start = index
            stack.append((start, h))

        for i, h in stack:
            maxArea = max(maxArea, h * (len(heights) - i))
        return maxArea

Tags: #stack 

RL: 

Time complexity: O(N)

Space complexity: O(N)