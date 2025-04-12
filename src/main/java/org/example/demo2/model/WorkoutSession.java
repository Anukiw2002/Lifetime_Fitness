package org.example.demo2.model;

import java.util.Date;

public class WorkoutSession {
    private int session_id;
    private int user_id;
    private Date started_at;
    private Date ended_at;
    private String notes;

    public WorkoutSession(int session_id, int user_id, Date started_at, Date ended_at, String notes) {
        this.session_id = session_id;
        this.user_id = user_id;
        this.started_at = started_at;
        this.ended_at = ended_at;
        this.notes = notes;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Date started_at) {
        this.started_at = started_at;
    }

    public Date getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Date ended_at) {
        this.ended_at = ended_at;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
