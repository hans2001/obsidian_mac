### **Stack**
- Automatic storage
- Scope-based lifetime
- Very fast
- Size limited
- Example:
```cpp
  void f() {
    int x;
    Fireball f(10);
	}
  ```
Destroyed automatically when scope ends.
### **Heap (dynamic storage)**
- Manual lifetime (new / delete)
- Slower than stack
- Flexible size
- Needed when lifetime must outlive scope
```cpp
Fireball* f = new Fireball(10);
```
### **Static storage**
- Exists for entire program runtime
- Initialized once
- Shared
```cpp
static int counter;
SpellJournal::journal;
```

## **One-sentence rules to memorize**
- new → heap (dynamic storage)
- Data members live **with the object**
- static members live **for the entire program**
- Stack vs heap is about **lifetime**, not type
- Pointers don’t imply heap allocation


**Storage is chosen by how an object is created, not by what the object is.**