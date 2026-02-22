2025-05-13 18:07

Link: https://neetcode.io/problems/single-threaded-cpu

Problem: 
You are given `n` tasks labeled from `0` to `n - 1` represented by a 2D integer array `tasks`, where `tasks[i] = [enqueueTimei, processingTimei]` means that the `ith` task will be available to process at `enqueueTime[i]` and will take `processingTime[i]` to finish processing.

You have a single-threaded CPU that can process at most one task at a time and will act in the following way:

- If the CPU is idle and there are no available tasks to process, the CPU remains idle.
- If the CPU is idle and there are available tasks, the CPU will choose the one with the **shortest processing time**. If multiple tasks have the same shortest processing time, it will choose the task with the **smallest index**.
- Once a task is started to process, the CPU will process the entire task without stopping.
- The CPU can finish a task then start a new one instantly.

Intuition:
use the heap as a sliding window, storing the available tasks and sort them by the processing time. idea is that we perform 3 steps in each iteration ,where we first update the current time, then we populate all tasks that starts before the current time, finally, we choose the available  tasks that has the shortest processing time and build the order list! 

Solution:
```python
from heapq import heappush, heappop
from typing import List

class Solution:
    def getOrder(self, tasks: List[List[int]]) -> List[int]:
        # Prepare tasks with their indices
        indexed_tasks = [(arrival, processing, idx) for idx, (arrival, processing) in enumerate(tasks)]
        # Sort tasks by arrival time
        indexed_tasks.sort()
        
        result_order = []
        min_heap = []  # For (processing_time, original_index)
        current_time = 0
        i = 0
        n = len(tasks)
        
        while i < n or min_heap:
            if not min_heap and i < n:
                current_time = max(current_time, indexed_tasks[i][0])
                
            # Add all tasks that have arrived by current_time to the heap
            while i < n and indexed_tasks[i][0] <= current_time:
                arrival, processing, idx = indexed_tasks[i]
                heappush(min_heap, (processing, idx))
                i += 1
                
            # Process the task with the shortest processing time
            if min_heap:
                processing_time, original_idx = heappop(min_heap)
                result_order.append(original_idx)
                current_time += processing_time
                
        return result_order
```

Complexity:
Time: 
heap operations takes O(n) time ,we have up to n nodes in the heap 
and we take n iteration, where each node is processed exactly once! 

Space: 
O(n) number of nodes in the heap

Tags: #heap #google 

RL: 

Considerations:
