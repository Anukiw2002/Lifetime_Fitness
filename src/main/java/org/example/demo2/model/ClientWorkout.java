package org.example.demo2.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;

public class ClientWorkout {
    private Long workoutId;
    private String clientPhone;
    private String workoutName;
    private Long categoryId;
    private Long instructorId;
    private LocalDateTime createdAt;
    private List<WorkoutExercise> exercises;
    private WorkoutCategory category;
    private Date createdAtDate; // For joining with category details

    // Constructors
    public ClientWorkout() {}

    public ClientWorkout(String clientPhone, String workoutName, Long categoryId, Long instructorId) {
        this.clientPhone = clientPhone;
        this.workoutName = workoutName;
        this.categoryId = categoryId;
        this.instructorId = instructorId;
    }

    // Getters and Setters
    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }
    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }
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
    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }
}
