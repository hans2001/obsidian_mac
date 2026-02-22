2025-02-17 16:49

Link: https://neetcode.io/problems/foreign-dictionary

Problem: 
There is a foreign language which uses the latin alphabet, but the order among letters is _not_ "a", "b", "c" ... "z" as in English.

You receive a list of _non-empty_ strings `words` from the dictionary, where the words are **sorted lexicographically** based on the rules of this new language. 

Derive the order of letters in this language. If the order is invalid, return an empty string. If there are multiple valid order of letters, return **any** of them.

A string `a` is lexicographically smaller than a string `b` if either of the following is true:
- The first letter where they differ is smaller in `a` than in `b`.
- There is no index `i` such that `a[i] != b[i]` _and_ `a.length < b.length`.

Motivation:
DFS:
build a directed acyclic graph with dictionary, with character as node and child node as elements in the set. Then we could run a post-order dfs traversal, recursively explore a node's neighbors and append nodes to the answer array in the reverse order (child -> parent). The approach inherently ensure all child node is explored and recorded before the parent node is recorded. Once a cycle is detected in the process ,it mean the graph is not a directed acyclic graph, and we return empty string.

Khan's algo (topological sort) :
similar to above, we build edge relationship with a dictionary, but record in-degree for each node along the way. As we traverse through the graph, we first process nodes that has no dependencies (small value come first), the in-degree of neighboring node will be reduced, and neighbors with zero dependencies will be append to the queue. The recorded order is the topological order which does not have to be reversed! 

Solution:
DFS
```python
class Solution:
    def foreignDictionary(self, words: List[str]) -> str:
        adj = {c: set() for w in words for c in w}

        for i in range(len(words) - 1):
            w1, w2 = words[i], words[i + 1]
            minLen = min(len(w1), len(w2))
            if len(w1) > len(w2) and w1[:minLen] == w2[:minLen]:
                return ""
            for j in range(minLen):
                if w1[j] != w2[j]:
                    adj[w1[j]].add(w2[j])
                    break

        visited = {}
        res = []

        def dfs(char):
            if char in visited:
                return visited[char]
            visited[char] = True

            for neighChar in adj[char]:
                if dfs(neighChar):
                    return True

            visited[char] = False
            res.append(char)

        for char in adj:
            if dfs(char):
                return ""

        res.reverse()
        return "".join(res)
```

Topological_sort (Kahn's Algorithm):
```python
class Solution:
    def foreignDictionary(self, words):
        adj = {c: set() for w in words for c in w}
        indegree = {c: 0 for c in adj}
        
        for i in range(len(words) - 1):
            w1, w2 = words[i], words[i + 1]
            minLen = min(len(w1), len(w2))
            if len(w1) > len(w2) and w1[:minLen] == w2[:minLen]:
                return ""
            for j in range(minLen):
                if w1[j] != w2[j]:
                    if w2[j] not in adj[w1[j]]:
                        adj[w1[j]].add(w2[j])
                        indegree[w2[j]] += 1
                    break
        
        q = deque([c for c in indegree if indegree[c] == 0])
        res = []
        
        while q:
            char = q.popleft()
            res.append(char)
            for neighbor in adj[char]:
                indegree[neighbor] -= 1
                if indegree[neighbor] == 0:
                    q.append(neighbor)
        
        if len(res) != len(indegree):
            return ""
        
        return "".join(res)
```
O(N * L) time to establish the adj and in-degree list / populate both list! 
(N: number of nodes, L: average number of characters in words)

Khan's Algo:
O(V+E)
V: number of unique characters
E: at most(N * L) if every adjacent pair is an edge

Time:
![[Screenshot 2025-02-17 at 10.52.25 PM.png]](N * L)

Tags: #topological_sort #dfs #khans_algorithm #graph

RL: 

Considerations:
### 1. **Why Do We Check for the Prefix Condition?**

Consider two words, say **w₁** and **w₂**, where one word is a prefix of the other. In a typical lexicographical order (think of dictionary order), the shorter word (i.e., the prefix) must come **before** the longer word. For example, in an English dictionary, the word "app" should come before "apple."

If we encounter a situation where **w₁** is longer than **w₂** but **w₁** starts with **w₂**, this ordering violates the expected rule. The algorithm checks for this condition:

Here, if **w₁** begins with **w₂** and yet appears before **w₂** in the list, the ordering is inconsistent. This check ensures that the input adheres to the basic lexicographical principle. Without this check, the subsequent steps would try to build an ordering that is inherently contradictory, because it would be trying to place a word before its prefix, which is logically invalid.

---

### 2. **Cycle Detection: What Does a Cycle Represent?**

In the context of this problem, we build a directed graph where nodes represent characters and an edge from **u** to **v**(written **u → v**) signifies that **u** must come before **v** in the dictionary order.

A **cycle** in this graph means there is a circular dependency among characters. For example, suppose we have:

- **a → b**
- **b → c**
- **c → a**

This cycle indicates that **a** must come before **b**, **b** before **c**, and simultaneously **c** must come before **a**. Such a scenario is impossible to satisfy because it violates the fundamental rule of a strict ordering (if **a** comes before **b**, and **b** comes before **c**, then **a** must come before **c**; having **c** come before **a** contradicts that).

Thus, detecting a cycle is crucial because it immediately tells us that no valid character ordering (or "alien dictionary") exists given the input constraints. When the DFS function encounters a node that is already in the recursion stack, it confirms the presence of a cycle, leading the algorithm to return an empty string.

### 3. **DFS, Post-order Traversal, and Valid Topological Sort**

Depth-First Search (DFS) is used to perform a topological sort of the graph. The key idea here is that we want to order the characters so that for every edge **u → v**, **u** appears before **v** in the ordering.

The DFS algorithm follows these steps:
1. **Explore All Descendants:**  
    For a given node, recursively explore all its neighbors (i.e., the characters that must follow it).
    
2. **Post-order Addition:**  
    Once all the descendants of a node have been fully explored, the node is added to the result list. This is a post-order process. The implication is that by the time a node is added, all characters that depend on it (directly or indirectly) are already in the result list.
    
3. **Reversal to Get Correct Order:**  
    Since nodes are added in post-order (i.e., children before parents), the result list is in reverse topological order. By reversing the list at the end, we obtain an order where every character appears before those that depend on it.
    

This reverse DFS finishing order works because in a **Directed Acyclic Graph (DAG)**, the absence of cycles guarantees that all dependencies can be satisfied in a linear order. Every edge **u → v** ensures that **u** is processed and added to the result list only after processing **v** (or vice versa in post-order), and reversing the list yields the correct topological sort.