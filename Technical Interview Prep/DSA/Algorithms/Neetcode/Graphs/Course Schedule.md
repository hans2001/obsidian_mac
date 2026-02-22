2025-02-07 13:46

Link:https://neetcode.io/problems/course-schedule

Problem: 
You are given an array `prerequisites` where `prerequisites[i] = [a, b]` indicates that you **must** take course `b` first if you want to take course `a`.

The pair `[0, 1]`, indicates that must take course `1` before taking course `0`.

There are a total of `numCourses` courses you are required to take, labeled from `0` to `numCourses - 1`. 

Return `true` if it is possible to finish all courses, otherwise return `false`.

Motivation:
initialize list of prerequisites for each courses. For each course, recursively run dfs to resolve every prerequisite courses for each course, if all prerequisites can be successfully resolved, the current course can be cleared, otherwise, a cycle is detect in the course schedule. The cycle is detected when under an active recursion chain, the prerequisites course we need to resolve , is actually course that we visited before(upward in the recursion chain). Since the course both depend on each other, the course cannot be cleared! 

Remove course from vis is the backtrack element, where we ensure the set:vis record courses that is being processed at the moment(under active recursion chain), instead of affecting other recursion branch!

Solution:
DFS + Cycle Detection
```python
class Solution:
    def canFinish(self, numCourses: int, prerequisites: List[List[int]]) -> bool:
        # Map each course to its prerequisites
        preMap = {i: [] for i in range(numCourses)}
        for crs, pre in prerequisites:
            preMap[crs].append(pre)

        # Store all courses along the current DFS path
        visiting = set()

        def dfs(crs):
            if crs in visiting:
                # Cycle detected
                return False
            if preMap[crs] == []:
                return True

            visiting.add(crs)
            for pre in preMap[crs]:
                if not dfs(pre):
                    return False
            visiting.remove(crs)
            preMap[crs] = []
            return True

        for c in range(numCourses):
            if not dfs(c):
                return False
        return True
```

Tags: #dfs #cycle_detection #backtracking 

RL: 