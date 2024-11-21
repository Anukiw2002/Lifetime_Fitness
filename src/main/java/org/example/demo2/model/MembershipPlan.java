package org.example.demo2.model;

import java.time.LocalTime;
import java.util.List;

public class MembershipPlan {
    private Long planId;
    private String planName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String pricingType;
    private List<Duration> durations;

    // Constructors
    public MembershipPlan() {}

    public MembershipPlan(String planName, LocalTime startTime, LocalTime endTime, String pricingType) {
        this.planName = planName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pricingType = pricingType;
    }

    // Getters and Setters
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public String getPricingType() { return pricingType; }
    public void setPricingType(String pricingType) { this.pricingType = pricingType; }
    public List<Duration> getDurations() { return durations; }
    public void setDurations(List<Duration> durations) { this.durations = durations; }
}
