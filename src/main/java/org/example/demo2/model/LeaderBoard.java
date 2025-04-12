package org.example.demo2.model;

public class LeaderBoard {
    private String name;
    private String email;
    private double startingWeight;
    private double currentWeight;
    private double weightLoss;
    private int rank;

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public double getStartingWeight(){return startingWeight;}
    public void setStartingWeight(double startingWeight){this.startingWeight = startingWeight;}

    public double getCurrentWeight(){return currentWeight;}
    public void setCurrentWeight(double currentWeight){this.currentWeight = currentWeight;}

    public double getWeightLoss(){return weightLoss;}
    public void setWeightLoss(double weightLoss){this.weightLoss = weightLoss;}

    public int getRank(){return rank;}
    public void setRank(int rank){this.rank = rank;}
}

