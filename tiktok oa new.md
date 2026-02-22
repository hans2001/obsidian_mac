Problem: 
# Q1) Bubble explosion + gravity
## What the rule means (plain)

- A cell **is eligible** if it has **≥ 2** same-color **orthogonal** (up/down/left/right) neighbors.
- In this OA, once a cell is eligible, **that cell AND its same-color orthogonal neighbors explode together in the same tick**. (It doesn’t keep cascading to neighbors-of-neighbors in the same tick unless those neighbors are directly adjacent to an eligible cell too.)

Intuition:
we first find the the eligible cells first, then mark the cell that to be exploded (eligible cell and its neighbor cells) 
then we explode all cell at the same time( turn value to 0 )
finally, we compact column to the bottom

Solution:
```python
from collections import deque

DIRS = [(1,0),(-1,0),(0,1),(0,-1)]

def explode_bubbles(board, include_adjacent=True):
    """
    board: list[list[int]] (0 = empty)
    include_adjacent=True  -> 'eligible cells AND their same-color orthogonal neighbors' explode
    include_adjacent=False -> 'only eligible cells' explode
    """
    if not board or not board[0]: 
        return board
    R, C = len(board), len(board[0])

    # 1) Find eligible cells
    eligible = [[False]*C for _ in range(R)]
    for r in range(R):
        for c in range(C):
            k = board[r][c]
            if k == 0: 
                continue
            cnt = 0
            for dr,dc in DIRS:
                nr, nc = r+dr, c+dc
                if 0 <= nr < R and 0 <= nc < C and board[nr][nc] == k:
                    cnt += 1
            if cnt >= 2:
                eligible[r][c] = True

    # 2) Build explosion mask
    explode = [[False]*C for _ in range(R)]
    if include_adjacent:
        # explode each eligible cell + its same-color orthogonal neighbors
        for r in range(R):
            for c in range(C):
                if eligible[r][c]:
                    k = board[r][c]
                    explode[r][c] = True
                    for dr,dc in DIRS:
                        nr, nc = r+dr, c+dc
                        if 0 <= nr < R and 0 <= nc < C and board[nr][nc] == k:
                            explode[nr][nc] = True
    else:
        # only the eligible cells
        explode = eligible

    # 3) Explode simultaneously
    for r in range(R):
        for c in range(C):
            if explode[r][c]:
                board[r][c] = 0

    # 4) Gravity: compact each column to the bottom
    for c in range(C):
        write = R - 1
        for r in range(R-1, -1, -1):
            if board[r][c] != 0:
                board[write][c] = board[r][c]
                write -= 1
        # fill the rest with zeros (remove old values)
        for r in range(write, -1, -1):
            board[r][c] = 0

    return board
```

Tags: #grid

# Problem 2: 
You can only **add 1** per operation. You want to transform `a[0..n-1]` into a sequence where **adjacent values differ by exactly 1**, and you’re allowed to choose either an **ascending** pattern or a **descending** pattern. (This is the interpretation that matches your earlier example and keeps the math clean. If your spec is “at most 1”, say so and I’ll give that variant too.)

Intuition:
![Output image](https://files09.oaiusercontent.com/file-5z7e3mtG5ief6smvopsZdA?se=2025-08-08T18%3A05%3A26Z&sp=r&sv=2024-08-04&sr=b&rscc=max-age%3D299%2C%20immutable%2C%20private&rscd=attachment%3B%20filename%3Dd3623c8f-1a06-4d1d-93a2-ec43a0281c3d&sig=pLY1NQOz7DH7R8lAkrRhyhdlcBkLWl8IJOBLkxS%2B3L0%3D)
### 1. `i` 的意义
`i` 就是当前位置到第 0 个位置的距离。  
因为严格差 1，所以从第 0 个位置到第 `i` 个位置，高度必须增加 `i`（递增模式）或减少 `i`（递减模式）。  
所以最终目标一定是：
- 递增：`target[i] = t + i`
- 递减：`target[i] = t - i`

### 2. 递增模式中的 `a[i] - i`
- `a[i] - i` 可以理解为：为了让第 0 个位置是 `t`，并且第 i 个位置的值 ≥ 原值 `a[i]`， 那么 `t` 至少要是 `a[i] - i`。
- 对所有位置 i 都算一遍，取 **最大值**，就是第 0 个位置的最小可行值 `Tasc`。  
    低于它，至少会有一个位置无法加到目标值。
直观比喻：每个位置给第 0 个元素“提要求”，`a[i] - i` 就是它的最低要求，我们必须满足要求最苛刻的那个点。

### 3. 递减模式中的 `a[i] + i`
- 递减模式下，`target[i] = t - i ≥ a[i]`  → `t ≥ a[i] + i`。
- 同样取所有 i 的最大值 `Tdesc`，它是递减模式下第 0 个元素的最小可行值。

### 4. 为什么取最大值能得到最小操作数
- 递增时，`t` 决定了整条直线的高度（往上平移），  
    如果低于最大要求就不合法，如果高于最大要求就多加了不必要的高度 → 成本更高。
- 所以选 **刚好卡在最高要求的那条线**，能保证所有点都满足条件，同时加的总量最少。
- 递减模式同理。

Solution:
```python
def min_ops_stepwise_exact1(a):
    n = len(a)
    S = sum(a)
    sum_i = n * (n - 1) // 2

    # Ascending base and cost
    Tasc = max(a[i] - i for i in range(n))
    cost_asc = n * Tasc + sum_i - S

    # Descending base and cost
    Tdesc = max(a[i] + i for i in range(n))
    cost_desc = n * Tdesc - sum_i - S

    if cost_asc <= cost_desc:
        t = Tasc
        target = [t + i for i in range(n)]
        return cost_asc, "ascending", target
    else:
        t = Tdesc
        target = [t - i for i in range(n)]
        return cost_desc, "descending", target
```


# Problem 3: 
## **题目重述（准确版）**

你现在的手机没电了，但你需要再使用 **t** 分钟。幸运的是，你有一批备用电池，都是满电的。
- 第 `i` 块电池能持续供电 `capacity[i]` 分钟。
- 当它耗尽后，必须 **完全充满** 才能再次使用，充满需要 `recharge[i]` 分钟。
- 电池的使用顺序是固定的（给定顺序），依次用完一块就切换到下一块。
- 如果切换到的下一块电池还在充电，就跳过它，继续切换到下一个（顺序循环）。
- 当一块电池正在充电时不能使用。
- 这个过程循环进行，直到手机使用时间达到 `t` 分钟。
要求：
- 返回在这 `t` 分钟里使用过的 **满电电池的总数**。
- 如果无法让手机连续工作满 `t` 分钟（即某个时刻所有电池都在充电且不可用），返回 `-1`。

Constraints:
- `t`：整数 (1 ≤ t ≤ 5000)
- `capacity`: 长度 ≤ 100，每个值 1 ≤ capacity[i] ≤ 100
- `recharge`: 与 `capacity` 等长，1 ≤ recharge[i] ≤ 100

Intuition:
- 有 `n` 块电池，顺序循环使用。
- 对每块电池，记录它**下次可用的时间**（初始为0，表示现在就可用）。
- 从第 0 块开始：
    - 如果当前电池可用（`current_time >= next_available[i]`），就使用它 `capacity[i]` 分钟，并更新 `next_available[i] = current_time + capacity[i] + recharge[i]`，并统计一次使用。
    - 如果不可用，就跳到下一块（顺序循环）。
    - 如果循环一圈发现所有电池都不可用 → 返回 `-1`。
- 循环直到 `current_time >= t`。

**复杂度**
- 最坏情况：每分钟可能循环检查 n 块电池 → O(t * n)，n ≤ 100，t ≤ 5000 → 50 万步，完全可接受。

Solution:
```python
def solution(t, capacity, recharge):
    n = len(capacity)
    next_available = [0] * n   # 每块电池下次可用时间
    time_used = 0
    idx = 0
    used_full = 0

    while time_used < t:
        start_idx = idx
        found = False

        # 在这一圈里找可用的电池
        while True:
            if time_used >= next_available[idx]:
                # 用这块电池
                duration = capacity[idx]
                time_used += duration
                used_full += 1
                next_available[idx] = time_used + recharge[idx]
                idx = (idx + 1) % n
                found = True
                break
            else:
                idx = (idx + 1) % n
                # 如果转回起点，说明所有电池都在充电
                if idx == start_idx:
                    return -1

        # 如果找到了就继续下一轮
        if not found:
            return -1

    return used_full
```

### **题目 1：字母大小写差值**

- **输入**：一个字符串 `typedText`，包含大小写英文字符
- **任务**：统计大写字母数量和小写字母数量的差值（大写数 - 小写数）
- **输出**：这个差值（整数）
- **示例**：`"CodeSignal"` → 大写 2 个，小写 8 个 → 差值 = `2 - 8 = -6`
    
```python
def case_diff(typedText: str) -> int:
    up = sum(ch.isupper() for ch in typedText)
    low = sum(ch.islower() for ch in typedText)
    return up - low
```
---
### **题目 2：卡牌对战**

- **输入**：两个玩家的牌堆 `playerDeckA`、`playerDeckB`（顶部到尾部的顺序），每张牌的值在 1~10 之间
- **规则**：
    - 每轮各出牌堆顶部一张牌
    - 如果 A 的牌值 ≥ B 的牌值，A 赢，把 **A 的牌 + B 的牌** 放到底部
    - 否则 B 赢，把 **B 的牌 + A 的牌** 放到底部
    - 有一方牌堆为空时结束
- **输出**：谁赢（或最终牌堆状态，具体看题目要求）

```python 
from collections import deque

def card_duel(playerDeckA, playerDeckB):
    A, B = deque(playerDeckA), deque(playerDeckB)
    # Optional: safety cap to avoid rare infinite cycles
    seen = set()

    while A and B:
        state = (tuple(A), tuple(B))
        if state in seen:  # declare draw or pick a rule; here return "Loop"
            return "Loop"
        seen.add(state)

        a, b = A.popleft(), B.popleft()
        if a >= b:
            A.append(a); A.append(b)
        else:
            B.append(b); B.append(a)

    return "A" if A else "B"
```
### **题目 3：赛车淘汰赛**

- **输入**：二维字符串数组 `laps`，每行是某一圈所有车手的“姓名+该圈用时”
- **规则**：
    - 车手的“最好圈速”是当前为止的最小圈速（数值越小越快）
    - 每圈结束时，淘汰“当前最好圈速最慢”的车手（即最好圈速数值最大的）
    - 如果有多个车手并列最慢，全部淘汰
    - 多个同时淘汰的车手，名字按字母顺序排列
- **输出**：淘汰顺序数组，最后剩下的车手或车手们排在最后
```python
def race_eliminations(laps):
    # laps: List[List["Name time"]]
    from math import inf
    best = {}                 # name -> best-so-far (min)
    alive = set()             # current competitors
    order = []                # elimination order list of names

    # Initialize alive from lap 0 names
    for token in laps[0]:
        name, t = token.rsplit(' ', 1)
        alive.add(name)

    for lap in laps:
        # update bests this lap
        for token in lap:
            name, t = token.rsplit(' ', 1)
            t = int(t)
            prev = best.get(name, inf)
            best[name] = min(prev, t)

        # compute slowest best among alive
        slowest = max(best[name] for name in alive)
        # eliminate everyone whose best == slowest
        elim = sorted([name for name in alive if best[name] == slowest])
        # if none (shouldn’t happen), continue
        if elim:
            order.extend(elim)
            alive -= set(elim)

    # Append survivors (if any) at the end, alphabetically
    if alive:
        order.extend(sorted(alive))
    return order
```

# Question 4
## **题目重述**

- 给定一个字符串 **`word`**（只包含小写英文字母）。
- 给定一个字符串列表 **`skeletons`**，每个字符串由小写英文字母和 `'-'` 组成。
- 每个 `skeleton` 的长度与 `word` 相同。

**匹配规则**：
- 如果一个 `skeleton` 能通过用其它位置的字母替换所有的 `'-'` 来变成 `word`，则认为它是 `word` 的有效骨架（skeleton）。
- 替换时的字母必须来自**同一个 skeleton 里其它位置**已有的字母（不是随便挑字母）。
- 已经有的字母必须与 `word` 对应位置的字母一致。
- 如果一个 `skeleton` 无法按规则变成 `word`，它就是无效的。

**要求**：
- 返回一个列表，包含所有能匹配 `word` 的 `skeleton`，顺序与输入相同。
- 如果没有匹配的，返回空列表。
- `skeletons` 里可能有重复项。
```python
def matching_skeletons(word, skeletons):
    res = []
    n = len(word)
    for s in skeletons:
        if len(s) != n:
            continue  # spec says equal length, but just in case

        letters = set(ch for ch in s if ch != '-')
        ok = True
        for i, ch in enumerate(s):
            if ch != '-':
                if ch != word[i]:
                    ok = False
                    break
            else:
                if word[i] not in letters:
                    ok = False
                    break
        if ok:
            res.append(s)
    return res

```


## **题目 1：统计长度为 3 且恰好有两个元音的子串**
**题目描述**：  
给定一个只包含小写字母的字符串 `text`，统计所有长度为 3 的子串中，**恰好有两个元音字母**（`a, e, i, o, u`）的个数。返回这个个数。

**解法思路**：  
用滑动窗口（长度 3）统计窗口中元音个数，移动窗口时维护计数，当窗口中元音个数等于 2 时，结果加 1。  
**时间复杂度**：O(n)  
**空间复杂度**：O(1)

```python
def count_len3_exact2_vowels(text: str) -> int:
    vowels = set("aeiou")
    n = len(text)
    if n < 3:
        return 0

    v = sum(text[i] in vowels for i in range(3))  # 第一个窗口
    ans = 1 if v == 2 else 0

    for i in range(3, n):
        if text[i-3] in vowels:
            v -= 1
        if text[i] in vowels:
            v += 1
        if v == 2:
            ans += 1
    return ans
```

## **题目 2：无人机运货的步行距离**
**题目描述**：  
数轴上从 `0` 到 `target`（`target > 0`），途中有若干充电站 `stations[i]`。  
无人机满电可飞 **最多 10 个单位距离**（例如从位置 12 可以飞到 22）。

运货规则：
1. 从当前位置 `pos`，**步行**到最近的**前方充电站**（小于 target）。如果没有前方充电站，则直接步行到 target 并结束。
2. 从该站起飞无人机运货到最远处（`min(站点位置 + 10, target)`）。
3. 如果还没到 target，则**步行**到无人机降落点取货，更新 `pos`，然后重复步骤 1。
    
**要求**：计算总共步行的距离。
```python
def walking_distance(target: int, stations: list[int]) -> int:
    return target
```


# **问题 1：绘制方形边框**  
给定一个整数 `n`，创建一个大小为 `n` 的正方形框架，表示为字符串数组。框架应由空格填充，外围由 `*` 号组成。

- 第一行和最后一行都是 `*` 号
- 中间行的首尾是 `*`，中间是空格

**输入**：整数 `n`  
**输出**：字符串数组，表示方框。  
**复杂度要求**：时间复杂度不超过 `O(n^2)`

```python
def frame(n: int) -> list[str]:
    if n == 1:
        return ["*"]
    row0 = "*" * n
    mid = "*" + " " * (n - 2) + "*"
    return [row0] + [mid for _ in range(n - 2)] + [row0]
```

# **问题 2：太空站往返任务**  
有两个空间站 **Alpha** 和 **Beta**，通过穿梭机连接：
- Alpha 到 Beta 的航程为 100 时间单位，出发时间由升序数组 `alpha2beta` 给出
- Beta 到 Alpha 也是 100 时间单位，出发时间由升序数组 `beta2alpha` 给出

你需要完成 `missions` 次任务，每次任务都需要从 Alpha 到 Beta 再返回 Alpha。你总是选择最早可用的班次。计算完成所有任务的时间。  
**保证** 给定的班次表能完成所有任务。

**输入**：
- `alpha2beta`：升序整数数组
- `beta2alpha`：升序整数数组
- `missions`：整数

**输出**：完成所有任务所需的总时间。
**复杂度要求**：  
`O(trips × (alpha2beta.length × max(alpha2beta) + beta2alpha.length × max(beta2alpha)))`

```python
def finish_time(alpha2beta: list[int], beta2alpha: list[int], missions: int) -> int:
    i = j = 0
    t = 0
    nA, nB = len(alpha2beta), len(beta2alpha)

    def next_depart(idx, arr, now):
        # advance idx to first arr[idx] >= now
        while idx < len(arr) and arr[idx] < now:
            idx += 1
        return idx

    for _ in range(missions):
        # A -> B
        i = next_depart(i, alpha2beta, t)
        t = alpha2beta[i] + 100
        i += 1

        # B -> A
        j = next_depart(j, beta2alpha, t)
        t = beta2alpha[j] + 100
        j += 1

    return t
```

# **问题 3：报纸排版**  
你有一个二维数组 `paragraphs`，其中每个段落是一个字符串数组（表示段落里的单词顺序）。  
还给定一个整数 `width` 表示每行的最大字符数（包括空格）。
**排版规则**：

1. 每个段落单独起一行开始。
2. 段落内的单词按顺序排列，单词之间用一个空格分隔。
3. 如果添加下一个单词会超过 `width`，则换行。
4. 单词不能拆分。
5. 每段落结束后，下一段落另起新行。

**输出**：  
返回一个字符串数组，每个元素是一行排好版的文本。
```python
def layout(paragraphs: list[list[str]], width: int) -> list[str]:
    out = []
    for words in paragraphs:
        line = ""
        for w in words:
            if not line:
                line = w
            elif len(line) + 1 + len(w) <= width:
                line += " " + w
            else:
                out.append(line)
                line = w
        if line:
            out.append(line)
    return out
```

# **问题 4：传送带水果配对**  
传送带上有水果，数组 `fruits` 表示每个位置上的水果种类（用整数编号）。  
你需要统计连续的区间数量，使得该区间内至少可以形成 `k` 对相同类型的水果。  
**注意**：每个位置的水果只能参与一对配对。

**输入**：
- `fruits`：整数数组
- `k`：整数

**输出**：符合条件的区间数量。
**示例**：
`fruits = [0, 1, 0, 1, 0] k = 2 输出 = 3`
解释：有三个区间能形成至少 2 对相同的水果。
```python
from collections import defaultdict

def count_subarrays_with_k_pairs(fruits: list[int], k: int) -> int:
    if k == 0:
        n = len(fruits)
        return n * (n + 1) // 2

    n = len(fruits)
    cnt = defaultdict(int)
    pairs = 0
    ans = 0
    l = 0

    for r, x in enumerate(fruits):
        c = cnt[x]
        pairs += ((c + 1) // 2) - (c // 2)
        cnt[x] = c + 1

        while pairs >= k:
            ans += n - r
            y = fruits[l]
            c = cnt[y]
            pairs += ((c - 1) // 2) - (c // 2)  # removing one y
            cnt[y] = c - 1
            l += 1

    return ans
```