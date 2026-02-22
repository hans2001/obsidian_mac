2026-01-02 23:08

Link:

Problem: 
Abstract base classes in C++ can only be used as base classes. Thus, they are allowed to have virtual member functions without definitions.

A cache is a component that stores data so future requests for that data can be served faster. The data stored in a cache might be the results of an earlier computation, or the duplicates of data stored elsewhere. A cache hit occurs when the requested data can be found in a cache, while a cache miss occurs when it cannot. Cache hits are served by reading data from the cache which is faster than recomputing a result or reading from a slower data store. Thus, the more requests that can be served from the cache, the faster the system performs.

One of the popular cache replacement policies is: "least recently used" (LRU). It discards the least recently used items first.

For example, if a cache with a capacity to store 5 keys has the following state(arranged from most recently used key to least recently used key) -
```
5 3 2 1 4
```

Now, If the next key comes as 1(which is a cache hit), then the cache state in the same order will be -
```
1 5 3 2 4
```

Now, If the next key comes as 6(which is a cache miss), then the cache state in the same order will be -
```
6 1 5 3 2
```

You can observe that 4 has been discarded because it was the least recently used key and since the capacity of cache is 5, it could not be retained in the cache any longer.

**Given an abstract base class _Cache_ with member variables and functions**:   
_mp_ - Map the key to the node in the linked list  
_cp_ - Capacity  
_tail_ - Double linked list tail pointer  
_head_ - Double linked list head pointer  
_set()_ - Set/insert the value of the key, if present, otherwise add the key as the most recently used key. If the cache has reached its capacity, it should replace the least recently used key with a new key.  
_get()_ - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.  

You have to write a class _LRUCache_ which extends the class _Cache_ and uses the member functions and variables to implement an LRU cache.

**Input Format**
First line of input will contain the  number of lines containing  or commands followed by the capacity  of the cache.  
The following  lines can either contain  or  commands.  
An input line starting with  will be followed by a  to be found in the cache. An input line starting with  will be followed by the  and respectively to be inserted/replaced in the cache.

**Output Format**
The code provided in the editor will use your derived class _LRUCache_ to output the value whenever a get command is encountered.

**Sample Input**
```
3 1
set 1 2
get 1
get 2
```

**Sample Output**
```
2
-1
```

**Explanation**
Since, the capacity of the cache is 1, the first _set_ results in setting up the key 1 with it's value 2. The first _get_ results in a cache hit of key 1, so 2 is printed as the value for the first _get_. The second _get_ is a cache miss, so -1 is printed.

failure: 

Intuition:
nothing fancy

Solution:
```cpp
#include <iostream>
#include <map>
#include <string>
using namespace std;

struct Node {
    Node* next;
    Node* prev;
    int value;
    int key;

    Node(Node* p, Node* n, int k, int val) : next(n), prev(p), value(val), key(k) {}
    Node(int k, int val) : next(nullptr), prev(nullptr), value(val), key(k) {}
};

class Cache {
protected:
    map<int, Node*> mp;
    int cp{};
    Node* tail{};
    Node* head{};

    virtual void set(int, int) = 0;
    virtual int get(int) = 0;
};

class LRUCache : public Cache {
public:
    LRUCache(int cap) {
        cp = cap;

        head = new Node(0, 0);   // IMPORTANT: assign to members
        tail = new Node(0, 0);

        head->next = tail;
        tail->prev = head;
    }

    void update_pos(Node* node) {
        // detach
        node->prev->next = node->next;
        node->next->prev = node->prev;

        // insert right after head
        node->next = head->next;
        node->prev = head;
        head->next->prev = node;
        head->next = node;
    }

    void set(int key, int val) override {
        if (cp == 0) return;

        if (auto it = mp.find(key); it != mp.end()) {
            Node* node = it->second;
            node->value = val;
            update_pos(node);
            return;
        }

        Node* new_node = new Node(head, head->next, key, val);
        head->next->prev = new_node;
        head->next = new_node;
        mp[key] = new_node;

        if (mp.size() > static_cast<size_t>(cp)) {
            Node* last_node = tail->prev;   // LRU
            if (last_node == head) return;  // should not happen, but safe

            // detach last_node
            last_node->prev->next = tail;
            tail->prev = last_node->prev;

            mp.erase(last_node->key);
            delete last_node;
        }
    }

    int get(int key) override {
        auto it = mp.find(key);
        if (it == mp.end()) return -1;

        Node* node = it->second;
        update_pos(node);
        return node->value;
    }
};

int main() {
    int n, capacity;
    cin >> n >> capacity;

    LRUCache l(capacity);

    for (int i = 0; i < n; i++) {
        string command;
        cin >> command;

        if (command == "get") {
            int key;
            cin >> key;
            cout << l.get(key) << '\n';
        } else if (command == "set") {
            int key, value;
            cin >> key >> value;
            l.set(key, value);
        }
    }
    return 0;
}
```

Tags: #LRU #struct #linked_list 

RL: 

Considerations:
