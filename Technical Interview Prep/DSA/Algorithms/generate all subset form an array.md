2025-08-06 12:29

Link:

Problem: 
as above

Constraints:

Intuition:
iterative: 
```python
def all_subarrays(arr):
    """Return a list of all subarrays of arr."""
    n = len(arr)
    result = []
    for i in range(n):
        # You can build progressively to avoid slicing each time:
        current = []
        for j in range(i, n):
            current.append(arr[j])
            result.append(current.copy())
    return result

# Example
A = [1, 2, 3]
print(all_subarrays(A))
# Output: [[1], [1,2], [1,2,3], [2], [2,3], [3]]
```

recursive: 
```python
def all_subarrays(arr):
    """
    Return a list of all contiguous subarrays of `arr` using recursion.
    """
    n = len(arr)
    result = []
    def recurse(i, j):
        # Base case: start index past end
        if i >= n:
            return
        
        # If end index past end, move start forward
        if j >= n:
            recurse(i + 1, i + 1)
        else:
            # Capture the current subarray arr[i..j]
            result.append(arr[i:j+1])
            # Extend end index
            recurse(i, j + 1)
    
    recurse(0, 0)
    return result
# Example
A = [1, 2, 3]
print(all_subarrays(A))
# → [[1], [1, 2], [1, 2, 3], [2], [2, 3], [3]]

```
Solution:

Tags: 

RL: 

Considerations:
