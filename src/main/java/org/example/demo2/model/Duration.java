package org.example.demo2.model;
import java.util.List;

public class Duration {
    private Long durationId;
    private Long planId;
    private Integer durationValue;
    private String durationType;
    private List<UniformPricing> uniformPricing;
    private List<CategoryPricing> categoryPricing;


    public Duration() {}

    public Duration(Long planId, Integer durationValue, String durationType) {
        this.planId = planId;
        this.durationValue = durationValue;
        this.durationType = durationType;
    }


    public Long getDurationId() { return durationId; }
    public void setDurationId(Long durationId) { this.durationId = durationId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Integer getDurationValue() { return durationValue; }
    public void setDurationValue(Integer durationValue) { this.durationValue = durationValue; }
    public String getDurationType() { return durationType; }
    public void setDurationType(String durationType) { this.durationType = durationType; }
    public List<UniformPricing> getUniformPricing() { return uniformPricing; }
    public void setUniformPricing(List<UniformPricing> uniformPricing) { this.uniformPricing = uniformPricing; }
    public List<CategoryPricing> getCategoryPricing() { return categoryPricing; }
    public void setCategoryPricing(List<CategoryPricing> categoryPricing) { this.categoryPricing = categoryPricing; }
}