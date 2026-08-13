package com.banking.session.oop;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Abstract base: shared state + template behavior.
 * Subclasses MUST implement formatLabel() — that is abstraction.
 */
public abstract class Account implements Transactable {
    private final String accountNumber;
    private final Customer customer;
    private final String branchCode;
    protected BigDecimal balance;
    private int transactionCount;

    protected Account(String accountNumber, Customer customer, String branchCode, BigDecimal openingBalance) {
        this.accountNumber = Objects.requireNonNull(accountNumber, "accountNumber");
        this.customer = Objects.requireNonNull(customer, "customer");
        this.branchCode = branchCode;
        this.balance = openingBalance == null ? BigDecimal.ZERO : openingBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    /** Template method: common header + subtype-specific label. */
    public final String statementLine() {
        return "[" + formatLabel() + "] " + accountNumber + " | " + customer.getName()
                + " | " + branchCode + " | balance=" + balance;
    }

    protected abstract String formatLabel();

    protected void recordTransaction() {
        transactionCount++;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
        recordTransaction();
        System.out.println("Deposited " + amount + " to " + accountNumber + " (" + customer.getName() + ")");
    }

    protected void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
        recordTransaction();
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds on " + accountNumber);
        }
        debit(amount);
        System.out.println("Withdrew " + amount + " from " + accountNumber);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account other = (Account) o;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
