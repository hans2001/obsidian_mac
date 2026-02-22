## python methods
![[Screenshot 2025-04-21 at 10.58.33 AM.png]]

type methods return the type of an object!

**Deep copying**
```
D.copy() is shallow copy
u need to defined deep copy function yourself! and pass in 2 objects! 
```

**File operation**
```python
f = open(filename)
while True: 
	line = f.readline( )
	if not line:
		break
	print(line)
f.close()
```

**Methods on string**
![[Screenshot 2025-04-21 at 11.01.36 AM.png]]

**Set**
![[Screenshot 2025-04-21 at 11.03.27 AM.png]]
```python
x = set('spam')
y =set (['h','a','m']

%% intersection %%
x & y
%% union %%
x | y
%% difference %%
x - y
%% symmetric difference %% (element appear in either set but wont appear in both set! )
x ^ y 
```

**Singleton**
```python
print '----------------------方法1--------------------------'  
#方法1,实现__new__方法  
#并在将一个类的实例绑定到类变量_instance上,  
#如果cls._instance为None说明该类还没有实例化过,实例化该类,并返回  
#如果cls._instance不为None,直接返回cls._instance  
class Singleton(object):  
    def __new__(cls, *args, **kw):  
        if not hasattr(cls, '_instance'):  
            orig = super(Singleton, cls)  
            cls._instance = orig.__new__(cls, *args, **kw)  
        return cls._instance  
  
class MyClass(Singleton):  
    a = 1  
  
one = MyClass()  
two = MyClass()  
  
two.a = 3  
print one.a  
#3  
#one和two完全相同,可以用id(), ==, is检测  
print id(one)  
#29097904  
print id(two)  
#29097904  
print one == two  
#True  
print one is two  
#True  
```

**Stack**
![[Screenshot 2025-04-21 at 11.09.02 AM.png]]

![[Screenshot 2025-04-21 at 11.09.46 AM.png]]
list always deep copy
tuple return the original object
tuple (): turn iterable to a tuple ( same element, same order! )

## Python是如何进行内存管理的？

Python引用了一个内存池(memory pool)机制，即Pymalloc机制(malloc:n.分配内存)，用于管理对小块内存的申请和释放

内存池（memory pool）的概念：
当创建大量消耗小内存的对象时，频繁调用new/malloc会导致大量的内存碎片，致使效率降低。内存池的概念就是预先在内存中申请一定数量的，大小相等 的内存块留作备用，当有新的内存需求时，就先从内存池中分配内存给这个需求，不够了之后再申请新的内存。这样做最显著的优势就是能够减少内存碎片，提升效率。

内存池的实现方式有很多，性能和适用范围也不一样。 

python中的内存管理机制——Pymalloc：  
python中的内存管理机制都有两套实现，一套是针对小对象，就是大小小于256bits时,pymalloc会在内存池中申请内存空间；当大于256bits，则会直接执行new/malloc的行为来申请内存空间。

关于释放内存方面，当一个对象的引用计数变为0时，python就会调用它的析构函数。在析构时，也采用了内存池机制，从内存池来的内存会被归还到内存池中，以避免频繁地释放动作。

## 什么是lambda函数？它有什么好处?
lambda 函数是一个可以接收任意多个参数(包括可选参数)并且返回单个表达式值的函数。 lambda 函数不能包含命令，它们所包含的表达式不能超过一个。不要试图向lambda 函数中塞入太多的东西；如果你需要更复杂的东西，应该定义一个普通函数，然后想让它多长就多长。

## 列表与元组的区别是什么.分别在什么情况下使用
列表中的项目应该包括在方括号中，你可以添加、删除或是搜索列表中的项目。由于你可以增加或删除项目，所以列表是可变的数据类型，即这种类型是可以被改变的。

元组和列表十分类似，但是元组是不可变的.也就是说你不能修改元组。元组通过圆括号中用逗号分割的项目定义。元组通常用在使语句或用户定义的函数能够安全地采用一组值的时候，即被使用的元组的值不会改变。

## 字典
键值对的集合(map)字典是以大括号“{}”包围的数据集合。

与列表区别：字典是无序的，在字典中通过键来访问成员。字典是可变的，可以包含任何其他类型。

## Python里面search()和match()的区别
```python
>>> import re  
>>> re.match(r'python','Programing Python, should be pythonic')  
>>> obj1 = re.match(r'python','Programing Python, should be pythonic')  #返回None  
>>> obj2 = re.search(r'python','Programing Python, should be pythonic') #找到pythonic  
>>> obj2.group()  
'python'  
#re.match只匹配字符串的开始，如果字符串开始不符合正则表达式，则匹配失败，函数返回None；  
#re.search匹配整个字符串，直到找到一个匹配。
```

## python 2 / 3
What will be the output of the code below in Python 2? Explain your answer.
```python
def div1(x,y):
    print "%s/%s = %s" % (x, y, x/y)
    
def div2(x,y):
    print "%s//%s = %s" % (x, y, x//y)

div1(5,2)
div1(5.,2)
div2(5,2)
div2(5.,2.)
```
By default, Python 2 automatically performs integer arithmetic if both operands are integers. As a result, 5/2 yields 2, while 5./2 yields 2.5.
fix it by using 
```python
from __future__ import division 
```

## 18 求最大树深
```python
def maxDepth(root):
	if not root:
		return 0
	return max(maxDepth(root.left), maxDepth(root.right)) + 1
```

## 19 求两棵树是否相同
```python
def isSameTree(p, q):
    if p == None and q == None:
        return True
    elif p and q :
        return p.val == q.val and isSameTree(p.left,q.left) and isSameTree(p.right,q.right)
    else :
        return False
```

## 21 单链表逆置
```python
class Node(object):
    def __init__(self, data=None, next=None):
        self.data = data
        self.next = next

link = Node(1, Node(2, Node(3, Node(4, Node(5, Node(6, Node(7, Node(8, Node(9)))))))))

def rev(link):
    pre = link
    cur = link.next
    pre.next = None
    while cur:
        tmp = cur.next
        cur.next = pre
        pre = cur
        cur = tmp
    return pre

root = rev(link)
while root:
    print root.data
    root = root.next
```