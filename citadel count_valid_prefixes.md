def count_valid_prefixes(s: str, k: int) -> int:
    """
    计算所有非空前缀中，有多少个可以通过追加使 '10' 子序列数恰好为 k。
    """
    n = len(s)
    result = 0
    for i in range(1, n + 1):
        prefix = s[:i]
        cnt_10, count_1, count_0 = count_10_subsequences(prefix)
        if cnt_10 == k:
            result += 1
        elif k > cnt_10 and k - cnt_10 >= count_1:
            result += 1
        print(f"Prefix: {prefix}, count_10: {cnt_10}, count_1: {count_1}, count_0: {count_0}, result: {result}")
    return result
   
   Hence you can reach exactly `k` if and only if

- `k >= cnt`, and
- **either** `k == cnt` (just append no zeros),
- **or** `c1 > 0` and **`(k – cnt)` is a multiple of `c1`** (so choose `x = (k – cnt) / c1` zeros).
k =2, cnt 1 and c1 is 1, then adding one 0 can give us what we want, 
multiple mean we can add 2 zeor, when k = , and cnt is 1  and k is 4, by adding 2 0, we can hvae waht we want
 4 -2  / 1  -> 2 / 1 = 2
how do we compute the substring info after splitting, we dont have to do taht every time, just use sliding window to update the info! 
when include a new element, we update the info we have and we start from subsequence of len 1 or start from the full sequence! and we count 1 if the subsequence is a valid prefix! 
when a zero is include in the original subsequence ,cnt should be incremented by c1