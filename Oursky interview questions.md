Explain the difference between process and thread.
## Process vs Thread
**Process:** 
independent execution unit
own memory space / overhead to create 
communicate via IPC

**Thread:** 
lightweight, share process memory
less overhead
share memory ( heap ?)
crash affect entire process

```python
# Process example
import multiprocessing
def worker():
    # Runs in separate memory space
    pass
p = multiprocessing.Process(target=worker)

# Thread example  
import threading
def worker():
    # Shares memory with main thread
    pass
t = threading.Thread(target=worker)
```

## Stack vs Heap: 
**Stack:**
stores function calls, local variables! 
fast access, fixed size, thread safe ? (each thread has its own stack)

**Heap:**
dynamic memory for objects? and data structures
slow access? larger space? shared across threads
## Async func
concurrent execution without blocking! (during I/O network, disk running)
```python
import asyncio
import aiohttp

# Async function definition
async def fetch_data(url):
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            return await response.text()

# Using async functions
async def main():
    # Run concurrently
    urls = ['http://api1.com', 'http://api2.com', 'http://api3.com']
    
    # All requests happen concurrently
    results = await asyncio.gather(*[fetch_data(url) for url in urls])
    return results

# Run the async function
asyncio.run(main())
```

1st round: prefix handling, big o notation and class implementation, etc.
```python
def longest_common_prefix(strings):
    if not strings:
        return ""
    
    # Compare characters at each position
    for i, char in enumerate(strings[0]):
        for s in strings[1:]:
            if i >= len(s) or s[i] != char:
                return strings[0][:i]
    
    return strings[0]

# Time: O(S) where S = sum of all string lengths
# Space: O(1)
```

2nd round: fix some bugs in a given todo app, refactor the code and then follow up. If you don't have knowledge in web dev they probably wouldn't give you this task. Some algo questions were asked as well.
## Build a simple cache management system
```python
from collections import OrderedDict
import time

class LRUCache:
    def __init__(self, capacity):
        self.cache = OrderedDict()
        self.capacity = capacity
        
    def get(self, key):
        if key in self.cache:
            # Move to end (most recent)
            self.cache.move_to_end(key)
            return self.cache[key]
        return None
        
    def put(self, key, value):
        if key in self.cache:
            self.cache.move_to_end(key)
        self.cache[key] = value
        
        if len(self.cache) > self.capacity:
            # Remove least recently used
            self.cache.popitem(last=False)

# Time: O(1) for get/put
# Space: O(capacity)
```

## When need to denormalize the database

- **Read performance critical**: Many JOINs slow queries
- **Reporting/Analytics**: Pre-computed aggregates
- **High read-to-write ratio**: Few updates, many reads
- **Distributed systems**: Avoid cross-service JOINs

```sql
-- Normalized: Need JOIN
SELECT o.id, c.name, c.email 
FROM orders o 
JOIN customers c ON o.customer_id = c.id

-- Denormalized: Direct access
SELECT id, customer_name, customer_email 
FROM orders  -- customer data duplicated
```

Convert a recurring function into a non-recurring function
```python
# Recursive factorial
def factorial_recursive(n):
    if n <= 1:
        return 1
    return n * factorial_recursive(n - 1)

# Iterative version
def factorial_iterative(n):
    result = 1
    for i in range(2, n + 1):
        result *= i
    return result

# More complex: Tree traversal
# Recursive
def inorder_recursive(root):
    if not root:
        return []
    return inorder_recursive(root.left) + [root.val] + inorder_recursive(root.right)

# Iterative with stack
def inorder_iterative(root):
    result = []
    stack = []
    current = root
    
    while stack or current:
        while current:
            stack.append(current)
            current = current.left
        current = stack.pop()
        result.append(current.val)
        current = current.right
    
    return result
```

## Q1 about counting CamelCase words (can use any language) 
```python
def count_camel_case_words(text):
    count = 0
    for i, char in enumerate(text):
        # Count uppercase letters that start words
        if char.isupper() and (i == 0 or not text[i-1].isalpha()):
            count += 1
    return count

# Alternative: Using regex
import re
def count_camel_case_regex(text):
    # Matches word boundaries followed by uppercase
    return len(re.findall(r'\b[A-Z][a-z]*', text))

# Time: O(n), Space: O(1)
```
## Q2 about the Big O notation Q3 convert the function without recursion

## Cookie vs Session 
**Cookie:** 
stored on client
limited size (4kb) / persistent and visible to client /less secure

**Session:** 
stored on server/ no size limit? 
expires with when browser close
session ID in cookie? secure? 

```python
# Cookie (Flask example)
response.set_cookie('user_id', '123', max_age=3600)

# Session
session['user_id'] = '123'  # Stored on server
```

## Identify whether to use list or dictionary in a use case.
**list:**
order matters, index access in O(1) , duplicates are ok 
**dict:** 
fast lookup, key-value relationship ,no duplicate, no order

## MVC model
**Mode** : Data and business logic
**View**: presentation layer
**Controller**: handles user input 
```python
# Simple MVC example
class Model:
    def __init__(self):
        self.data = []
    
    def add_item(self, item):
        self.data.append(item)

class View:
    def display(self, items):
        for item in items:
            print(f"- {item}")

class Controller:
    def __init__(self, model, view):
        self.model = model
        self.view = view
    
    def add_item(self, item):
        self.model.add_item(item)
        self.view.display(self.model.data)
```
## Synchronization 
**Synchronization** is coordinating multiple threads/processes to ensure they access shared resources safely and in the correct order.

race condition 
```python
# PROBLEM: Without synchronization
balance = 1000

def withdraw(amount):
    global balance
    temp = balance      # Thread 1 reads: 1000
    temp -= amount      # Thread 2 reads: 1000 (before Thread 1 updates!)
    balance = temp      # Both set to 900, lost $100!

# Two threads withdrawing $100 each
# Expected: $800, Actual: $900 (race condition)
```

Mutex Locks
```python
import threading

balance = 1000
lock = threading.Lock()

def withdraw(amount):
    global balance
    with lock:  # Only one thread can enter at a time
        temp = balance
        temp -= amount
        balance = temp
```

Conditions 
```python
# Thread waits for specific condition
condition = threading.Condition()
queue = []

def consumer():
    with condition:
        while not queue:  # Wait until queue has items
            condition.wait()
        item = queue.pop(0)
        
def producer():
    with condition:
        queue.append(item)
        condition.notify()  # Wake up waiting consumer
```

Atomic operations
```python 
from threading import Lock
import threading

class Counter:
    def __init__(self):
        self._value = 0
        self._lock = Lock()
    
    def increment(self):
        with self._lock:  # Atomic increment
            self._value += 1
```

## Explain the property of GET and POST
**GET method**
```python
# Example GET request
requests.get('https://api.example.com/users?id=123&name=john')
```
- **Idempotent**: Multiple identical requests = same result
- **Cacheable**: Browsers/proxies can cache
- **Bookmarkable**: Can save URLs
- **Length limits**: ~2048 characters
- **Visible in URL**: Parameters exposed
- **No request body**: Data in URL only
- **Safe**: Should not modify server state

for : 
- Fetching data
- Search queries
- Filtering/sorting

**POST method**
```python
# Example POST request
requests.post('https://api.example.com/users', 
              json={'name': 'john', 'email': 'john@example.com'})
```
- **Not idempotent**: Each request may change state
- **Not cacheable**: By default
- **Not bookmarkable**: No data in URL
- **No length limits**: Large payloads OK
- **Hidden data**: In request body
- **Has request body**: Can send complex data
- **Not safe**: Modifies server state

for :
- Creating resources
- Submitting forms
- Uploading files

producer / consumer
```python
import threading
import queue
import time

def producer(q):
    for i in range(5):
        item = f"item_{i}"
        q.put(item)
        print(f"Produced {item}")
        time.sleep(1)

def consumer(q):
    while True:
        item = q.get()
        if item is None:  # Poison pill
            break
        print(f"Consumed {item}")
        q.task_done()

# Thread-safe queue handles synchronization
q = queue.Queue(maxsize=10)
```

## Write a function that takes two arrays as input, each array contains a list of A-Z; Your program should return True if the 2nd array is a subset of 1st array, or False if not.
For example: isSubset([A,B,C,D,E], [A,E,D]) = true isSubset([A,B,C,D,E], [A,D,Z]) = false isSubset([A,D,E], [A,A,D,E]) = true

the intersection set should equal to the second set , otherwise false!
## What is the computational complexity of your answer in Question 1?
building the set takes  O(n+m) , but the inersection operation takes O(1)

Write a function that takes an array of integers as input. For each integer, output the next fibonacci number.
Fibonacci number of Fn is defined by: Fn = Fn-1 + Fn-2 F1 = 1, F2 = 1

Your program should run correctly for the first 60 Fibonacci Numbers.
```
For example: nextFibonacci([1,9,22])
Output: 2
13
34
```

base case? so we should keep track of an additional index ? 
base case is when n == 1 or 2 %%  %%
we start fomr the top? 
dp  = {}
dp [1] = 1
dp [2] = 2
def main(self):
	def fib( n ) :
		if n == 1: 
			return 1
		if n == 2: 
			return 2
		if n in dp: 
			return dp[n]
		dp[n-1] = fib( n-1 )
		dp[n-2] = fib( n-2 )
		return dp[n-1] + dp[n-2]
num = fib( 60 )
return list(dp.values( ))

fib(60) (here we just got a single number for the 60th fibonacci number, so we need an array to store the numbers from bottom to top)

   how long is the recursion stack, should be O(n) ? is there duplicate computation? single tree, so no! 

# Question 4
Please explain what is the bug in the following Javascript function, and how to correct it.

```js
function createArrayOfFunctions(y) {
  var arr = [];
  for(var i = 0; i<y; i++) {
    arr[i] = function(x) { return x + i; }
  }
  return arr; 
}
```
specify the arr to store functions ,where the fucntion will be different depending on the 
var is function based, so it should be let  where i is create each time! 
intention of code, array of functions, this part is correct
