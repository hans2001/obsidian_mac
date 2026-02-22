[[codewalk high level overview]]
[[codewalk questions]]
## **Adds**
### `add(left, right)` (static)
- **Logic:**
    - Checks whether either operand (`left` or `right`) is a `SparseMatrix`.
    - If so, calls the appropriate `AddStrategy`, ensuring the _sparse operand_ is passed as the **left** argument.
    - If neither operand is sparse, routes to the **dense–dense** path implemented in `ArrayMatrix.addDenseDense()`.
        
- **Why flip?**  
    `AddStrategy` always assumes the **left** operand is sparse.  
    So if only the **right** matrix is sparse, we swap arguments — `(right, left)` — to preserve this invariant. ✅
    
## **AddStrategy**

- Always assumes **`self` is a `SparseMatrix`**.
- Determines the correct addition routine based on the _type_ of the other operand:
    - If the other is also `SparseMatrix` → use **AddSparseSparse**.
    - If the other is `ArrayMatrix` (dense) → use **AddSparseDense**.
    - Otherwise → throw an `IllegalArgumentException` (unsupported matrix type).
---
## **AddSparseDense**

- Performs **sparse + dense addition** as follows:
    1. Create an output matrix by **deep-copying** the dense operand.
    2. Iterate through **nonzero entries** in the sparse matrix.
    3. Add each sparse value into the corresponding position of the output.
    4. Optionally drop near-zero results based on `EPS` (for sparsity cleanup).
        
## **AddSparseSparse**

- Implements **sparse + sparse addition** via a **two-pointer merge**:
    - Each row in a sparse matrix is a sorted linked list of entries (by column).
    - Traverse both row lists simultaneously.
    - Merge like a “sorted list addition,” summing overlapping columns and omitting near-zero results.
        
# **Interface Layer**
## SquareMatrix

- Abstract base interface for all matrix types.
- Defines common public methods such as:
    - `add`, `premul`, `postmul`, `set`, `get`, `size`, `setIdentity`, etc.
- Serves as a unifying API for both dense and sparse implementations.
---
# ⚗️ **Multiplication Choosers**

Utility classes that select the correct multiplication algorithm.

## **Compressed Sparse Row (CSR)**

- Storage format for sparse matrices:
    - Keeps only **nonzero entries**.
    - Stores indexing info to know where each row starts and ends.
- Saves memory and speeds up sparse operations by avoiding full `n×n` arrays.

# **Multiplication**
## MulDenseSparsePremul

- Used for **dense × sparse** multiplication (`A * B`) where `A` is dense, `B` is sparse.
- **Outer loop:** iterates rows of the dense matrix (`A`).
- **Inner loop:** uses `denseColSparseRow(k)` to access column `k` of `A` and row `k` of `B`.
- For each pair `(i, k)`:
    - Multiply `A[i,k] * B[k, *]` (only where both are nonzero).
    - Add scaled row contributions to result:
        `C[i,*] += A[i,k] * B[k,*]`
- This builds each row of `C` via **scaled-row accumulations** rather than element-wise dot products.
---
## MulSparseDensePostmul

- Used for **sparse × dense** multiplication (`A * B`) where `A` is sparse, `B` is dense.
- **Outer loop:** iterates over each sparse row of `A`.
- For each nonzero `A[i,k]`:
    - Multiply by column values in `B` (`B[k, col]`).
    - Accumulate into `C[i,col]` only when both factors are nonzero.
        
- Conceptually also follows:
    `C[i,*] += A[i,k] * B[k,*]`
    (scaled-row accumulation pattern)
    
## MulSparseSparsePremul

- Handles **sparse × sparse** multiplication for `A * B`.
- Similar scaled-row accumulation logic as `MulDenseSparsePremul`,  
    but traverses **only nonzero entries** from both operands to minimize computation.
    
## MulSparseSparsePostmul

- Performs **sparse × sparse** post-multiplication by reusing the pre-multiplication algorithm:
    - Simply calls the **premul version** with **flipped parameters**.

# 🔗 **Linked List Structures**

## **RowList / ColList / Entry**

These underpin the sparse matrix’s internal structure.
- **`linkIntoRow(Entry e)`**  
    Inserts a new entry into the linked list for its row, maintaining column sort order.  
    Throws if an entry with the same `(row, col)` already exists.
    
- **`unlink(Entry e)`**  
    Removes an entry from its linked list (used when values drop to zero).
    
- **`iterateRow(int row)`**  
    Returns an iterator for traversing all nonzero entries in a specific row.  
    Implemented as an **anonymous inner iterator** with custom `hasNext()` and `next()` methods.
    
## **Iterator (Row traversal)**

- Standard iterator pattern over linked lists:
    - `ret` references the **current node**.
    - The global `cur` pointer advances to the **next node**.
    - Returns the current entry reference on each call to `next()`.
- Allows safe traversal without exposing the internal linked-list structure.

# Tests 
## **MatrixTests**
Tests dense→dense, sparse→sparse, and mixed operation paths.

- **`testAdd`** – Verifies correct element-wise addition on a small 4×4 dense matrix.
    
- **`testLargeIdentities`** – Checks addition and multiplication behavior on large identity matrices (dense) for correctness and performance.
    
- **`testLargeSparseMatrixOperations`** – Validates `add`, `premul`, and `postmul` on two large sparse matrices to ensure correctness and no timeouts.
    
- **`testLargeMixedMatrixOperations`** – Tests all operations between a large dense (`ArrayMatrix`) and a large sparse (`SparseMatrix`) matrix for proper interoperability.
    
---
## **MatrixTestUtils (static utility class)**
Provides reusable helpers for matrix tests.

- **`assertMatrixEquals`** – Deeply compares two matrices element-by-element within a tolerance (epsilon).
    
- **`denseFrom`** – Builds a dense `ArrayMatrix` from a 2D float array.
    
- **`denseNaiveMul`** – Computes a dense reference multiplication (`left × right`) using the naive triple-loop algorithm.
    
- **`toSparse`** – Builds a `SparseMatrix` from a 2D array, inserting only nonzero entries.
    
---
## **DummyMatrix**
A stub implementation of `SquareMatrix` used to test that unsupported matrix types are correctly rejected by dispatchers.

## **AdditionTest**
### **Dispatcher routing tests**

- **`densePlusSparse_swapsToSparseFirst`** – Ensures that when the left operand is dense and the right is sparse, the dispatcher correctly routes to the sparse-first addition strategy (and results are consistent).
    
- **`sparsePlusDense_directSparseFirst`** – Confirms that when the left operand is sparse, the dispatcher uses the sparse-first path directly (no swap).
    
- **`densePlusDense_fastKernel`** – Validates that dense+dense addition goes through the fast kernel path and produces correct sums.
    
- **`sparsePlusSparse_mergeRowsAndDropZeros`** – Checks sparse+sparse row merging, ensuring overlapping entries are summed and near-zero results are dropped.
    
### **EPS threshold behavior**

- **`add_usesEpsThreshold_forZeroSumOmission`** – Verifies the EPS zero-tolerance rule: small sums (|x| ≤ EPS) are omitted, larger ones retained.

### **Sparse + Dense merge mechanics**

- **`sparseDense_copyThenAdd`** – Tests merge correctness and zero-sum cancellation when adding a sparse to a dense matrix.
    
- **`addSparseDense_skipsEmptyRows`** – Ensures rows without sparse entries are skipped and unrelated dense values remain unchanged.

### **Sparse + Sparse merge mechanics**

- **`sparseSparse_allMergeCases`** – Validates all sparse row-merge scenarios: left-only, right-only, matching columns, and zero-sum cancellations.
    
- **`sparseSparse_rowExhaustionOnBothSides_minimal`** – Ensures merging continues correctly when one row’s entries are exhausted before the other’s.

# NumbersAndBasicsTest

- **`constructorAndSize`** – Checks constructors reject zero size and `size()` reports correct dimension.
    
- **`sparseMatrix_constructor_createsEmptyMatrix`** – Ensures a new `SparseMatrix` starts with all zero values.
    
- **`identityAndZero`** – Verifies `setIdentity()` correctly sets diagonal = 1 and others = 0.
    
- **`sparseZeroToleranceInsertUpdateRemove`** – Tests sparse insert/remove rules: small or zero values are dropped.
    
- **`strategyDispatchUnsupportedType`** – Confirms operations throw on unsupported matrix types (`DummyMatrix`).
    
- **`sparseZeroBoundary_exactlyEps`** – Checks EPS boundary: ±EPS counts as zero, just above EPS is kept.
    
- **`arrayMatrix_boundsChecks`** – Validates `ArrayMatrix` throws for out-of-range `get`/`set` indices.
    
- **`sparseMatrix_nullAndSizeMismatchGuards`** – Ensures sparse `premul`/`postmul` reject null or wrong-sized operands.
    
- **`arrayMatrix_setIdentity_zeroesOffDiagonals`** – Confirms `setIdentity()` also clears old off-diagonal values.

# Why Epsilon: 
Floating-point math is never exact, so we define a _numerical zero band_ (`|x| ≤ EPS`) to make computations stable and efficient.
### Definition: **IEEE 754 floating-point**
float x = 0.1f + 0.2f = `0.3000000119f`

In matrix operations — especially addition and multiplication — numbers often **cancel out**: `1.0000001f + (-1.0000000f) → 0.000000119f`

**That’s not _exactly_ zero, but it’s _numerically negligible_.**  
Without an epsilon rule, that residue would stay in your sparse matrix and falsely look like a real entry.
## Sparse matrices rely on pruning tiny entries
Sparse matrices only store _significant_ non-zero values.  

Tiny numerical noise below some threshold should be **treated as zero and removed**, or else:
- The matrix slowly fills with junk values (losing sparsity),
- Operations get slower and less memory-efficient,
- Subsequent results drift slightly due to accumulated round-off.

So a tolerance like:
`EPS = 1e-6f if (Math.abs(value) <= EPS) value = 0f;`
acts as a **cleanup rule**: “If it’s effectively zero, drop it.”