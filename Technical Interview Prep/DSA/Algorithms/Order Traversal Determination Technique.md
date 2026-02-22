### Pre-order traversal (Node → Left → Right)

Choose pre-order when:
- **You need to process the parent before its children**
- You're creating a copy/clone of a tree
- You're implementing a depth-first search
- You need to print a hierarchical structure (like a file directory)

### In-order traversal (Left → Node → Right)

Choose in-order when:
- You need elements in **sorted order** (for binary search trees)
- You want to process nodes in increasing order of their values
- You're implementing tree flattening where order matters

### Post-order traversal (Left → Right → Node)

Choose post-order when:
- **You need to process children before their parent**
- You're deleting a tree (to avoid memory leaks)
- You're calculating properties that depend on child values first
- You're solving problems that need bottom-up calculations (like height or sum)

## Main diff: 
The key is understanding the dependency relationships: if a parent's processing depends on its children, use post-order; if children depend on their parent being processed first, use pre-order; and if you need a specific ordering (especially with BSTs), use in-order.