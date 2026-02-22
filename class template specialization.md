2025-12-29 16:28

Link:

Problem: 
You are given a _main_ function which reads the enumeration values for two different types as input, then prints out the corresponding  [enumeration](http://en.cppreference.com/w/cpp/language/enum) names. Write a class template that can provide the names of the enumeration values for both types. If the enumeration value is not valid, then print `unknown`.

**Input Format**
The first line contains , the number of test cases.   
Each of the  subsequent lines contains two space-separated integers. The first integer is a color value, , and the second integer is a fruit value, .

**Output Format**
The locked stub code in your editor prints  lines containing the _color_ name and the _fruit_name corresponding to the input enumeration index.

**Sample Input**
```
2
1 0
3 3
```

**Sample Output**
```
green apple
unknown unknown
```

**Explanation**
Since , there are two lines of output. 

1. The two input index values,  and , correspond to _green_ in the color enumeration and _apple_ in the fruit enumeration. Thus, we print `green apple`.
2. The two input values,  and , are outside of the range of our enums. Thus, we print `unknown unknown`.

failure: 

Intuition:

Solution:
```cpp
#include <iostream>
#include <string>
using namespace std;

enum class Fruit { apple, orange, pear };
enum class Color { red, green, orange };

template <typename T>
struct Traits;

// Color specialization
template <>
struct Traits<Color> {
    static string name(int index) {
        switch (index) {
            case 0: return "red";
            case 1: return "green";
            case 2: return "orange";
            default: return "unknown";
        }
    }
};

// Fruit specialization
template <>
struct Traits<Fruit> {
    static string name(int index) {
        switch (index) {
            case 0: return "apple";
            case 1: return "orange";
            case 2: return "pear";
            default: return "unknown";
        }
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t; 
    cin >> t;

    for (int i = 0; i < t; ++i) {
        int index1, index2;
        cin >> index1 >> index2;

        cout << Traits<Color>::name(index1) << " "
             << Traits<Fruit>::name(index2) << "\n";
    }
}
```

Tags: #template_specialization #cpp 

RL: 

Considerations:
