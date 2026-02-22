2025-04-10 14:00

Link:

Problem: 
standard union find algo

Intuition:
init: 
initialize a DSU class to perform union or find operation! 

union: 
we take root of tree with the smaller rank and set it as the child of the representative root of the larger rank tree .the root node of the larger set now becomes the root node of both set

find: 
locate parents node until parent node is itself! then we find the root node of this set! 

Solution:
```python
class DSU:
    def __init__(self, n):
        self.parent = [i for i in range(n)]
        self.rank = [0] * n          
    
    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]
    
    def union(self, x, y):
        # Union by rank (merge two sets)
        rootX = self.find(x)
        rootY = self.find(y)
        
        if rootX != rootY:
            # Attach the smaller tree under the larger tree
            if self.rank[rootX] > self.rank[rootY]:
                self.parent[rootY] = rootX
            elif self.rank[rootX] < self.rank[rootY]:
                self.parent[rootX] = rootY
            else:
                self.parent[rootY] = rootX
                self.rank[rootX] += 1
```

Tags: #disjoin_set_union 

RL: 

Considerations:
how do we set the rank for each tree? what does that mean ,how do we model this data structure to solve other problem

what is the time and space complexity for the find and union methods 
