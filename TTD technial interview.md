 The coding assessment was to build a cache system, which can is API-able. The cache should have a **MRU algorithm and LRU algorithm** inbuilt and a method for a user to provide a user **defined Cache replacement algorithm** as well.

一共三轮：
1. Technical，两道题，每道题一个小时。第一道题就是data structure and algorithm需要compile然后跑的；关于string abbreviation。第二道题是oo design and algorithm，不需要跑，graph相关的，需要用到dfs / bfs之类的algorithm。

题目还是地里能搜到的那个万年不变的N-Way Set Associative Cache的题目，但是具体要求有变动，主要是5个部分

1. Implement the interface: 这里的要求是改写出一个接口类，用以适用于任何的缓存替换算法（而不是地里其他面经说的仅仅LRU和MRU）
2. Optimize it: 这里要求你优化代码结构，但不能改变代码数据类型——我一开始想把Array换成Map，被告知这是一个好办法，但不希望我在这里这么做，地里其他面经里的优化还不够到位，可以最终达到O(n)
3. Make it thread-safe: 要求把代码改成多线程环境下也是安全的形式，这里可以使用别的数据类型，性能可以达到O(1)
4. Implement LRU & MRU: 要求基于以上更改，实现LRU和MRU
5. Optimize MRU to O(1): 要求把类似于LRU实现的MRU优化成O(1)的另一种实现

# N way associate cache
https://github.com/Novotarskyi/n-way-set-associative-cache/tree/master/src/main/java/nwaycache

## TODO :
I should practice medium to hard questions( leetcode based )
i should get familiar to python OOP style and try work on some problems
try to utilize concepts such as 

# Questions: 
URL shortener (Design TinyURL), rate limiter, real-time chat (Second Round)

How to design a publisher, subscriber system

Reverse a matrix given input n

Design a Cache system with MRU, LRU and custom algorithm

I got something related to a **stack** Some other person got something related to **decoding/encoding** and **string manipulation**. 

## Marking Scheme
We want you to **ask as many questions** as you can to help build a successful solution to the problem. This is to ensure that you are on the same page as your interviewer.

Your interviewer will also be considering your **ability to reason** in the context of systems using concepts you already know as well as ask questions to ensure you’re understanding **the requirements of the design**. Be vocal and make sure you’re **explaining your thought process** in coming to your decision

We recommend focusing on **requirement gathering**, potential **edge cases**, **scalability** and **efficiency** during your interviews.

# List of topics
Data Structures • HashMaps • Dictionaries • Stacks • **Heaps** • Linked Lists • **Trees** • **Class Design and Interfaces** • Algorithm fundamentals • **Recursion** • Nested loops • Iteration

(do u think the assignment is in java ?)
do i have to show that i have skills such as defining abstract class?  concrete class interface and write test case? 

OOD is for the second round? 
# Format: 
You can expect all the interviews to start with some form of introduction and time will given to you to ask questions at the end.

# Key points: 
how you tackle challenges and how you respond to feedback from your interviewer. 

Demonstrating a **structured approach, event** when the problem is tough, is a key part of what we are looking for

1. Being attentive throughout your interview and pick up on any suggestions and hints. the point is checking how we apply their suggestion into our solution

2. Communication is KEY, walk them through my solution and help them understand why i choose to do it that way 

3. ask them questions to make sure i understand the problem, that i have the info that i need, is there element that need adjusting or improving? hwo would they suggest optimizing and more (edge cases  and constraints! )

# TODO:
work on medium to hard problems first (focus on the list of topic , no backtracking or dp? )
review tree and graphs
work on medium to hard questions  
practice working with Python OOP ( how to represent ideas in java? )