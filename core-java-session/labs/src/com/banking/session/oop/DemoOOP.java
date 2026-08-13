package com.banking.session.oop;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Live demo: classes, inheritance, polymorphism, abstraction, interfaces.
 * Run: javac ... && java com.banking.session.oop.DemoOOP
 */
public class DemoOOP {

    public static void main(String[] args) {
        Customer priya = new Customer("C1001", "Priya Sharma");
        Customer arjun = new Customer("C2002", "Arjun Mehta");

        Account savings = new SavingsAccount("SA-101", priya, "BLR-01",
                new BigDecimal("5000.00"), new BigDecimal("0.04"));
        Account checking = new CheckingAccount("CA-202", arjun, "MUM-02",
                new BigDecimal("1200.00"), new BigDecimal("500.00"));
        Account wallet = new PrepaidWallet("PW-303", priya, "BLR-01", new BigDecimal("250.00"));

        System.out.println("=== 1. Class vs Object ===");
        System.out.println("Blueprint: Customer");
        System.out.println("Instance:  " + priya);
        System.out.println("Instance:  " + arjun);

        System.out.println("\n=== 2. Polymorphism (same type, different behavior) ===");
        List<Account> portfolio = Arrays.asList(savings, checking, wallet);
        for (Account account : portfolio) {
            System.out.println(account.statementLine());
        }

        System.out.println("\n=== 3. Interface contracts ===");
        transactSafely(savings, new BigDecimal("500.00"));
        transactSafely(checking, new BigDecimal("800.00"));
        transactSafely(checking, new BigDecimal("700.00"));
        transactSafely(checking, new BigDecimal("300.00")); // exceeds overdraft

        System.out.println("\n=== 4. Abstraction: InterestBearing vs not ===");
        applyInterest(savings);
        applyInterest(wallet);

        System.out.println("\n=== 5. equals/hashCode identity (same account number) ===");
        Account duplicate = new SavingsAccount("SA-101", priya, "BLR-01",
                new BigDecimal("1.00"), new BigDecimal("0.04"));
        System.out.println("savings.equals(duplicate)? " + savings.equals(duplicate));
    }

    private static void transactSafely(Transactable account, BigDecimal amount) {
        try {
            account.withdraw(amount);
            System.out.println("  balance now: " + account.getBalance());
        } catch (RuntimeException ex) {
            System.out.println("  FAILED withdraw " + amount + ": " + ex.getMessage());
        }
    }

    private static void applyInterest(Account account) {
        if (account instanceof InterestBearing) {
            InterestBearing bearing = (InterestBearing) account;
            BigDecimal interest = bearing.accrueInterest();
            System.out.println(account.getAccountNumber() + " accrued interest " + interest
                    + " at rate " + bearing.getInterestRate());
        } else {
            System.out.println(account.getAccountNumber() + " does not accrue interest");
        }
    }
}
