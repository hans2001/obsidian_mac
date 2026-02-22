package mat;

/**
 * Defines a multiplication strategy where the result is computed as
 * {@code result = left × right}.
 *
 * <p>Implementations differ based on operand sparsity (dense vs. sparse)
 * and may iterate over rows or columns to exploit sparsity.
 */
interface PostmulStrategy {
  /**
   * Computes the matrix product {@code result = left × right}.
   *
   * @param left  the left operand (usually sparse)
   * @param right the right operand
   * @return the product matrix
   */
  SquareMatrix compute(SparseMatrix left, SquareMatrix right);
}
