### **Core Concept: Model your software after the business domain**
DDD is about creating a shared language between developers and business experts. In banking, this is CRUCIAL.

### **Key Building Blocks:**
**1. Entities - Objects with identity**
javascript
```javascript
class Account {
  constructor(accountNumber) {
    this.accountNumber = accountNumber; // Identity
    this.balance = 0;
    this.transactions = [];
  }
  
  // Two accounts with same balance are different if accountNumber differs
  equals(other) {
    return this.accountNumber === other.accountNumber;
  }
}
```

**2. Value Objects - Objects without identity**
javascript
```javascript
class Money {
  constructor(amount, currency) {
    this.amount = amount;
    this.currency = currency;
  }
  
  // Two money objects with same amount/currency are equal
  equals(other) {
    return this.amount === other.amount && 
           this.currency === other.currency;
  }
  
  // Value objects are immutable
  add(other) {
    if (this.currency !== other.currency) {
      throw new Error('Currency mismatch');
    }
    return new Money(this.amount + other.amount, this.currency);
  }
}
```

**3. Aggregates - Cluster of related objects**
javascript
```javascript
// Account is the aggregate root
class Account {
  constructor(accountNumber, customerId) {
    this.accountNumber = accountNumber;
    this.customerId = customerId;
    this.transactions = []; // Only accessible through Account
  }
  
  // All transaction operations go through the aggregate root
  addTransaction(transaction) {
    // Business rules enforced here
    if (this.balance + transaction.amount < 0) {
      throw new Error('Insufficient funds');
    }
    this.transactions.push(transaction);
  }
  
  // Don't expose internal collections directly
  getTransactions() {
    return [...this.transactions]; // Return copy
  }
}
```

**4. Bounded Contexts - Separate models for different parts of the business**
javascript
```javascript
// In "Accounts" bounded context
class Account {
  constructor(accountNumber, balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
  }
}

// In "Lending" bounded context - same concept, different model
class LoanAccount {
  constructor(accountNumber, principal, interestRate) {
    this.accountNumber = accountNumber;
    this.principal = principal;
    this.interestRate = interestRate;
    this.remainingBalance = principal;
  }
}

// In "Marketing" bounded context - different view of same data
class CustomerAccount {
  constructor(accountNumber, customerSegment, lifetimeValue) {
    this.accountNumber = accountNumber;
    this.customerSegment = customerSegment;
    this.lifetimeValue = lifetimeValue;
  }
}
```

### **Domain Services - Business logic that doesn't belong to one entity**
javascript
```javascript
// Transfer involves two accounts - doesn't belong to either
class TransferService {
  transfer(fromAccount, toAccount, amount) {
    // Complex business logic
    if (fromAccount.type === 'SAVINGS' && 
        this.getMonthlyTransferCount(fromAccount) >= 6) {
      throw new Error('Exceeded monthly transfer limit');
    }
    
    // Coordinate between aggregates
    fromAccount.withdraw(amount);
    toAccount.deposit(amount);
    
    // Emit domain event
    this.eventBus.emit(new TransferCompleted(fromAccount, toAccount, amount));
  }
}
```

### **Ubiquitous Language - Shared vocabulary**
javascript
```javascript
// Bad - Technical terms
function updateAccountBalance(accountId, delta) {
  const account = db.find(accountId);
  account.balance += delta;
  db.save(account);
}

// Good - Business terms
function depositToAccount(accountNumber, amount) {
  const account = accountRepository.findByNumber(accountNumber);
  account.deposit(amount);
  accountRepository.save(account);
}
```

### **Repository Pattern - Abstract data access**
javascript
```javascript
// Domain layer - pure business logic
interface AccountRepository {
  findByNumber(accountNumber);
  save(account);
}

// Infrastructure layer - actual implementation
class MongoAccountRepository implements AccountRepository {
  async findByNumber(accountNumber) {
    const data = await this.collection.findOne({ accountNumber });
    return new Account(data.accountNumber, data.balance);
  }
  
  async save(account) {
    await this.collection.updateOne(
      { accountNumber: account.accountNumber },
      { $set: { balance: account.balance } }
    );
  }
}
```

### **Real Banking Example - Loan Approval Process**
javascript
```javascript
// Aggregate
class LoanApplication {
  constructor(applicationId, customerId) {
    this.applicationId = applicationId;
    this.customerId = customerId;
    this.status = 'PENDING';
    this.creditChecks = [];
    this.documents = [];
  }
  
  // Business rules encapsulated
  submit() {
    if (!this.hasRequiredDocuments()) {
      throw new Error('Missing required documents');
    }
    if (!this.hasPassedCreditCheck()) {
      throw new Error('Credit check not completed');
    }
    this.status = 'SUBMITTED';
    // Emit domain event
    DomainEvents.raise(new LoanApplicationSubmitted(this));
  }
  
  approve(approver) {
    if (this.status !== 'SUBMITTED') {
      throw new Error('Can only approve submitted applications');
    }
    if (!approver.canApproveLoan(this.amount)) {
      throw new Error('Insufficient approval authority');
    }
    this.status = 'APPROVED';
    this.approvedBy = approver.id;
    this.approvedAt = new Date();
  }
}

// Value Object
class CreditScore {
  constructor(score, agency) {
    if (score < 300 || score > 850) {
      throw new Error('Invalid credit score');
    }
    this.score = score;
    this.agency = agency;
  }
  
  isGood() {
    return this.score >= 700;
  }
}

// Domain Service
class LoanUnderwritingService {
  assessApplication(application, creditScore, incomeVerification) {
    const debtToIncomeRatio = this.calculateDTI(
      application, 
      incomeVerification
    );
    
    if (creditScore.isGood() && debtToIncomeRatio < 0.43) {
      return new UnderwritingDecision('APPROVED', 'Auto-approved');
    }
    
    return new UnderwritingDecision('MANUAL_REVIEW', 'Requires review');
  }
}
```