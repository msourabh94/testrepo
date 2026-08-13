package com.banking.session.oop;

import java.math.BigDecimal;

/**
 * Contract for accounts that accept deposits and withdrawals.
 * Prefer interfaces for capability ("can transact") rather than identity ("is a").
 */
public interface Transactable {
    void deposit(BigDecimal amount);

    boolean withdraw(BigDecimal amount);

    BigDecimal getBalance();
}
