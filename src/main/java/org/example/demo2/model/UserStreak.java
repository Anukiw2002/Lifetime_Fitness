package org.example.demo2.model;

public class UserStreak {
    private String name;
    private int streak;

    public UserStreak(String name, int streak) {
        this.name = name;
        this.streak = streak;
    }

    public String getName(){
        return name;
    }
    public int getStreak(){
        return streak;
    }
}
