2025-12-27 13:37

Link:

Problem: 
You are given  integers in sorted order. Also, you are given  queries. In each query, you will be given an integer and you have to tell whether that integer is present in the array. If so, you have to tell at which index it is present and if it is not present, you have to tell the index at which the smallest integer that is just greater than the given number is present.

Lower bound is a function that can be used with a sorted vector. Learn how to use lower bound to solve this problem by [clicking here](http://www.cplusplus.com/reference/algorithm/lower_bound/).

**Input Format**
The first line of the input contains the number of integers . The next line contains integers in sorted order. The next line contains , the number of queries. Then  lines follow each containing a single integer .  

Note: If the same number is present multiple times, you have to print the first index at which it occurs. Also, the input is such that you always have an answer for each query.


**Output Format**
For each query you have to print "Yes" (without the quotes) if the number is present and at which index(1-based) it is present separated by a space.  

If the number is not present you have to print "No" (without the quotes) followed by the index of the next smallest number just greater than that number.  

You have to output each query in a new line.  

**Sample Input**
```
 8
 1 1 2 2 6 9 9 15
 4
 1
 4
 9
 15
```

**Sample Output**
```
 Yes 1
 No 5
 Yes 6
 Yes 8
```

failure: 

Intuition:
answer q number of queries, if the number is to be inserted into the vector, which idnex position should we inserted it at ( 1 based index)

use binary search with lower bound method to find left bound for the number( given arr is sorted) 

Solution:
```c++
#include <bits/stdc++.h>
using namespace std;

int main() {
    int n;
    cin >> n;

    vector<int> v(n);
    for (int i = 0; i < n; i++) {
        cin >> v[i];
    }

    int q;
    cin >> q;

    while (q--) {
        int x;
        cin >> x;

        auto it = lower_bound(v.begin(), v.end(), x);
        int idx = it - v.begin();  // 0-based

        if (*it == x) {
            cout << "Yes " << idx + 1 << "\n";
        } else {
            cout << "No " << idx + 1 << "\n";
        }
    }
}
```

Tags: #binary_search 

RL: 

Considerations:
