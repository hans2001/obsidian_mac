2025-12-29 15:43

Link:

Problem: 
A student signed up for  workshops and wants to attend the maximum number of workshops where no two workshops overlap. You must do the following:

Implement  [structures](http://www.cplusplus.com/doc/tutorial/structures/): 

1. _struct Workshop_ having the following members:
    - The workshop's start time.
    - The workshop's duration.
    - The workshop's end time.

2. _struct Available_Workshops_ having the following members:
    - An integer,  (the number of workshops the student signed up for).
    - An array of type _Workshop_ array having size .

Implement  [functions](http://www.cplusplus.com/doc/tutorial/functions/):
1. _Available_Workshops* initialize (int start_time[], int duration[], int n)_  
    Creates an _Available_Workshops_ object and initializes its elements using the elements in the  and  parameters (both are of size ). Here,  and  are the respective start time and duration for the workshop. This function must return a pointer to an _Available_Workshops_ object.
    
2. _int CalculateMaxWorkshops(Available_Workshops* ptr)_  
    Returns the maximum number of workshops the student can attend—without overlap. The next workshop cannot be attended until the previous workshop ends.
    

**Note:** An array of unknown size () should be declared as follows:
```
DataType* arrayName = new DataType[n];
```

**Input Format**
Input from stdin is handled by the locked code in the editor; you simply need to write your functions to meet the specifications of the problem statement above.

**Output Format**
Output to stdout is handled for you.

Your _initialize_ function must return a pointer to an _Available_Workshops_ object.   
Your _CalculateMaxWorkshops_ function must return maximum number of non-overlapping workshops the student can attend.

**Sample Input**
```
6
1 3 0 5 5 8
1 1 6 2 4 1
```

**Sample Output**
_CalculateMaxWorkshops_ should return .

**Explanation**
The first line denotes , the number of workshops.   
The next line contains  space-separated integers where the  integer is the workshop's start time.   
The next line contains  space-separated integers where the  integer is the workshop's duration. 

The student can attend the workshops  and  without overlap, so _CalculateMaxWorkshops_ returns  to _main_ (which then prints  to stdout).

Constraints:
![[Screenshot 2025-12-29 at 3.43.58 PM.png]]

failure: syntax not familiar, dont know how to sort with std::sort

Intuition:
greedy + sorting to find the number of workshop that are not overlapping with each other! 

Solution:
```cpp
#include <bits/stdc++.h>
using namespace std;

struct Workshop {
    int start_time;
    int duration;
    int end_time;
};

struct Available_Workshops {
    int n;
    Workshop* workshops;
};

Available_Workshops* initialize(int start_time[], int duration[], int n) {
    auto* aw = new Available_Workshops;
    aw->n = n;
    aw->workshops = new Workshop[n];

    for (int i = 0; i < n; i++) {
        aw->workshops[i].start_time = start_time[i];
        aw->workshops[i].duration   = duration[i];
        aw->workshops[i].end_time   = start_time[i] + duration[i];
    }
    return aw;
}

int CalculateMaxWorkshops(Available_Workshops* ptr) {
    int n = ptr->n;
    Workshop* w = ptr->workshops;

    // Sort by end time (earliest finishing first)
    sort(w, w + n, [](const Workshop& a, const Workshop& b) {
        if (a.end_time != b.end_time) return a.end_time < b.end_time;
        return a.start_time < b.start_time; // tie-breaker
    });

    int count = 0;
    int current_end = 0; // earliest time we are free; start from 0

    for (int i = 0; i < n; i++) {
        if (w[i].start_time >= current_end) {
            count++;
            current_end = w[i].end_time;
        }
    }
    return count;
}
```

Tags: #cpp #sorting 

RL: 

Considerations:
