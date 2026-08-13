package com.learnhub.session.oop;

import java.math.BigDecimal;

public class LiveWorkshop extends Content implements Priced {
    private final int seats;
    private final BigDecimal price;

    public LiveWorkshop(int id, String title, String category, Instructor instructor,
                        int seats, BigDecimal price) {
        super(id, title, category, instructor);
        this.seats = seats;
        this.price = price;
    }

    @Override
    public void enroll(String learnerEmail) {
        if (getEnrollmentCount() >= seats) {
            throw new IllegalStateException("Workshop is full (" + seats + " seats)");
        }
        super.enroll(learnerEmail);
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public int remainingSeats() {
        return seats - getEnrollmentCount();
    }

    @Override
    protected String formatLabel() {
        return "LIVE seats=" + remainingSeats();
    }
}
