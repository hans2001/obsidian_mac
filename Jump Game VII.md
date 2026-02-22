2025-07-22 11:08

Link: https://neetcode.io/problems/jump-game-vii?list=neetcode250

Problem: 
You are given a **0-indexed** binary string `s` and two integers `minJump` and `maxJump`. In the beginning, you are standing at index `0`, which is equal to `'0'`. You can move from index `i` to index `j` if the following conditions are fulfilled:

- `i + minJump <= j <= min(i + maxJump, s.length - 1)`, and
- `s[j] == '0'`.

Return `true` if you can reach index `s.length - 1` in `s`, or `false` otherwise.

**Example 1:**
```java
Input: s = "00110010", minJump = 2, maxJump = 4

Output: true
```
Explanation: The order of jumps is: indices 0 -> 4 -> 7.

**Example 2:**
```java
Input: s = "0010", minJump = 1, maxJump = 1

Output: false
```

Constraints:
- `2 <= s.length <= 100,000`
- `s[i]` is either `'0'` or `'1'`.
- `s[0] == '0'`
- `1 <= minJump <= maxJump < s.length`

Intuition:
**BFS:**
Since path with min step is not required, a simple bfs can do the job , treat node in window as neighbor for the parent node (current position), and check if in the future we can reach the end position

**Sliding window:** 
Check if form previous state, there are valid position that can reach the current index
the state is useful when we are at the last position, directly determining the answer!

The window count  incrementally process each position, so number of valid blocks is tracked properly, and we use the leave position to remove valid place, since it is not in the window anymore(not belong to last step)

Solution:
bfs
![[Screenshot 2025-07-22 at 11.09.01 AM.png]]

Sliding window
![[Screenshot 2025-07-22 at 11.14.43 AM.png]]

Tags: #bfs   #sliding_window 

RL: 

Considerations:
