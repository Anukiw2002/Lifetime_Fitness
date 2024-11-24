package org.example.demo2.model;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutCategory {
    private Long categoryId;
    private String categoryName;

    // Constructors
    public WorkoutCategory() {}

    public WorkoutCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}