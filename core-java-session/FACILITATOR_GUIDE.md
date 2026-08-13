# Facilitator Guide — Core Java (3 hours, Banking)

Use this as a **second-screen script**. Speak from the slides; use these notes when you stall.

**Audience assumption:** people who have written some Java but have fuzzy OOP/collections mental models.

**Room setup (15 min before start)**
- Projector: `presentation/index.html` full screen
- IDE: labs compiled; run configs: `DemoOOP`, `DemoCollections`, `DemoGenerics`, `BankingApp`
- Whiteboard + markers
- Chat thread for parking-lot questions

**Tone:** technical, not theatrical. Prefer one tight banking example over five cute ones. Pause after every demo: “what would break if…?”

---

## Block A — Conceptual overview (30 min)

| Min | Slide theme | You do | They do |
|-----|-------------|--------|---------|
| 0–3 | Title + objective | Contract: *leave able to model accounts and store them correctly* | Settle |
| 3–6 | Agenda + rules | “Interrupt for confusion, park for rabbit holes.” | — |
| 6–12 | Why OOP exists | Draw: scattered `if ("SAVINGS")` vs polymorphic `withdraw()` | — |
| 12–18 | Four pillars map | Map each pillar to Savings / Checking / Wallet | Shout examples |
| 18–24 | Collections | “OOP models *accounts*. Collections model *groups of accounts*.” | Pick structure for: statement order, unique rails, lookup by number |
| 24–28 | Generics | Raw `List` vs `List<Account>` | — |
| 28–30 | Bridge to labs | “Next two hours we *build* the bank core.” | Open workbook |

### Talking points (Block A)

**Classes vs objects:** `Customer` vs `new Customer("C1001", "Priya Sharma")`.

**Inheritance:** `SavingsAccount extends Account` is **is-a**. Do not inherit to reuse one helper.

**Polymorphism:** `List<Account>` holding savings, checking, wallet. Call `statementLine()` / `withdraw()` without type switches.

**Abstraction:** `statementLine()` is template method; `formatLabel()` is the hole. `InterestBearing` is capability — wallet transacts but does not accrue interest.

**Interfaces vs abstract classes:** `Account` holds balance and account number. `Transactable` / `InterestBearing` are can-do contracts.

**Collections:**
- List — ordered ledger of accounts
- Set — unique payment rails (UPI, NEFT, IMPS)
- Map — `accountNumber → Account`, `branchCode → Set<Account>`

**Iterators:** Only safe way to close zero-balance inactive accounts while iterating.

**Generics:** `Repository<Customer>` and `Repository<SavingsAccount>` — one class, compiler-enforced types.

**Banking hook:** Legacy cores often store `accountType` as String and branch in code. Today we replace that with a type hierarchy + registry service.

---

## Block B — Hands-on (120 min)

Whiteboard mini-agenda:

```
0:30–0:50  Customer + Account + SavingsAccount     (20)
0:50–1:10  Polymorphism + template method           (20)
1:10–1:30  Transactable / InterestBearing + equals (20)
1:30–1:40  BREAK                                   (10)
1:40–2:10  List / Set / Map + iterators            (30)
2:10–2:25  Generics + PECS                         (15)
2:25–2:30  Capstone BankingApp                     (5)
```

If late: cut PECS depth, not the CME demo or capstone.

### 0:30–0:50 Classes, objects, inheritance (20)

**Demo:** `Customer`, `Account`, `SavingsAccount`.

**Say:** private balance, validated amounts, `@Override` on withdraw in checking.

**Exercise 1:** `TermDeposit extends Account implements InterestBearing`.

### 0:50–1:10 Polymorphism + abstraction (20)

**Demo:** `DemoOOP` loop over `List<Account>`.

**Whiteboard:** compile-time `Account` vs runtime `CheckingAccount`.

### 1:10–1:30 Interfaces + equals (20)

**Demo:** overdraft limit on third withdraw fails. Wallet does not accrue interest.

**equals/hashCode** on `accountNumber`.

**Exercise 2:** polymorphic print without `instanceof`.

### 1:30–1:40 Break

### 1:40–2:10 Collections + iterators (30)

**Demo:** `DemoCollections`. **Break** for-each remove → CME. Fix with `Iterator.remove()`.

**Exercise 3:** `uniqueCustomers()` and `totalBalanceByBranch()`.

### 2:10–2:25 Generics (15)

**Demo:** `DemoGenerics` raw list ClassCastException. `Repository<SavingsAccount>`.

### 2:25–2:30 Capstone (5)

Run `BankingApp`. Trace customer repo → registry → interest accrual → branch grouping.

---

## Block C — Q&A (30 min)

1. Recap five bullets (2 min)
2. Open floor (20 min) — [Q_AND_A.md](Q_AND_A.md)
3. Homework (5 min)
4. Close (3 min)

Planted questions if silent:
- Why not `interestRate = 0` on every account instead of `InterestBearing`?
- Why `BigDecimal` not `double` for money?
- HashMap vs Hashtable?

---

## Failure modes

| What happens | Recover |
|--------------|---------|
| Compile error live | Revert to committed lab; explain; move on |
| Spring/JPA questions | Park until Q&A |
| Advanced bored | Exercise 5 (wildcards + custom Comparator) |
| Beginners lost | Pair them; stay on happy path |
| Time overrun | Skip PECS; keep CME + capstone |
