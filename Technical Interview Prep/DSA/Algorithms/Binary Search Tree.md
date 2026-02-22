insertion: 
- Start at the root
- Compare the new value with the current node
- If smaller, go to the left child; if larger, go to the right child
- Repeat until you reach a null link (empty spot)
- Insert the new node at that position
The time complexity for insertion is O(h), where h is the height of the tree. In a balanced BST, this becomes O(log n), but in the worst case (skewed tree), it can degrade to O(n).

props: 
- All elements in the left subtree are less than the node's value
- All elements in the right subtree are greater than the node's value

space complexity: O(h)
- The recursive call stack during traversal/operations
- The actual storage of the tree nodes themselves requires O(n) space