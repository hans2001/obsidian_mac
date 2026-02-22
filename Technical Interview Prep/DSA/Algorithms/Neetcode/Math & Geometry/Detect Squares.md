2025-02-23 16:46

Link: https://neetcode.io/problems/count-squares

Problem: 
You are given a stream of points consisting of x-y coordinates on a 2-D plane. Points can be added and queried as follows:

- **Add** - new points can be added to the stream into a data structure. Duplicate points are allowed and should be treated as separate points.
- **Query** - Given a single query point, **count** the number of ways to choose three additional points from the data structure such that the three points and the query point form a **square**. The square must have all sides parallel to the x-axis and y-axis, i.e. no diagonal squares are allowed. Recall that a **square** must have four equal sides.

Implement the `CountSquares` class:

- `CountSquares()` Initializes the object.
- `void add(int[] point)` Adds a new point `point = [x, y]`.
- `int count(int[] point)` Counts the number of ways to form valid **squares** with point `point = [x, y]` as described above.

Intuition:
duplicate point can form an additional square. for iterating point in points, we treat each point in points as the diagonal point for the query point, and we consider duplicate diagonal naturally. to Consider duplicate horizontal and vertical point , we multiple their occurrence as the number of ways to form squares!

Solution:
```python
class CountSquares:
    def __init__(self):
        self.ptsCount = defaultdict(int)
        self.pts = []

    def add(self, point: List[int]) -> None:
        self.ptsCount[tuple(point)] += 1
        self.pts.append(point)

    def count(self, point: List[int]) -> int:
        res = 0
        px, py = point
        for x, y in self.pts:
            if (abs(py - y) != abs(px - x)) or x == px or y == py:
                continue
            res += self.ptsCount[(x, py)] * self.ptsCount[(px, y)]
        return res
```
O(1) for add ,O(n) for count / O(n)

Tags: #google #math #matrix #geometry 

RL: 

Considerations:
