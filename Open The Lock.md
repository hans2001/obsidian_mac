2025-05-10 18:02

Link: https://leetcode.com/problems/open-the-lock/description/

Problem: 
You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: `'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'`. The wheels can rotate freely and wrap around: for example we can turn `'9'` to be `'0'`, or `'0'` to be `'9'`. Each move consists of turning one wheel one slot.

The lock initially starts at `'0000'`, a string representing the state of the 4 wheels.

You are given a list of `deadends` dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.

Given a `target` representing the value of the wheels that will unlock the lock, return the minimum total number of turns required to open the lock, or -1 if it is impossible.

Intuition:

Solution:
Editorial:
```python
class Solution:
    def openLock(self, deadends: List[str], target: str) -> int:
        # Map the next slot digit for each current slot digit.
        next_slot = {
            "0": "1",
            "1": "2",
            "2": "3",
            "3": "4",
            "4": "5",
            "5": "6",
            "6": "7",
            "7": "8",
            "8": "9",
            "9": "0",
        }
        # Map the previous slot digit for each current slot digit.
        prev_slot = {
            "0": "9",
            "1": "0",
            "2": "1",
            "3": "2",
            "4": "3",
            "5": "4",
            "6": "5",
            "7": "6",
            "8": "7",
            "9": "8",
        }

        visited_combinations = set(deadends)
        pending_combinations = deque()

        turns = 0
        if "0000" in visited_combinations:
            return -1

        # Start with the initial combination '0000'.
        pending_combinations.append("0000")
        visited_combinations.add("0000")

        while pending_combinations:
            # Explore all combinations of the current level.
            curr_level_nodes_count = len(pending_combinations)
            for _ in range(curr_level_nodes_count):
                current_combination = pending_combinations.popleft()
                if current_combination == target:
                    return turns

                # Explore all possible new combinations 
                # by turning each wheel in both directions.
                for wheel in range(4):
                    new_combination = list(current_combination)
                    new_combination[wheel] = next_slot[new_combination[wheel]]
                    new_combination_str = "".join(new_combination)
                    # If the new combination is not a dead-end and 
                    # was never visited, 
                    # add it to the queue and mark it as visited.
                    if new_combination_str not in visited_combinations:
                        pending_combinations.append(new_combination_str)
                        visited_combinations.add(new_combination_str)

                    new_combination = list(current_combination)
                    new_combination[wheel] = prev_slot[new_combination[wheel]]
                    new_combination_str = "".join(new_combination)
                    
                    if new_combination_str not in visited_combinations:
                        pending_combinations.append(new_combination_str)
                        visited_combinations.add(new_combination_str)

            # We will visit next-level combinations.
            turns += 1

        # We never reached the target combination.
        return -1
```

Complexity:
Time: 
Each wheel has 10 slots and we have 4 wheels ,so number of unique combinations sums up to 10^4. 

Initializing the hashset for visited and deadends takes O(2⋅n) and O(d⋅w) respectively ,where d is number of elements in deadends ,and w is the length of the string (4).

In worst case, we have to iterate through 10^4 combinations ,and for each combination, we explore 2 * w turns (each wheel can either increment or decrement). thus i takes O(n^w * w) time

So, this approach will take O(n+(d+n^w)⋅w)=O(10+(d+10^4)⋅4)=O(4(d+10^4)) time.

Space: 
- The hash maps with n key-value pairs, and the hash set with d combinations of length w will take O(2⋅n) and O(d⋅w) space respectively.

- In the worst case, we might push all n^w unique combinations of length w in the queue and the hash set. Thus, it will take O(n^w⋅w) space.

- So, this approach will take O(n+(d+n^w)⋅w)=O(4(d+10^4)) space.

Tags: #bfs  #hash_map #string 

RL: 

Considerations:
