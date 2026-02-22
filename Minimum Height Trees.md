2025-05-16 14:42

Link: https://leetcode.com/problems/minimum-height-trees/description/

Problem: 
A tree is an undirected graph in which any two vertices are connected by _exactly_ one path. In other words, any connected graph without simple cycles is a tree.

Given a tree of `n` nodes labelled from `0` to `n - 1`, and an array of `n - 1` `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between the two nodes `ai` and `bi` in the tree, you can choose any node of the tree as the root. When you select a node `x` as the root, the result tree has height `h`. Among all possible rooted trees, those with minimum height (i.e. `min(h)`)  are called **minimum height trees** (MHTs).

Return _a list of all **MHTs'** root labels_. You can return the answer in **any order**.

The **height** of a rooted tree is the number of edges on the longest downward path between the root and a leaf.

Intuition:
**The roots of Minimum Height Trees must be the centroids (middle nodes) of the tree.** (which is the middle node of the longest path in the tree as you fold the tree around that node)
the middle node has the shortest distance to any leaf node( node with single connection edge )
as we trim away leaves that is far away from center of the node, we remove candidate for centroid, until we reach 2 to 1 nodes, the theoretical limit of number of centroids ,we know that we have found the centroid nodes ! 

Solution:
fake topological sort
```python
class Solution:
    def findMinHeightTrees(self, n: int, edges: List[List[int]]) -> List[int]:

        # edge cases
        if n <= 2:
            return [i for i in range(n)]

        # Build the graph with the adjacency list
        neighbors = [set() for i in range(n)]
        for start, end in edges:
            neighbors[start].add(end)
            neighbors[end].add(start)

        # Initialize the first layer of leaves
        leaves = []
        for i in range(n):
            if len(neighbors[i]) == 1:
                leaves.append(i)

        # Trim the leaves until reaching the centroids
        remaining_nodes = n
        while remaining_nodes > 2:
            remaining_nodes -= len(leaves)
            new_leaves = []
            # remove the current leaves along with the edges
            while leaves:
                leaf = leaves.pop()
                # the only neighbor left for the leaf node
                neighbor = neighbors[leaf].pop()
                # remove the only edge left
                neighbors[neighbor].remove(leaf)
                if len(neighbors[neighbor]) == 1:
                    new_leaves.append(neighbor)

            # prepare for the next round
            leaves = new_leaves

        # The remaining nodes are the centroids of the graph
        return leaves
```

Complexity:
Time: 
O(V + E)
E : V -1
it take V -1 iterations to construct a graph, with O(V) steps to retrieve initial leaf nodes, then we trim out all nodes (V) and edges V-1 from edges, which takes us V + V-1 operations to reach centroids
total complexity is O(V)

Space: 
O(V) for V number of nodes in graph and V-1 number of edges
with a nested queue that could store up to V-1 number of nodes that is not centroid
overall space complexity is O(V)

Tags: #topological_sort #minimum_height_tree #leaf_removal

RL: 

Considerations:
