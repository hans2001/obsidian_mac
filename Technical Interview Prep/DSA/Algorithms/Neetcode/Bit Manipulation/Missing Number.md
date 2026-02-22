2025-02-25 14:32

Link: https://neetcode.io/problems/missing-number

Problem: 
Given an array `nums` containing `n` integers in the range `[0, n]` without any duplicates, return the single number in the range that is missing from `nums`.

**Follow-up**: Could you implement a solution using only `O(1)` extra space complexity and `O(n)` runtime complexity?

Intuition:

Solution:
```csharp
public class Solution {
    public int MissingNumber(int[] nums) {
        int n = nums.Length;
        int xorr = n;  
        for (int i = 0; i < n; i++) {
            xorr ^= i ^ nums[i];
        }
        return xorr;
    }
}
```
O(n) / O(1)
Tags: 

RL: 

Considerations:
