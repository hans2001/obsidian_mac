2026-01-02 17:01

Link:

Problem: 
You inherited a piece of code that performs username validation for your company's website. The existing function works reasonably well, but it throws an exception when the username is too short. Upon review, you realize that nobody ever defined the exception. 

The inherited code is provided for you in the locked section of your editor. Complete the code so that, when an exception is thrown, it prints `Too short: n` (where  is the length of the given username). 

**Input Format**
The first line contains an integer, , the number of test cases.   
Each of the  subsequent lines describes a test case as a single username string, .

**Constraints**
- The username consists only of uppercase and lowercase letters.

**Output Format**
You are not responsible for directly printing anything to stdout. If your code is correct, the locked stub code in your editor will print either `Valid` (if the username is valid), `Invalid` (if the username is invalid), or `Too short: n`(where  is the length of the too-short username) on a new line for each test case.

**Sample Input**
```
3
Peter
Me
Arxwwz
```

**Sample Output**
```
Valid
Too short: 2
Invalid
```

**Explanation**
Username `Me` is too short because it only contains characters, so your exception prints .   
All other validation is handled by the locked code in your editor.

Constraints:

failure: 

Intuition:
c_str() is character string? C++ will not auto-convert a string object into a C string.
and msg is a string

why initialize as string instead of c_string
- std::string **owns the memory**
- automatic allocation / deallocation
- exception object is trivially safe to copy/move
- no manual new / delete
- no dangling pointers

bad example (undefined beahviour) (pointer only)
```cpp
class BadLengthException {
    const char* msg;
public:
    BadLengthException(int n) {
        msg = std::to_string(n).c_str(); // ❌
    }
};
```
- std::to_string(n) creates a temporary std::string
- .c_str() points into it
- temporary is destroyed at end of statement
- msg now points to freed memory

inherited on the std: :exception( base class)
and override the virtual function what, which logs the message, where the n should be stored  as member variable ,and return by the what function( so it has to be  string( char*))

Solution:
```cpp
try {
	...
} catch (BadLengthException e) {
	cout << "Too short: " << e.what() << '\n';
}

class BadLengthException : public std::exception{
	string msg;
public:
	explicit BadLengthException(int n) : msg(std::to_string(n)) {}
	const char* what() const noexcept override {
		return msg.c_str();
	}
};
```

Tags: #exception

RL: 

Considerations:
