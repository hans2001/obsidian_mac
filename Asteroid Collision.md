2025-05-06 11:36

Link: https://neetcode.io/problems/asteroid-collision

Problem: 
You are given an array `asteroids` of integers representing asteroids in a row. The indices of the asteroid in the array represent their relative position in space.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

Intuition:
only if last asteroid goes right and current ast goes left, we resolve collision, otherwise they are fine !

Solution:
```python
class Solution:
    def asteroidCollision(self, asteroids: List[int]) -> List[int]:
        sk = []
        
        for ast in asteroids:
            # Empty stack case - just add the asteroid
            if not sk:
                sk.append(ast)
                continue
                
            # Same direction or left-moving meets right-moving - no collision
            if sk[-1] * ast > 0 or (sk[-1] < 0 and ast > 0):
                sk.append(ast)
                continue
                
            # Handle potential collision (right-moving meets left-moving)
            explode = False
            while sk and (sk[-1] > 0 and ast < 0):
                # Equal size - both explode
                if abs(sk[-1]) == abs(ast):
                    sk.pop()
                    explode = True
                    break
                # Stack asteroid smaller - it explodes, continue checking
                elif abs(sk[-1]) < abs(ast):
                    sk.pop()
                # Current asteroid smaller - it explodes
                else:
                    explode = True
                    break
                    
            # Add current asteroid if it survives
            if not explode:
                sk.append(ast)
                
        return sk
```

Complexity:
Time: O(n)
Space: O(n)

Tags: #stack 

RL: 

Considerations:
