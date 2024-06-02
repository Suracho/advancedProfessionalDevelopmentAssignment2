package com.example.assignment2.models;

// class for credits
public class Credits {
    private Integer credits;
    private Integer userId;

    public Credits(Integer credits, Integer userId) {
        this.credits = credits;
        this.userId = userId;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
