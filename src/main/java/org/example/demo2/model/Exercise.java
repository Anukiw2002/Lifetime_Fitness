package org.example.demo2.model;

import java.time.LocalDateTime;
import java.util.List;

public class Exercise {
    private Long exerciseId;
    private String exerciseName;
    private String description;


    public Exercise() {}

    public Exercise(String exerciseName, String description) {
        this.exerciseName = exerciseName;
        this.description = description;
    }


    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}