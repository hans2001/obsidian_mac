2025-07-22 18:07

Link: https://neetcode.io/problems/ipo?list=neetcode250

Problem: 
A company has limited resources, it can only finish at most `k` distinct projects before the IPO. Help the company to design the best way to **maximize** its total capital after finishing at most `k` distinct projects.

You are given `n` projects where the `ith` project has a pure profit `profits[i]` and a minimum capital of `capital[i]` is needed to start it. Initially, you have `w` capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

Pick a list of at most `k` distinct projects from given projects to **maximize** your final capital, and return the final maximized capital.

The answer is guaranteed to fit in a 32-bit signed integer.

**Example 1:**
```java
Input: k = 3, w = 0, profits = [1,4,2,3], capital = [0,3,1,1]

Output: 8
```
Explanation : The order of indices to pick are [0,3,1] and final capital is (1 + 3 + 4) = 8.

**Example 2:**
```java
Input: k = 4, w = 2, profit = [2,3,1,5,3], capital = [4,4,2,3,3]

Output: 14
```
Explanation: The order of indices to pick are [2,3,4,1] and final capital is (2 + (1 + 5 + 3 + 3)) = 14.

**Constraints:**

- `n == profits.length == capital.length`
- `1 <= n, k <= 100,000`
- `0 <= w <= 1,000,000,000`
- `0 <= profits[i] <= 10,000`
- `0 <= capital[i] <= 1,000,000,000`

Intuition:
sorting + maxHeap: 
we have 2 metric we need to take care of ,the first one is we want the capital to be sorted by ascending order, so that when we populate proj into the heap ,we dont have to check the whole arr each time, adn the total time spend at checking will be O(1 * n). but we still have use the proj that provide the max financial gain, here is where the heap comes into play, where after current capital refresh ,we reevaluate the valid projs that we can do to pump up the financial gain!  

Solution:
![[Screenshot 2025-07-24 at 10.48.38 AM.png]]

Tags: #heap

RL: 

Considerations:
