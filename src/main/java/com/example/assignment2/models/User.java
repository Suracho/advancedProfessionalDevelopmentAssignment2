package com.example.assignment2.models;

// model class for user
public class User {

    private String username;
    private Integer userId;
    private Integer isVip;



    private String firstName;
    private String lastName;
    private String password;

    public User(String username, Integer userId, Integer isVip, String firstName, String lastName, String password) {
        this.username = username;
        this.userId = userId;
        this.isVip = isVip;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }
}
