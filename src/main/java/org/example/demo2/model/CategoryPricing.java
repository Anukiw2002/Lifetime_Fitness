package org.example.demo2.model;

import java.math.BigDecimal;

public class CategoryPricing {
    private Long categoryPricingId;
    private Long durationId;
    private String category;
    private BigDecimal price;

    // Constructors
    public CategoryPricing() {}

    public CategoryPricing(Long durationId, String category, BigDecimal price) {
        this.durationId = durationId;
        this.category = category;
        this.price = price;
    }

    // Getters and Setters
    public Long getCategoryPricingId() { return categoryPricingId; }
    public void setCategoryPricingId(Long categoryPricingId) { this.categoryPricingId = categoryPricingId; }
    public Long getDurationId() { return durationId; }
    public void setDurationId(Long durationId) { this.durationId = durationId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
