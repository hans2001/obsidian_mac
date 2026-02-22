### Q: Did you use a linked list?
**A:** Yes. Each sparse row is a **sorted linked list of `Entry(col, val)`** (`RowList`). This gives:
- **O(1)** inserts/removes once the position is found, plus stable iterators.
- Easy **two-pointer merges** for sparse+sparse addition.
- We also track **`nnzRow[i]`** to skip empty rows in O(1).
    
_(If asked “why not CSR arrays?”)_: Linked lists kept implementation simple for mutation (insert/update/remove) and test coverage. CSR arrays are faster for pure SpMV, but resizing and mid-row inserts are more complex. Our lists + `nnzRow`gave good enough performance with cleaner code.

---
### Q: How do you traverse?
**A:**
- **Sparse rows:** via `RowList.iterateRow(i)`—an iterator over sorted `(col, value)` entries.
- **Sparse+Sparse add:** **two-pointer merge** over the two row iterators, summing equal columns and preserving order.
- **Multiplication (scaled-row accumulation):**
    - For each nonzero A[i,k]A[i,k], we traverse **row k of B** (sparse) or **row k of B** (dense) and do `C[i, col] += A[i,k] * B[k, col]`.
- We **skip**:
    - Empty rows using `nnzRow[i]`.
    - Dense factors that are exactly 0 (and ≤ EPS where applicable) to avoid wasted work.

---
### Q: How did you optimize sparse multiplication?
**A:**
- **Strategy dispatch:** pick Dense×Sparse vs Sparse×Dense vs Sparse×Sparse to minimize work.
- **Work only on nonzeros:** Iterate **only** `A[i,k] ≠ 0` and the nonzeros of `B[k,*]`.
- **Scaled-row accumulation:** compute `C[i,*]` by accumulating scaled rows, which is cache-friendlier than naive i-j-k loops.
- identify sparse input in dense matrix addition and multiplication
- **Early-return:**
    - Skip rows with `nnzRow==0`.
    - Skip multiplications where the dense factor is 0 (or ≤ EPS).
        
- **EPS pruning:** after updates, drop results with |value| ≤ EPS to prevent fill-in noise and keep the structure sparse.

# Additional

- **Identity laws:** `I * S = S` and `S * I = S`; tests verify both `premul` and `postmul`.
    
- **Addition mixed path:** For Sparse+Dense, we **copy dense** then add sparse deltas; for Dense+Sparse we **flip** to keep sparse on the left in the strategy.
    
- **Zero handling:** `set(..., 0)` **unlinks** the entry; values at exactly ±EPS are treated as 0 (boundary test in suite).
    
- **Failure modes guarded:** bounds checks, size mismatches, null operands, and duplicate `(row,col)` inserts all throw.

### 2. **How do you ensure efficiency despite using linked lists?**

> “We keep each row sorted by column and track `nnzRow[i]`, the count of nonzeros per row. That lets us skip empty rows and avoid iterating over zeros. For most operations, we only touch existing entries.”

### 3. **How does your EPS threshold work in multiplication or addition?**

> “After each update, if the absolute value ≤ EPS, we treat it as zero and unlink it. That keeps matrices clean and sparse and prevents floating-point noise from growing.”

### 4. **How do you handle type dispatch?**

> “All operations go through static dispatcher classes (`Adds`, `MulChooser`). They detect operand types — if one side is sparse, they flip arguments so the sparse matrix is always first and then call the proper strategy.”

### 5. **How does multiplication differ from the naive triple loop?**

> “We don’t loop over all i,j,k. Instead, we only visit existing nonzeros — each nonzero `A[i,k]` scales row `k` of `B`. This avoids multiplying by zeros and keeps complexity proportional to the number of nonzeros, not n³.”

### 6. **How do you traverse or merge sparse rows?**

> “We use two-pointer merging on sorted linked lists for addition. Each row list is sorted by column, so merging and zero-dropping are straightforward and efficient.”