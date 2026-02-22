# Problem 1: 
Bytedance, renowned for its innovative products like TikTok, is expanding its financial analytics capabilities to offer more comprehensive insights for its creators and partners. The task is to optimize a data processing pipeline for TikTok's financial analytics platform. The objective is to enhance the module to efficiently identify the longest 'good subarray' of financial metrics meeting a specific criterion.

Given an array `financialMetrics` of size `n`, where each element represents a numerical financial metric, and a threshold value `limit`, the goal is to find the maximum length of a non-empty consecutive sequence of data points in `financialMetrics` that satisfies the following condition:

- Each data point in the sequence must be greater than `(limit / length of the sequence)`. This sequence is termed a 'good subarray' for analysis. If there is no 'good subarray' in the dataset, the function should return `-1`.
```
n = 5
limit = 6
financialMetrics = [1, 3, 4, 3, 1]
```
#### FUNCTION DESCRIPTION
Complete the function `getMaxGoodSubarrayLength` in the editor. `getMaxGoodSubarrayLength` has the following parameter:
- `int limit`
- `int financialMetrics[n]`: an array of length `n`
#### RETURNS
- `int`: the maximum length of a good subarray of `financialMetrics`.

Constraints:
- `1 ≤ n ≤ 10^5`
- `1 ≤ financialMetrics[i] ≤ 10^9`
- `1 ≤ limit ≤ 10^9`

Intuition:
|Subarray|Comparison|Evaluation|
|[1]|1 < (6/1=6)|Not a good subarray|
|[3]|3 < (6/1=6)|Not a good subarray|
|[4]|4 < (6/1=6)|Not a good subarray|
|[3]|3 < (6/1=6)|Not a good subarray|
|[1]|1 < (6/1=6)|Not a good subarray|
|[1, 3]|1 < (6/2=3)|Not a good subarray|
|[3, 4]|3 < (6/2=3)|Not a good subarray|
|[4, 3]|4 < (6/2=3)|Not a good subarray|
|[3, 1]|3 < (6/2=3)|Not a good subarray|
|[1, 3, 4]|1 < (6/3=2)|Not a good subarray|
|[3, 4, 3]|3 < (6/3=2)|Not a good subarray|
|[4, 3, 1]|4 < (6/3=2)|Not a good subarray|
|[3, 1]|3 < (6/2=3)|Not a good subarray|
|[1, 3, 4, 3, 1]|1 < (6/5=1.2)|Not a good subarray|

find longest good subarray
what is a good subarray :
each data point must be greater than limit/ length of subsequence
if no good subarray found, return 1

fist we need to generate all subarray and verify if they are valid, here we can use complete search and verify arr at the base case , if yes update the global res, otherwise dont! 

when it comes to subarray ,we could always think if we can work with sliding window or stack, when it comes to finding specific target, like find the longest length ,we check if we can bs on the range of the length ,and find the max length accordingly!

when do we shrink or expand window? 
for size l, we compare min of every size l window with the me,and update rs if there is valid result, and increment L,otherwise shrink L
deque based sliding window? use single O(n) we get mi ,and determine if we should update based on if leave element is min? 

Solution:
sliding window + binary search
```python
from collections import deque

def can_find_good(metrics, limit, L):
    n = len(metrics)
    # num = limit / L, but to avoid floats use: min * L > limit
    dq = deque()  # will store indices, increasing metrics[]

    # Initialize first window
    for i in range(L):
        while dq and metrics[dq[-1]] >= metrics[i]:
            dq.pop()
        dq.append(i)
    
    # Check first window
    if metrics[dq[0]] * L > limit:
        return True

    # Slide
    for i in range(L, n):
        # pop out of range
        while dq and dq[0] <= i - L:
            dq.popleft()
        # add new
        while dq and metrics[dq[-1]] >= metrics[i]:
            dq.pop()
        dq.append(i)

        if metrics[dq[0]] * L > limit:
            return True

    return False

def getMaxGoodSubarrayLength(limit, financialMetrics):
    n = len(financialMetrics)
    lo, hi = 1, n
    best = -1

    while lo <= hi:
        mid = (lo + hi) // 2
        if can_find_good(financialMetrics, limit, mid):
            best = mid      # mid is feasible
            lo = mid + 1    # try longer
        else:
            hi = mid - 1    # try shorter

    return best
```

monotonic stack
```python
def getMaxGoodSubarrayLength(financialMetrics, limit):
    n = len(financialMetrics)
    A = financialMetrics
    
    # 1) Compute L[]: index of prev smaller element for each k
    L = [-1]*n
    stack = []
    for i, v in enumerate(A):
        while stack and A[stack[-1]] >= v:
            stack.pop()
        L[i] = stack[-1] if stack else -1
        stack.append(i)

    # 2) Compute R[]: index of next smaller element for each k
    R = [n]*n
    stack.clear()
    for i in range(n-1, -1, -1):
        v = A[i]
        while stack and A[stack[-1]] >= v:
            stack.pop()
        R[i] = stack[-1] if stack else n
        stack.append(i)

    # 3) For each k, span = R[k] - L[k] - 1. Check a[k]*span > limit.
    ans = -1
    for k in range(n):
        span = R[k] - L[k] - 1
        if A[k] * span > limit and span > ans:
            ans = span

    return ans

```

Tags: #stack  #binary_search #deque #sliding_window 

2025-08-06 13:09
# Problem 2: 
ByteDance has launched a new feature on TikTok called "TikTok Collectibles," where users can collect and trade digital cards featuring popular TikTok creators. Each creator has different categories of cards, such as rare cards for the most followed creators, special edition cards with unique designs, and interactive cards that come with exclusive video content.

ByteDance wants to create a number of collectible packs, each containing equal numbers of each type of card. To achieve this, they need to add more cards to ensure each type can be evenly distributed across the packs.  
Given the current inventory of each category of cards as an integer array `cardTypes` of size `n`, determine the minimum number of additional cards needed so that they can create more than one pack with an equal type distribution.

#### EXAMPLE
```
n = 5
cardTypes = [4, 7, 5, 11, 15]
```

In order to make 2 matching packets, the following numbers of additional cards of each type must be added: `[0, 1, 1, 1, 1]`. This sums to 4 additional cards. The numbers of cards are `[4, 8, 6, 12, 16]` and they can be divided evenly among 2 packets.  
If 3 packets are created, an additional `[2, 2, 1, 1, 0]` cards are needed, `sum = 6` items. This yields quantities `[6, 9, 6, 12, 15]`. Any number of packets ≥ 2 can be created, but creating 2 packets requires the minimum number of additional cards.

#### FUNCTION DESCRIPTION
Complete the function `cardPackets` in the editor below.
`cardPackets` has the following parameter(s):
- `int cardTypes[n]`: the quantity available of card type
#### RETURNS
- `int`: the minimum number of additional cards to add

#### INPUT FORMAT FOR CUSTOM TESTING
```
538764
```
#### SAMPLE OUTPUT
```
2
```

#### EXPLANATION
For 2 packets add: `[1, 0, 1, 0, 0]` (2 cards) to get `[4, 8, 8, 6, 4]`.
For 3 add `[0, 1, 2, 0, 2]` (5 cards) to get `[3, 9, 9, 6, 6]`.
Any number of packets ≥ 2 can be created, but making 2 packets requires the minimum number of additional cards.

#### SAMPLE CASE 1
```
6397652
```
#### SAMPLE OUTPUT
```
4
```

#### EXPLANATION
To make 2 packets, add `[1, 1, 1, 0, 1, 0]` (4 additional cards) to get `[4, 10, 8, 6, 6, 4]`.
For 3 packets, add `[0, 0, 2, 0, 1, 1]` (4 additional cards) to get `[3, 9, 9, 6, 6, 3]`.
Either of these solutions is minimal.

Constraints:
- `1 ≤ n ≤ 10^5`
- `1 ≤ cardTypes[i] ≤ 500`

Intuition:
we can binary search on the mod value (what is the range lower bound should be 2 , and upper bound should be the min number of cards from the ar)? but based on different mod value ,the number of card to be added is different? after we found a valid packet, should we continue, when do we stop? how do we measure how many cards we should be adding, how do we verify if a number has become divisible by the mod value ?

1. **v mod k** tells you how many “leftover” cards you’d have if you grouped vv into packs of size k.
2. **k−(v mod k)** is how many more you’d need to **top up** to reach the next multiple of k.
3. Wrapping that with another `… % k` handles the case when v is already a multiple of k, giving 0 instead of k.
4. Multiplying by `freq[v]` means you do that same top-up for **every** type that currently has vv cards.

use freq to keep track of the unique frequency cont, so that we can use the count(number of card types with the same remainder after modulo k) to know how many card to be added for the same group( same remainder )

### why try k until max(freq) instead of min(freq)
- **Padding makes even very large k feasible**
    - Suppose your counts are [4,7,5,11,15][4,7,5,11,15]. The minimum is 4, so naïvely you might think “I can’t make more than 4 packs, since one type only has 4 cards.”
    - **But** you’re _allowed_ to add cards! To make k=15k=15 packs, you’d pad the type that has 4 cards by 15−4=1115−4=11 new cards, the type that has 5 by 10 cards, the type that has 7 by 8 cards, and the type that has 11 by 4 cards—and the 15-card type needs 0.
    - That works (and costs ∑(15−ai)∑(15−ai​)), so there’s **no hard upper bound** at min⁡(ai)min(ai​). You can always buy your way up to larger kk.
        
- **Why stop at max⁡(ai)max(ai​)?**
    - Once kk exceeds max⁡(ai)max(ai​), _every_ type aiai​ satisfies ai<kai​<k, so you’d have to add (k−ai)(k−ai​) cards for _every_ type. That total cost is
        ∑i(k−ai)  =  n⋅k  −  ∑iai,i∑​(k−ai​)=n⋅k−i∑​ai​,
        which _increases_ linearly as kk grows.
    - So beyond k=max⁡(ai)k=max(ai​), the “add cost” never goes down again—it only goes up. There’s no point in testing k>max⁡(ai)k>max(ai​).
    - 
Solution:
```python
from collections import Counter
def cardPackets(cardTypes):
    freq    = Counter(cardTypes)
    max_val = max(freq)       # largest current count
    best    = float('inf')

    for k in range(2, max_val + 1):
        total = 0
        for v, cnt in freq.items():
            extra = (k - (v % k)) % k
            total += extra * cnt
        best = min(best, total)

    return best
```

Tags: #modulo #counter #hash_map 


2025-08-06 14:47
# problem 3
Problem: 
TikTok Viral Campaign
TikTok is a widely - used social media platform where users follow influencers who shape their content preferences. Formally, if user A follows user B, then user A is said to be influenced by user B. Once a user is influenced by a trend, they immediately propagate this information to all users who follow them.

As a brand manager tasked with designing a viral campaign, your objective is to determine the minimum number of users who need to be initially targeted with the trend, such that the information eventually reaches every user on the platform. If a user is neither an influencer nor a follower, they are still a TikTok user and must be directly introduced to the viral campaign independently.

You are given an integer n, representing the total number of TikTok users, along with two lists - influencers and followers - each of size n. For each index i, influencers[i] represents an influencer and followers[i] represents the user who follows that influencer. (If followers[i] is - 1, it indicates that the corresponding influencer has no followers.)
It is important to note the following:

Some users may not have any followers. In such cases, followers[i] will be set to - 1, indicating that the corresponding influencer is not followed by anyone.
There may be mutual follow relationships, i.e., user a may follow user b, and user b may follow user a.

Some users might appear in the influencers list but not in the followers list. In such cases, assume these influencers follow nobody.

Some users might appear in neither the influencers list nor the followers list. In such cases, assume these users neither follow nor influence anyone but are still considered individual TikTok users.

Your goal is to compute the size of the smallest set of users to whom the trend should initially be introduced, ensuring that the trend ultimately reaches every user on the platform.

## Example
n = 4 influencers = [2, 1, 3, 4] followers = [1, 3, 2, - 1]
Consider introducing the viral trend to users 4 and 1. Note that nobody follows influencer 4, so we must introduce the trend directly to influencer 4. Once the trend is introduced to influencer 1, it is propagated to influencer 3, who follows influencer 1. Influencer 3, in turn, propagates the trend to influencer 2, who follows influencer 3. As a result, the viral trend eventually reaches all the users. Thus, the minimum number of users required to initiate the trend is 2. It can be proven that it is not possible to achieve this with fewer than 2 users.

## Function Description
Complete the function findMinimumInfluencers in the editor below.
Function Parameters
```
int influencers[n]: an array where influencers[i] denotes the influencers
int followers[n]: an array where followers[i] denotes the followers such that followers[i] follows influencers[i]
```
Returns
```
int: the minimum number of influencers to whom the product should be introduced
```

Constraints:

2 ≤ n ≤ 2×10^5
1 ≤ influencers[i] ≤ n
1 ≤ followers[i] ≤ n
followers[i] is not equal to 0 for all 1 ≤ i ≤ n
followers[i] is not equal to influencers[i] for all 1 ≤ i ≤ n

Intuition:
Tarjan’s and Kosaraju’s algorithms, for finding 0 indegree component in a strongly connected graph

Solution:
```python
import sys
sys.setrecursionlimit(10**7)

def findMinimumInfluencers(n, influencers, followers):
    # 1. Build forward and reverse graphs
    adj  = [[] for _ in range(n+1)]
    radj = [[] for _ in range(n+1)]
    for B, A in zip(influencers, followers):
        if A != -1:
            adj[B].append(A)
            radj[A].append(B)

    # 2. First pass: DFS on adj to get finishing order
    visited = [False]*(n+1)
    order = []
    def dfs1(u):
        visited[u] = True
        for v in adj[u]:
            if not visited[v]:
                dfs1(v)
        order.append(u)

    for u in range(1, n+1):
        if not visited[u]:
            dfs1(u)

    # 3. Second pass: DFS on radj in reverse finishing order to label SCCs
    comp = [-1]*(n+1)
    C = 0
    def dfs2(u):
        comp[u] = C
        for v in radj[u]:
            if comp[v] < 0:
                dfs2(v)

    for u in reversed(order):
        if comp[u] < 0:
            dfs2(u)
            C += 1

    # 4. Build condensed DAG and count in-degrees
    indeg = [0]*C
    seen = set()
    for u in range(1, n+1):
        cu = comp[u]
        for v in adj[u]:
            cv = comp[v]
            if cu != cv and (cu,cv) not in seen:
                seen.add((cu,cv))
                indeg[cv] += 1

    # 5. Count how many components have zero in-degree
    return sum(1 for c in range(C) if indeg[c] == 0)

# Example
n = 4
influencers = [2,1,3,4]
followers   = [1,3,2,-1]
print(findMinimumInfluencers(n, influencers, followers))  # → 2
```

Tags: #tarzan #scc 

# Problem 4
TikTok Content Clustering: 
TikTok has to deliver video content to users in an optimal manner. The system has a sequence of video chunks, represented by an array of integers, videoChunks, where each element videoChunks[i] represents a chunk with an associated delivery cost (e.g., video quality, network usage, server load). The task is to partition the sequence of video chunks into exactly k non - empty groups, such that the total cost of all the groups is minimized. The cost of a group (subarray) is defined as the sum of the costs of its first and last video chunk in that group. 
## Example
Given n = 5, videoChunks = [7, 8, 3, 9, 6], and k = 2. An optimal way is to divide the array into two subarrays: [[7, 8], [3, 9, 6]]. Cost of first subarray = 7 + 8 = 15 Cost of second subarray = 3 + 6 = 9 Thus, the total cost is 15 + 9 = 24. It can be proven that the minimum possible total cost is 24. Hence, the minimum total cost is 24. 
## Function Description 
Complete the function getMinimumTotalCost in the editor below. 
**getMinimumTotalCost** takes the following parameters:
```
int videoChunks[n]: representing the chunks of videos to be grouped together int k: representing the number of partitions 
```

Returns
```
long: representing the minimum possible total cost to partition into non - empty groups 
```

## Constraints 
1 ≤ n ≤ 10^5
1 ≤ videoChunks[i] ≤ 10^9 
1 ≤ k ≤ n 

Sample Case 0 
Sample Input For Custom Testing |STDIN|FUNCTION| |----|----| |7|videoChunks[] 
size  n = 7| |8|
videoChunks = [8, 9, 8, 2, 9, 9, 2]| |9| |8| |2| |9| |9| |2| |3|k = 3| 

Sample Output 
31 

Explanation 
Given n = 7, videoChunks = [8, 9, 8, 2, 9, 9, 2], and k = 3. One of the optimal way is to partition the array into these three subarrays: [[8, 9, 8], [2, 9, 9], [2]]. Cost of first subarray = 8 + 8 = 16 Cost of second subarray = 2 + 9 = 11 Cost of third subarray = 2 + 2 = 4 

Total cost = 16 + 11 + 4 = 31 It can be shown that the sum of the costs cannot be less than 31. Hence, the minimum total cost is 31.

Intuition:
**总代价刚好是“端点代价”＋各切分增量之和**
- 不切分时，你一整段的代价是 `videoChunks[0] + videoChunks[n–1]`。
- 在某个位置 `i` 处切一刀，会**额外**引入增量代价：
    `videoChunks[i]   （作为左新组的尾）  + videoChunks[i+1]（作为右新组的首）`
    
- 如果要做总共 `k–1` 刀，这些增量互不重叠、完全相加，总代价就是
    `videoChunks[0] + videoChunks[n–1] + ∑(在每个切点 i 处的增量 videoChunks[i] + videoChunks[i+1])`

**增量代价之间没有交叉影响**  
一旦决定在某一对相邻元素之间切分，就固定地添加那一对的和；它不会改变其它切点的增量值，也不会影响端点代价或组内其它位置。因此，为了让 ∑增量 最小化——也就让总代价最小化——只需要在所有 `n–1` 个可能的增量里，选出最小的 `k–1` 项。

Solution:
```python
def getMinimumTotalCost(videoChunks, k):
    n = len(videoChunks)
    if k == 1:
        return videoChunks[0] + videoChunks[-1]
    # 构造相邻对代价
    cuts = [videoChunks[i] + videoChunks[i+1] for i in range(n-1)]
    cuts.sort()  # 升序
    # 取最小的 k-1 项
    return videoChunks[0] + videoChunks[-1] + sum(cuts[:k-1])
```

Tags:  #greedy 

# problem4
Problem: 
TikTok creates random clips made up of lowercase English letters.
A clip is called **“smooth”** if the difference between any two letters next to each other is not bigger than a number called **diff**. The difference between letters x and y is |x – y| in alphabet order (e.g. 'a'→0, 'b'→1, …, 'z'→25).

You are given two integers:
- **clipLength**: the number of letters in the clip
- **diff**: the maximum allowed difference between adjacent letters

**Task:** Compute how many smooth clips of length `clipLength` can be formed. Return the answer modulo 10⁹ + 7.

**Case 0:** 
2    ← clipLength
2    ← diff
Output: 
124

**Case 1:** 
2    ← clipLength
3    ← diff
Output:
170

Constraints:
1 <= diff <= 25
2 <= clipLength <=10^5

Intuition:
given the list of alphabets, we should try combination of char in clipLength to test out number of smooth clips we can reach? diff between any 2 char in the group cannot be bigger than diff
the solution can be simplified, use bottom up approach, since we can compute the char within diff in O(1) time for each char, and we should concern with the ending char of the string only, number of possibilities form this point onward would be the number of possible extension!

Solution:
```python

```

Tags: 

RL: 

Considerations:







# MC
![[Pasted image 20250806180249.png]]
![[Pasted image 20250806180254.png]]
![[Pasted image 20250806180259.png]]
![[Pasted image 20250806180304.png]]
![[Pasted image 20250806180309.png]]
![[Pasted image 20250806180314.png]]


![[Screenshot 2025-08-06 at 6.07.51 PM.png]]
![[Screenshot 2025-08-06 at 6.07.57 PM.png]]
![[Screenshot 2025-08-06 at 6.08.04 PM.png]]
![[Screenshot 2025-08-06 at 6.08.17 PM.png]]
![[Screenshot 2025-08-06 at 6.07.33 PM.png]]

