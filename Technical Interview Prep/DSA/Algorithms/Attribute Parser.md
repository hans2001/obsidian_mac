2025-12-30 23:03

Link:

Problem: 
This challenge works with a custom-designed markup language _HRML_. In _HRML_, each element consists of a starting and ending tag, and there are attributes associated with each tag. Only starting tags can have attributes. We can call an attribute by referencing the tag, followed by a tilde, '`~`' and the name of the attribute. The tags may also be nested. 

The _opening tags_ follow the format: 
`<tag-name attribute1-name = "value1" attribute2-name = "value2" ...>`

The _closing tags_ follow the format:
`</tag-name>`

The attributes are referenced as:
```
tag1~value  
tag1.tag2~name
```

Given the source code in HRML format consisting of  lines, answer queries. For each query, print the value of the attribute specified. Print _"Not Found!"_ if the attribute does not exist. 

**Example**
HRML listing
<tag1 value = "value">
<tag2 name = "name">
<tag3 another="another" final="final">
</tag3>
</tag2>
</tag1>

Queries
tag1~value
tag1.tag2.tag3~name
tag1.tag2~value

Here, tag2 is nested within tag1, so attributes of tag2 are accessed as `tag1.tag2~<attribute>`. Results of the queries are:

Query                 Value
tag1~value                     "value"
tag1.tag2.tag3~name   "Not Found!"
tag1.tag2.tag3~final      "final"

**Input Format**
The first line consists of two space separated integers,  and . specifies the number of lines in the HRML source program.  specifies the number of queries.

The following  lines consist of either an opening tag with zero or more attributes or a closing tag. There is a space after the tag-name, attribute-name, '=' and value.There is no space after the last value. _If there are no attributes there is no space after tag name._

 queries follow. Each query consists of string that references an attribute in the source program.More formally, each query is of the form  ~ where  and  are valid tags in the input.

**Constraints**
- Each line in the source program contains, at most,  characters. 
- Every reference to the attributes in the  queries contains at most  characters. 
- All tag names are unique and the HRML source program is logically correct, i.e. valid nesting.
- A tag can may have no attributes.

**Output Format**
Print the value of the attribute for each query. Print "_Not Found!_" without quotes if the attribute does not exist. 

**Sample Input**
```
4 3
<tag1 value = "HelloWorld">
<tag2 name = "Name1">
</tag2>
</tag1>
tag1.tag2~name
tag1~name
tag1~value
```

**Sample Output**

```
Name1
Not Found!
HelloWorld
```
Constraints:

Failure: 
dont know how to parse stirng with stringstream (parse each word directly, and parse sentence  with multiple variables! )

dont know how to trim the white space before and after a string
dont know how to use the stack to build the string i need for the key in hashmap that map to attribute value( attribute already in the key )

Intuition:
use stack to record the unclosed tag ,and use all tag to build the full path from root tag to sub tag attribute , so we can answer queries with full path in O(1)

Solution:
```cpp
#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, q;
    cin >> n >> q;

    // Clear the remaining newline after reading n and q
    string line;
    getline(cin, line);

    // Stores: "tag1.tag2~attr" -> value
    unordered_map<string, string> attributes;

    // Stack to track current tag nesting
    vector<string> tagStack;

    // Helper function to trim spaces
    auto trim = [](string s) {
        while (!s.empty() && isspace(s.front()))
            s.erase(s.begin());
        while (!s.empty() && isspace(s.back()))
            s.pop_back();
        return s;
    };

    /* -------------------- PARSE HRML -------------------- */
    for (int i = 0; i < n; ++i) {
        getline(cin, line);
        line = trim(line);

        // Closing tag: </tag>
        if (line[1] == '/') {
            tagStack.pop_back();
            continue;
        }

        // Remove '<' and '>'
        line = line.substr(1, line.size() - 2);

        // Parse tag and attributes
        stringstream ss(line);

        string tag;
        ss >> tag;

        // Build full tag path
        string path = tag;
        if (!tagStack.empty()) {
            path = tagStack[0];
            for (size_t j = 1; j < tagStack.size(); ++j) {
                path += "." + tagStack[j];
            }
            path += "." + tag;
        }

        // Push current tag to stack
        tagStack.push_back(tag);

        // Parse attributes
        string attr, eq, val;
        while (ss >> attr) {
            ss >> eq;   // '='
            ss >> val;  // "value"

            // Remove quotation marks
            val.erase(0, 1);
            val.pop_back();

            attributes[path + "~" + attr] = val;
        }
    }

    /* -------------------- PROCESS QUERIES -------------------- */
    for (int i = 0; i < q; ++i) {
        string query;
        getline(cin, query);
        query = trim(query);

        if (attributes.count(query))
            cout << attributes[query] << "\n";
        else
            cout << "Not Found!\n";
    }

    return 0;
}
```

Tags: #cpp #stringstream #trim #unordered_map #stack 

RL: 

Considerations:
```cpp
string line;
getline(cin, line);
```
- Declares a string called line
- Reads **an entire line of input**, including spaces, into line

```cpp
cin >> line;
```
Everything after the first space would be lost.
So **getline is required**.

After cin >> n >> q;
The newline \n is still in the input buffer.
so getline(cin, line); is used one to consume the leftover newline


```cpp 
line = line.substr(1, line.size() - 2);
```
It **removes the first and last characters** of the string.