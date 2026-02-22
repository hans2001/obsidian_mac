concept review: https://leetcode.com/discuss/post/3828150/oops-cheatsheet-for-interviews-30-questi-7nt4/
# Immutable class
```python
from dataclasses import dataclass

@dataclass(frozen=True)
class Point:
    x: int
    y: int
```
# Interfaces in python
```python
from typing import Protocol

class Notifier(Protocol):
    def send(self, msg: str) -> None: ...

class Emailer:
    def send(self, msg: str) -> None:
        print("Email:", msg)

def alert(n: Notifier):
    n.send("System down")

alert(Emailer())  # OK because Emailer matches the Protocol shape
```
alert function taking a consumer object? 
# Inheritance
```python
class Animal:
    def __init__(self, name: str):
        self.name = name

    def speak(self) -> str:
        raise NotImplementedError

class Dog(Animal):
    def __init__(self, name: str, breed: str):
        super().__init__(name)     # call parent initializer
        self.breed = breed

    def speak(self) -> str:
        return f"{self.name}: woof!"
```

Composition over inheritance
no ABC needed, just define the interface and verify the input consumer object for function variables or object variables
```python
from typing import Protocol

class Storage(Protocol):
    def save(self, data): ...
    def load(self, key): ...

class DatabaseStorage:
    def __init__(self):
        self._db = {}
    def save(self, data):
        self._db[data["id"]] = data
    def load(self, key):
        return self._db.get(key)

class CacheWrapper:
    def __init__(self, backend: Storage):
        self.backend = backend
        self.cache = {}

    def save(self, data):
        self.cache[data["id"]] = data
        self.backend.save(data)

    def load(self, key):
        if key in self.cache:
            return self.cache[key]
        data = self.backend.load(key)
        if data:
            self.cache[key] = data
        return data

# Plug components together
db = DatabaseStorage()
cached_db = CacheWrapper(db)

cached_db.save({"id": 1, "name": "Alice"})
print(cached_db.load(1))

```

**Liskov Substitution Principle (LSP):**  
Subclasses must be usable wherever their base class is expected — without changing the program’s correctness.  
If overriding breaks that expectation → violation

**Motivation:**
“Inheritance is fine when the behavior truly fits an ‘is-a’ relationship and when I want shared implementation, but composition gives me more flexibility and avoids tight coupling.”

# Classes
### Abstract classes
```python
from abc import ABC, abstractmethod

class Repository(ABC):
    @abstractmethod
    def get(self, id: str): ...
    @abstractmethod
    def add(self, obj) -> None: ...
    # optional default behavior
    def exists(self, id: str) -> bool:
        return self.get(id) is not None
```
### Concrete class: ```
```python
class InMemoryRepo(Repository):
    def __init__(self):
        self._store = {}
    def get(self, id: str):
        return self._store.get(id)
    def add(self, obj) -> None:
        self._store[obj["id"]] = obj
```

# Encapsulation
```python
class BankAccount:
    def __init__(self, owner: str, balance: float = 0.0):
        self.owner = owner           # public
        self._currency = "USD"       # internal convention
        self.__balance = balance     # name-mangled (private-ish)

    @property
    def balance(self) -> float:
        return self.__balance

    @balance.setter
    def balance(self, amount: float):
        if amount < 0:
            raise ValueError("Balance cannot be negative directly")
        self.__balance = amount

    def deposit(self, amount: float):
        self.__check_amount(amount)
        self.__balance += amount

    def _internal_note(self):        # “protected” by convention
        return "for subclasses"

    def __check_amount(self, amount: float):  # private-ish helper
        if amount <= 0:
            raise ValueError("Amount must be positive")
```


ABC + protocol( interface )
```python
from abc import ABC, abstractmethod
from typing import Protocol

@runtime_checkable
class Storage(Protocol):
    def get(self, key: str) -> bytes | None: ...
    def put(self, key: str, value: bytes) -> None: ...

# ABC that provides shared behavior (e.g., key normalization)
class BaseStorage(ABC):
    def _norm(self, key: str) -> str:
        return key.strip().lower()

    @abstractmethod
    def get(self, key: str) -> bytes | None: ...
    @abstractmethod
    def put(self, key: str, value: bytes) -> None: ...

# Concrete implementation
class MemoryStorage(BaseStorage):
    def __init__(self):
        self._m = {}

    def get(self, key: str) -> bytes | None:
        return self._m.get(self._norm(key))

    def put(self, key: str, value: bytes) -> None:
        self._m[self._norm(key)] = value

# Anything expecting the interface can use MemoryStorage
def cache_avatar(store: Storage, username: str, blob: bytes):
    store.put(f"avatar:{username}", blob)

cache_avatar(MemoryStorage(), "Ada", b"...")
assert isinstance(MemoryStorae(), Storage)      # OK at runtime because of
```

Typical ARCH:
```python
class AvatarService:
    def __init__(self, store: Storage):
        self.store = store

    def upload(self, username: str, blob: bytes):
        self.store.put(f"avatar:{username}", blob)

    def download(self, username: str) -> bytes | None:
        return self.store.get(f"avatar:{username}")

# Dependency injection at the edge
service = AvatarService(MemoryStorage())
```

# Pythonic concepts: 
**Dependency Injection (DI)**: 
“Don’t create your dependencies inside a class — pass them in from the outside.”
Benefits: 
- **Testable:** inject fake or mock objects easily.
- **Flexible:** swap real dependencies without touching core logic.
- **Decoupled:** each class does one job and doesn’t “know” how others work.

Example
```python
class Storage(Protocol):
    def save(self, data): ...
    def load(self, key): ...

class Database:
    def __init__(self):
        self._db = {}
    def save(self, data):
        self._db[data["id"]] = data
    def load(self, key):
        return self._db.get(key)
        
class UserService:
    def __init__(self):
        self.db = Database()

You do this 👇
class UserService:
    def __init__(self, db: Storage):
        self.db = db  # injected dependency ✅
```
Now `UserService` **depends on an abstraction**, not a specific implementation.

```python
UserService(Database())
UserService(MockDatabase())
UserService(CachedDatabase(Database()))
```
That’s **composition**: the service _has a_ database (not _is a_ one).
And that’s **dependency injection**: you inject the dependency instead of hardwiring it.

# Polymorphism (extensible principle)
```python
class Dog:
    def speak(self):
        return "woof"

class Cat:
    def speak(self):
        return "meow"

def make_it_talk(animal):
    print(animal.speak())  # works for both

make_it_talk(Dog())
make_it_talk(Cat())
```

- Write **generic code** that works for many types.
- Extend behavior **without modifying existing code** (the _Open/Closed Principle_).
- Use **composition + DI** effectively — because the injected object only needs to _fit the protocol_, not be a specific class

# Virtual method vs Abstract Method
Abstract methods are “you _must_ implement me.”  
Virtual methods are “you _can_ override me if you want.”