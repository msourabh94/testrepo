# Core Java Cheatsheet (session takeaway)

## OOP in one table

| Pillar | Meaning | In our bank |
|--------|---------|-------------|
| Encapsulation | Hide state; protect invariants | private balance, validated deposit/withdraw |
| Inheritance | is-a shared family | `SavingsAccount extends Account` |
| Polymorphism | one reference, many behaviors | `Account.statementLine()` / `withdraw()` |
| Abstraction | show what, hide how | `abstract formatLabel()`, `interface InterestBearing` |

**is-a** → extend a class. **has-a** → field (`Customer` on `Account`). **can-do** → interface (`Transactable`).

```
Account (abstract)
  ├── SavingsAccount     implements InterestBearing
  ├── CheckingAccount    overdraft-aware withdraw
  └── PrepaidWallet      no interest
```

- Override = runtime dispatch. Overload = compile-time.
- Always `@Override`.
- If you override `equals`, override `hashCode` (use stable key: `accountNumber`).
- Abstract class: shared state. Interface: capability (multiple).

## Collections — pick in 10 seconds

| Need | Type | Usual impl |
|------|------|------------|
| Ordered statement list, index, duplicates OK | `List` | `ArrayList` |
| Unique payment rails / tags | `Set` | `HashSet` |
| Unique + insertion order | `Set` | `LinkedHashSet` |
| Unique + sorted | `Set` | `TreeSet` |
| accountNumber → Account | `Map` | `HashMap` |
| Stable iteration of map | `Map` | `LinkedHashMap` |
| Sorted keys | `Map` | `TreeMap` |
| Concurrent map | `Map` | `ConcurrentHashMap` |

Do **not** start new code with `Vector`, `Hashtable`, `Stack`.

### Iterator rule

```java
Iterator<Account> it = accounts.iterator();
while (it.hasNext()) {
    if (shouldClose(it.next())) {
        it.remove();   // OK
    }
}
```

`for (Account a : accounts) { accounts.remove(a); }` → `ConcurrentModificationException`.

## Generics

```java
class Repository<T> { void save(String id, T e); T find(String id); }

void print(List<? extends Account> src);              // read Account  (producer)
void addInterest(List<? super InterestBearing> d, InterestBearing p); // write (consumer)
```

- Raw `List` → runtime `ClassCastException`. Parameterize everything.
- `List<SavingsAccount>` is **not** a `List<Account>` (invariance).
- Erasure: no `new T()` without a `Class<T>`.

## Copy-paste decisions

```java
List<Account> ledger = new ArrayList<Account>();
Set<String> rails = new LinkedHashSet<String>();
Map<String, Account> byNumber = new HashMap<String, Account>();
```

Return `Collections.unmodifiableList(...)` from services so callers cannot corrupt your indexes.

## Three bugs this session exists to prevent

1. `switch (account.getType())` exploding as new products appear → **polymorphism**
2. Duplicate account numbers / lost entries in a list → **Map by accountNumber + equals/hashCode**
3. “It compiled” then exploded in prod → **generics, not raw types**
