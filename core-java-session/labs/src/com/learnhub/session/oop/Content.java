package com.learnhub.session.oop;

import java.util.Objects;

/**
 * Abstract base: shared state + template behavior.
 * Subclasses MUST implement formatLabel() — that is abstraction.
 */
public abstract class Content implements Enrollable {
    private final int id;
    private final String title;
    private final String category;
    private final Instructor instructor;
    private int enrollmentCount;

    protected Content(int id, String title, String category, Instructor instructor) {
        this.id = id;
        this.title = Objects.requireNonNull(title, "title");
        this.category = category;
        this.instructor = instructor;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    /** Template method: common header + subtype-specific label. */
    public final String catalogLine() {
        return "[" + formatLabel() + "] #" + id + " " + title + " | " + category;
    }

    protected abstract String formatLabel();

    @Override
    public void enroll(String learnerEmail) {
        if (learnerEmail == null || !learnerEmail.contains("@")) {
            throw new IllegalArgumentException("Valid learner email required");
        }
        enrollmentCount++;
        System.out.println(learnerEmail + " enrolled in: " + title);
    }

    @Override
    public int getEnrollmentCount() {
        return enrollmentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        Content other = (Content) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
