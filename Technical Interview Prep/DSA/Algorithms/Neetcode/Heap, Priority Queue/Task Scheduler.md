2025-02-02 18:08

Link:https://neetcode.io/problems/task-scheduling

Problem: 
You are given an array of CPU tasks `tasks`, where `tasks[i]` is an uppercase english character from `A`to `Z`. You are also given an integer `n`. 

Each CPU cycle allows the completion of a single task, and tasks may be completed in any order.

The only constraint is that **identical** tasks must be separated by at least `n` CPU cycles, to cooldown the CPU.

Return the _minimum number_ of CPU cycles required to complete all tasks.

Motivation:
use heap allow us to handle the most frequent task first, leave room for idle time. use queue allow us to hold off task in a FIFO manner, make sure we process tasks once they are available. Anyways, they are inherently sorted due to property of time!

Solution:
![[Screenshot 2025-02-02 at 6.08.33 PM.png]]
O(n) / O(1)

Tags: #heap #queue #counter

RL: 