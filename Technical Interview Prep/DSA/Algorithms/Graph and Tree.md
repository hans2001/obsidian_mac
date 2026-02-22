## tree
树(tree)是一种能够分层存储数据的重要数据结构，树中的每个元素被称为树的节点，每个节点有若干个指针指向子节点。从节点的角度来看，树是由唯一的起始节点引出的节点集合。这个起始结点称为根(root)。树中节点的子树数目称为节点的度(degree)。在面试中，关于树的面试问题非常常见，尤其是关于二叉树(binary tree)，二叉搜索树(Binary Search Tree, BST)的问题。

所谓的二叉树，是指对于树中的每个节点而言，至多有左右两个子节点，即任意节点的度小于等于2。而广义的树则没有如上限制。二叉树是最常见的树形结构。二分查找树是二叉树的一种特例，对于二分查找树的任意节点，该节点存储的数值一定比左子树的所有节点的值大比右子树的所有节点的值小“(与之完全对称的情况也是有效的：即该节点存储的数值一定比左子树的所有节点的值小比右子树的所有节点的值大)。

基于这个特性，二分查找树通常被用于维护ordered data。二分查找树search,dletion,insert!的效率都会于一般的线性数据结构。事实上，对于二分查找树的操作相当于执行二分搜索，其执行效率与树的高度(depth)有关，检索任意数据的比较次数不会多于树的高度。这里需要引入高度的概念：
对一棵树而言，从根节点到某个节点的路径长度称为该节点的层数(level)，根节点为第0层，非根节点的层数是其父节点的层数加1。树的高度定义为该树中层数最大的叶节点的层数加1，即相当于于从根节点到叶节点的最长路径加1。
由此，对于n个数据，二分查找树应该以“尽可能小的高度存储所有数据。由于二叉树第L层至多可以存储 2L 个节点，故树的高度应在logn量级，因此，二分查找树的搜索效率为O(logn)。(balanced bst -> best case)

直观上看，尽可能地把二分查找树的每一层“塞满”数据可以使得搜索效率最高，但考虑到每次插入删除都需要维护二分查找树的性质，要实现这点并不容易。特别地，当二分查找树退化为一个由小到大排列的单链表(skewed BST)，其搜索效率变为O(n)(worst)。
为了解决这样的问题，人们引入平衡二叉树的概念。所谓平衡二叉树，是指一棵树的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。通过恰当的构造与调整，平衡二叉树能够保证每次插入删除之后都保持平衡性。平衡二叉树的具体实现算法包括**AVL算法和红黑算法**等。由于平衡二叉树的实现比较复杂，故一般面试官只会问些概念性的问题。

## 堆与优先队列

通常所说的堆(Heap)是指二叉堆，从结构上说是完全二叉树，从实现上说一般用数组。以数组的下标建立父子节点关系：对于下标为i的节点，其父节点为(int)i/2，其左子节点为2i，右子节点为2i+1。堆最重要的性质是，它满足部分有序(partial order)：最大(小)堆的父节点一定大于等于(小于等于)当前节点，且堆顶元素一定是当前所有元素的最大(小)值。

堆算法的核心在于插入，删除算法如何保持堆的性质(以下讨论均以最大堆为例):

下移(shift-down)操作：下移是堆算法的核心。对于最大值堆而言，对于某个节点的下移操作相当于比较当前节点与其左右子节点的相对大小。如果当前节点小于其子节点，则将当前节点与其左右子节点中较大的子节点对换，直至操作无法进行(即当前节点大于其左右子节点)。

建堆：假设堆数组长度为n，建堆过程如下，注意这里数组的下标是从 1 开始的：

```
for i, n/2 downto 1
    do shift-down(A,i)
```

插入：将新元素插入堆的末尾，并且与父节点进行比较，如果新节点的值大于父节点，则与之交换，即上移(shift-up)，直至操作无法进行。

弹出堆顶元素：弹出堆顶元素(假设记为A[1]，堆尾元素记为A[n])并维护堆性质的过程如下：

```
output = A[1]
exchange A[1] <-> A[n]
heap size -= 1
shift-down(A,1)
return output
```

值得注意的是，heap 的插入操作逐层上移，耗时**O(log(n))**，与二叉搜索树的插入相同。但建堆通过下移所有非叶子节点(下标n/2至1)实现，耗时O(n)，小于BST的O(nlog(n))。

# graph 
图(Graph)是节点集合的一个拓扑结构，节点之间通过边相连。图分为有向图和无向图。有向图的边具有指向性，即AB仅表示由A到B的路径，但并不意味着B可以连到A。与之对应地，无向图的每条边都表示一条双向路径。

图的数据表示方式也分为两种，即邻接表(adjacency list)和邻接矩阵(adjacency matrix)。对于节点A，A的邻接表将与A之间相连的所有节点以链表的形势存储起来，节点A为链表的头节点。这样，对于有V个节点的图而言，邻接表表示法包含V个链表。因此，链接表需要的空间复杂度为O(V+E)。邻接表适用于边数不多的稀疏图。但是，如果要确定图中边(u, v)是否存在，则只能在节点u对应的邻接表中以O(E)复杂度线性搜索。

对于有V个节点的图而言，邻接矩阵用V*V的二维矩阵形式表示一个图。矩阵中的元素Aij表示节点i到节点j之间是否直接有边相连。若有，则Aij数值为该边的权值，否则Aij数值为0。特别地，对于无向图，由于边的双向性，其邻接矩阵的转置矩阵为其本身。邻接矩阵的空间复杂度为O(V2 )，适用于边较为密集的图。邻接矩阵在检索两个节点之间是否有边相连这样一个需求上，具有优势。
# toolbox:
### 二叉树类
一个最基本的二叉树类可以如下定义：
```python
class TreeNode:
    def __init__(self, val):
        self.left = None
        self.right = None
        self.parent = None
        self.val = val

class BinarySearchTree:
    def __init__(self, root_value=None):
        if root_value is not None:
            self.root = TreeNode(root_value)
        else:
            self.root = None
    
    def insert_node_with_value(self, value):
        """Insert a new node with the given value into the BST."""
        if self.root is None:
            self.root = TreeNode(value)
            return True
        
        current = self.root
        while True:
            # Don't insert duplicates
            if value == current.val:
                return False
            
            # Go left
            elif value < current.val:
                if current.left is None:
                    current.left = TreeNode(value)
                    current.left.parent = current
                    return True
                current = current.left
            
            # Go right
            else:
                if current.right is None:
                    current.right = TreeNode(value)
                    current.right.parent = current
                    return True
                current = current.right
    
    def delete_node_with_value(self, value):
        """Delete a node with the given value from the BST."""
        # Find the node to delete
        current = self.root
        while current is not None and current.val != value:
            if value < current.val:
                current = current.left
            else:
                current = current.right
                
        if current is None:  # Value not found
            return False
            
        # Case 1: Node is a leaf
        if current.left is None and current.right is None:
            self._delete_leaf(current)
            
        # Case 2: Node has one child
        elif current.left is None:
            self._replace_node(current, current.right)
        elif current.right is None:
            self._replace_node(current, current.left)
            
        # Case 3: Node has two children
        else:
            # Find in-order successor (minimum in right subtree)
            successor = current.right
            while successor.left is not None:
                successor = successor.left
                
            # Copy successor value to current node
            current.val = successor.val
            
            # Delete successor
            if successor.right is None:
                self._delete_leaf(successor)
            else:
                self._replace_node(successor, successor.right)
                
        return True
    
    def _delete_leaf(self, node):
        if node.parent is None:  # Node is root
            self.root = None
        elif node is node.parent.left:  # Node is left child
            node.parent.left = None
        else:  # Node is right child
            node.parent.right = None
    
    def _replace_node(self, node, replacement):
        if node.parent is None:  # Node is root
            self.root = replacement
            if replacement is not None:
                replacement.parent = None
        elif node is node.parent.left:  # Node is left child
            node.parent.left = replacement
            if replacement is not None:
                replacement.parent = node.parent
        else:  # Node is right child
            node.parent.right = replacement
            if replacement is not None:
                replacement.parent = node.parent
    
    def print_tree(self):
        """Print the tree in-order (sorted order for BST)."""
        if self.root is None:
            print("Empty tree")
            return
        
        print("In-order traversal:")
        self._print_in_order(self.root)
        print()
    
    def _print_in_order(self, node):
        if node is not None:
            self._print_in_order(node.left)
            print(node.val, end=" ")
            self._print_in_order(node.right)
```

Trie
```python
class TrieNode:
    def __init__(self):
        self.children = {}
        self.endOfWord = False

class PrefixTree:
    def __init__(self):
        self.root = TrieNode()

    def insert(self, word: str) -> None:
        cur = self.root
        for c in word:
            if c not in cur.children:
                cur.children[c] = TrieNode()
            cur = cur.children[c]
        cur.endOfWord = True

    def search(self, word: str) -> bool:
        cur = self.root
        for c in word:
            if c not in cur.children:
                return False
            cur = cur.children[c]
        return cur.endOfWord

    def startsWith(self, prefix: str) -> bool:
        cur = self.root
        for c in prefix:
            if c not in cur.children:
                return False
            cur = cur.children[c]
        return True
```
字典树的基本功能如下：

1) void addWord(string key, int value);
添加一个键:值对。添加时从根节点出发，如果在第i层找到了字符串的第i个字母，则沿该节点方向下降一层(注意，如果下一层存储的是数据，则视为没有找到)。否则，将第i个字母作为新的兄弟插入到第i层。将键插入完成后插入值节点。

2) bool searchWord(string key, int &value);
查找某个键是否存在，并返回值。从根节点出发，在第i层寻找字符串中第i个字母是否存在。如果是，沿着该节点方向下降一层；否则，返回false。

3) void deleteWord(string key)
删除一个键:值对。删除时从底层向上删除节点，“直到遇到第一个有兄弟的节点(说明该节点向上都是与其他节点共享的前缀)，删除该节点

## 单源最短路径问题
对于每条边都有一个权值的图来说，单源最短路径问题是指从某个节点出发，到其他节点的最短距离。该问题的常见算法有Bellman-Ford和Dijkstra算法。前者适用于一般情况(包括存在负权值的情况，但不存在从源点可达的负权值回路)，后者仅适用于均为非负权值边的情况。Dijkstra的运行时间可以小于Bellman-Ford。本小节重点介绍Dijkstra算法。

特别地，如果每条边权值相同(无权图)，由于从源开始访问图遇到节点的最小深度 就等于到该节点的最短路径，因此 Priority Queue就退化成Queue，Dijkstra算法就退化成BFS。

Dijkstra的核心在于，构造一个节点集合S，对于S中的每一个节点，源点到该节点的最短距离已经确定。进一步地，对于不在S中的节点，“我们总是选择其中到源点最近的节点，将它加入S，并且更新其邻近节点到源点的距离。算法实现时需要依赖优先队列。一句话总结，Dijkstra算法利用贪心的思想，在剩下的节点中选取离源点最近的那个加入集合，并且更新其邻近节点到源点的距离，直至所有节点都被加入集合。关于Dijkstra算法的正确性分析，可以使用数学归纳法证明，详见《算法导论》第24章，单源最短路径。 给出伪代码如下：

```
DIJKSTRA(G, s)
S = EMPTY
Insert all vertexes into Q
While Q is not empty
    u = Q.top
    S.insert(u)
    For each v is the neighbor of u
        If d[v] > d[u] + weight(u, v)
            d[v] = d[u] + weight(u, v)
            parent[v] = u
```

## 任意两点之间的最短距离

另一个关于图常见的算法是，如何获得任意两点之间的最短距离(All-pairs shortest paths)。直观的想法是，可以对于每个节点运行Dijkstra算法，该方法可行，但更适合的算法是Floyd-Warshall算法。

Floyd算法的核心是动态编程，利用二维矩阵存储i，j之间的最短距离，矩阵的初始值为i，j之间的权值，如果i，j不直接相连，则值为正无穷。动态编程的递归式为：d(k)ij = min(d(k-1)ij, d(k-1)ik+ d(k-1)kj) (1<= k <= n)。直观上理解，对于第k次更新，我们比较从i到j只经过节点编号小于k的中间节点(d(k-1)ij)，和从i到k，从k到j的距离之和(d(k-1)ik+ d(k-1)kj)。Floyd算法的复杂度是O(n^3)。关于Floyd算法的理论分析，请见《算法导论》第25章，每对顶点间的最短路径。 给出伪代码如下：

```
FLOYD(G)
Distance(0) = Weight(G)
For k = 1 to n
    For i = 1 to n
        For j = 1 to n
Distance(k)ij = min(Distance (k-1)ij, Distance (k-1)ik+ Distance(k-1)kj)  
Return Distance(n)
```

