package org.example.demo2.model;

public class LeaderBoardEntry {
    private String full_name;
    private int amount;

    public LeaderBoardEntry( String full_name,  int amount) {
        this.full_name = full_name;
        this.amount = amount;
    }

    public LeaderBoardEntry() {
        // default constructor
    }



    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
