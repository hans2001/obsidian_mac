2025-12-23 14:07

Link: https://neetcode.io/problems/minimum-time-to-collect-all-apples-in-a-tree/question

Problem: 
You are given an undirected tree consisting of `n` vertices numbered from `0` to `n-1`, which has some apples in their vertices. You spend one second to walk over one edge of the tree. Return the minimum time in seconds you have to spend to collect all apples in the tree, starting at **vertex 0** and coming back to this vertex.

The edges of the undirected tree are given in the array `edges`, where `edges[i] = [ai, bi]` means that exists an edge connecting the vertices `a[i]` and `b[i]`. Additionally, there is a boolean array `hasApple`, where `hasApple[i] = true` means that vertex `i`has an apple; otherwise, it does not have any apple.

**Example 1:**
```java
Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]

Output: 8
```

Explanation: The path that takes minimum time to collect all apples is: 0 -> 1 -> 4 -> 1 -> 5 -> 1 -> 0 -> 2 -> 0.

**Example 2:**
```java
Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,false,true,false]

Output: 6
```

Explanation: The path that takes minimum time to collect all apples is: 0 -> 2 -> 0 -> 1 -> 5 -> 1 -> 0.

**Constraints:**
- `1 <= n <= 100,000`
- `edges.length == n - 1`
- `edges[i].length == 2`
- `0 <= a[i] < b[i] <= n - 1`
- `hasApple.length == n`


Intuition:
The graph is a tree.
If you’ve trained yourself well, this should trigger an alarm:
> “Tree ⇒ unique path between any two nodes.”
> **“Which edges are absolutely unavoidable, no matter what order I choose?”**

You’re forced to:
- cross that edge to reach apples
	- cross it again to come back

**In trees, optimal walks are determined by structure, not choices.**

> “I root the tree at 0, propagate whether each subtree contains an apple, and add 2 seconds for every edge whose child-side subtree contains at least one apple.”

Solution:
```python
class Solution:
    def minTime(self, n: int, edges: List[List[int]], hasApple: List[bool]) -> int:
        # Build undirected adjacency list
        tree_map = [[] for _ in range(n)]
        for u, v in edges:
            tree_map[u].append(v)
            tree_map[v].append(u)

        ans = 0

        def dfs(node: int, parent: int) -> bool:
            nonlocal ans
            has = hasApple[node]

            for nei in tree_map[node]:
                if nei == parent:
                    continue
                if dfs(nei, node):
                    ans += 2
                    has = True

            return has

        dfs(0, -1)
        return ans
```

Tags: #tree 

RL: 

Considerations:
