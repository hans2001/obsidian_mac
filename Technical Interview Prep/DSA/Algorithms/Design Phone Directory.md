2025-04-18 17:38

Link: https://leetcode.com/problems/design-phone-directory/description/

Problem: 
Design a phone directory that initially has `maxNumbers` empty slots that can store numbers. The directory should store numbers, check if a certain slot is empty or not, and empty a given slot.

Implement the `PhoneDirectory` class:

- `PhoneDirectory(int maxNumbers)` Initializes the phone directory with the number of available slots `maxNumbers`.
- `int get()` Provides a number that is not assigned to anyone. Returns `-1` if no number is available.
- `bool check(int number)` Returns `true` if the slot `number` is available and `false` otherwise.
- `void release(int number)` Recycles or releases the slot `number`.

Intuition:
1. Initialize a hash set `slotsAvailable` having numbers from `0` till `(maxNumbers - 1)` in it.
    
2. Implementing the `get()` method:  
    If `slotsAvailable` is not empty then we pop its first element `slot` and return it.  
    Otherwise, return `-1`.
    
3. Implementing the `check(number)` method:  
    Return `true` if `number` is present in `slotsAvailable`, otherwise, return `false`.
    
4. Implementing the `release(number)` method:  
    Push `number` in `slotsAvailable`.

Solution:
```python
class PhoneDirectory:
    def __init__(self, max_numbers):
        # Hash set to store all available slots.
        self.slots_available = set(range(max_numbers))

    def get(self):
        # If the hash set is empty it means no slot is available.
        if not self.slots_available:
            return -1

        # Otherwise, pop and return the first element from the hash set.
        return self.slots_available.pop()

    def check(self, number):
        # Check if the slot at index 'number' is available or not.
        return number in self.slots_available

    def release(self, number):
        # Mark the slot 'number' as available.
        self.slots_available.add(number)
```

Tags: #ood 

RL: 

Considerations:
