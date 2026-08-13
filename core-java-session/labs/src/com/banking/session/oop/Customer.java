package com.banking.session.oop;

/**
 * Simple class vs object demo.
 * Class = blueprint. Object = instance in memory.
 */
public class Customer {
    private final String customerId;
    private final String name;

    public Customer(String customerId, String name) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer id is required");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        this.customerId = customerId.trim();
        this.name = name.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + customerId + ")";
    }
}
