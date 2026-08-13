package com.banking.session.oop;

import java.math.BigDecimal;

/** Capability: account earns interest on its balance. */
public interface InterestBearing {
    BigDecimal getInterestRate();

    BigDecimal accrueInterest();
}
