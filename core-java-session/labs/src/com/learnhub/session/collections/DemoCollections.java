package com.learnhub.session.collections;

import com.learnhub.session.oop.Assessment;
import com.learnhub.session.oop.Content;
import com.learnhub.session.oop.Instructor;
import com.learnhub.session.oop.LiveWorkshop;
import com.learnhub.session.oop.VideoCourse;

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
        Instructor ada = new Instructor("Ada Lovelace", "Foundations");
        Instructor grace = new Instructor("Grace Hopper", "Systems");

        CatalogService catalog = new CatalogService();
        catalog.add(new VideoCourse(101, "Core Java", "Backend", ada, 180, new BigDecimal("49")));
        catalog.add(new LiveWorkshop(202, "Kafka Hands-on", "Data", grace, 12, new BigDecimal("199")));
        catalog.add(new Assessment(303, "Java OOP Quiz", "Backend", ada, 70));
        catalog.add(new VideoCourse(404, "Spring MVC Intro", "Backend", grace, 90, new BigDecimal("29")));

        catalog.findById(101).enroll("alex@learnhub.io");
        catalog.findById(202).enroll("sam@learnhub.io");

        System.out.println("=== LIST (ordered, duplicates allowed) ===");
        for (Content item : catalog.listAll()) {
            System.out.println("  " + item.catalogLine());
        }

        System.out.println("\n=== SET uniqueness (HashSet vs LinkedHashSet vs TreeSet) ===");
        List<String> tags = Arrays.asList("java", "oop", "java", "collections", "oop");
        System.out.println("  HashSet:       " + new HashSet<String>(tags));
        System.out.println("  LinkedHashSet: " + new LinkedHashSet<String>(tags));
        System.out.println("  TreeSet:       " + new TreeSet<String>(tags));

        System.out.println("\n=== MAP lookup by id (O(1) typical) ===");
        Content found = catalog.findById(202);
        System.out.println("  findById(202) => " + found.getTitle());

        System.out.println("\n=== MAP grouping by category ===");
        Set<Content> backend = catalog.byCategory("Backend");
        System.out.println("  Backend count: " + backend.size());

        System.out.println("\n=== ITERATOR safe remove of unenrolled items ===");
        int dropped = catalog.dropUnenrolled();
        System.out.println("  dropped: " + dropped);
        System.out.println("  remaining:");
        for (Content item : catalog.listAll()) {
            System.out.println("    " + item.getTitle() + " enrollments=" + item.getEnrollmentCount());
        }

        System.out.println("\n=== LinkedHashMap insertion order ===");
        for (Map.Entry<Integer, String> e : catalog.recentTitles().entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        }

        System.out.println("\n=== ArrayList vs why not always List? ===");
        List<String> mutable = new ArrayList<String>(Arrays.asList("a", "b"));
        mutable.add("c");
        System.out.println("  ArrayList grew to " + mutable);
    }
}
