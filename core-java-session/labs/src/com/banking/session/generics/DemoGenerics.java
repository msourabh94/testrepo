package com.banking.session.generics;

import com.banking.session.oop.Account;
import com.banking.session.oop.Customer;
import com.banking.session.oop.InterestBearing;
import com.banking.session.oop.SavingsAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoGenerics {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        Customer priya = new Customer("C1001", "Priya Sharma");
        SavingsAccount savings = new SavingsAccount("SA-101", priya, "BLR-01",
                new BigDecimal("5000"), new BigDecimal("0.04"));

        System.out.println("=== Raw types are a trap (don't do this) ===");
        List raw = new ArrayList(); // intentional: runtime ClassCastException
        raw.add("oops");
        raw.add(savings);
        try {
            SavingsAccount first = (SavingsAccount) raw.get(0);
            System.out.println(first);
        } catch (ClassCastException ex) {
            System.out.println("  Runtime boom: " + ex);
        }

        System.out.println("\n=== Parameterized types catch mistakes at compile time ===");
        Repository<SavingsAccount> accounts = new Repository<SavingsAccount>();
        accounts.save(savings.getAccountNumber(), savings);
        SavingsAccount loaded = accounts.find("SA-101");
        System.out.println("  loaded: " + loaded.getAccountNumber());
        // accounts.save("C1001", priya); // would not compile — Customer is not SavingsAccount

        Repository<Customer> customers = new Repository<Customer>();
        customers.save(priya.getCustomerId(), priya);
        System.out.println("  customers size: " + customers.size());

        System.out.println("\n=== Wildcards: PECS (Producer Extends, Consumer Super) ===");
        List<SavingsAccount> savingsList = Arrays.asList(savings);
        printAccountNumbers(savingsList);

        List<InterestBearing> interestProducts = new ArrayList<InterestBearing>();
        addInterestProduct(interestProducts, savings);
        System.out.println("  interest products: " + interestProducts.size());

        System.out.println("\n=== Generic method ===");
        SavingsAccount picked = firstOrNull(savingsList);
        System.out.println("  firstOrNull => " + (picked == null ? "none" : picked.getAccountNumber()));
    }

    private static void printAccountNumbers(List<? extends Account> accounts) {
        for (Account account : accounts) {
            System.out.println("  account: " + account.getAccountNumber());
        }
    }

    private static void addInterestProduct(List<? super InterestBearing> dest, InterestBearing product) {
        dest.add(product);
    }

    private static <T> T firstOrNull(List<T> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        return items.get(0);
    }
}
