2025-03-06 12:31

Link:

Problem: 
You are organizing a prestigious event, and you must arrange the order in which guests arrive based on their status. The sequence is dictated by a 0-indexed string `arrival_pattern` of length `n`, consisting of the characters `'I'` meaning the next guest should have a higher status than the previous one, and `'D'` meaning the next guest should have a lower status than the previous one.

You need to create a 0-indexed string `guest_order` of length `n + 1` that satisfies the following conditions:

- `guest_order` consists of the digits `'1'` to `'9'`, where each digit represents the guest's status and is used at most once.
- If `arrival_pattern[i] == 'I'`, then `guest_order[i] < guest_order[i + 1]`.
- If `arrival_pattern[i] == 'D'`, then `guest_order[i] > guest_order[i + 1]`.

Return the [lexicographically](https://en.wikipedia.org/wiki/Lexicographic_order) smallest possible string guest_order that meets the conditions.

Intuition:
resolve sk only when arrival_pattern[i] == "I" or when i == len(arrival_pattern)
resolve sk: arrange guest order priority!

Solution:
```python
def arrange_guest_arrival_order(arrival_pattern):
    guest_order =[ 0] *  ( len( arrival_pattern ) + 1) 
    sk=[]
    c = 1 
    i =0 
    while i <len ( arrival_pattern)  +1 :
        sk.append( i)
        if  i == len(arrival_pattern) or arrival_pattern[i] == "I":
            while sk: 
                index= sk.pop ( )
                guest_order[index]  = str ( c )
                c +=1 
        i +=1 
    return ''.join( v for v in guest_order)
```
O(n) / O(n)

Tags: #stack 

RL: 

Considerations:
