package com.learnhub.session.capstone;

import com.learnhub.session.collections.CatalogService;
import com.learnhub.session.generics.Repository;
import com.learnhub.session.oop.Assessment;
import com.learnhub.session.oop.Content;
import com.learnhub.session.oop.Instructor;
import com.learnhub.session.oop.LiveWorkshop;
import com.learnhub.session.oop.Priced;
import com.learnhub.session.oop.VideoCourse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Capstone walkthrough: OOP + collections + generics in one catalog flow.
 * Mirrors LearnHub: catalog items, lookup, grouping, enrollment.
 */
public class CatalogApp {

    public static void main(String[] args) {
        Repository<Instructor> faculty = new Repository<Instructor>();
        faculty.save(1, new Instructor("Ada Lovelace", "Foundations"));
        faculty.save(2, new Instructor("Grace Hopper", "Systems"));

        CatalogService catalog = new CatalogService();
        catalog.add(new VideoCourse(101, "Core Java", "Backend", faculty.find(1), 180, new BigDecimal("49.00")));
        catalog.add(new LiveWorkshop(202, "Kafka Hands-on", "Data", faculty.find(2), 8, new BigDecimal("199.00")));
        catalog.add(new Assessment(303, "Java OOP Quiz", "Backend", faculty.find(1), 70));

        catalog.findById(101).enroll("alex@learnhub.io");
        catalog.findById(202).enroll("sam@learnhub.io");
        catalog.findById(303).enroll("riya@learnhub.io");

        System.out.println("LEARNHUB MINI CATALOG");
        System.out.println("=====================");
        for (Content item : catalog.listAll()) {
            System.out.printf("  %-28s  enrollments=%d%n", item.catalogLine(), item.getEnrollmentCount());
        }

        System.out.println("\nRevenue from priced items:");
        BigDecimal revenue = BigDecimal.ZERO;
        List<Priced> billed = new ArrayList<Priced>();
        for (Content item : catalog.listAll()) {
            if (item instanceof Priced) {
                Priced priced = (Priced) item;
                billed.add(priced);
                revenue = revenue.add(priced.getPrice().multiply(
                        BigDecimal.valueOf(item.getEnrollmentCount())));
            }
        }
        System.out.println("  billed SKUs: " + billed.size());
        System.out.println("  revenue:     $" + revenue);

        System.out.println("\nBackend category:");
        for (Content item : catalog.byCategory("Backend")) {
            System.out.println("  - " + item.getTitle());
        }
    }
}
