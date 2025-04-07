package org.example.demo2.model;

import java.sql.Date;
import java.sql.Time;

public class BookSession {
    private int bookingId;
    private Date date;
    private Time timeSlot;
    private int userId;

    public int getBookingId(){
        return bookingId;
    }

    public void setBookingId(int bookingId){
        this.bookingId = bookingId;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Time getTimeSlot(){
        return timeSlot;
    }

    public void setTimeSlot(Time timeSlot){
        this.timeSlot = timeSlot;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
}
