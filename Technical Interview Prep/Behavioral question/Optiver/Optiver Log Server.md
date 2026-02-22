2025-07-15 14:26

Link:

Problem: 
![[Pasted image 20250715142640.png]]
Intuition:
maintain the object log with a list, and use binary search to quickly find the lower bound for logs that is within one hour! for get log count, the number m does not matter!

Solution:
```python
from collections import deque
import bisect
class Solution :
  def __init__ ( self , m ) :
    self.m = m
    self.q = []
    
  def recordLog ( self, logId , time_stamp ): 
    if self.q and time_stamp < self.q[-1][1] :
      return 
    self.q.append( (logId ,time_stamp) )
  
  def find_start(self): 
    last = self.q[ -1 ][ 1 ]
    l_bound = last - 3600
    start = bisect.bisect_right ( self.q , l_bound , key = lambda x: x[1] )
    return start
    
  def getLogs(self):
    if not self.q: 
      return ""
    start = self.find_start ()
    valid = [str( self.q[i][0] ) for i in range( start, len( self.q ))]  
handle    res = valid[-self.m : ]
    return ",".join( res )
  
  def getLogCount(self):
    if not self.q: 
      return 0
    start = self.find_start ()
    return len( self.q ) - start
```

Tags: #ood 

RL: [[981. Time Based Key-Value Store]]

Considerations:
