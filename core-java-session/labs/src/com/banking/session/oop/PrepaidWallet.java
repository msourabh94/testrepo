package com.banking.session.oop;

import java.math.BigDecimal;

/** Prepaid wallet — transactable, but does not earn interest. */
public class PrepaidWallet extends Account {
    public PrepaidWallet(String accountNumber, Customer customer, String branchCode, BigDecimal openingBalance) {
        super(accountNumber, customer, branchCode, openingBalance);
    }

    @Override
    protected String formatLabel() {
        return "WALLET no-interest";
    }
}
