package org.example.demo2.model;

import java.sql.Date;

public class Booking {
    private Date date;
    private String timeSlot;
    private int userId;

    public Booking(Date date, String timeSlot, int userId) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int getUserId() {
        return userId;
    }
}