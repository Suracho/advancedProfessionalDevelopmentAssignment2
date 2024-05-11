package com.example.assignment2.models;

// model class for user
public class User {

    private String username;
    private Integer userId;

    private String firstName;
    private String lastName;
    private String password;
    private String isLoggedIn;
    private String isVipPermissionAsked;

    public User(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String username, Integer userId, String firstName, String lastName, String password, String isLoggedIn, String isVipPermissionAsked) {
        this.username = username;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
        this.isVipPermissionAsked = isVipPermissionAsked;
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

    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getIsVipPermissionAsked() {
        return isVipPermissionAsked;
    }

    public void setIsVipPermissionAsked(String isVipPermissionAsked) {
        this.isVipPermissionAsked = isVipPermissionAsked;
    }
}
