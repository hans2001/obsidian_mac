2025-04-10 18:02

Link:

Problem: 
You are given a 2D matrix `battlefield` of size `m x n`, where `(row, column)` represents:

- An impassable obstacle if `battlefield[row][column] = 0`, or
- An square containing `battlefield[row][column]` enemy troops, if `battlefield[row][column] > 0`.

Your kingdom can start at any non-obstacle square `(row, column)` and can do the following operations any number of times: - Capture all the troops at square `battlefield[row][column]` or - Move to any adjacent cell with troops up, down, left, or right.

Return the maximum number of troops your kingdom can capture if they choose the starting cell optimally. Return `0` if no troops exist on the `battlefield`

Intuition:
find path that captures the maximum number of troops
with dfs, we find the best path to take for the local maximum_troop, therefore, we can return the max number of troops the root node can take! and we do it for each node, and find out the best starting point that can reach the maximum_troop in the  battlefield

Solution:
```python
from collections import defaultdict

def capture_max_troops(battlefield):
    if not battlefield: 
        return 0
    memo = defaultdict( int )
    dr = [( 0,1 ),( 0, -1 ) ,( 1,0 ) ,( -1, 0 )]
    r ,c =len( battlefield ), len ( battlefield[0] )
    
    def dfs ( i , j , vis ) :
        state =  ( i, j, frozenset( vis) )
        if state in memo: 
            return memo[state]
        if (i,j) in vis or not 0 <= i < r or not 0 <= j < c or battlefield[i][j] == 0: 
            return 0
            
        new_vis = vis  | { (i,j) }
        max_ad = 0
        for dx,dy in dr: 
            nx ,ny = i + dx, j + dy 
            max_ad = max( max_ad,  dfs ( nx, ny , new_vis ) ) 
        res= battlefield[i][j] + max_ad 
        memo[ state ] = res
        return res
    
    sm = 0 
    for i in range( r ) : 
        for j in range ( c ) : 
            if battlefield[i][j] > 0: 
                sm = max( sm , dfs( i, j , frozenset ( ) ) )
    return sm 
    
battlefield_1 = [
    [0,2,1,0],
    [4,0,0,3],
    [1,0,0,4],
    [0,3,2,0]]

print(capture_max_troops(battlefield_1))
```

Tags: #dfs #graph #memoization 

RL: 

Considerations:
