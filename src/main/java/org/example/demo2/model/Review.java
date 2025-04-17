package org.example.demo2.model;

import java.util.Date;

public class Review {
    private int rating;
    private String review;
    private Date createdAt;
    private String name;

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt(){
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
