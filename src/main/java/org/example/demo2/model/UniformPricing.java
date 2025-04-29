package org.example.demo2.model;

import java.math.BigDecimal;

public class UniformPricing {
    private Long pricingId;
    private Long durationId;
    private BigDecimal price;


    public UniformPricing() {}

    public UniformPricing(Long durationId, BigDecimal price) {
        this.durationId = durationId;
        this.price = price;
    }


    public Long getPricingId() { return pricingId; }
    public void setPricingId(Long pricingId) { this.pricingId = pricingId; }
    public Long getDurationId() { return durationId; }
    public void setDurationId(Long durationId) { this.durationId = durationId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
