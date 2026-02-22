| Term                | Meaning                                    | Example in Lecture                         |
| ------------------- | ------------------------------------------ | ------------------------------------------ |
| **Tree**            | The entire hierarchical structure          | `FileSystemTree`                           |
| **Node (TreeNode)** | One element of the tree; may have children | `FSComponent`, `Folder`, `File`, `Symlink` |
| **Root**            | The topmost node                           | `Folder` representing `/`                  |
| **Subtree**         | A node and all its descendants             | `/teaching` folder                         |
| **Leaves**          | Nodes with no children                     | `File`, `Symlink`                          |
## Composite pattern
- The pattern lets you treat **individual elements (leaves)** and **groups (composites)** the same way.
    
- In the file system:
    - `File` and `Symlink` → **Leaf nodes**
    - `Folder` → **Composite node**
        
- They all share a **common abstract type** (`FSComponent`), so you can call the same methods on any of them (`find`, `copy`, `list`, etc.) without worrying whether it’s a file or folder.
👉 **Key takeaway:** use a common interface to unify parts and wholes.

### 4. **Delegation Through Composition**
- The top-level `FileSystemTree` doesn’t do everything itself.  
    It **delegates** to the nodes (`FSComponent` and its subclasses).  
    Each node knows how to perform its own part of the operation.
👉 This shows how **encapsulation** and **delegation** lead to cleaner, modular code.

| Concept                      | Meaning                                                                      | Example in Lecture            |
| ---------------------------- | ---------------------------------------------------------------------------- | ----------------------------- |
| **Tail recursion**           | Recursive call is the last operation; can be optimized or replaced by a loop | `find()` method in Folder     |
| **Recursive data structure** | Defined in terms of itself                                                   | Folder contains folders       |
| **Functional programming**   | Uses pure, higher-order functions like `map`, `fold`                         | Counting files, summing sizes |
| **Generic types**            | Code works for any data type, safely                                         | `TreeNode<T>`, `map`, `fold`  |
structural recursion: if data structure is intended to be recursive, make the mothods recursive as well

recursive methods might learn to stack overflow, that is why we should turn it into tail -recursive functions and change it to iteration based method! 

Generic types: 
```java
public interface TreeNode<T> {
    <R> TreeNode<R> map(Function<T, R> transform);
    T reduce(T initialValue, BiFunction<T, T, T> combiner);
}
```
- `T` represents the type of _data_ stored in the tree.
- You can have a `TreeNode<String>` (names), `TreeNode<Integer>` (numbers), etc.
- `<R>` allows `map` to _change_ the data type of the resulting tree.
but still Treenode? 

Generics make your tree **reusable and type-safe** — you can use it for _any kind of data_ (files, employees, HTML nodes, etc.) without rewriting the code.


```java
T reduce(T initialValue, BiFunction<T, T, T> combiner);
```
- `T` is a **type parameter** — a placeholder for _some data type_.

The goal of `reduce` (or _fold_) is to **combine all elements** in the tree into a _single value_.

| Position                             | What it represents | Meaning                                                               |
| ------------------------------------ | ------------------ | --------------------------------------------------------------------- |
| **First T** (return type)            | The result type    | The final, single value you get after reducing the tree               |
| **Second T** (in `initialValue`)     | The starting value | Where the reduction begins — like `0` for sum, `""` for concatenation |
| **Third T** (in `BiFunction<T,T,T>`) | The argument types | The two values that get combined each time                            |

A `BiFunction<T, T, T>` is a **function that takes two Ts and returns one T**. (the aggregatoe and the single values tor be aggregate) (and then the return type!)
```java
int sum = tree.reduce(0, (a, b) -> a + b);
```

## What if a node has multiple variables: 
**Functional Programming**
```java
class Student {
    String name;
    int grade;
}
TreeNode<Student> students = new GroupNode<>(new Student("Alice", 95));

int numPass = studentTree.reduce(
    0,
    (count, s) -> s.grade >= 60 ? count + 1 : count
);

int total = studentTree.reduce(0, (sum, s) -> sum + s.grade);
int count = studentTree.reduce(0, (c, s) -> c + 1);
double average = (double) total / count;
```

## ⚙️ 2. The Key Functional Interfaces (All Generic)

| Interface                 | Signature             | What it represents                                | Typical use                     |
| ------------------------- | --------------------- | ------------------------------------------------- | ------------------------------- |
| **`Function<T, R>`**      | `R apply(T t)`        | transforms one value into another                 | used in `map()`                 |
| **`BiFunction<T, U, R>`** | `R apply(T t, U u)`   | combines two values into one                      | used in `reduce()` / `fold()`   |
| **`Predicate<T>`**        | `boolean test(T t)`   | tests a condition                                 | used in `filter()` or `find()`  |
| **`Consumer<T>`**         | `void accept(T t)`    | performs an action (no return)                    | used in `forEach()`             |
| **`Supplier<T>`**         | `T get()`             | produces a value (no input)                       | used to create values lazily    |
| **`UnaryOperator<T>`**    | `T apply(T t)`        | a `Function` where input and output are same type | like increment, negate          |
| **`BinaryOperator<T>`**   | `T apply(T t1, T t2)` | a `BiFunction` returning same type                | often used for summing, merging |
### ✅ `BiFunction<T, T, T>` → used in `reduce`
`T reduce(T initial, BiFunction<T, T, T> combiner);`
This says:
> “Take a combiner function that merges two `T` values into one,  
> and use it to reduce the tree into a single result.”

### ✅ `Predicate<T>` → used in `filter` or `addChild`
In the lecture’s generic tree:
`TreeNode<T> addChild(Predicate<T> identifier, TreeNode<T> child);`

This says:
> “Use a predicate (a condition) to find the right parent node to attach a child to.”

Example:
`tree.addChild(data -> data.equals("root"), new LeafNode<>("child"));`

### ✅ `Consumer<T>` → could be used for traversal
You might add a method like:
`void forEach(Consumer<T> action);`

Then:
`tree.forEach(node -> System.out.println(node));`

```java
public interface TreeNode<T> {
    <R> TreeNode<R> map(Function<T, R> transform);
    T reduce(T init, BiFunction<T, T, T> combiner);
    void forEach(Consumer<T> action);
    boolean anyMatch(Predicate<T> test);
}

tree.forEach(System.out::println);
int sum = tree.reduce(0, Integer::sum);
boolean hasLarge = tree.anyMatch(x -> x > 100);
```


