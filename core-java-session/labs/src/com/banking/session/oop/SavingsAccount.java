package com.banking.session.oop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingsAccount extends Account implements InterestBearing {
    private final BigDecimal interestRate;

    public SavingsAccount(String accountNumber, Customer customer, String branchCode,
                          BigDecimal openingBalance, BigDecimal interestRate) {
        super(accountNumber, customer, branchCode, openingBalance);
        this.interestRate = interestRate;
    }

    @Override
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @Override
    public BigDecimal accrueInterest() {
        BigDecimal interest = getBalance()
                .multiply(interestRate)
                .setScale(2, RoundingMode.HALF_UP);
        deposit(interest);
        return interest;
    }

    @Override
    protected String formatLabel() {
        return "SAVINGS rate=" + interestRate.multiply(BigDecimal.valueOf(100)) + "%";
    }
}
