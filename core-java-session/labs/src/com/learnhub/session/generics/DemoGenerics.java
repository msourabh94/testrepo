package com.learnhub.session.generics;

import com.learnhub.session.oop.Content;
import com.learnhub.session.oop.Instructor;
import com.learnhub.session.oop.Priced;
import com.learnhub.session.oop.VideoCourse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoGenerics {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        Instructor ada = new Instructor("Ada Lovelace", "Foundations");
        VideoCourse course = new VideoCourse(101, "Core Java", "Backend", ada, 180, new BigDecimal("49"));

        System.out.println("=== Raw types are a trap (don't do this) ===");
        List raw = new ArrayList(); // intentional: runtime ClassCastException
        raw.add("oops");
        raw.add(course);
        try {
            VideoCourse first = (VideoCourse) raw.get(0); // ClassCastException
            System.out.println(first);
        } catch (ClassCastException ex) {
            System.out.println("  Runtime boom: " + ex);
        }

        System.out.println("\n=== Parameterized types catch mistakes at compile time ===");
        Repository<VideoCourse> courses = new Repository<VideoCourse>();
        courses.save(course.getId(), course);
        VideoCourse loaded = courses.find(101);
        System.out.println("  loaded: " + loaded.getTitle());
        // courses.save(1, ada); // would not compile — Instructor is not VideoCourse

        Repository<Instructor> instructors = new Repository<Instructor>();
        instructors.save(1, ada);
        System.out.println("  instructors size: " + instructors.size());

        System.out.println("\n=== Wildcards: PECS (Producer Extends, Consumer Super) ===");
        List<VideoCourse> videos = Arrays.asList(course);
        printTitles(videos); // List<? extends Content> can accept List<VideoCourse>

        List<Priced> cart = new ArrayList<Priced>();
        addPriced(cart, course); // List<? super Priced> can accept List<Priced>
        System.out.println("  cart size: " + cart.size());

        System.out.println("\n=== Generic method ===");
        VideoCourse picked = firstOrNull(videos);
        System.out.println("  firstOrNull => " + (picked == null ? "none" : picked.getTitle()));
    }

    /** Producer: we only READ Content (or a subtype). Use extends. */
    private static void printTitles(List<? extends Content> items) {
        for (Content item : items) {
            System.out.println("  title: " + item.getTitle());
        }
        // items.add(new VideoCourse(...)); // illegal — compiler does not know the exact subtype
    }

    /** Consumer: we WRITE Priced into the list. Use super. */
    private static void addPriced(List<? super Priced> dest, Priced item) {
        dest.add(item);
    }

    private static <T> T firstOrNull(List<T> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        return items.get(0);
    }
}
