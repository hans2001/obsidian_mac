2025-05-07 11:54

Link: https://leetcode.com/problems/delete-node-in-a-bst/description/

Problem: 
Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return _the **root node reference** (possibly updated) of the BST_.

Basically, the deletion can be divided into two stages:

1. Search for a node to remove.
2. If the node is found, delete the node.

Intuition:
for a node to be deleted in BST, its original position has to be replaced by either a predecessor or successor  to maintain the BST property! so as we iterate through the tree with target node value as key, we first determine either go left or right by comparing node value with key. 

After we found the node, if current node has right subtree, we have to copy its value to current value, then delete the duplicate successor node at the right( smallest node at right subtree). and we call ourself recursively, with root updated as previous node's right, and updated key as successor' s value.  if right child does not occur, we check for max nod in left subtree( predecessor ) ,and replace current node value as its value! and delete that recursively until we reach a leaf!
otherwise we are at leaf node and we can delete it safely!

As we recursively delete node, call it directly on either left or right child node reduce search space , as we know which part of the subtree they belong to! Also, since current node value has been replaced as the new key(replaced node value)!, calling on this node will result in infinite recursion! 

Solution:
recursion
```python
class Solution:
    # One step right and then always left
    def successor(self, root: TreeNode) -> int:
            root = root.right
            while root.left:
                root = root.left
            return root.val
        
    # One step left and then always right
    def predecessor(self, root: TreeNode) -> int:
        root = root.left
        while root.right:
            root = root.right
        return root.val

    def deleteNode(self, root: TreeNode, key: int) -> TreeNode:
        if not root:
            return None

        # delete from the right subtree
        if key > root.val:
            root.right = self.deleteNode(root.right, key)
        # delete from the left subtree
        elif key < root.val:
            root.left = self.deleteNode(root.left, key)
        # delete the current node
        else:
            # the node is a leaf
            if not (root.left or root.right):
                root = None
            # The node is not a leaf and has a right child
            elif root.right:
                root.val = self.successor(root)
                root.right = self.deleteNode(root.right, root.val)
            # the node is not a leaf, has no right child, and has a left child    
            else:
                root.val = self.predecessor(root)
                root.left = self.deleteNode(root.left, root.val)
                        
        return root
```

Complexity:
Time: 
O(logN). 
The first call on target node takes O(h1) time, where h1 is height from root to target node. the next and continuous call on delete_node function takes O(h2) time, as the height from target node to the leaves! 

Total time takes O( h1+h2 ) = O(h), which equals to O(logN) time complexity

Space: 
O(H) to keep the recursion stack

Tags: #binary_search_tree #predecessor #successor #recursion 

RL: 

Considerations:
basic facts about BST:
Inorder traversal show ascending order of BST
```python
def inorder(root: Optional[TreeNode]) -> List:
    return inorder(root.left) + [root.val] + inorder(root.right) if root else []
```

successor is the smallest node in the right subtree (the node right after current node)
```python
def successor(root: TreeNode) -> TreeNode:
    root = root.right
    while root.left:
        root = root.left
    return root
```
we goes right once, then goes to left as deep as possible until reach leaf

predecessor is the largest node in left subtree (the node before current node)
```python
def predecessor(root: TreeNode) -> TreeNode:
    root = root.left
    while root.right:
        root = root.right
    return root
```
goes left once, then goes to right! 