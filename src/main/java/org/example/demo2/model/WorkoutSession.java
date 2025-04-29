package org.example.demo2.model;

import java.sql.Time;
import java.util.Date;

public class WorkoutSession {
    private int session_Id;
    private int user_Id;
    private Date started_at;
    private Date ended_at;
    private String notes;
    private int workout_id;
    private Time duration;
    private String workoutName;


    public WorkoutSession(int sessionId, int userId, Date startedAt, Date endedAt, String notes) {
        this.session_Id = sessionId;
        this.user_Id = userId;
        this.started_at = startedAt;
        this.ended_at = endedAt;
        this.notes = notes;
    }

    public WorkoutSession(){}


    public int getSession_Id() {
        return session_Id;
    }

    public void setSession_Id(int session_Id) {
        this.session_Id = session_Id;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
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

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
