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
    private String status;
    private String colour;


    public MembershipPlan() {}

    public MembershipPlan(String planName, LocalTime startTime, LocalTime endTime, String pricingType, String colour) {
        this.planName = planName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pricingType = pricingType;
        this.colour =  colour;
    }


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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status;}
    public String getColour() { return colour; }
    public void setColour(String colour) { this.colour = colour;}

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
}
