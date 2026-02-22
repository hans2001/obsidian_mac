2025-10-22 18:00

Link:

Problem: 

Constraints:

Intuition:

Solution:
```python
from typing import List

class Solution:
    def merge(self, nums1: List[int], m: int, nums2: List[int], n: int) -> None:
        i = m - 1          # last valid in nums1
        j = n - 1          # last in nums2
        k = m + n - 1      # write position in nums1

        while j >= 0:      # as long as nums2 has items, place the larger of nums1[i], nums2[j]
            if i >= 0 and nums1[i] > nums2[j]:
                nums1[k] = nums1[i]
                i -= 1
            else:
                nums1[k] = nums2[j]
                j -= 1
            k -= 1
```
Tags: 

RL: 

Considerations:
