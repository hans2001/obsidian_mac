2025-05-14 13:01

Link: https://leetcode.com/problems/accounts-merge/description/

Problem: 
Given a list of `accounts` where each element `accounts[i]` is a list of strings, where the first element `accounts[i][0]` is a name, and the rest of the elements are **emails** representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails **in sorted order**. The accounts themselves can be returned in **any order**.

Intuition:
use index to track each account, when common email found (email already exist in dictionary), we joined the current account with account attached with the existing email.
after disjoin union set is set up, we build an additional dictionary where parent node attach to the list of emails, and we build the final array with user name and emails from unique  connected components! 
user name is not a indicator for same account, but might indicate that! 
dsu initialization is always number based? 

Solution:
disjoint set union
```python
class DSU:
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank = [1] * n
    
    def find(self, node):
        cur = node
        while cur != self.parent[cur]:
            self.parent[cur] = self.parent[self.parent[cur]]
            cur = self.parent[cur]
        return cur
    
    def union(self, node1, node2):
        pa1 = self.find(node1)
        pa2 = self.find(node2)
        if pa1 == pa2:
            return
        if self.rank[pa1] < self.rank[pa2]:
            pa1, pa2 = pa2, pa1
        self.parent[pa2] = pa1
        self.rank[pa1] += self.rank[pa2]
        return

from collections import defaultdict

class Solution:
    def accountsMerge(self, accounts: List[List[str]]) -> List[List[str]]:
        email_index = {}  # Maps email to account index
        account_to_name = {}  # Maps account index to name
        dsu = DSU(len(accounts))
        
        # First pass: record emails and perform unions
        for i in range(len(accounts)):
            account = accounts[i]
            account_to_name[i] = account[0]
            
            for j in range(1, len(account)):
                if account[j] in email_index:
                    dsu.union(email_index[account[j]], i)
                else:
                    email_index[account[j]] = i
        
        # Group emails by representative account
        group_emails = defaultdict(set)
        for email, account_idx in email_index.items():
            pa = dsu.find(account_idx)
            group_emails[pa].add(email)
        
        # Build the final result
        rs = []
        for group_id in group_emails:
            rs.append([account_to_name[group_id]] + sorted(list(group_emails[group_id])))
        return rs
```

DFS: 
```python
from collections import defaultdict

class Solution:
    def accountsMerge(self, accounts: List[List[str]]) -> List[List[str]]:
        # Build the email graph
        email_graph = defaultdict(set)  # email -> set of connected emails
        email_to_name = {}  # email -> account name
        
        # Process all accounts
        for account in accounts:
            name = account[0]
            emails = account[1:]
            
            # Skip accounts with no emails
            if not emails:
                continue
                
            # Record the name for each email (we'll use any name for duplicate people)
            for email in emails:
                email_to_name[email] = name
            
            # Connect all emails in this account with each other
            for i in range(len(emails)):
                for j in range(i + 1, len(emails)):
                    # Add bidirectional edges
                    email_graph[emails[i]].add(emails[j])
                    email_graph[emails[j]].add(emails[i])
        
        # DFS to find connected components
        visited = set()
        result = []
        
        def dfs(email, component):
            visited.add(email)
            component.add(email)
            
            for neighbor in email_graph[email]:
                if neighbor not in visited:
                    dfs(neighbor, component)
        
        # Find all connected components
        for email in email_to_name:
            if email not in visited:
                component = set()
                dfs(email, component)
                
                # Get name from any email in the component
                name = email_to_name[email]
                result.append([name] + sorted(list(component)))
        
        return result
```

Complexity:
Time: 
DSU: 
O(n log n)
E > N
N is number of accounts , E is total number of emails
- Grouping emails by representative: O(E)
- Build the dsu takes O(n) time
- Sorting emails in each group: O(E log E) in the worst case
- Overall: O(E log E) dominates, which is roughly O(N log N) since E is at most 10N

DFS:
O(E²) (undirected graph)
- Building the adjacency list: O(E²) in the worst case (if all emails are in one account)
- email_to_name map: O(E)
- DFS traversal: O(E + V) = O(E) since V (number of vertices/emails) ≤ E
- Sorting emails in each component: O(E log E) in the worst case
- Overall: O(E² + E log E) = O(E²) in the theoretical worst case

**DSU solution has better time complexity**

Space: 
DSU: 
- DSU data structure: O(N)
- email_index map: O(E)
- account_to_name map: O(N)
- group_emails map: O(E)
- Result array: O(E)

DFS:
- Adjacency list (email_graph): O(E)
- email_to_name map: O(E)
- visited set: O(E)
- Result array: O(E)

**Similar Space complexity** 

Tags: #disjoin_set_union #dfs #hash_map #minimum_height_tree 

RL: 

Considerations:
