2025-12-28 12:04

Link:

Problem: 
Given a text file with many lines of numbers to format and print, for each row of  space-separated doubles, format and print the numbers using the specifications in the _Output Format_ section below.

**Input Format**
The first line contains an integer, , the number of test cases.   
Each of the  subsequent lines describes a test case as  space-separated floating-point numbers: , , and , respectively.

**Output Format**
For each test case, print  lines containing the formatted , , and , respectively. Each , , and  must be formatted as follows:

1. : Strip its decimal (i.e., truncate it) and print its hexadecimal representation (including the  prefix) in lower case letters.
2. : Print it to a scale of  decimal places, preceded by a  or  sign (indicating if it's positive or negative), right justified, and left-padded with underscores so that the printed result is exactly  characters wide.
3. : Print it to a scale of exactly nine decimal places, expressed in scientific notation using upper case.

**Sample Input**
```
1  
100.345 2006.008 2331.41592653498
```

**Sample Output**
```
0x64             
_______+2006.01  
2.331415927E+03
```

**Explanation**
For the first line of output,  (in reverse, ).   
The second and third lines of output are formatted as described in the _Output Format_section.

**Constraints**
- Each number will fit into a double.

failure: 

Intuition:

Solution:
```cpp
#include <bits/stdc++.h>
using namespace std;

int main() {
    int T;
    cin >> T;

    while (T--) {
        double a, b, c;
        cin >> a >> b >> c;

        // a: truncate + hex
        cout << nouppercase << showbase << hex
             << (long long)a << "\n";

        // b: signed, padded
        cout << dec << showpos << fixed << setprecision(2)
             << setw(15) << setfill('_')
             << b << "\n";

        // c: scientific, uppercase, NO sign
        cout << noshowpos
             << uppercase << scientific << setprecision(9)
             << c << "\n";
    }
}
```

Tags: #cpp 

RL: 

Considerations:
