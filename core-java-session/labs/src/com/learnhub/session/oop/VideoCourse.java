package com.learnhub.session.oop;

import java.math.BigDecimal;

public class VideoCourse extends Content implements Priced {
    private final int durationMinutes;
    private final BigDecimal price;

    public VideoCourse(int id, String title, String category, Instructor instructor,
                       int durationMinutes, BigDecimal price) {
        super(id, title, category, instructor);
        this.durationMinutes = durationMinutes;
        this.price = price;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    protected String formatLabel() {
        return "VIDEO " + durationMinutes + "m";
    }
}
