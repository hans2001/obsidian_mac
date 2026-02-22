2025-03-11 17:07

Link:

Problem: 
![[Screenshot 2025-03-11 at 5.08.07 PM.png]]
Intuition:
append none node into the q is not feasible -> infinite loop, so we should just keep track of the index instead, and compute max width at each level

Solution:
```python
def width_of_binary_tree(root):
	q =deque ( [(root , 0) ])
	md =0
	while q:
		lo,hi = float( 'inf'),float('-inf')
		for _ in range( len(q)) :
			node ,index = q.popleft( )
			if not node:
				continue
			lo = min ( lo ,index )
			hi = max ( hi ,index)
			if node .left:
				q.append( (node.left, (2 * index) + 1 ))
			if node.right:
				q.append( (node.right ,(2 * index) +2) )
		md = max( md , hi - lo +1 )
	return md
```

Tags: #bfs #deque 

RL: 

Considerations:
