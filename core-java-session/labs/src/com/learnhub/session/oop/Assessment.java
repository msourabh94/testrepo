package com.learnhub.session.oop;

/** Free skill check — enrollable, but not priced. */
public class Assessment extends Content {
    private final int passingScore;

    public Assessment(int id, String title, String category, Instructor instructor, int passingScore) {
        super(id, title, category, instructor);
        this.passingScore = passingScore;
    }

    public int getPassingScore() {
        return passingScore;
    }

    @Override
    protected String formatLabel() {
        return "ASSESS pass>=" + passingScore;
    }
}
