package org.example.demo2.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ClientWorkout {
    private Long workoutId;
    private Long userId;  // Changed to match your preference (though DAO uses int)
    private String workoutName;
    private Long categoryId;
    private Long instructorId;
    private Timestamp createdAt;  // Changed to Timestamp for database compatibility
    private List<WorkoutExercise> exercises;
    private WorkoutCategory category;

    // Constructors
    public ClientWorkout() {}  // Added default constructor

    public ClientWorkout(Long userId, String workoutName, Long categoryId, Long instructorId) {
        this.userId = userId;
        this.workoutName = workoutName;
        this.categoryId = categoryId;
        this.instructorId = instructorId;
    }

    // Remove the unused phoneNumber constructor unless needed
    // public ClientWorkout(String phoneNumber, String workoutName, long categoryId, long instructorId) {}

    // Getters and Setters
    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getWorkoutName() { return workoutName; }
    public void setWorkoutName(String workoutName) { this.workoutName = workoutName; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    public LocalDateTime getCreatedAt() {
        return createdAt != null ? createdAt.toLocalDateTime() : null;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt != null ? Timestamp.valueOf(createdAt) : null;
    }

    public List<WorkoutExercise> getExercises() { return exercises; }
    public void setExercises(List<WorkoutExercise> exercises) { this.exercises = exercises; }

    public WorkoutCategory getCategory() { return category; }
    public void setCategory(WorkoutCategory category) { this.category = category; }

    // Remove these if not needed
    /*
    public String getClientPhone() {
        return "";
    }

    public void setCreatedAtDate(Date date) {
    }
    */
}