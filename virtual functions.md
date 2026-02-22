2026-01-02 21:58

Link:

Problem: 
This problem is to get you familiar with virtual functions. Create three classes _Person, Professor_ and _Student_. The class _Person_ should have data members name and age. The classes _Professor_ and _Student_ should inherit from the class _Person_.

The class _Professor_ should have two integer members: _publications_ and cur_id. There will be two member functions: _getdata_ and _putdata_. The function _getdata_ should get the input from the user: the _name, age_ and _publications_ of the professor. The function _putdata_ should print the _name, age, publications_ and the cur_id of the professor.

The class _Student_ should have two data members: _marks_, which is an array of size  and cur_id. It has two member functions: _getdata_ and _putdata_. The function _getdata_ should get the input from the user: the _name, age_, and the _marks_ of the student in  subjects. The function _putdata_ should print the _name, age_, _sum_ of the marks and the cur_id of the student.

For each object being created of the _Professor_ or the _Student_ class, sequential id's should be assigned to them starting from .

Solve this problem using virtual functions, constructors and static variables. You can create more data members if you want.

**Note:** Expand the main function to look at how the input is being handled.

**Input Format**
The first line of input contains the number of objects that are being created. If the first line of input for each object is , it means that the object being created is of the _Professor_ class, you will have to input the _name, age_ and _publications_ of the professor.

If the first line of input for each object is , it means that the object is of the _Student_class, you will have to input the _name, age_ and the _marks_ of the student in  subjects.

**Constraints**
, where  is the length of the name.   
, where marks is the marks of the student in each subject.

**Output Format**
There are two types of output depending on the object. 

If the object is of type _Professor_, print the space separated _name, age, publications_ and _id_ on a new line.

If the object is of the _Student_ class, print the space separated _name, age_, the _sum of the marks_ in  subjects and _id_ on a new line.

**Sample Input**
```
4
1
Walter 56 99
2
Jesse 18 50 48 97 76 34 98
2
Pinkman 22 10 12 0 18 45 50
1
White 58 87
```

**Sample Output**
```
Walter 56 99 1
Jesse 18 403 1
Pinkman 22 135 2
White 58 87 2
```

Constraints:

failure: 

Intuition:
virtualize based method and declare no implementation 
then child class inherit virtual class  and initialize class based static variables next_id ,where cur_id will be the next_id for each new object initialized! 
remember the modern cin and override keyword


Solution:
```cpp
#include <array>
#include <cmath>
#include <cstdio>
#include <iostream>
#include <numeric>
#include <string>

class Person {
protected:
    std::string name{};
    int age{};
    int cur_id{};

public:
    virtual void getdata() = 0;
    virtual void putdata() const = 0;
    virtual ~Person() = default;
};

class Professor : public Person {
private:
    int publications{};
    inline static int next_id = 1;

public:
    void getdata() override {
        std::cin >> name >> age >> publications;
        cur_id = next_id++;
    }

    void putdata() const override {
        std::cout << name << " "
                  << age << " "
                  << publications << " "
                  << cur_id << "\n";
    }
};

class Student : public Person {
private:
    std::array<int, 6> marks{};
    inline static int next_id = 1;

public:
    void getdata() override {
        std::cin >> name >> age;
        for (int& m : marks) {
            std::cin >> m;
        }
        cur_id = next_id++;
    }

    void putdata() const override {
        std::cout << name << " "
                  << age << " "
                  << std::accumulate(marks.begin(), marks.end(), 0) << " "
                  << cur_id << "\n";
    }
};

int main() {
    int n{}, val{};
    std::cin >> n;  // number of objects to be created

    Person* per[n];

    for (int i = 0; i < n; ++i) {
        std::cin >> val;

        if (val == 1) {
            per[i] = new Professor;
        } else {
            per[i] = new Student;
        }

        per[i]->getdata();
    }

    for (int i = 0; i < n; ++i) {
        per[i]->putdata();
    }

    return 0;
}
```

Tags: #virtual #polymorphism #static #inline 

RL: 

Considerations:
