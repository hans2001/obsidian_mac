2025-02-07 13:46

Link:https://neetcode.io/problems/course-schedule-ii

Problem: 
You are given an array `prerequisites` where `prerequisites[i] = [a, b]` indicates that you **must** take course `b` first if you want to take course `a`.

- For example, the pair `[0, 1]`, indicates that to take course `0` you have to first take course `1`.

There are a total of `numCourses` courses you are required to take, labeled from `0` to `numCourses - 1`. 

Return a valid ordering of courses you can take to finish all courses. If there are many valid answers, return **any** of them. If it's not possible to finish all courses, return an **empty array**.

Motivation:
post order traversal! 
use set:cycle to track visiting node in the active recursion chain, and use the set: visit to track node that is cleared( node that depend on no cycle ). 
what is node that is cleared? 
When we backtrack, we append the course node to result list and visit set at the deepest dependency first, so it can inform other recursive chain that the node does not result in cycle! 

the backtrack append to list ensure the deepest dependencies is resolved first, building the ordering of the courses naturally! 

Solution:
DFS + Cycle detection:
```python
class Solution:
    def findOrder(self, numCourses: int, prerequisites: List[List[int]]) -> List[int]:
        prereq = {c: [] for c in range(numCourses)}
        for crs, pre in prerequisites:
            prereq[crs].append(pre)

        output = []
        visit, cycle = set(), set()

        def dfs(crs):
            if crs in cycle:
                return False
            if crs in visit:
                return True

            cycle.add(crs)
            for pre in prereq[crs]:
                if dfs(pre) == False:
                    return False
            cycle.remove(crs)
            visit.add(crs)
            output.append(crs)
            return True

        for c in range(numCourses):
            if dfs(c) == False:
                return []
        return output
```

TP sort:
```python
class Solution:
    def findOrder(self, numCourses: int, prerequisites: List[List[int]]) -> List[int]:
        adj = [[] for i in range(numCourses)]
        indegree = [0] * numCourses
        for nxt, pre in prerequisites:
        %% build the hashmap and indeg list, for later computation purpose  %%
            indegree[nxt] += 1
            adj[pre].append(nxt)
        
        output = []
        def dfs(node):
	        %% push to the tp sorted list!  %%
            output.append(node)
            %% mark as visited %%
            indegree[node] -= 1
            %% visit neighbors!  %%
            for nei in adj[node]:
                indegree[nei] -= 1
                if indegree[nei] == 0:
                    dfs(nei)
        
        %% find the starting node!  %%
        for i in range(numCourses):
            if indegree[i] == 0:
                dfs(i)
        
        return output if len(output) == numCourses else []
```

Tags: #dfs #topological_sort #post_order_traversal #google 

RL: [[Course Schedule]]