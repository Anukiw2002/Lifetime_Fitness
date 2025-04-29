package org.example.demo2.model;

public class WorkoutLogs {
    private int user_id;
    private int workout_id;
    private int exercise_id;
    private int set_number;
    private Double weight;
    private int reps;
    private String notes;


    public WorkoutLogs() {
    }


    public WorkoutLogs(int user_id, int workout_id, int exercise_id, int set_number, Double weight, int reps, String notes) {
        this.user_id = user_id;
        this.workout_id = workout_id;
        this.exercise_id = exercise_id;
        this.set_number = set_number;
        this.weight = weight;
        this.reps = reps;
        this.notes = notes;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int userId) {
        this.user_id = userId;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workoutId) {
        this.workout_id = workoutId;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exerciseId) {
        this.exercise_id = exerciseId;
    }

    public int getSet_number() {
        return set_number;
    }

    public void setSet_number(int set_number) {
        this.set_number = set_number;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "WorkoutLogs{" +
                "user_id=" + user_id +
                ", workout_id=" + workout_id +
                ", exercise_id=" + exercise_id +
                ", set_number=" + set_number +
                ", weight=" + weight +
                ", reps=" + reps +
                ", notes='" + notes + '\'' +
                '}';
    }
}