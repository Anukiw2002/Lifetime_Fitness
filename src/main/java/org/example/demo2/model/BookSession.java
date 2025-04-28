package org.example.demo2.model;

import java.sql.Date;
import java.sql.Time;

public class BookSession {
    private int bookingId;
    private Date date;
    private Time timeSlot;
    private String status;
    private int userId;
    private String fname;
    private String lname;
    private String phoneNumber;
    private String displayDateLabel;


    public BookSession(Date date, Time timeSlot){
        this.date = date;
        this.timeSlot = timeSlot;
    }

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

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getFname(){
        return fname;
    }

    public void setFname(String fname){
        this.fname = fname;
    }

    public String getLname(){
        return lname;
    }

    public void setLname(String lname){
        this.lname = lname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayDateLabel() {
        return displayDateLabel;
    }

    public void setDisplayDateLabel(String displayDateLabel) {
        this.displayDateLabel = displayDateLabel;
    }
}
