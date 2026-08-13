package com.banking.session.oop;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    private final BigDecimal overdraftLimit;

    public CheckingAccount(String accountNumber, Customer customer, String branchCode,
                           BigDecimal openingBalance, BigDecimal overdraftLimit) {
        super(accountNumber, customer, branchCode, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        BigDecimal projected = balance.subtract(amount);
        if (projected.compareTo(overdraftLimit.negate()) < 0) {
            throw new IllegalStateException(
                    "Overdraft limit exceeded on " + getAccountNumber() + " (limit " + overdraftLimit + ")");
        }
        debit(amount);
        System.out.println("Withdrew " + amount + " from " + getAccountNumber() + " (checking/overdraft)");
        return true;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    protected String formatLabel() {
        return "CHECKING od=" + overdraftLimit;
    }
}
