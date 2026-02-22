## 1. Counting Paths/Ways

### Core Idea
Count the number of distinct ways to reach a goal by accumulating paths from subproblems.

### Key Characteristics
- Base case returns 1 for valid path, 0 for invalid
- Combine subproblems by **adding** (not OR/MAX)
- Often involves exploring multiple choices at each step

### Template
```python
def count_ways(state):
    # Base cases
    if reached_goal(state):
        return 1  # Found one valid way
    if invalid(state):
        return 0  # This path doesn't work
    
    if dp[state] != -1:
        return dp[state]
    
    ways = 0
    for next_choice in get_choices(state):
        ways += count_ways(next_state)
    
    dp[state] = ways
    return ways
```

### Related Problems
- **[[Decode Ways]]** (LC 91): Count ways to decode numeric string
- **[[Unique Path]]** (LC 62): Count paths in grid
- **[[Climbing Stairs]]** (LC 70): Count ways to reach top
- **Unique Paths II** (LC 63): Count paths with obstacles
- **Target Sum** (LC 494): Count ways to assign +/- to reach target

---

## 2. Palindrome Expansion

### Core Idea
Treat each position as potential palindrome center and expand outward while valid.

### Key Characteristics
- Check both odd-length (single center) and even-length (between chars) palindromes
- Expand while `s[left] == s[right]`
- Track desired metric during expansion (count, length, etc.)

### Template
```python
def palindrome_expansion(s):
    def expand_around_center(left, right):
        while left >= 0 and right < len(s) and s[left] == s[right]:
            # Process current palindrome
            update_result()
            left -= 1
            right += 1
        return result
    
    for i in range(len(s)):
        # Odd length palindromes (single center)
        expand_around_center(i, i)
        # Even length palindromes (between chars)
        expand_around_center(i, i + 1)
```

### Related Problems
- **[[Palindromic Substrings]]** (LC 647): Count all palindromic substrings
- **[[Longest Palindromic Substring]]** (LC 5): Find longest palindrome
- **[[Palindrome Partitioning]]** (LC 131): All ways to partition into palindromes
- **Palindrome Partitioning II** (LC 132): Min cuts for palindrome partitioning

---

## 3. Kadane's Algorithm (Contiguous Subarray)

### Core Idea
At each position, decide whether to extend current subarray or start fresh. Track local optimum (best ending here) and global optimum (best overall).

### Key Characteristics
- **Local decision**: Include current element with previous OR start new
- **Greedy choice**: Keep extending if beneficial, restart if not
- Works for sum, product, and other accumulative operations

### Template
```python
def kadane(nums):
    local_best = nums[0]   # Best subarray ending at current position
    global_best = nums[0]  # Best subarray found so far
    
    for i in range(1, len(nums)):
        # Extend previous subarray or start new
        local_best = max(nums[i], local_best + nums[i])
        global_best = max(global_best, local_best)
    
    return global_best
```

### Variations
```python
# For product (need to track min due to negatives)
def max_product(nums):
    max_so_far = min_so_far = global_max = nums[0]
    
    for i in range(1, len(nums)):
        temp = max_so_far
        max_so_far = max(nums[i], max_so_far * nums[i], min_so_far * nums[i])
        min_so_far = min(nums[i], temp * nums[i], min_so_far * nums[i])
        global_max = max(global_max, max_so_far)
    
    return global_max
```

### Related Problems
- **[[Maximum Subarray]]** (LC 53): Max sum subarray
- **[[Maximum Product Subarray]]** (LC 152): Max product subarray
- **[[Maximum Sum Circular Subarray]]** (LC 918): Kadane with wrap-around
- **[[Best Time to Buy and Sell Stock]]** (LC 121): Kadane on differences
- **Maximum Subarray Sum with One Deletion** (LC 1186): Modified Kadane

---

## 4. 0/1 Knapsack

### Core Idea
For each item, make binary choice (take/skip). Build up solutions for larger capacities using smaller ones.

### Key Characteristics
- Each item used at most once
- Limited capacity/constraint
- State: `dp[i][capacity]` = best result using first i items with given capacity

### Template
```python
# Top-down
def knapsack_topdown(items, capacity):
    @cache
    def dfs(i, remaining_capacity):
        if i >= len(items) or remaining_capacity <= 0:
            return 0
        
        # Skip current item
        skip = dfs(i + 1, remaining_capacity)
        
        # Take current item (if possible)
        take = 0
        if items[i].weight <= remaining_capacity:
            take = items[i].value + dfs(i + 1, remaining_capacity - items[i].weight)
        
        return max(skip, take)
    
    return dfs(0, capacity)

# Bottom-up
def knapsack_bottomup(items, capacity):
    n = len(items)
    dp = [[0] * (capacity + 1) for _ in range(n + 1)]
    
    for i in range(1, n + 1):
        for w in range(capacity + 1):
            # Skip item
            dp[i][w] = dp[i-1][w]
            
            # Take item if possible
            if items[i-1].weight <= w:
                dp[i][w] = max(dp[i][w], 
                              dp[i-1][w - items[i-1].weight] + items[i-1].value)
    
    return dp[n][capacity]
```

### Related Problems
- **[[Partition Equal Subset Sum]]** (LC 416): Can we make sum/2?
- **[[Target Sum]]** (LC 494): Assign +/- to reach target
- **[[Last Stone Weight II]]** (LC 1049): Minimize final stone weight
- **[[Ones and Zeroes]]** (LC 474): Knapsack with 2D constraint

---

## 5. Unbounded Knapsack

### Core Idea
Like 0/1 knapsack but items can be used unlimited times.

### Key Difference from 0/1
When taking item, stay at same index: `dfs(i, capacity - weight)` not `dfs(i + 1, ...)`

### Template
```python
def unbounded_knapsack(items, capacity):
    @cache
    def dfs(i, remaining):
        if i >= len(items) or remaining <= 0:
            return 0
        
        # Skip current item
        skip = dfs(i + 1, remaining)
        
        # Take current item (can reuse)
        take = 0
        if items[i].weight <= remaining:
            take = items[i].value + dfs(i, remaining - items[i].weight)  # Stay at i
        return max(skip, take)
```

### Related Problems
- **[[Coin Change]]** (LC 322): Min coins to make amount
- **Coin Change II** (LC 518): Count ways to make amount
- [[377. Combination Sum IV]] Count ways to make amount
- **[[Perfect Squares]]** (LC 279): Min squares to sum to n
- **[[Integer Break]]** (LC 343): Max product of breaking integer
- [[Best Time to Buy and Sell Stock With Cooldown]]

---

## 6. Interval DP

### Core Idea
Solve for intervals by trying all possible split points. Build larger intervals from smaller ones.

### Key Characteristics
- State: `dp[i][j]` = result for interval from i to j
- Try all split points k where i < k < j
- Often used for optimization problems on sequences

### Template
```python
def interval_dp(arr):
    n = len(arr)
    dp = [[0] * n for _ in range(n)]
    
    # Base case: single elements
    for i in range(n):
        dp[i][i] = base_value
    
    # Try all interval lengths
    for length in range(2, n + 1):
        for i in range(n - length + 1):
            j = i + length - 1
            
            # Try all split points
            for k in range(i, j):
                cost = dp[i][k] + dp[k+1][j] + merge_cost(i, k, j)
                dp[i][j] = min(dp[i][j], cost)
    
    return dp[0][n-1]
```

### Related Problems
- **Burst Balloons** (LC 312): Max coins from bursting
- **Minimum Cost to Merge Stones** (LC 1000): Merge k stones
- **Unique Binary Search Trees** (LC 96): Count BSTs
- **Matrix Chain Multiplication**: Classic interval DP

---

## 7. State Machine DP

### Core Idea
Model problem as transitions between states. Each state represents a different condition/mode.

### Key Characteristics
- Define clear states (e.g., holding stock, not holding stock)
- Define valid transitions between states
- Track best result for each state

### Template
```python
def state_machine_dp(prices):
    # States: bought, sold, cooldown
    n = len(prices)
    if n == 0:
        return 0
    
    # State definitions
    hold = -prices[0]     # Holding stock
    sold = 0              # Just sold
    rest = 0              # Cooldown/ready to buy
    
    for i in range(1, n):
        prev_hold = hold
        prev_sold = sold
        prev_rest = rest
        
        hold = max(prev_hold, prev_rest - prices[i])  # Buy or keep holding
        sold = prev_hold + prices[i]                  # Sell
        rest = max(prev_rest, prev_sold)              # Rest or finish cooldown
    
    return max(sold, rest)
```

### Related Problems
- **Best Time to Buy and Sell Stock with Cooldown** (LC 309): With cooldown period
- **Best Time to Buy and Sell Stock with Transaction Fee** (LC 714): With fee
- **Best Time to Buy and Sell Stock III** (LC 123): At most 2 transactions
- **Best Time to Buy and Sell Stock IV** (LC 188): At most k transactions

---
## 8. Two-Sequence Comparison

### Core Idea
Compare two sequences element-by-element to find optimal alignment, 
matching, or transformation.

### Key Characteristics
- State: `dp[i][j]` represents result for first i elements of seq1 
  and first j elements of seq2
- Decision based on whether elements match
- Often involves choosing which sequence to advance

### Template
```python
def two_string_dp(text1, text2):
    @cache
    def dfs(i, j):
        # Base case: reached end of either string
        if i >= len(text1) or j >= len(text2):
            return 0
        
        if text1[i] == text2[j]:
            # Characters match - take both
            return 1 + dfs(i + 1, j + 1)
        else:
            # No match - skip one character
            skip_first = dfs(i + 1, j)
            skip_second = dfs(i, j + 1)
            return max(skip_first, skip_second)
    
    return dfs(0, 0)
```

### Related Problems
- **Longest Common Subsequence** (LC 1143): Find LCS length
- **Edit Distance** (LC 72): Min operations to transform
- **Distinct Subsequences** (LC 115): Count subsequences
- **Interleaving String** (LC 97): Can two strings interleave
- **Regular Expression Matching** (LC 10): Pattern matching

---
# Partition

This technique of reducing a complex problem to subset sum/partition is common in DP problems. The pattern to recognize:

1. When you can express the final answer as a sum with +/- signs
2. When order of operations doesn't affect the final mathematical result
3. When you're minimizing difference between groups

## General DP Tips

1. **State Definition**: What information uniquely identifies a subproblem?
2. **Recurrence Relation**: How do subproblems relate?
use decision tree to see how subproblems were reused
3. **Base Cases**: What are the simplest subproblems?
4. **Memoization**: Cache results to avoid re-computation
5. **Order of Computation**: Ensure dependencies are computed first
6. **Decision Tree**: model the problem as a decision tree

### When to Use DP

- Optimal substructure (optimal solution contains optimal sub_solutions)
- Overlapping subproblems (same subproblems solved multiple times)
- Usually involves making choices that affect future choices