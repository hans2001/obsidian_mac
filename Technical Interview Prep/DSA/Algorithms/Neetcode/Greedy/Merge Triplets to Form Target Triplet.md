2025-02-19 14:40

Link: https://neetcode.io/problems/merge-triplets-to-form-target

Problem: 
You are given a 2D array of integers `triplets`, where `triplets[i] = [ai, bi, ci]` represents the `ith` **triplet**. You are also given an array of integers `target = [x, y, z]` which is the triplet we want to obtain.

To obtain `target`, you may apply the following operation on `triplets` zero or more times:

Choose two **different** triplets `triplets[i]` and `triplets[j]` and update `triplets[j]` to become `[max(ai, aj), max(bi, bj), max(ci, cj)]`.  
* E.g. if `triplets[i] = [1, 3, 1]` and `triplets[j] = [2, 1, 2]`, `triplets[j]` will be updated to `[max(1, 2), max(3, 1), max(1, 2)] = [2,3, 2]`.

Return `true` if it is possible to obtain `target` as an **element** of `triplets`, or `false` otherwise.

Motivation:
ignore triplets that has number bigger than element at corresponding position in target, since those triplet wont ever contribute to forming the target triplet, the bigger number can only be replaced by a even bigger number or stay as it is, using those triplet would result in an dead end. Instead, we check if every position in target has a corresponding element in other triplets, if yes, we can always take that number(since it would be the maximum element at that position: the bigger ones are filtered!). 
simplify: filter the triplets that has bigger element at those positions, then find the maximum among the rest. if all of them matched, return True, otherwise, return False

greedy nature: non-candidate filtering, immediate value incorporation once target met!
condition: number in target, maximum among triplet! -> update triplet or not dont matter!

Solution:
greedy
```python
class Solution:
    def mergeTriplets(self, triplets: List[List[int]], target: List[int]) -> bool:
        good = set()

        for t in triplets:
            if t[0] > target[0] or t[1] > target[1] or t[2] > target[2]:
                continue
            for i, v in enumerate(t):
                if v == target[i]:
                    good.add(i)
        return len(good) == 3
```
O(n) / O(1)

optimized:
```python
class Solution:
    def mergeTriplets(self, triplets: List[List[int]], target: List[int]) -> bool:
        x = y = z = False
        for t in triplets:
            x |= (t[0] == target[0] and t[1] <= target[1] and t[2] <= target[2])
            y |= (t[0] <= target[0] and t[1] == target[1] and t[2] <= target[2])
            z |= (t[0] <= target[0] and t[1] <= target[1] and t[2] == target[2])
            if x and y and z:
                return True
        return False
```
O(n) / O(1)
take the xor result of each position, if all of them are true, we have a solution, otherwise not!

Tags: #greedy

RL: 

Considerations:
