- **Goal:** A square-matrix library with both **dense (`ArrayMatrix`)** and **sparse (`SparseMatrix`)** backends, plus **addition & multiplication** that automatically choose efficient strategies based on operand types.
    
- **Interfaces & dispatch:**
    - `SquareMatrix` is the common interface (`get`, `set`, `add`, `premul`, `postmul`, `setIdentity`, `size`). PremulStrategy / PostmulStrategy interface for multiplication classes (define the method and context)
    - Public calls delegate to **dispatchers** (e.g., `Adds.add(left,right)` and Mul choosers) that pick a strategy ( that handles routing ):
        - Dense+Dense → dense kernels.
        - Sparse+Dense or Dense+Sparse → sparse/dense mixed kernels.
        - Sparse+Sparse → sparse kernels.
	    return the correct instance to handle the computation

- **Dense Representation:**
	- implements squareMatrix
	- 2d array
	- dimension variable: size

- **Sparse representation:**
	- implements squareMatrix
	- circular doubly linked that keep track of non zero values only (stared with ta dummy node)
	- entry class to represent node in linked list (row,col,value)
    - CSR-like structure using **`RowList` linked lists** of `Entry(row, col, value)`, kept sorted by column within each row (and by row within each column).
    - We maintain **`nnzRow[i]`** counts to skip empty rows fast.( can be optimized )
        
- **Addition strategies:**
    - **Sparse+Dense:** 
	    deep copy the dense matrix to output matrix first, then walk through the non zeros in sparse linked list and add into output (drop zeros). (flipped for dense + sparse)
        
    - **Sparse+Sparse:** **two-pointer merge** 
		merge 2 sorted list for each row, we would insert node taht has a smaller col value first! if same col for both node, we aggregate values to a sum and then insert (call insertNew to work with the linkedlist in output Matrix)
        
- **Multiplication strategies (scaled-row accumulation):**
    - General form: for each nonzero A[i,k]A[i,k], accumulate C[i,∗]+=A[i,k]⋅B[k,∗]C[i,∗]+=A[i,k]⋅B[k,∗].
        
    - **Dense×Sparse (premul path):** iterate dense rows/columns; for each A[i,k]≠0A[i,k]=0, traverse **row k** of sparse BB.
        
    - **Sparse×Dense (postmul path):** iterate sparse rows; for each A[i,k]≠0A[i,k]=0, scale **row k** of dense BB.
        
    - **Sparse×Sparse:** same pattern but traverse only nonzeros on both sides; skip empty rows via `nnzRow`.
        
    - Short-circuits: skip zero dense factors, skip empty sparse rows, and prune near-zero results via EPS.

- **Utility class:**
	- MulChoosers
		- detect operand types then return corresponding multiplication instance to handle the computation 
	- Adds
		- detect operand types then return corresponding addition instance to handle the computation 
	- Numbers 
		- matrix validation and boundary checking logic

# Script
So my project is a matrix library that supports both **dense** and **sparse** square matrices, with full support for **addition** and **multiplication**.

At the top level, I have an interface called **`SquareMatrix`**, which defines the common public methods like `get`, `set`, `add`, `premul`, `postmul`, `setIdentity`, and `size`. Both `ArrayMatrix` and `SparseMatrix` implement this interface — `ArrayMatrix` stores values in a 2D array, while `SparseMatrix` uses a linked-list–based structure to store only nonzero entries.

The key idea is that all the operations go through **dispatcher classes** like `Adds` and `MulChooser`.  
These decide which strategy to use depending on the operand types — so for example, if both matrices are dense, we use the fast dense kernel; if one is sparse, we flip the arguments so the sparse matrix is always on the left and then call the appropriate sparse–dense or sparse–sparse algorithm.

In the **sparse representation**, each row is a **sorted linked list of entries**, where each node stores `(row, col, value)`.  
I also keep a parallel **`ColList`** for quick column access and an **`nnzRow[]`** array that stores how many nonzero entries each row currently has.  
That helps with skipping empty rows quickly during operations.

For **addition**, we have two main strategies:
- **Sparse + Dense:** copy the dense matrix, then iterate through the sparse nonzeros and add them in, dropping any results that fall below our epsilon tolerance.
    
- **Sparse + Sparse:** use a **two-pointer merge** per row since both rows are sorted by column index — this lets us efficiently add the rows together and skip any zeros that cancel out.
    
For **multiplication**, we follow a **scaled-row accumulation** model rather than the naive triple loop.  
For each nonzero `A[i,k]`, we add `A[i,k] * B[k,*]` into the result row `i`.  
That means we only ever touch nonzero entries, which saves a lot of unnecessary work.

- For **dense × sparse**, we iterate through dense rows and then sparse rows of `B`.
    
- For **sparse × dense**, we iterate through the sparse rows and scale rows of the dense matrix.
    
- For **sparse × sparse**, we do the same pattern but only over nonzero entries in both operands.

Throughout the library, I use a small constant **`EPS = 1e-6`** to decide what counts as effectively zero — any value whose magnitude is ≤ EPS is dropped from the sparse structure.  
That’s important because floating-point arithmetic often produces tiny rounding errors, and this keeps the matrix truly sparse and stable numerically.

I also have comprehensive **unit tests** for each part: they verify dense/dense, sparse/sparse, and mixed operations, EPS behavior, identity multiplication, bounds checks, linked-list integrity, and performance for large matrices.

So overall, the library dynamically chooses the most efficient algorithm depending on the operands, while maintaining correctness and sparsity under floating-point noise.

![[Screenshot 2025-10-21 at 4.46.29 PM.png]]