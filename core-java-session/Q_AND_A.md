# Prepared Q&A — Core Java Session (Banking)

### OOP

**Q. Class vs object?**  
Class = definition (`Account`). Object = instance (`new SavingsAccount(...)`).

**Q. Encapsulation?**  
Hide balance; deposits/withdrawals enforce positive amounts and overdraft rules.

**Q. Inheritance vs composition?**  
`SavingsAccount extends Account` (is-a). `Account` has a `Customer` (has-a). Prefer composition unless you need substitutability.

**Q. Abstract class vs interface?**  
`Account` holds balance and account number. `InterestBearing` / `Transactable` are capabilities.

**Q. Overload vs override?**  
Override: `CheckingAccount.withdraw` — runtime. Overload: same name, different params — compile time.

**Q. Why not put interest on every account with rate 0?**  
Magic zero leaks into reports. `PrepaidWallet` simply does not implement `InterestBearing`.

**Q. equals/hashCode?**  
Keyed on `accountNumber` for `HashSet`/`HashMap`. Equal account numbers must share hash codes.

**Q. Why BigDecimal for money?**  
`double` has binary rounding error — unacceptable for ledgers. Use `BigDecimal` with explicit scale/rounding mode.

---

### Collections

**Q. List vs Set vs Map?**  
List = ordered accounts. Set = unique payment rails or customer names. Map = lookup by account number or branch bucket.

**Q. HashSet vs LinkedHashSet vs TreeSet?**  
Hash: unique, no order. LinkedHash: unique + insertion order. Tree: unique + sorted.

**Q. ConcurrentModificationException?**  
Structural change during for-each. Fix: `Iterator.remove()` or collect-then-delete.

**Q. HashMap internals (interview)?**  
hashCode → bucket; equals confirms key; resize at load factor.

**Q. Why unmodifiable lists from AccountRegistry?**  
Callers must not mutate internal indexes.

---

### Generics

**Q. Why generics?**  
`Repository<SavingsAccount>` prevents storing a `Customer` in the account store at compile time.

**Q. Raw types?**  
Demo shows `ClassCastException`. Never ship raw collections.

**Q. PECS?**  
Read accounts: `List<? extends Account>`. Write interest products: `List<? super InterestBearing>`.

**Q. List<SavingsAccount> vs List<Account>?**  
Invariance — prevents adding a checking account to a savings-only list through a widened reference.

---

### Planted questions

1. Why override `withdraw` in checking instead of a flag on `Account`?
2. Should the database entity be your polymorphism root? (Usually no — domain vs persistence.)
3. When is `instanceof InterestBearing` OK? (Reporting/accrual batch, not core transaction loop.)
4. HashMap vs Hashtable?
5. How would you unit-test overdraft rejection?

### Park (framework)

Spring, JPA, REST controllers — consumers of these ideas, not replacements. Offer office hours.
