# Participant Workbook — Core Java Session (Banking)

Keep this open. Type in `core-java-session/labs/src`. Try before you paste.

Baseline: JDK 8+. From `core-java-session/labs`:

```bash
./run-labs.sh
```

Source root = `labs/src`.

---

## Mental model (fill during the first 30 minutes)

| Idea | One sentence in your words | Banking example |
|------|----------------------------|-----------------|
| Class | | |
| Object | | |
| Encapsulation | | |
| Inheritance | | |
| Polymorphism | | |
| Abstraction | | |
| Interface | | |
| List | | |
| Set | | |
| Map | | |
| Generic type | | |

---

## Exercise 1 — Classes & inheritance (pairs, 5 min)

Add `TermDeposit extends Account implements InterestBearing`.

Requirements:
- Constructor: `accountNumber`, `customer`, `branchCode`, `openingBalance`, `interestRate`, `termMonths`
- `formatLabel()` returns `TERM months=N`
- Deposits/withdrawals use parent behavior
- Accrues interest like `SavingsAccount`

**Check:** add a `TermDeposit` to `DemoOOP` portfolio and re-run.

---

## Exercise 2 — Polymorphism (solo, 5 min)

Without `instanceof`, print for every account:

```
ACCOUNT_NUMBER | balance=BALANCE
```

Which method dispatch made this possible?

**Stretch:** where would a `summary()` method live — `Account` or `Transactable`? Why?

---

## Exercise 3 — Collections (pairs, 8 min)

In `AccountRegistry`, add:

```java
public Set<String> uniqueCustomers()
```

Return customer **names**, no duplicates, any order.

Then add:

```java
public Map<String, BigDecimal> totalBalanceByBranch()
```

Key = branch code, value = **sum of balances** in that branch.

**Check:** after deposits/withdrawals in the demo, branch totals match manual addition.

---

## Exercise 4 — Generics (homework if not live)

Write:

```java
public final class Pager<T> {
    public List<T> page(List<T> items, int offset, int limit) { ... }
}
```

Same rules as before (validate offset/limit; empty list when past end).

Page `registry.listAll()`.

---

## Exercise 5 — Stretch

1. Why is `equals` based on `accountNumber` only?
2. Implement `copyInterestProducts(List<? extends InterestBearing> src, List<? super InterestBearing> dest)`.
3. Why is `List<SavingsAccount>` not a `List<Account>`?

---

## Exercise 6 — Real-world mapping

Discuss with your pair — no repo required:

- Where would `accountType` as String hurt you in a legacy core?
- What belongs in `Map<String, Account>` vs a `List<Account>`?
- Why use `BigDecimal` for money?

Write 5 bullets for your team’s next code review.

---

## During demos — watch for

1. Checking account **overdraft limit** rejection
2. `HashSet` vs `LinkedHashSet` on payment rails
3. `ConcurrentModificationException` on for-each remove
4. Raw `List` ClassCastException

If you cannot explain those four after the session, ask in Q&A.
