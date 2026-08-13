package com.banking.session.capstone;

import com.banking.session.collections.AccountRegistry;
import com.banking.session.generics.Repository;
import com.banking.session.oop.Account;
import com.banking.session.oop.CheckingAccount;
import com.banking.session.oop.Customer;
import com.banking.session.oop.InterestBearing;
import com.banking.session.oop.PrepaidWallet;
import com.banking.session.oop.SavingsAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Capstone: OOP + collections + generics in one banking flow.
 */
public class BankingApp {

    public static void main(String[] args) {
        Repository<Customer> customers = new Repository<Customer>();
        customers.save("C1001", new Customer("C1001", "Priya Sharma"));
        customers.save("C2002", new Customer("C2002", "Arjun Mehta"));

        AccountRegistry registry = new AccountRegistry();
        registry.open(new SavingsAccount("SA-101", customers.find("C1001"), "BLR-01",
                new BigDecimal("5000.00"), new BigDecimal("0.04")));
        registry.open(new CheckingAccount("CA-202", customers.find("C2002"), "MUM-02",
                new BigDecimal("1200.00"), new BigDecimal("500.00")));
        registry.open(new PrepaidWallet("PW-303", customers.find("C1001"), "BLR-01",
                new BigDecimal("250.00")));

        registry.findByNumber("SA-101").deposit(new BigDecimal("500.00"));
        registry.findByNumber("CA-202").withdraw(new BigDecimal("200.00"));
        registry.findByNumber("PW-303").deposit(new BigDecimal("50.00"));

        System.out.println("BANK MINI CORE — ACCOUNT STATEMENTS");
        System.out.println("===================================");
        for (Account account : registry.listAll()) {
            System.out.printf("  %-58s  txns=%d%n", account.statementLine(), account.getTransactionCount());
        }

        System.out.println("\nInterest accrual on eligible accounts:");
        List<InterestBearing> products = new ArrayList<InterestBearing>();
        BigDecimal totalInterest = BigDecimal.ZERO;
        for (Account account : registry.listAll()) {
            if (account instanceof InterestBearing) {
                InterestBearing bearing = (InterestBearing) account;
                products.add(bearing);
                BigDecimal interest = bearing.accrueInterest();
                totalInterest = totalInterest.add(interest);
                System.out.println("  " + account.getAccountNumber() + " + " + interest);
            }
        }
        System.out.println("  products: " + products.size() + "  total interest: " + totalInterest);

        System.out.println("\nBLR-01 branch accounts:");
        for (Account account : registry.byBranch("BLR-01")) {
            System.out.println("  - " + account.getAccountNumber() + " (" + account.getCustomer().getName() + ")");
        }
    }
}
