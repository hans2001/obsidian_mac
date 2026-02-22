2025-05-07 11:54

Link: https://leetcode.com/problems/lfu-cache/description/

Problem: 
Design and implement a data structure for a [Least Frequently Used (LFU)](https://en.wikipedia.org/wiki/Least_frequently_used)cache.

Implement the `LFUCache` class:

- `LFUCache(int capacity)` Initializes the object with the `capacity` of the data structure.
- `int get(int key)` Gets the value of the `key` if the `key` exists in the cache. Otherwise, returns `-1`.
- `void put(int key, int value)` Update the value of the `key` if present, or inserts the `key` if not already present. When the cache reaches its `capacity`, it should invalidate and remove the **least frequently used** key before inserting a new item. For this problem, when there is a **tie** (i.e., two or more keys with the same frequency), the **least recently used** `key` would be invalidated.

To determine the least frequently used key, a **use counter** is maintained for each key in the cache. The key with the smallest **use counter** is the least frequently used key.

When a key is first inserted into the cache, its **use counter** is set to `1`(due to the `put` operation). The **use counter** for a key in the cache is incremented either a `get` or `put` operation is called on it.

The functions `get` and `put` must each run in `O(1)` average time complexity.

Intuition:
we use orderedDict to maintain the key insertion order, while we maintain 2 other hashmap that return the key_value and the key_frequency in O(1) time. We maintain an additional min_freq variable to get the min frequency in O(1) time! 

Solution:
hashmap + OrderDict
```python
    def _update(self, key: int):
        freq = self.key_freq[key]
        # remove from old bucket BY KEY
        old_bucket = self.key_order[freq]
        del old_bucket[key]
        if not old_bucket:
            del self.key_order[freq]
            if freq == self.min_freq:
                self.min_freq += 1

        # bump frequency and add to new bucket
        self.key_freq[key] += 1
        self.key_order[self.key_freq[key]][key] = None

    def put(self, key: int, value: int) -> None:
        if self.cap == 0:
            return

        if key in self.key_val:
            self.key_val[key] = value
            self._update(key)
            return

        if self.size == self.cap:
            # evict exactly from the current min_freq bucket
            bucket = self.key_order[self.min_freq]
            evict_key, _ = bucket.popitem(last=False)
            del self.key_val[evict_key]
            del self.key_freq[evict_key]
            if not bucket:
                del self.key_order[self.min_freq]
            self.size -= 1

        # insert new
        self.key_val[key] = value
        self.key_freq[key] = 1
        self.min_freq = 1            # <- reset here!
        self.key_order[1][key] = None
        self.size += 1
```

Complexity:
Time: 
all operations runs in O(1) time

Space: 
we store at most C nodes, where C is capacity of the list! 

Tags: #ordered_dict #linked_list #hash_map

RL: 

Considerations:
