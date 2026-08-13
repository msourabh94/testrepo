package com.learnhub.session.oop;

/**
 * Contract for anything a learner can enroll in.
 * Prefer interfaces for capability ("can do") rather than identity ("is a").
 */
public interface Enrollable {
    void enroll(String learnerEmail);

    int getEnrollmentCount();
}
