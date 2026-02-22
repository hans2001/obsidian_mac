2025-01-26 12:41

Link:https://neetcode.io/problems/time-based-key-value-store

Problem: 
Implement a time-based key-value data structure that supports:

- Storing multiple values for the same key at specified time stamps
- Retrieving the key's value at a specified timestamp

Implement the `TimeMap` class:

- `TimeMap()` Initializes the object.
- `void set(String key, String value, int timestamp)` Stores the key `key` with the value `value` at the given time `timestamp`.
- `String get(String key, int timestamp)` Returns the most recent value of `key` if `set` was previously called on it _and_ the most recent timestamp for that key `prev_timestamp` is less than or equal to the given timestamp (`prev_timestamp <= timestamp`). If there are no values, it returns `""`.

Note: For all calls to `set`, the timestamps are in strictly increasing order.

Motivation:
Return the most recent timestamp that is smaller if desired timestamp not exist in associated values! Use binary search to match the timestamp.

Solution:
![[Screenshot 2025-01-26 at 12.41.58 PM.png]]

Tags: #binary_search #dict 

RL: [[Binary Search]]

Time complexity: O(1) for set( ) andO(log n) for get( )

Space complexity: O(m * n)