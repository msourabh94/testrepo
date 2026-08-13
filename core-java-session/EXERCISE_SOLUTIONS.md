# Exercise solutions (facilitator only)

## Exercise 1 — TermDeposit

```java
package com.banking.session.oop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TermDeposit extends Account implements InterestBearing {
    private final BigDecimal interestRate;
    private final int termMonths;

    public TermDeposit(String accountNumber, Customer customer, String branchCode,
                       BigDecimal openingBalance, BigDecimal interestRate, int termMonths) {
        super(accountNumber, customer, branchCode, openingBalance);
        this.interestRate = interestRate;
        this.termMonths = termMonths;
    }

    @Override
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @Override
    public BigDecimal accrueInterest() {
        BigDecimal interest = getBalance().multiply(interestRate).setScale(2, RoundingMode.HALF_UP);
        deposit(interest);
        return interest;
    }

    @Override
    protected String formatLabel() {
        return "TERM months=" + termMonths;
    }
}
```

## Exercise 2

```java
for (Account account : portfolio) {
    System.out.println(account.getAccountNumber() + " | balance=" + account.getBalance());
}
```

Dispatch via `getBalance()` on `Transactable` / `Account`.

## Exercise 3

```java
public Set<String> uniqueCustomers() {
    Set<String> names = new HashSet<String>();
    for (Account account : orderedAccounts) {
        names.add(account.getCustomer().getName());
    }
    return names;
}

public Map<String, BigDecimal> totalBalanceByBranch() {
    Map<String, BigDecimal> totals = new HashMap<String, BigDecimal>();
    for (Account account : orderedAccounts) {
        BigDecimal soFar = totals.get(account.getBranchCode());
        if (soFar == null) {
            soFar = BigDecimal.ZERO;
        }
        totals.put(account.getBranchCode(), soFar.add(account.getBalance()));
    }
    return totals;
}
```

## Exercise 4 — Pager

Same as prior pack; only generic type name changes.

## Exercise 5 — notes

1. Account number is the business key; two different numbers with same customer are distinct products.
2. `for (InterestBearing p : src) dest.add(p);`
3. Invariance: you could add a `CheckingAccount` through a `List<Account>` reference and corrupt a savings-only list.

## Exercise 6 — talking points

- String `accountType` → switch grows forever; polymorphism localizes change
- Map for O(1) lookup by account number; List for ordered statements
- `double` binary rounding errors are unacceptable for ledger money
