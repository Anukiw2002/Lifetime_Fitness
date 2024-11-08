package org.example.demo2.model;

public class LeaderBoardEntry {
    private int rating;
    private String name;
    private String category;
    private int maximumKg;

    public LeaderBoardEntry(int rating, String name, String category, int maximumKg) {
        this.rating = rating;
        this.name = name;
        this.category = category;
        this.maximumKg = maximumKg;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getMaximumKg() {
        return maximumKg;
    }
}
