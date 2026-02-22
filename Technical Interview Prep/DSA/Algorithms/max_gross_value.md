2025-08-04 11:34

Link:

Problem: 
You are given a 0-based integer array `arr` of length `n`. Logically, think of its elements as `a₁, a₂, …, aₙ` (1-based). You want to choose three “cut” indices

`1 ≤ i₁ ≤ i₂ ≤ i₃ ≤ n+1`
to partition the array into four contiguous, half-open segments
`[1, i₁),  [i₁, i₂),  [i₂, i₃),  [i₃, n+1)`

and maximize the **grossValue** defined by
`grossValue(i₁,i₂,i₃)   = sum(a₁ … a_{i₁−1})     − sum(a_{i₁} … a_{i₂−1})     + sum(a_{i₂} … a_{i₃−1})     − sum(a_{i₃} … aₙ).`

**Input:**
- An integer array `arr` of length `n` (0 ≤ n ≤ some limit, e.g. 10^5), elements may be positive or negative.

**Output:**
- A single integer: the maximum possible value of  
    `sum[1,i₁) − sum[i₁,i₂) + sum[i₂,i₃) − sum[i₃,n+1)`  
    over all valid choices of `1 ≤ i₁ ≤ i₂ ≤ i₃ ≤ n+1`.

Intuition:
![[Screenshot 2025-08-04 at 11.34.43 AM.png]]

Solution:
```python
def max_gross_value(arr: list[int]) -> int:
    n = len(arr)
    # 1) Build 1-based prefix sums of length n+2
    prefix = [0] * (n + 2)
    for i in range(1, n+1):
        prefix[i] = prefix[i-1] + arr[i-1]
    prefix[n+1] = prefix[n]

    # 2) best_left[i] = max over i1≤i of ( prefix[i1] - (prefix[i] - prefix[i1]) )
    best_left = [float('-inf')] * (n + 2)
    cur_max = float('-inf')
    for i in range(1, n+2):
        # at cut = i, consider i1 = i:
        val = prefix[i] - (prefix[i] - prefix[i])
        # but really we need to consider every i1 up to i; maintain running best:
        # Equivalent to: cur_max = max(cur_max, prefix[i] + prefix[i] )
        # Correction: expand expression:
        #   prefix[i1] - (prefix[i]-prefix[i1]) = 2*prefix[i1] - prefix[i]
        # so cur_max tracks max(2*prefix[i1]) for i1≤i
        candidate = 2 * prefix[i]  # this is 2*prefix[i1] when i1=i
        if candidate > cur_max:
            cur_max = candidate
        # now best_left[i] = cur_max - prefix[i]
        best_left[i] = cur_max - prefix[i]

    # 3) best_right[i] = max over i3≥i of ((prefix[i3]-prefix[i]) - (prefix[n+1]-prefix[i3]))
    best_right = [float('-inf')] * (n + 2)
    cur_max = float('-inf')
    total = prefix[n+1]
    for i in range(n+1, 0, -1):
        # expression: (prefix[i3] - prefix[i]) - (total - prefix[i3])
        #            = 2*prefix[i3] - total - prefix[i]
        candidate = 2 * prefix[i]  # treating i3 = i
        if candidate > cur_max:
            cur_max = candidate
        best_right[i] = cur_max - total - prefix[i]

    # 4) Combine
    result = float('-inf')
    for i in range(1, n+2):
        result = max(result, best_left[i] + best_right[i])
    return result
```

Tags: 

RL: 

Considerations:
