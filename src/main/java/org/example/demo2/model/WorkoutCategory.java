package org.example.demo2.model;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutCategory {
    private Long categoryId;
    private String categoryName;


    public WorkoutCategory() {}

    public WorkoutCategory(String categoryName) {
        this.categoryName = categoryName;
    }


    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}