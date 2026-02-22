### **Stack**
- **What it stores:**
    - Function calls (activation records / stack frames).
    - Local variables inside functions. (var without the new keyword) (automatically managed)
    - Return addresses (where to continue execution after a function finishes).
    - Parameters passed to functions.

- **Structure:**
    - Organized as **LIFO (Last In, First Out)**.
    - Memory is allocated and freed automatically as functions are called and return.
        
- **Characteristics:**
    - Fast access (because it’s just moving the stack pointer up and down).
    - Limited size (can cause a _stack overflow_ if you recurse too deeply or allocate huge local arrays).
    - Variables exist only while the function is active (automatic lifetime).
### **Heap**
- **What it stores:**
    - Dynamically allocated objects (via `malloc`/`new` in C/C++ or just by creating objects in Python). (get back a pointer to the memory)
    - Objects that need to persist beyond the lifetime of a function call.

- **Structure:**
    - Unorganized free memory area managed by the runtime/OS.
    - Memory must be explicitly allocated and freed (in Python, the **garbage collector** manages it).

- **Characteristics:**
    - More flexible: objects can outlive the function that created them.
    - Slower access compared to stack.
    - If not managed properly, can lead to **memory leaks** or **fragmentation**.

# Shallow Copy
**default copy constructor** just copies member values
If your class has a pointer, only the pointer value (address) is copied — both objects point to the same heap memory.
```cpp
class MyClass {
public:
    int* data;
    MyClass(int val) {
        data = new int(val);
    }
    // Default copy constructor = shallow copy
};

MyClass a(10);
MyClass b = a;  // shallow copy: b.data points to same int as a.data
*b.data = 20;
std::cout << *a.data; // prints 20, both share the same int
```
new object, but pointers inside new object still ref the same heap instance
### **Deep Copy**
- You explicitly define a **copy constructor** (and usually an assignment operator) that allocates new memory and copies the content, not just the pointer.
- Example:
- ```cpp
  class MyClass {
public:
    int* data;
    MyClass(int val) {
        data = new int(val);
    }
    // Deep copy constructor
    MyClass(const MyClass& other) {
        data = new int(*other.data);  // allocate new int, copy value
    }
    ~MyClass() {
        delete data;
    }
};

MyClass a(10);
MyClass b = a;  
*b.data = 20;
std::cout << *a.data; // prints 10, independent copy
  ```
new object **and** new heap allocations, so instances are independent.

# Smart pointer
In C++, a **raw pointer** (`T*`) gives you control but also responsibility:
```cpp
MyClass* obj = new MyClass(); 
// ... use obj delete obj;   
// you MUST remember to do this, or memory leaks
```
Smart pointers wrap raw pointers into objects that automatically manage the memory. They live in `<memory>`.

### 1. **`std::unique_ptr`**
- Represents **exclusive ownership** of an object.
- Only one `unique_ptr` can own a resource at a time.
- Automatically deletes the object when it goes out of scope.
```cpp
include <memory>
void foo() {
     std::unique_ptr<MyClass> p = std::make_unique<MyClass>(10);     
     // No delete needed — automatically freed when foo() ends 
}
```
- Can’t be copied, only **moved** (more on that later).
### 2. **`std::shared_ptr`**
- Represents **shared ownership**.
- Keeps a **reference count** of how many `shared_ptr`s point to the same object.
- The object is deleted only when the last `shared_ptr` is destroyed.
```cpp
```#include <memory> void foo() {
     std::shared_ptr<MyClass> p1 = std::make_shared<MyClass>(10);    
     std::shared_ptr<MyClass> p2 = p1;  // both share ownership     
     // deleted only after p1 and p2 go out of scope 
}
```
### 3. **`std::weak_ptr`**
- A **non-owning reference** to an object managed by a `shared_ptr`.
- Used to **avoid circular references** (which can cause memory leaks with `shared_ptr`).
```cpp
```#include <memory> struct Node {     
	std::shared_ptr<Node> next;
     std::weak_ptr<Node> prev;  // avoids circular reference
};
```
### **Summary:**
- `unique_ptr` = single owner (like “move-only” object).
- `shared_ptr` = multiple owners (reference counting).
- `weak_ptr` = observer (no ownership, avoids cycles).

# Move Constructor & Move Assignment
- Introduced in C++11.
- Allow resources (like heap memory, file handles, sockets) to be **moved instead of copied**.
- Uses `&&` (rvalue reference).
```cpp
class MyClass {     int* data; public:     MyClass(int val) { data = new int(val); }          // Move constructor     MyClass(MyClass&& other) noexcept {         data = other.data;     // steal pointer         other.data = nullptr;  // leave other empty     }          ~MyClass() { delete data; } };
```
Usage:
`MyClass a(10); MyClass b = std::move(a);  // ownership moved from a → b`
- After `std::move`, `a` is left in a “valid but empty” state.
### Why Move Semantics Matter
- **Efficiency**: No deep copies when unnecessary.
- Used heavily in **return value optimization (RVO)**:
    `std::vector<int> makeVector() {     std::vector<int> v = {1,2,3,4};     return v;   // moved, not copied }`
- Critical for `std::unique_ptr` (can’t be copied, but can be moved).

✅ **Summary of Move Semantics:**
- “Don’t copy resources, just steal them.”
- Implemented via **move constructor** and **move assignment operator**.
- Works hand-in-hand with smart pointers (`unique_ptr` especially).
