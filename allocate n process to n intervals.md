2025-08-05 14:10

Link:

Problem: 
![[Pasted image 20250805141017.png]]
![[Pasted image 20250805141025.png]]
Constraints:

Intuition:
\let n_process be m and n_interval be N
besides edge cases, rest of the cases can be handled with combination. where the first slot has m choice, and rest of the interval has m-1 choice (excluding the previously used process)

![[Screenshot 2025-08-05 at 2.13.53 PM.png]]
1. **Correctness to the problem statement**  
    They don’t want the full integer (m−1)N−1(m−1)N−1—they only care about its value **mod 10⁹+7**.

2. **Efficiency and safety**
    - If you naively computed `(m-1)**(N-1)` in Python, you’d get an astronomically large integer (hundreds or thousands of digits) which is slow to multiply or reduce.
    - Python’s built-in `pow(base, exp, mod)` implements **modular exponentiation** by repeated squaring, doing every multiplication “mod M” as it goes. That keeps all interim values small (below M) and runs in **O(log exp)** time instead of trying to build a gigantic integer in memory.
        
that is why we ground our multiplication and exponentiation result on MOD


Solution:
```python
MOD = 10**9 + 7
def processScheduling(n_intervals, n_processes):
    m, N = n_processes, n_intervals
    if N == 1:
        return m % MOD
    if m <= 1:
        return 0
    # fast modular exponentiation:
    return m * pow(m-1, N-1, MOD) % MOD
```

Tags: #math #exponentiation 

RL: 

Considerations:
