2025-07-22 11:48

Link: https://neetcode.io/problems/dota2-senate?list=neetcode250

Problem: 
In the world of Dota2, there are two parties: the **Radiant** and the **Dire**.

The Dota2 senate consists of senators coming from two parties. Now the Senate wants to decide on a change in the Dota2 game. The voting for this change is a round-based procedure. In each round, each senator can exercise one of the two rights:

- **Ban one senator's right:** A senator can make another senator lose all his rights in this and all the following rounds.
- **Announce the victory:** If this senator found the senators who still have rights to vote are all from the same party, he can announce the victory and decide on the change in the game.
    
You are given a string `senate` representing each senator's party belonging. The character `'R'` and `'D'` represent the Radiant party and the Dire party. Then if there are `n` senators, the size of the given string will be `n`.

The round-based procedure starts from the first senator to the last senator in the given order. This procedure will last until the end of voting. All the senators who have lost their rights will be skipped during the procedure.

Suppose every senator is smart enough and will play the best strategy for his own party. Predict which party will finally announce the victory and change the Dota2 game. The output should be `"Radiant"` or `"Dire"`.

**Example 1:**
```java
Input: senate = "RRDDD"

Output: "Radiant"
```

Explanation: 
- The first 'R' takes the rights of the first 'D'.
- THe second 'R' takes the rights of the second 'D'.
- The next two 'D's have lost their rights.
- The last 'D' takes the rights of the first 'R'.
- The last remaining 'R' takes the rights of the last 'D'.
- As only 'R' is left, he announces the victory.

**Example 2:**
```java
Input: senate = "RDD"

Output: "Dire"
```

**Constraints:**
- `1 <= senate.length <= 10,000`
- `senate[i]` is either `'R'` or `'D'`.

Intuition:
the order and number of each party matter, if played optimally, party a will takes the rights of the nearest opposite party, so that each party has greatest advantage. we maintain 2 queue to record the remaining party members, and append previous member to the queue if he still alive! 
the end result depends on which party has remaining members

Solution:
```python
class Solution:
    def predictPartyVictory(self, senate: str) -> str:
        D, R = deque(), deque()
        n = len(senate)

        for i, c in enumerate(senate):
            if c == 'R':
                R.append(i)
            else:
                D.append(i)

        while D and R:
            dTurn = D.popleft()
            rTurn = R.popleft()

            if rTurn < dTurn:
                R.append(rTurn + n)
            else:
                D.append(dTurn + n)

        return "Radiant" if R else "Dire"
```

Tags: #greedy 

RL: 

Considerations:
