package com.learnhub.session.oop;

/**
 * Simple class vs object demo.
 * Class = blueprint. Object = instance in memory.
 */
public class Instructor {
    private final String name;
    private final String specialty;

    public Instructor(String name, String specialty) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Instructor name is required");
        }
        this.name = name.trim();
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String toString() {
        return name + " (" + specialty + ")";
    }
}
