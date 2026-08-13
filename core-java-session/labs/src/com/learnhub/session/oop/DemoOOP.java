package com.learnhub.session.oop;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Live demo: classes, inheritance, polymorphism, abstraction, interfaces.
 * Run: javac ... && java com.learnhub.session.oop.DemoOOP
 */
public class DemoOOP {

    public static void main(String[] args) {
        Instructor ada = new Instructor("Ada Lovelace", "Foundations");
        Instructor grace = new Instructor("Grace Hopper", "Systems");

        Content javaCore = new VideoCourse(101, "Core Java", "Backend", ada,
                180, new BigDecimal("49.00"));
        Content kafkaLab = new LiveWorkshop(202, "Kafka Hands-on", "Data", grace,
                2, new BigDecimal("199.00"));
        Content quiz = new Assessment(303, "Java OOP Quiz", "Backend", ada, 70);

        System.out.println("=== 1. Class vs Object ===");
        System.out.println("Blueprint: Instructor");
        System.out.println("Instance:  " + ada);
        System.out.println("Instance:  " + grace);

        System.out.println("\n=== 2. Polymorphism (same type, different behavior) ===");
        List<Content> catalog = Arrays.asList(javaCore, kafkaLab, quiz);
        for (Content item : catalog) {
            System.out.println(item.catalogLine());
        }

        System.out.println("\n=== 3. Interface contracts ===");
        enrollSafely(javaCore, "alex@learnhub.io");
        enrollSafely(kafkaLab, "sam@learnhub.io");
        enrollSafely(kafkaLab, "riya@learnhub.io");
        enrollSafely(kafkaLab, "lee@learnhub.io"); // should fail: full

        System.out.println("\n=== 4. Abstraction: Priced vs not Priced ===");
        printPrice(javaCore);
        printPrice(quiz);

        System.out.println("\n=== 5. equals/hashCode identity (same id) ===");
        Content duplicate = new VideoCourse(101, "Core Java (relisted)", "Backend", ada,
                180, new BigDecimal("49.00"));
        System.out.println("javaCore.equals(duplicate)? " + javaCore.equals(duplicate));
    }

    private static void enrollSafely(Enrollable item, String email) {
        try {
            item.enroll(email);
            System.out.println("  enrollments now: " + item.getEnrollmentCount());
        } catch (RuntimeException ex) {
            System.out.println("  FAILED for " + email + ": " + ex.getMessage());
        }
    }

    private static void printPrice(Content item) {
        if (item instanceof Priced) {
            Priced priced = (Priced) item;
            System.out.println(item.getTitle() + " costs " + priced.getPrice());
        } else {
            System.out.println(item.getTitle() + " is free");
        }
    }
}
