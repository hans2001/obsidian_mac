2026-01-02 17:36

Link:

Problem: 
In this challenge, you are required to handle error messages while working with small computational server that performs complex calculations.   
It has a function that takes  large numbers as its input and returns a numeric result. Unfortunately, there are various exceptions that may occur during execution.

Complete the code in your editor so that it prints appropriate error messages, should anything go wrong. The expected behavior is defined as follows:

- If the _compute_ function runs fine with the given arguments, then print the result of the function call. 
- If it fails to allocate the memory that it needs, print `Not enough memory`. 
- If any other standard C++ exception occurs, print `Exception: S` where  is the exception's error message. 
- If any non-standard exception occurs, print `Other Exception`.

**Input Format**
The first line contains an integer, , the number of test cases.   
Each of the  subsequent lines describes a test case as  space-separated integers, and , respectively.

**Output Format**
For each test case, print a single line containing whichever message described in the _Problem Statement_ above is appropriate. After all messages have been printed, the locked stub code in your editor prints the server load.

**Sample Input**
```
2
-8 5
1435434255433 5
```

**Sample Output**
```
Exception: A is negative
Not enough memory
2
```

**Explanation**
is negative, hence 'Exception: A is negative' is thrown. Since the second input is too large, 'not enough memory' is displayed.  is the server load.
Constraints:

failure: 

Intuition:
- unsigned values are never negative

use at to check if index is within boundaries by comparing size-t pointer! before allowing access to the data ( return a reference )
```cpp
int x = -1;
v.at(x); -> implicit conversion
```
size_t(-1) == very_large_number -> if (i >= size_) -> true
So it throws **std::out_of_range**.
✅ Negative indices are still caught
❌ Just not by i < 0

## at() underlying code: 
Because that single check covers **everything**:
- valid: 0 <= i < size
- invalid:
    - i >= size
    - negative values converted to huge numbers

Solution:
```cpp
#include <cmath>
#include <exception>
#include <iostream>
#include <stdexcept>
#include <vector>

class Server {
private:
    inline static int load_ = 0;  // C++17: no out-of-class definition needed

public:
    static int compute(long long A, long long B) {
        ++load_;

        if (A < 0) {
            throw std::invalid_argument("A is negative");
        }

        std::vector<int> v(static_cast<std::size_t>(A), 0);

        const int real = -static_cast<int>(A / B);  // keep same intent, clearer
        const int ans  = v.at(static_cast<std::size_t>(B));

        return real + static_cast<int>(A) - static_cast<int>(B) * ans;
    }

    static int getLoad() noexcept {
        return load_;
    }
};

int main() {
    int T{};
    std::cin >> T;

    while (T--) {
        long long A{}, B{};
        std::cin >> A >> B;

        try {
            const int res = Server::compute(A, B);
            std::cout << res << '\n';
        } catch (const std::bad_alloc&) {
            std::cout << "Not enough memory\n";
        } catch (const std::out_of_range& e) {
            std::cout << "Exception: " << e.what() << '\n';
        } catch (const std::invalid_argument& e) {
            std::cout << "Exception: " << e.what() << '\n';
        } catch (const std::exception& e) {
            // Any other standard exception (e.g., if you later add more throws)
            std::cout << "Exception: " << e.what() << '\n';
        } catch (...) {
            // Catches the old-style `throw 0;` case and any non-std exceptions
            std::cout << "Other Exception\n";
        }
    }

    std::cout << Server::getLoad() << '\n';
    return 0;
}
```

Tags: #stdexception #at 

RL: 

Considerations:
```cpp
std::vector<int> v(static_cast<std::size_t>(A), 0);
```
- std::vector size **must** be size_t
- A is long long (signed)
- If A < 0, we already throw earlier
- After validation, converting to size_t is **safe and intentional**

```cpp
v.at(static_cast<std::size_t>(B));
```
- vector::at() takes a size_t
- B is used as an **index**
- Indexing is **unsigned** by design

Casting to size_t **does not mean** the value becomes “negative but unsigned”
It means: _reinterpret the numeric value as unsigned_
	If B = 3 → size_t(3)
	If B = -1 → huge number → caught by at() as out-of-range

```cpp
const int real = -static_cast<int>(A / B);
```
- A and B are long long
- A / B is done in **signed integer arithmetic**
- Result type: long long
- Then we cast the **result** to int
- Then we negate it
- 🚨 **No unsigned math is involved here at all**

