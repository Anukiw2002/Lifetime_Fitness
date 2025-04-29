package org.example.demo2.model;

import java.sql.Date; // Import for SQL date handling
import java.sql.Timestamp;

public class Report {
    private int id;
    private String name;
    private int age;
    private String programNo;
    private Date startingDate;
    private Date expireDate;
    private String frequency;
    private int timesPerWeek;
    private int maxHeartRate;
    private int bpm65;
    private int bpm75;
    private int bpm85;
    private int waistCircumference;
    private double bodyWeight;
    private double height;
    private double fatPercentage;
    private double bmr;
    private String goal;
    private String warmUp;
    private String flexibility;
    private String cardio;
    private String remarks;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private double target_weight;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProgramNo() {
        return programNo;
    }

    public void setProgramNo(String programNo) {
        this.programNo = programNo;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getTimesPerWeek() {
        return timesPerWeek;
    }

    public void setTimesPerWeek(int timesPerWeek) {
        this.timesPerWeek = timesPerWeek;
    }

    public int getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(int maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    public int getBpm65() {
        return bpm65;
    }

    public void setBpm65(int bpm65) {
        this.bpm65 = bpm65;
    }

    public int getBpm75() {
        return bpm75;
    }

    public void setBpm75(int bpm75) {
        this.bpm75 = bpm75;
    }

    public int getBpm85() {
        return bpm85;
    }

    public void setBpm85(int bpm85) {
        this.bpm85 = bpm85;
    }

    public int getWaistCircumference() {
        return waistCircumference;
    }

    public void setWaistCircumference(int waistCircumference) {
        this.waistCircumference = waistCircumference;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    public double getBmr() {
        return bmr;
    }

    public void setBmr(double bmr) {
        this.bmr = bmr;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getWarmUp() {
        return warmUp;
    }

    public void setWarmUp(String warmUp) {
        this.warmUp = warmUp;
    }

    public String getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(String flexibility) {
        this.flexibility = flexibility;
    }

    public String getCardio() {
        return cardio;
    }

    public void setCardio(String cardio) {
        this.cardio = cardio;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTarget_weight(double target_weight) {this.target_weight = target_weight;}

    public double getTarget_weight() {return target_weight;}

    public double getBmi() {
        if (height <= 0) {
            return 0;
        }


        double heightInMeters = height;
        if (height > 3) {
            heightInMeters = height / 100.0;
        }

        double bmi = bodyWeight / (heightInMeters * heightInMeters);
        return Math.round(bmi * 10) / 10.0; // Round to one decimal place
    }
}

