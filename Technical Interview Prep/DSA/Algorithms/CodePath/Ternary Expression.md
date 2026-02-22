2025-03-05 10:47

Link:

Problem: 
Given a string `expression` representing arbitrarily nested ternary expressions, evaluate the expression, and return its result as a string.

You can always assume that the given expression is valid and only contains digits, `'?'`, `':'`, `'T'`, and `'F'` where `'T'`is `True` and `'F'` is `False`. All the numbers in the expression are one-digit numbers (i.e., in the range `[0, 9]`).

Ternary expressions use the following syntax:

`condition ? true_choice : false_choice`

- `condition` is evaluate first and determines which choice to make.
    - `true_choice` is taken if `condition` evaluates to `True`
    - `false_choice` is taken if `condition` evaluates to `False`

The conditional expressions group right-to-left, and the result of the expression will always evaluate to either a digit, `'T'` or `'F'`.

Intuition:
dynamic indexing: return index in recursive calls to indicate the end of sub-expression(if any) only ? is skipped hard-coded, since after a condition, it must followed by a question mark! the true and false expression is recursively evaluated for cases like nested expression

Solution:
recursive:
```python
def evaluate_ternary_expression_recursive(expression):
    n = len (expression )
    def dfs ( i ):
        if i >= n - 1 or expression[i +1 ] != '?' :
            return (expression[i],i +1) 
        true_val ,j = dfs ( i + 2)
        false_val,k  = dfs (j +1 )
        return (true_val if expression[i] =='T' else false_val ,k)
    val , index=  dfs( 0 )
    return val
```
O(n) / O(1) (n is the length of the ternary expression)

iterative:
```python 
def evaluate_ternary_expression_iterative(expression):
    stack = []
    
    # Traverse the expression from right to left
    for i in range(len(expression) - 1, -1, -1):
        char = expression[i]
        
        if stack and stack[-1] == '?':
            stack.pop()  # Remove the '?'
            true_expr = stack.pop()  # True expression
            stack.pop()  # Remove the ':'
            false_expr = stack.pop()  # False expression
            
            if char == 'T':
                stack.append(true_expr)
            else:
                stack.append(false_expr)
        else:
            stack.append(char)
    
    return stack[0]
```

Tags: 

RL: 

Considerations:
