package org.example.demo2.model;

import java.time.LocalDate;
import java.util.List;

public class UserWeightData {
    private double beginningWeight;
    private double targetWeight;
    private double currentWeight;
    private List<Double> allWeights;
    private List<LocalDate> weightDates;


    public double getBeginningWeight() {return beginningWeight;}
    public void setBeginningWeight(double beginningWeight) {this.beginningWeight = beginningWeight;}

    public double getTargetWeight() { return targetWeight; }
    public void setTargetWeight(double targetWeight) { this.targetWeight = targetWeight; }

    public double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(double currentWeight) { this.currentWeight = currentWeight; }

    public List<Double> getAllWeights() { return allWeights; }
    public void setAllWeights(List<Double> allWeights) { this.allWeights = allWeights; }

    public List<LocalDate> getWeightDates() {return weightDates;}
    public void setWeightDates(List<LocalDate> weightDates) {this.weightDates = weightDates;}
}
