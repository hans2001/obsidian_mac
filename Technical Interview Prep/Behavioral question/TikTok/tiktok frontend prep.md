# Closure
A **closure** happens when:
- You define a function **inside another function**, and
- The inner function **accesses variables** from the outer function’s scope.
    
Even after the outer function returns, the inner function **still has access** to those variables.
```js
function makeCounter() {
  let count = 0; // variable in outer scope

  return function() { // inner function (closure)
    count++;
    return count;
  };
}

const counter = makeCounter();

console.log(counter()); // 1
console.log(counter()); // 2
console.log(counter()); // 3
```
- `makeCounter()` creates a variable `count` and returns a function.
- That returned function still “remembers” `count` from `makeCounter()` — even though `makeCounter()` has already finished running.
- That’s a **closure**.

question 
```js
function fun(n,o){
  console.log(o);
  return {
    fun: function(m){
      return fun(m,n);
    }
  };
}

var a = fun(0);  // ?
a.fun(1);        // ?        
a.fun(2);        // ?
a.fun(3);        // ?

var b = fun(0).fun(1).fun(2).fun(3);  // ?

var c = fun(0).fun(1);  // ?
c.fun(2);        // ?
c.fun(3); 
```

```js
function createUser(name) {
  let score = 0;

  return {
    getName: () => name,
    increaseScore: () => score++,
    getScore: () => score
  };
}

const user = createUser("Alice");
user.increaseScore();
console.log(user.getScore()); // 1
console.log(user.score); // undefined (private)
```
score is encapsulated to the createUser scope, but the returned function has memory of it

# Throttle
**Throttle** ensures that a function executes **at most once** every `delay` (or `wait`) milliseconds — even if it’s called many times.

👉 It’s useful for performance-heavy events like:
- `scroll`
- `resize`
- `mousemove`
- `click` (in rapid succession)

```js
function throttle(fn, delay) {
  var preTime = Date.now(); // record the previous time

  return function() {
    var context = this,
      args = [...arguments],
      nowTime = Date.now();

    // If enough time has passed since last execution
    if (nowTime - preTime >= delay) {
      preTime = Date.now();      // update last execution time
      return fn.apply(context, args);  // execute function
    }
  };
}
```
- Executes **immediately** the first time.
- Ignores all subsequent calls within the delay window.
### ⚡ Characteristics:
- Executes **immediately** the first time.
- Ignores all subsequent calls within the delay window.

Timer based throttle
```js
function throttle(fun, wait){
  let timeout = null;

  return function(){
    let context = this;
    let args = [...arguments];

    if(!timeout){ // if not already waiting
      timeout = setTimeout(() => {
        fun.apply(context, args);
        timeout = null; // reset timer after execution
      }, wait);
    }
  };
}
```

## 🧩 What is **Debounce**?
**Debounce** ensures that a function runs **only after a certain period of inactivity** — i.e. it _waits_ until the user stops triggering the event.

Every time the event fires, it resets the timer.  
Only when no new events happen for `delay` milliseconds does it actually run the function.

```js
function debounce(fn, delay) {
  let timer = null;

  return function() {
    let context = this;
    let args = [...arguments];

    clearTimeout(timer); // clear previous timer
    timer = setTimeout(() => {
      fn.apply(context, args); // run after delay
    }, delay);
  };
}
```
1. When the function is called, it **clears any existing timer**.
2. Then it sets a **new timer**.
3. If the function is called again before the timer finishes, the timer resets.
4. The wrapped function `fn` executes **only when the user stops calling** it for `delay` milliseconds.

```js
const search = debounce((text) => {
  console.log('Searching for:', text);
}, 500);
```

# Promise.all
```js
const p0 = Promise.resolve(3);

const p1 = 42;

const p2 = new Promise((resolve, reject) => {
  setTimeout(() => {
    resolve('foo');
  }, 100);
});

const p3 = new Promise((resolve,reject) => {
setTimeout (() => {
	resolve('2')
	},200)
})

try{ 
await promiseAll([p0, p1, p2]); // [3, 42, 'foo']
} catch (err) {
console.log(err)
}
```
