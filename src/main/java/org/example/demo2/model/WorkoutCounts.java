package org.example.demo2.model;

public class WorkoutCounts {
    private int today;
    private int yesterday;
    private int tomorrow;

    public WorkoutCounts(int today, int yesterday, int tomorrow){
        this.today = today;
        this.yesterday = yesterday;
        this.tomorrow = tomorrow;
    }
    public int getToday(){
        return today;
    }

    public int getYesterday(){
        return yesterday;
    }

    public int getTomorrow(){
        return tomorrow;
    }

}
