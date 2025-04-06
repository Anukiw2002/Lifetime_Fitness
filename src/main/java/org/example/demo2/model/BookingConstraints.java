package org.example.demo2.model;

public class BookingConstraints {
    private int constraintId;
    private int cancelLimitMinutes;
    private int rescheduleLimitMinutes;
    private int minBookingGapMins;
    private int maxBookingAdvanceWeeks;
    private boolean showBookingCount;
    private int maxBookingsPerSlot;

    public int getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(int constraintId) {
        this.constraintId = constraintId;
    }

    public int getCancelLimitMinutes() {
        return cancelLimitMinutes;
    }

    public void setCancelLimitMinutes(int cancelLimitMinutes){
        this.cancelLimitMinutes = cancelLimitMinutes;
    }

    public int getRescheduleLimitMinutes(){
        return rescheduleLimitMinutes;
    }

    public void setRescheduleLimitMinutes(int rescheduleLimitMinutes){
        this.rescheduleLimitMinutes = rescheduleLimitMinutes;
    }

    public int getMinBookingGapMins() {
        return minBookingGapMins;
    }

    public void setMinBookingGapMins(int minBookingGapMins){
        this.minBookingGapMins = minBookingGapMins;
    }

    public int getMaxBookingAdvanceWeeks() {
        return maxBookingAdvanceWeeks;
    }

    public void setMaxBookingAdvanceWeeks(int maxBookingAdvanceWeeks){
        this.maxBookingAdvanceWeeks = maxBookingAdvanceWeeks;
    }

    public boolean getShowBookingCount() {
        return showBookingCount;
    }

    public void setShowBookingCount(boolean showBookingCount){
        this.showBookingCount = showBookingCount;
    }

    public int getMaxBookingsPerSlot() {
        return maxBookingsPerSlot;
    }

    public void setMaxBookingsPerSlot(int maxBookingsPerSlot){
        this.maxBookingsPerSlot = maxBookingsPerSlot;
    }
}