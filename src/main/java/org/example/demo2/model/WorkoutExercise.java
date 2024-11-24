package org.example.demo2.model;

public class WorkoutExercise {
    private Long workoutExerciseId;
    private Long workoutId;
    private Long exerciseId;
    private Integer setNumber;
    private Integer reps;
    private String notes;
    private Exercise exercise;  // For joining with exercise details

    // Constructors
    public WorkoutExercise() {}

    public WorkoutExercise(Long workoutId, Long exerciseId, Integer setNumber, Integer reps) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.setNumber = setNumber;
        this.reps = reps;
    }

    // Getters and Setters
    public Long getWorkoutExerciseId() { return workoutExerciseId; }
    public void setWorkoutExerciseId(Long workoutExerciseId) { this.workoutExerciseId = workoutExerciseId; }
    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }
    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }
    public Integer getSetNumber() { return setNumber; }
    public void setSetNumber(Integer setNumber) { this.setNumber = setNumber; }
    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }
}