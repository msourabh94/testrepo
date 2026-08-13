package com.banking.session.collections;

import com.banking.session.oop.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Real branch operations you would write in a service layer.
 */
public class AccountRegistry {
    private final List<Account> orderedAccounts = new ArrayList<Account>();
    private final Map<String, Account> byAccountNumber = new HashMap<String, Account>();
    private final Map<String, Set<Account>> byBranch = new HashMap<String, Set<Account>>();

    public void open(Account account) {
        if (byAccountNumber.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Duplicate account number: " + account.getAccountNumber());
        }
        orderedAccounts.add(account);
        byAccountNumber.put(account.getAccountNumber(), account);

        Set<Account> bucket = byBranch.get(account.getBranchCode());
        if (bucket == null) {
            bucket = new HashSet<Account>();
            byBranch.put(account.getBranchCode(), bucket);
        }
        bucket.add(account);
    }

    public Account findByNumber(String accountNumber) {
        return byAccountNumber.get(accountNumber);
    }

    public List<Account> listAll() {
        return Collections.unmodifiableList(orderedAccounts);
    }

    public Set<Account> byBranch(String branchCode) {
        Set<Account> found = byBranch.get(branchCode);
        if (found == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(found);
    }

    /** Safe removal while iterating — never for-each + remove. */
    public int closeZeroBalanceInactive() {
        int removed = 0;
        Iterator<Account> it = orderedAccounts.iterator();
        while (it.hasNext()) {
            Account account = it.next();
            if (account.getBalance().signum() == 0 && account.getTransactionCount() == 0) {
                it.remove();
                byAccountNumber.remove(account.getAccountNumber());
                Set<Account> bucket = byBranch.get(account.getBranchCode());
                if (bucket != null) {
                    bucket.remove(account);
                }
                removed++;
            }
        }
        return removed;
    }

    public List<Account> topByAccountNumber(int limit) {
        List<Account> copy = new ArrayList<Account>(orderedAccounts);
        Collections.sort(copy, new Comparator<Account>() {
            @Override
            public int compare(Account a, Account b) {
                return a.getAccountNumber().compareToIgnoreCase(b.getAccountNumber());
            }
        });
        if (limit >= copy.size()) {
            return copy;
        }
        return copy.subList(0, limit);
    }

    /** LinkedHashMap preserves insertion order — useful for "recently opened". */
    public Map<String, String> recentAccountNumbers() {
        Map<String, String> recent = new LinkedHashMap<String, String>();
        for (Account account : orderedAccounts) {
            recent.put(account.getAccountNumber(), account.getCustomer().getName());
        }
        return recent;
    }
}
