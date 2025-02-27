package org.example.demo2.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ClientWorkout {
    private Long workoutId;
    private Long userId;
    private String workoutName;
    private Long categoryId;
    private Long instructorId;
    private LocalDateTime createdAt;
    private List<WorkoutExercise> exercises;
    private WorkoutCategory category;

    // Constructors
    public ClientWorkout(String phoneNumber, String workoutName, long categoryId, long instructorId) {}

    public ClientWorkout(Long userId, String workoutName, Long categoryId, Long instructorId) {
        this.userId = userId;
        this.workoutName = workoutName;
        this.categoryId = categoryId;
        this.instructorId = instructorId;
    }

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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<WorkoutExercise> getExercises() { return exercises; }
    public void setExercises(List<WorkoutExercise> exercises) { this.exercises = exercises; }
    public WorkoutCategory getCategory() { return category; }
    public void setCategory(WorkoutCategory category) { this.category = category; }

    public String getClientPhone() {
        return "";
    }

    public void setCreatedAtDate(Date date) {

    }
}
