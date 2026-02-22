### **Core Concept: Red-Green-Refactor Cycle**
```
1. RED: Write a failing test first
2. GREEN: Write minimal code to pass the test
3. REFACTOR: Improve code while keeping tests green
```

### **Practical Example - Banking Transfer Function**
Let's build a transfer function using TDD:

**Step 1: Write the test FIRST (Red)**
javascript
```javascript
// transfer.test.js
describe('Transfer', () => {
  test('should transfer money between accounts', () => {
    const account1 = new Account(1000); // $1000 balance
    const account2 = new Account(500);   // $500 balance
    
    transfer(account1, account2, 200);
    
    expect(account1.balance).toBe(800);
    expect(account2.balance).toBe(700);
  });
});
// This test FAILS because transfer() doesn't exist yet
```

**Step 2: Write minimal code to pass (Green)**
javascript
```javascript
// transfer.js
function transfer(from, to, amount) {
  from.balance -= amount;
  to.balance += amount;
}
// Test now passes!
```

**Step 3: Write more tests to catch edge cases**

javascript
```javascript
test('should throw error if insufficient funds', () => {
  const account1 = new Account(100);
  const account2 = new Account(500);
  
  expect(() => transfer(account1, account2, 200))
    .toThrow('Insufficient funds');
});
// This fails! Need to add validation
```

**Step 4: Improve implementation**

javascript
```javascript
function transfer(from, to, amount) {
  if (from.balance < amount) {
    throw new Error('Insufficient funds');
  }
  from.balance -= amount;
  to.balance += amount;
}
```

### **Benefits for Banking/FinTech:**

1. **Confidence in money handling** - Every edge case is tested
2. **Documentation** - Tests show how code should work
3. **Refactoring safety** - Change implementation without breaking functionality
4. **Regression prevention** - Old bugs don't come back

### **Interview Talking Points:**

- "TDD forces you to think about edge cases BEFORE implementation"
- "In financial systems, we can't afford bugs - TDD catches them early"
- "Tests serve as living documentation of business rules"

### **Common TDD Patterns:**

**1. Arrange-Act-Assert (AAA)**
javascript
```javascript
test('should calculate interest correctly', () => {
  // Arrange
  const account = new SavingsAccount(1000, 0.05); // 5% interest
  
  // Act
  const interest = account.calculateInterest();
  
  // Assert
  expect(interest).toBe(50);
});
```

**2. Given-When-Then (BDD style)**
javascript
```javascript
test('given low balance, when withdrawing, then charge overdraft fee', () => {
  // Given
  const account = new Account(50);
  
  // When
  account.withdraw(100);
  
  // Then
  expect(account.balance).toBe(-55); // -50 + $5 overdraft fee
});
```