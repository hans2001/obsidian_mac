## 1. Why BB

I assume you're asking about Bloomberg (BB), a major financial, software, data, and media company.

Bloomberg is known for its:

- Industry-leading financial data, analytics, and information services
- Highly respected technology infrastructure that processes vast amounts of financial market data
- Strong engineering culture with opportunities to work on challenging technical problems
- Competitive compensation and benefits
- Global presence with offices in major financial centers

Many engineers are drawn to Bloomberg for the opportunity to work with financial data at scale while building mission-critical systems that power global markets.

## 2. Why Software Engineer

Software engineering is an attractive career choice for many reasons:

- **Problem-solving**: Software engineers apply logical thinking and creativity to solve complex problems.
- **Innovation**: You get to build products and services that can impact millions of users.
- **High demand**: Strong job security with consistent growth in demand for skilled engineers.
- **Competitive compensation**: Generally offers above-average salaries and benefits.
- **Flexibility**: Many roles offer remote work options and flexible schedules.
- **Continuous learning**: The field constantly evolves, providing ongoing opportunities to learn new technologies and approaches.
- **Career versatility**: Skills are transferable across industries from finance to healthcare to entertainment.

## 3. What is hashtable? How to resolve the collision?

A hash table is a data structure that implements an associative array (or dictionary), which maps keys to values using a hash function to compute an index into an array of buckets or slots.

**Key components**:

- **Hash function**: Converts keys into array indices
- **Array/bucket storage**: Stores the key-value pairs
- **Collision handling mechanism**: Manages when different keys hash to the same index

**Collision resolution techniques**:

1. **Separate Chaining**:
    - Uses a linked list (or other data structure) to store multiple key-value pairs at the same index
    - Simple implementation but can have memory overhead
    - Example: Java's HashMap implementation
2. **Open Addressing**:
    - Stores all entries directly in the bucket array
    - When collision occurs, it finds another empty spot in the array
    - Sub-techniques include:
        - **Linear Probing**: Check next slot sequentially until empty slot found
        - **Quadratic Probing**: Check slots that are offset by increasing perfect squares
        - **Double Hashing**: Use a second hash function to determine the offset
3. **Robin Hood Hashing**:
    - A variation of open addressing that tries to reduce the variance of probe sequence lengths
    - Moves elements that are already in the table if a new element has traveled farther from its ideal position
4. **Cuckoo Hashing**:
    - Uses multiple hash functions and tables
    - When collision occurs, displaces the existing item to its alternative location

The choice between these methods depends on expected load factor, memory constraints, and performance requirements.

## 4. Difference between C and C++

**Key differences between C and C++**:

|Feature|C|C++|
|---|---|---|
|**Paradigm**|Procedural|Multi-paradigm (procedural, object-oriented, generic, functional)|
|**Type System**|Statically-typed|Statically-typed with more advanced features|
|**Memory Management**|Manual (malloc/free)|Manual + RAII + smart pointers in modern C++|
|**Object-Oriented**|No built-in support|Full OOP support with classes, inheritance, polymorphism|
|**Function Overloading**|Not supported|Supported|
|**Operator Overloading**|Not supported|Supported|
|**Templates**|Not supported|Supported (enabling generic programming)|
|**Exception Handling**|Not built-in|Built-in try/catch mechanism|
|**Standard Library**|C Standard Library|C++ Standard Library (includes STL)|
|**Namespace**|Not supported|Supported|
|**References**|Not supported|Supported|
|**Default Arguments**|Not supported|Supported|
|**Inline Functions**|Limited (via macros)|Full support|

C++ was designed as an extension of C with the addition of object-oriented features, while maintaining backward compatibility with C. Modern C++ (C++11 and later) has evolved significantly beyond its C roots with features like lambda expressions, move semantics, and smart pointers.

## 5. How to mimic Class in C++ using C (especially the private part)

To mimic C++ classes in C, you can use a combination of structures, function pointers, and the module pattern:

c

```c
// In the header file (myclass.h)
#ifndef MYCLASS_H
#define MYCLASS_H

// Opaque pointer (or handle) technique
typedef struct MyClass_t* MyClass;

// Constructor and destructor
MyClass MyClass_create(int initialValue);
void MyClass_destroy(MyClass instance);

// Public methods
int MyClass_getValue(MyClass instance);
void MyClass_setValue(MyClass instance, int newValue);

#endif
```

c

```c
// In the implementation file (myclass.c)
#include "myclass.h"
#include <stdlib.h>

// Private structure definition - hidden from users
struct MyClass_t {
    // Private data members
    int privateValue;
    
    // More private fields
    double privateCalculation;
};

// Constructor
MyClass MyClass_create(int initialValue) {
    MyClass instance = (MyClass)malloc(sizeof(struct MyClass_t));
    if (instance != NULL) {
        instance->privateValue = initialValue;
        instance->privateCalculation = 0.0;
    }
    return instance;
}

// Destructor
void MyClass_destroy(MyClass instance) {
    free(instance);
}

// Public method implementations
int MyClass_getValue(MyClass instance) {
    return instance->privateValue;
}

void MyClass_setValue(MyClass instance, int newValue) {
    instance->privateValue = newValue;
    // Could update private calculation here
    instance->privateCalculation = newValue * 1.5;
}

// Private helper functions
static double calculateSomething(MyClass instance) {
    return instance->privateValue * 2.0;
}
```

**Key techniques used**:

1. **Information hiding** through opaque pointers:
    - The header file only declares a pointer to the structure
    - The actual structure definition is in the implementation file
    - Users of the "class" cannot access the private members directly
2. **Encapsulation** through function interfaces:
    - All operations on the data must go through the public API functions
    - Private helper functions are marked as static to limit their scope
3. **Constructor/Destructor pattern**:
    - Creation function allocates and initializes the structure
    - Destroy function handles cleanup
4. **Naming conventions**:
    - Prefixing functions with the "class" name simulates the scope
    - Static helper functions hide implementation details

This approach achieves the key goals of C++ classes in C:

- Data hiding (private members)
- Encapsulation of data with operations
- Creation and cleanup management

However, it lacks C++ features like inheritance, polymorphism, operator overloading, and automatic constructor/destructor calling.

## 6. Find out the maximum average of 30 numbers given a sequence of number

To find the maximum average of any contiguous subsequence of 30 numbers from a given sequence, we can use the sliding window technique:
```c
double findMaximumAverageOf30(const vector<int>& nums) {
    // Handle edge case
    if (nums.size() < 30) {
        // Calculate average of all numbers if fewer than 30
        double sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum / nums.size();
    }
    
    // Calculate sum of first 30 numbers
    double currentSum = 0;
    for (int i = 0; i < 30; i++) {
        currentSum += nums[i];
    }
    
    double maxSum = currentSum;
    
    // Slide the window through the array
    for (int i = 30; i < nums.size(); i++) {
        // Add the new element and remove the oldest element
        currentSum += nums[i] - nums[i - 30];
        maxSum = max(maxSum, currentSum);
    }
    
    // Return the maximum average
    return maxSum / 30;
}
```

**Time Complexity**: O(n) where n is the length of the input array **Space Complexity**: O(1) as we only use a constant amount of extra space

The algorithm works by:

1. Calculating the sum of the first 30 elements
2. Sliding the window one element at a time (adding the new element and removing the oldest)
3. Tracking the maximum sum found
4. Returning the maximum sum divided by 30 to get the average

## 7. Follow up: how to make it able to deal with real-time data?
```c
class MaximumAverage30Tracker {
private:
    // Circular buffer to store the last 30 elements
    vector<double> buffer;
    int bufferSize;
    int currentPosition;
    double currentSum;
    double maxSum;
    int elementsProcessed;
    
public:
    MaximumAverage30Tracker() {
        bufferSize = 30;
        buffer.resize(bufferSize, 0);
        currentPosition = 0;
        currentSum = 0;
        maxSum = std::numeric_limits<double>::lowest();
        elementsProcessed = 0;
    }
    
    void processNewValue(double newValue) {
        // Subtract the value that will be overwritten (if buffer is full)
        if (elementsProcessed >= bufferSize) {
            currentSum -= buffer[currentPosition];
        }
        
        // Add the new value
        buffer[currentPosition] = newValue;
        currentSum += newValue;
        
        // Update pointer to next position (circular)
        currentPosition = (currentPosition + 1) % bufferSize;
        
        // Update elements processed count
        elementsProcessed = min(elementsProcessed + 1, bufferSize);
        
        // Update max sum if we have a full window
        if (elementsProcessed == bufferSize) {
            maxSum = max(maxSum, currentSum);
        }
    }
    
    // Get current average of the window
    double getCurrentAverage() {
        if (elementsProcessed == 0) return 0;
        return currentSum / elementsProcessed;
    }
    
    // Get maximum average encountered so far
    double getMaximumAverage() {
        if (elementsProcessed < bufferSize) return getCurrentAverage();
        return maxSum / bufferSize;
    }
    
    // Reset the tracker
    void reset() {
        fill(buffer.begin(), buffer.end(), 0);
        currentPosition = 0;
        currentSum = 0;
        maxSum = std::numeric_limits<double>::lowest();
        elementsProcessed = 0;
    }
};
```

## 8. Logic question: given a scale, how to tell the different one from 12 balls or something (using the minimum times of comparison) (brain teaser) 
This is the classic 12 balls problem, where you have 12 identical-looking balls, one of which is either heavier or lighter than the others. You need to identify the odd ball and determine whether it's heavier or lighter using a balance scale with the minimum number of weighings.

The solution requires just 3 weighings.
**Step 1: Divide the 12 balls into 3 groups of 4 each (let's call them A, B, and C)**

- Place group A against group B on the scale
- Possible outcomes:
    - If balanced: The odd ball is in group C
    - If unbalanced: The odd ball is in either A or B

**Step 2: Depends on the result of step 1**

If A and B balanced (the odd ball is in group C):
- Take 3 balls from C, call them C1, C2, and C3
- Compare C1 + C2 vs any two known normal balls from A
- Possible outcomes:
    - If balanced: C4 is the odd ball, proceed to step 3
    - If C1 + C2 is heavier: Either C1 or C2 is heavier, or C4 is lighter
    - If C1 + C2 is lighter: Either C1 or C2 is lighter, or C4 is heavier

If A was heavier than B (or vice versa):

- Take 3 balls from the heavier group, plus one ball from the lighter group
- Example: If A was heavier, take A1, A2, A3 (from A) and B1 (from B)
- Compare A1 + A2 + B1 vs A3 + 2 normal balls from C
- Based on the outcome, you can narrow it down to specific balls

**Step 3: Final weighing to identify the exact ball and whether it's heavier or lighter**

For example, if we determined in step 2 that either C1 or C2 is the odd ball:

- Compare C1 against a known normal ball
- If C1 balances: C2 is the odd ball
- If C1 is heavier: C1 is the heavier odd ball
- If C1 is lighter: C1 is the lighter odd ball

The full solution involves decision trees based on each weighing outcome. The key insight is that each weighing gives you ternary information (left heavier, right heavier, or balanced), and with 3 weighings you get 3^3 = 27 possible outcomes, which is enough to distinguish between the 24 possibilities (12 balls × 2 weight variations).

This problem demonstrates information theory principles, where the minimum number of weighings needed is ceiling(log₃(24)) = 3.