# Decorators 
It **returns a new function** — a wrapped version — that _controls_ what happens before/after the original. (run at definition time)
```python
def changecase(func):  
  def myinner(*args, **kwargs):  
    return func(*args, **kwargs).upper()  
  return myinner  
  
@changecase  
def myfunction(nam):  
  return "Hello " + nam
  
print(myfunction("John"))
```
A decorator is a function that takes another function as input and returns a new function. let you add extra behavior to a function, without changing the function's code.
### Decorators with arguments
```python
def changecase(n):  
  def changecase(func):  
    def myinner():  
      if n == 1:  
        a = func().lower()  
      else:  
        a = func().upper()  
      return a  
    return myinner  
  return changecase  
  
@changecase(1)  
def myfunction():  
  return "Hello Linus"  
  
print(myfunction())
```
A decorator factory that takes an argument and transforms the casing based on the argument value.
## Multiple Decorators
```python
def changecase(func):  
  def myinner():  
    return func().upper()  
  return myinner  
  
def addgreeting(func):  
  def myinner():  
    return "Hello " + func() + " Have a good day!"  
  return myinner  
  
@changecase  
@addgreeting  
def myfunction():  
  return "Tobias"  
  
print(myfunction())
```
This is done by placing the decorator calls on top of each other.
Decorators are called in the reverse order, starting with the one closest to the function.

A decorator is just a function that:
- **Takes a function** as input
- **Returns a new function** that wraps or replaces it
## Common usages
- auth checks (@requires_login)
- caching (@lru_cache)
- validation / logging
- registering routes or handlers

# Generator -> iterator
Generators are functions that can pause and resume their execution.
When a generator function is called, it returns a _generator object_, which is an iterator.

The code inside the function is not executed yet, it is only compiled. The function only executes when you iterate over the generator.
```python
def count_up_to(n):  
  count = 1  
  while count <= n:  
    yield count  
    count += 1  
  
for num in count_up_to(5):  
  print(num)
```
The `yield` keyword is what makes a function a generator. (must have yield in generator (pausing func state))

When `yield` is encountered, the function's state is saved, and the value is returned. The next time the generator is called, it continues from where it left off.

# Iterator
An iterator is an object that contains a countable number of values.

An iterator is an object that can be iterated upon, meaning that you can traverse through all the values.

Technically, in Python, an iterator is an object which implements the iterator protocol, which consist of the methods `__iter__()` and `__next__()`.

using the next func
```python
mystr = "banana"  
myit = iter(mystr)  
  
print(next(myit))  
print(next(myit))  
print(next(myit))  
print(next(myit))  
print(next(myit))  
print(next(myit))
```

iteration on iterator
```python
mytuple = ("apple", "banana", "cherry")  
  
for x in mytuple:  
  print(x)
```

create iterator
```python
class MyNumbers:  
  def __iter__(self):  
    self.a = 1  
    return self  
  
  def __next__(self):  
    x = self.a  
    self.a += 1  
    return x  
  
myclass = MyNumbers()  
myiter = iter(myclass)  
  
print(next(myiter))  
print(next(myiter))  
print(next(myiter))  
print(next(myiter))  
print(next(myiter))
```

# JSON
## json to python
```python
import json  
  
# some JSON:  
x =  '{ "name":"John", "age":30, "city":"New York"}'  
  
# parse x:  
y = json.loads(x)  
  
# the result is a Python dictionary:  
print(y["age"])
```
## object to json
```python
import json  
  
# a Python object (dict):  
x = {  
  "name": "John",  
  "age": 30,  
  "city": "New York"  
}  
  
# convert into JSON:  
y = json.dumps(x)  
  
# the result is a JSON string:  
print(y)
```

# Event loop
That loop does this over and over:
1. Look at all scheduled coroutines (“tasks”)
2. Pick one that is **ready to run**
3. Run it until it hits an await on something that **cannot finish immediately**
4. Pause that coroutine, mark what it’s waiting for
5. Move on to another coroutine that’s ready

**cooperative multitasking**: coroutines _yield control_ voluntarily by using await.
# Async
coroutine: async func
```python
async def io_task( name, delay, n_iter) :
	for i in range (1, n_iter + 1 ) :
	print( f"{name}.: iteration {i}" )
	%% give back contorl to event loop %%
	await asyncio.sleep(delay) (wait response form server) ( downtime )
```
one thread in event loop (switch between coroutine)
event loop switch between coroutine
which coroutine is capable of resuming! 

await asyncio.gather( io_task(task A,1,3),io_task(task B,1,3) ,io_task(task C,1,3) )
when task A is sleeping, we can do B ,when b sleeping, we can do C so on ...
```python
async def main():
	start = time.perf_conter()
	await ashncio.gather(io_task(A.),.....)
	end = time.perf_conter()
	print(end-start) 
	
	start = time.perf_conter()
	await io_task(task A ,...)
	await io_task(task B ,...)
	await io_task(task C,...)
	end = time.perf_conter()
	
	print(end-start)
```
the above loop run faster ( run concurrently, time.sleep give back control to the event loop! ) (while the second loop run synchronously) (thread is blocked )

```python
import httpx
import asyncio

app = FastAPI()
@app.get("/async-example")
async def async_example():
    # Non-blocking sleep
    await asyncio.sleep(1)

    # Async HTTP request
    async with httpx.AsyncClient() as client:
        r = await client.get("https://example.com")
    return {"status": r.status_code}
```
while await asyncio.sleep, we can return control of the running thread to the event loop ,and it can be used to process other coroutines (event lop has single thread! )

# Multiprocessing ( ProcessPool )
**GIL** (Global Interpreter Lock) means in CPython only one thread executes Python bytecode at a time. For CPU-heavy work, threads don’t really run in parallel.

Spawning multiple processes each with its own interpreter + GIL 
```python
from multiprocessing import Process, Queue
import time

def worker(x, q):
    # pretend CPU-heavy
    s = sum(i*i for i in range(10_000_000))
    q.put((x, s))

if __name__ == "__main__":
    q = Queue()
    procs = [Process(target=worker, args=(i, q)) for i in range(4)]

    for p in procs:
        p.start()
    for p in procs:
        p.join()

    while not q.empty():
        print(q.get())
```

```python
from multiprocessing import Pool
def f(x):
    return x * x

if __name__ == "__main__":
    with Pool(processes=4) as pool:
        results = pool.map(f, range(10))
    print(results)
```
**Pros**
- True parallel CPU execution
- Good for heavy numerical work, simulations, etc.
**Cons**
- Each process has its **own memory**: you need to pass data via pickle (Queues, Pipes) → overhead
- Spawning processes is expensive
- Not ideal to spin up and down inside each FastAPI request

# ThreadPoolExecutor
Used for **I/O-bound** concurrency inside a single process.
Threads:
- Share memory
- Still hit the GIL → not great for CPU-bound work
- Perfect for: waiting on network calls, disk, DB clients that don’t have async APIs

```python
from concurrent.futures import ThreadPoolExecutor, as_completed
import time

def blocking_io(x):
    time.sleep(1)  # simulate slow I/O
    return x * 2

with ThreadPoolExecutor(max_workers=5) as executor:
    futures = [executor.submit(blocking_io, i) for i in range(10)]
    for fut in as_completed(futures):
        print(fut.result())
```

# Difference (Asyncio, Multiprocessing, ThreadPoolExecutor)
- **Pure async library available** (httpx async, asyncpg, etc.)
    → use async def and await directly, no thread pool.
    
- **Only sync/blocking library exists, mostly I/O bound**
    → use run_in_threadpool / ThreadPoolExecutor.
    
- **Heavy CPU-bound work** (big matrix ops, simulations, ML inference in Python)
    → Threads don’t help much; use **multiprocessing** or an external worker service.

use async when possible, if not use multi processing,  for heavy CPU bound task where multi thread wont help much, only then we consider using multi-processing 
# Interpreter
1. Your .py code is compiled into **bytecode** → simple instructions, not machine code.
2. CPython has a **bytecode interpreter** (a loop written in C) that reads these bytecode instructions one by one.
3. As it runs, it manages objects, memory, variables, frames, etc.

# Global interpreter lock
Only **one** thread can execute Python bytecode at a time.
```python
import threading
def work():
    for i in range(10_000_000):
        i += 1

for _ in range(10):
    threading.Thread(target=work).start()
```
Why does the GIL exist?
- Python objects (lists, dicts, etc.) are not inherently thread-safe.
- Instead of adding complex locks everywhere, Python simplified things by protecting the interpreter with one big lock.

Cons: 
**parallel execution of Python code**

# Event loop
The event loop is the **core of async programming** (asyncio, FastAPI, Node.js, etc.).

The event loop:
- Runs in **one thread**
- Keeps a list of tasks (coroutines)
- Runs a task until it hits an await on something that will take time (I/O)
- Pauses that task and switches to another one
- When the result is ready, resumes the task from where it stopped

A coroutine pauses voluntarily → the loop gives control to another coroutine.