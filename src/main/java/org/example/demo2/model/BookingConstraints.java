package org.example.demo2.model;

public class BookingConstraints {
    private int constraintId;
    private int maxBookingAdvanceWeeks;
    private int maxBookingsPerSlot;

    public int getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(int constraintId) {
        this.constraintId = constraintId;
    }

    public int getMaxBookingAdvanceWeeks() {
        return maxBookingAdvanceWeeks;
    }

    public void setMaxBookingAdvanceWeeks(int maxBookingAdvanceWeeks){
        this.maxBookingAdvanceWeeks = maxBookingAdvanceWeeks;
    }

    public int getMaxBookingsPerSlot() {
        return maxBookingsPerSlot;
    }

    public void setMaxBookingsPerSlot(int maxBookingsPerSlot){
        this.maxBookingsPerSlot = maxBookingsPerSlot;
    }
}