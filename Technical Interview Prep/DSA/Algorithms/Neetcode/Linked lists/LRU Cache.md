2025-01-29 12:07

Link:https://neetcode.io/problems/lru-cache

Problem: 
Implement the [Least Recently Used (LRU)](https://en.wikipedia.org/wiki/Cache_replacement_policies#LRU) cache class `LRUCache`. The class should support the following operations

- `LRUCache(int capacity)` Initialize the LRU cache of size `capacity`.
- `int get(int key)` Return the value corresponding to the `key` if the `key` exists, otherwise return `-1`.
- `void put(int key, int value)` Update the `value` of the `key` if the `key` exists. Otherwise, add the `key`-`value` pair to the cache. If the introduction of the new pair causes the cache to exceed its capacity, remove the least recently used key.

A key is considered used if a `get` or a `put` operation is called on it.

Ensure that `get` and `put` each run in O(1) average time complexity.

Motivation:
update the dictionary whenever put() and get() is called! ensure length of dictionary falls within the capacity, otherwise we remove the least used key! 

hash map with doubly linked list :
implement the insert and remove method for the doubly linked list ,and we store node as value in the dictionary. in get method of LRU, we return -1 if no key in dict, otherwise, we move the node to end by delete and insert at the end! same for put method! update value requires us to move the node to the end, if dict size > self.cap ,we remove least recently used node from self.left.next !

Solution:
self
```python
class Node:
    def __init__(self, key: int, val: int):
        self.key = key
        self.val = val
        self.prev: "Node | None" = None
        self.next: "Node | None" = None


class LRUCache:
    def __init__(self, capacity: int):
        # Sentinels: left = LRU head, right = MRU tail
        self.left, self.right = Node(0, 0), Node(0, 0)
        self.left.next = self.right
        self.right.prev = self.left

        self.cap = capacity
        self.cache: dict[int, Node] = {}

    def get(self, key: int) -> int:
        """
        Return value for key if present; move node to MRU position.
        """
        if key not in self.cache:
            return -1

        cur = self.cache[key]

        # detach current node
        prev, nxt = cur.prev, cur.next
        prev.next, nxt.prev = nxt, prev  # type: ignore[union-attr]

        # move to MRU (before right)
        tail = self.right.prev  # type: ignore[union-attr]
        tail.next = cur
        cur.prev, cur.next = tail, self.right
        self.right.prev = cur

        return cur.val

    def put(self, key: int, value: int) -> None:
        """
        Insert/update key with value; evict LRU if over capacity.
        """
        if self.cap == 0:
            return

        if key in self.cache:
            cur = self.cache[key]
            cur.val = value

            # detach current node
            prev, nxt = cur.prev, cur.next
            prev.next, nxt.prev = nxt, prev  # type: ignore[union-attr]
        else:
            if len(self.cache) >= self.cap:
                # evict LRU: first real node after left
                lru = self.left.next  # type: ignore[union-attr]
                p, n = lru.prev, lru.next
                p.next, n.prev = n, p  # type: ignore[union-attr]
                del self.cache[lru.key]

            cur = Node(key, value)
            self.cache[key] = cur

        # insert at MRU (before right)
        tail = self.right.prev  # type: ignore[union-attr]
        tail.next = cur
        cur.prev, cur.next = tail, self.right
        self.right.prev = cur
```

Doubly linked list
```python
class Node:
    def __init__(self, key, val):
        self.key, self.val = key, val
        self.prev = self.next = None

class LRUCache:
    def __init__(self, capacity: int):
        self.cap = capacity
        self.cache = {}  # map key to node

        self.left, self.right = Node(0, 0), Node(0, 0)
        self.left.next, self.right.prev = self.right, self.left

    def remove(self, node):
        prev, nxt = node.prev, node.next
        prev.next, nxt.prev = nxt, prev

    def insert(self, node):
        prev, nxt = self.right.prev, self.right
        prev.next = nxt.prev = node
        node.next, node.prev = nxt, prev

    def get(self, key: int) -> int:
        if key in self.cache:
            self.remove(self.cache[key])
            self.insert(self.cache[key])
            return self.cache[key].val
        return -1

    def put(self, key: int, value: int) -> None:
        if key in self.cache:
            self.remove(self.cache[key])
        self.cache[key] = Node(key, value)
        self.insert(self.cache[key])

        if len(self.cache) > self.cap:
            lru = self.left.next
            self.remove(lru)
            del self.cache[lru.key]
```

OrderedDict
```python
from collections import OrderedDict
class LRUCache:
    def __init__(self, capacity: int):
        self.cache = OrderedDict()
        self.cap = capacity

    def get(self, key: int) -> int:
        if key not in self.cache:
            return -1
        self.cache.move_to_end(key)
        return self.cache[key]

    def put(self, key: int, value: int) -> None:
        if key in self.cache:
            self.cache.move_to_end(key)
        self.cache[key] = value

        if len(self.cache) > self.cap:
            self.cache.popitem(last=False)
```

Tags: #linked_list #doubly_linked_list

RL: 

Time complexity: O(1) for put () and get ()

Space complexity: O(n)