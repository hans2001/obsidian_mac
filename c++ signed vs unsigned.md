### **2) Signed vs unsigned integers (the essence)**

**Signed integer** (e.g. int, long long)

- Can represent negative and positive numbers.
- Typically stored in **two’s complement**.
- Example range for 32-bit int: -2,147,483,648 .. 2,147,483,647.

**Unsigned integer** (e.g. size_t, unsigned int)

- Only non-negative numbers.
- Range for 32-bit unsigned: 0 .. 4,294,967,295.
- When an operation “goes below 0”, it **wraps around** modulo 2^N.