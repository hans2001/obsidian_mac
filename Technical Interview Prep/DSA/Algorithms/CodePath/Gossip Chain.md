2025-03-13 19:49

Link:

Problem: 
In Hollywood, rumors spread rapidly among celebrities through various connections. Imagine each celebrity is represented as a vertex in a directed graph, and the connections between them are directed edges indicating who spread the latest gossip to whom.

The arrival time of a rumor for a given celebrity is the moment the rumor reaches them for the first time, and the departure time is when all the celebrities they could influence have already heard the rumor, meaning they are no longer involved in spreading it.

Given a list of edges `connections` representing connections between celebrities and the number of celebrities in the the graph `n`, find the arrival and departure time of the rumor for each celebrity in a Depth First Search (DFS) starting from a given celebrity `start`.

Return a dictionary where each celebrity in `connections` is a key whose corresponding value is a tuple `(arrival_time, departure_time)` representing the arrival and departure times of the rumor for that celebrity. If a celebrity never hears the rumor their arrival and departure times should be `(-1, -1)`.

Intuition:
we derive the arrival time by the first time the node being visited , and departure time by all its neighbor being visited. we can derive the departure time with backtracking, for visited neighbors, we increment global time by 2 to indicate an immediate arrival/departure (no particularly reason here : poor question) (we should ignore duplicate path by default! )

Solution:
```python
connections = [
    ["A", "B"],
    ["A", "C"],
    ["B", "C"],
    ["C", "D"],
    ["D", "B"],  # introduces a cycle (B, C, D)
    ["E", "F"]   # disconnected component
]

from collections import deque ,defaultdict
def rumor_spread_times(connections, n, start):
    d= defaultdict( list )
    res = {}
    for con in connections: 
        head ,tail = con
        d[head].append( tail )
        if head not in res: 
            res[head]  = ( -1 ,-1)
        if tail not in res:
            res[tail]  = ( -1 ,-1) 
    time = 1 
    vis = set ( )
    def dfs( node )  :
        nonlocal time
        if node in vis:
            return
        vis .add (node )
        st = time
        time +=1 
        for  ne in d[node] : 
            if ne not in vis: 
                dfs( ne )
            else: 
                if node != start: 
                    time += 2
        end = time 
        time +=1 
        res[ node ] = (st ,end )
    if start in d: 
        dfs ( start )
    return res
    
print(rumor_spread_times(connections, 7, "A"))
```

Tags: #dfs #graph 

RL: 

Considerations:
