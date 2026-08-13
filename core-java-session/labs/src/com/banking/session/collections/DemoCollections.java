package com.banking.session.collections;

import com.banking.session.oop.Account;
import com.banking.session.oop.CheckingAccount;
import com.banking.session.oop.Customer;
import com.banking.session.oop.PrepaidWallet;
import com.banking.session.oop.SavingsAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DemoCollections {

    public static void main(String[] args) {
        Customer priya = new Customer("C1001", "Priya Sharma");
        Customer arjun = new Customer("C2002", "Arjun Mehta");

        AccountRegistry registry = new AccountRegistry();
        registry.open(new SavingsAccount("SA-101", priya, "BLR-01", new BigDecimal("5000"), new BigDecimal("0.04")));
        registry.open(new CheckingAccount("CA-202", arjun, "MUM-02", new BigDecimal("1200"), new BigDecimal("500")));
        registry.open(new PrepaidWallet("PW-303", priya, "BLR-01", new BigDecimal("250")));
        registry.open(new SavingsAccount("SA-404", arjun, "MUM-02", new BigDecimal("8000"), new BigDecimal("0.035")));
        registry.open(new PrepaidWallet("PW-505", arjun, "MUM-02", BigDecimal.ZERO)); // inactive, zero balance

        registry.findByNumber("SA-101").withdraw(new BigDecimal("200"));
        registry.findByNumber("CA-202").deposit(new BigDecimal("100"));

        System.out.println("=== LIST (ordered, duplicates allowed) ===");
        for (Account account : registry.listAll()) {
            System.out.println("  " + account.statementLine());
        }

        System.out.println("\n=== SET uniqueness (HashSet vs LinkedHashSet vs TreeSet) ===");
        List<String> tags = Arrays.asList("UPI", "NEFT", "UPI", "IMPS", "NEFT");
        System.out.println("  HashSet:       " + new HashSet<String>(tags));
        System.out.println("  LinkedHashSet: " + new LinkedHashSet<String>(tags));
        System.out.println("  TreeSet:       " + new TreeSet<String>(tags));

        System.out.println("\n=== MAP lookup by account number (O(1) typical) ===");
        Account found = registry.findByNumber("CA-202");
        System.out.println("  findByNumber(CA-202) => " + found.getCustomer().getName());

        System.out.println("\n=== MAP grouping by branch ===");
        Set<Account> blr = registry.byBranch("BLR-01");
        System.out.println("  BLR-01 account count: " + blr.size());

        System.out.println("\n=== ITERATOR safe remove of zero-balance inactive accounts ===");
        int closed = registry.closeZeroBalanceInactive();
        System.out.println("  closed: " + closed);
        System.out.println("  remaining:");
        for (Account account : registry.listAll()) {
            System.out.println("    " + account.getAccountNumber() + " balance=" + account.getBalance()
                    + " txns=" + account.getTransactionCount());
        }

        System.out.println("\n=== LinkedHashMap insertion order ===");
        for (Map.Entry<String, String> e : registry.recentAccountNumbers().entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        }

        System.out.println("\n=== ArrayList vs why not always List? ===");
        List<String> mutable = new ArrayList<String>(Arrays.asList("debit", "credit"));
        mutable.add("transfer");
        System.out.println("  ArrayList grew to " + mutable);
    }
}
