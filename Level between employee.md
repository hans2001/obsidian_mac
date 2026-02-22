2025-07-09 19:25

Link:

Problem: 
![[Pasted image 20250709193124.png]]
Intuition:
find depth of both node, then minus the depth of their lowest lca to get their level!

Solution:
```python
from collections import defaultdict
def solve_hierarchy_problem ( query,pairs ) :
    # only one boss for each employee, so here should be str
    d = defaultdict ( str )
    seen = set ( )
    for u ,v in pairs: 
        d[u] = v
        seen.add ( u )
        seen.add ( v )
    root = None 
    for node in seen: 
        if node not in d: 
            root = node
    print( 'root', root )
        
    def find_dis ( query , root ): 
        node1 ,node2 = query
        cur1,cur2 = node1 ,node2 
        d1,d2 = 0, 0
        parents = set ( [root] )
        while cur1 != root: 
            # child become parent, distance add 1
            parents.add ( cur1 )
            cur1 =  d[ cur1 ]
            d1 += 1
        while cur2 != root: 
            # child become parent, distance add 1
            cur2 =  d[ cur2 ]
            d2 += 1
        print ( d1, d2 )
        lca = node2
        # find lca first, then find distance from lca to root!
        while lca not in parents: 
            # child become parent, distance add 1
            lca =  d[ lca ]
        print ("lca" , lca )
        # find distance from lca to root!
        lca_dis = 0 
        temp_lca = lca
        while temp_lca != root: 
            temp_lca = d[temp_lca]
            lca_dis += 1
        return d1 + d2  - (2 * lca_dis)
    
    return find_dis ( query, root )
query = ("Intern", "CEO")
pairs = [
    ("Intern", "Junior"),
    ("Junior", "Senior"),
    ("Senior", "TeamLead"),
    ("TeamLead", "Manager"),
    ("Manager", "Director"),
    ("Director", "CEO")
]
result = solve_hierarchy_problem(query, pairs)
print(f"Distance between Susan and Amy: {result}")
# Output: Distance between Susan and Amy: 2
```
Tags: 

RL: 

Considerations:
