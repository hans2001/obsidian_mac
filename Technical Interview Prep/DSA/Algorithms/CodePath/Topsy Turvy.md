2025-03-11 15:14

Link:

Problem: 
You're walking down the hotel hallway one night and something strange begins to happen - the entire hotel flips upside down. The room sand their connections were flipped in a peculiar way and now you need to restore order. Given the root of a binary tree `hotel` where each node represents a room in the hotel, write a function `upside_down_hotel()` that flips the hotel right side up according to the following rules:

1. The original left child becomes the new root
2. The original root becomes the new right child
3. The original right child becomes the new left child.

[![Three node tree showing each step applied to tree](https://courses.codepath.org/course_images/tip102/unit9_session1/topsy_turvy.jpg "Three node tree showing each step applied to tree")](https://courses.codepath.org/course_images/tip102/unit9_session1/topsy_turvy.jpg)

The above steps are done level by level. It is **guaranteed** each right node has a sibling (a left node with the same parent) and has no children.
Return the root of the flipped hotel.

Evaluate the time and space complexity of your function. Define your variables and provide a rationale for why you believe your solution has the stated time and space complexity. Assume the input tree is balanced when calculating time and space complexity.

Intuition:
need to traverse to the leftmost node first, since a change at higher level will lose pointer to leftnode's child, so we should use a bottom up approach. Then propagate the new root to the top, but dont need to use it in the recursive stack ,since the passed in node from parameters is the actual node we need to  use and modify! (no pointer losing problem!) 

Solution:
```python
def flip_hotel(hotel):
    def dfs( node) : 
        if not node.left:
            return node 
        new_root = dfs( node.left)
        child = node.left
        right = node.right
        child.left = right
        child.right= node
        node.left=  None
        node.right=  None
        return new_root
    return dfs( hotel)
```

Tags: #dfs #tree 

RL: 

Considerations:
