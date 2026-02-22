2025-08-22 11:44

Link:

Problem: 
![[Pasted image 20250822114412.png]]

Constraints:
max stock priced: 100k

Intuition:
we should use dp to keep track of the max amount we can obtain at each day, and we should map stock options to days ,and consider all path that can result in a good result
we initialize all holding stock as 0, and we consider selling oppo and buying oppo each day, if none of the option give a new and greater amount, nothing is done! 
if selling considered and gave greater new amount, we can use the new cash to buy shares, and all stock that can be buy today should be considered, since we dont know which path would give us the best profit at the end, we only know that 


Solution:
```python
from collections import defaultdict

def parse_date(s):
    m,d,y = s.split("/")
    return (int(y), int(m), int(d))

class Solution:
    def __init__(self):
        self.amount = 1000.0
        self.rows = []

    def add_trade(self, trade):
        stock, date_str, price_str = trade
        self.rows.append((parse_date(date_str), stock, float(price_str)))

    def run(self):
        if not self.rows:
            return 0
        # group by date
        by_date = defaultdict(list)
        for dk, stock, price in self.rows:
            by_date[dk].append((stock, price))
        dates = sorted(by_date.keys())

        cash = self.amount
        # track holding: dict[stock] = equivalent cash value if liquidated today
        hold = defaultdict(lambda: float('-inf'))

        for dk in dates:
            # first, selling opportunities today
            new_cash = cash
            for stock, price in by_date[dk]:
                if hold[stock] > float('-inf'):
                    # sell what we hold
                    new_cash = max(new_cash, hold[stock] * price)
            cash = max(cash, new_cash)
            # then, buying opportunities today
            for stock, price in by_date[dk]:
                # how many shares we could buy with current cash
                shares = cash / price
                hold[stock] = max(hold[stock], shares)

        profit = cash - self.amount
        return int(round(profit))

```

Tags: #dp  #akuna_capital #stock #OA 

RL: 

Considerations:
