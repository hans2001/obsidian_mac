1. Finding element that appear more than n/2 times ( n is length of input array )
maintain one candidate and swap cand when count reach zero! since the majority element will not be canceled out! 
in second pass, we count the number of appearance of candidate we obtain in the first pass, and check if it actually passed n/2 times!

2. Finding element that appear more than n/3 times ( n is length of input array )
maintain 2 candidate and swap candidate if there is an empty slot( one of the count is zero!)

## Considerations: 
### Why k-1 Candidates?
it is proven mathematically that if a candidate appear morn than n/k times, it will be found in one of the k-1 containers after the first pass! 
At most k-1 distinct elements can appear more than n/k time!
if had k such elements, their combined occurrence would exceed n (k × n/k = n).

### Why Decrement All Candidates?
the problem is viewed as having k-1 containers and a trash bag. if an element not in any container, we put it in the trash bag along with one element from each container! 

1. If an element appears more than n/k times, it can have at most n/k occurrences thrown in the trash
2. Since it appears more than n/k times, at least one occurrence will remain to be counted

### Can We Miss a Candidate?

The "cancellation" mechanism ensures that frequent elements survive:

- An element appearing more than n/k times cannot be completely eliminated
- At most n/k occurrences of any element can be "cancelled out"
- For an element with frequency > n/k, at least one occurrence will remain