## try_emplace
auto [it, inserted] = m.try_emplace(key, args...);
**Meaning:**
- If key **does not exist**
    → construct the value **in-place** using args..., insert it
    → inserted == true
    
- If key **already exists**
    → do **nothing** (no construction, no overwrite)
    → inserted == false
    
- it always points to the element for key
That’s it. No side effects. No surprises.

Example
```c++
auto [it, inserted] = m.try_emplace(key, 0);
```
if key not in map, set default as 0, otherwise the function on right is not ran, and inserted will be false, and returned the existing iterator pointing to the node in tree!

## lvalue vs rvalue

**lvalue = has identity**
An expression that **refers to an identifiable object** (has identity).
- You can usually **take its address**
- It **can appear on the left side** of =

**rvalue = temporary / no identity**
An expression that is a **temporary value** with **no persistent identity**.
- Usually **can’t take its address**
- Typically **appears on the right side** of =

lvalue vs rvalue
```cpp
int x = 5;
int y = x;
```
 - x is an **lvalue**
- 5 is an **rvalue**

pass by ref
```cpp
void foo(int& a) {
    a = 100;
}

int x = 5;
foo(x);
```
- x is an **lvalue**
- int& a is a **reference bound to x**
- You are **NOT returning anything**
- You are **aliasing the same object**
