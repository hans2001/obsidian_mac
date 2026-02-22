build tree from a list
```python
from collections import deque

# Definition for a binary tree node
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def build_tree_bfs(values):
    """
    Build a binary tree from a list of values in level-order (BFS).
    None values represent missing nodes.
    """
    if not values:
        return None
    
    root = TreeNode(values[0])
    q = deque([root])
    i = 1
    
    while q and i < len(values):
        node = q.popleft()
        
        # Left child
        if i < len(values) and values[i] is not None:
            node.left = TreeNode(values[i])
            q.append(node.left)
        i += 1
        
        # Right child
        if i < len(values) and values[i] is not None:
            node.right = TreeNode(values[i])
            q.append(node.right)
        i += 1
    
    return root
# Example usage:
lst = [1, 2, 3, None, 4, 5, 6]
root = build_tree_bfs(lst)
```

LRU but with expiration
```python
import time
import heapq
from collections import OrderedDict
from typing import Any, Optional

class LRUWithTTL:
    """
    LRU cache with optional per-item expiration.
    - get/set are amortized O(1)
    - Expirations are cleaned lazily on access and eagerly via a min-heap (O(log n) per purge step)
    """
    __slots__ = ("_cap", "_store", "_heap", "_counter", "_default_ttl")

    def __init__(self, capacity: int, default_ttl: Optional[float] = None):
        """
        capacity: max number of live (non-expired) items to keep by LRU policy
        default_ttl: seconds; if None => no expiration unless set() provides one
        """
        if capacity <= 0:
            raise ValueError("capacity must be > 0")
        self._cap = capacity
        self._store: OrderedDict[Any, tuple[Any, float, int]] = OrderedDict()
        # heap entries: (expiry_ts, token, key)
        self._heap: list[tuple[float, int, Any]] = []
        self._counter = 0  # monotonically increasing token to invalidate stale heap entries
        self._default_ttl = default_ttl

    # ---- internal helpers ----
    def _now(self) -> float:
        return time.monotonic()

    def _expiry_ts(self, ttl: Optional[float]) -> float:
        if ttl is None:
            ttl = self._default_ttl
        if ttl is None:
            return float("inf")
        return self._now() + float(ttl)

    def _purge_expired(self) -> None:
        """Remove all items that have passed their expiry (heap-guided)."""
        now = self._now()
        while self._heap and self._heap[0][0] <= now:
            exp, token, key = heapq.heappop(self._heap)
            rec = self._store.get(key)
            if rec is None:
                continue
            _, exp_live, tok_live = rec
            # Only remove if heap entry matches the current live record
            if tok_live == token and exp_live <= now:
                self._store.pop(key, None)

    def _evict_if_needed(self) -> None:
        """Evict LRU items (non-expired) to respect capacity."""
        while len(self._store) > self._cap:
            # popitem(last=False) pops the least-recently-used
            k, _ = self._store.popitem(last=False)
            # Heap entry for k (if any) will be ignored later due to token mismatch

    # ---- public API ----
    def get(self, key: Any, default: Any = None) -> Any:
        self._purge_expired()
        rec = self._store.get(key)
        if rec is None:
            return default
        val, exp, tok = rec
        if exp <= self._now():
            # expired; drop it lazily
            self._store.pop(key, None)
            return default
        # refresh LRU position
        self._store.move_to_end(key, last=True)
        return val

    def set(self, key: Any, value: Any, ttl: Optional[float] = None) -> None:
        self._purge_expired()
        self._counter += 1
        exp = self._expiry_ts(ttl)
        # Insert/update value
        self._store[key] = (value, exp, self._counter)
        self._store.move_to_end(key, last=True)
        heapq.heappush(self._heap, (exp, self._counter, key))
        self._evict_if_needed()

    def delete(self, key: Any) -> bool:
        self._purge_expired()
        return self._store.pop(key, None) is not None

    def __contains__(self, key: Any) -> bool:
        return self.get(key, default=object()) is not object()

    def __len__(self) -> int:
        self._purge_expired()
        return len(self._store)

    def keys(self):
        self._purge_expired()
        # Ordered by recency (LRU -> MRU)
        return list(self._store.keys())

    def clear(self) -> None:
        self._store.clear()
        self._heap.clear()
        self._counter = 0
```


feedback: 
coding style is messy
did not clarify everything first before implementation
	the time calculation makes me nervous
	understand the constraints!

did not think about edge cases , base cases
did not have a logical way of approaching problems!
were not familiar with tree based questions! (backtracking and dfs )

ask for hint in a more polite way ( and make it more obvious )
did not have a good control of pace and time (get nervous)

should have a clearer picture first before code implementation 
and go through with the interviewer to make sure we are on the right direction
explain python syntax, making sure interviewer is on track! 
