package com.example.assignment2.models;

// model class for vip user
public class VipUsers {
    private String emailAddress;
    private String vipUserId;
    private String userId;

    public VipUsers(String emailAddress, String vipUserId, String userId) {
        this.emailAddress = emailAddress;
        this.vipUserId = vipUserId;
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getVipUserId() {
        return vipUserId;
    }

    public void setVipUserId(String vipUserId) {
        this.vipUserId = vipUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
