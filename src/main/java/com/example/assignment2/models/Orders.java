package com.example.assignment2.models;

//This is the class for Order Entity in the database
public class Orders {
    private Double totalPrice;
    private Double waitingTime;
    private boolean pendingPayment;
    private Integer orderId;
    private Integer userId;

    private String orderStatus;
    private String timeOrdered;
    private String dayOrdered;
    private String collectionTime;
    private String summaryText;
    private Integer creditsApplied;


    public Orders(Double totalPrice, Double waitingTime, boolean pendingPayment, Integer orderId, Integer userId, String orderStatus, String timeOrdered, String dayOrdered, String collectionTime, String summaryText, Integer creditsApplied) {
        this.totalPrice = totalPrice;
        this.waitingTime = waitingTime;
        this.pendingPayment = pendingPayment;
        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.timeOrdered = timeOrdered;
        this.dayOrdered = dayOrdered;
        this.collectionTime = collectionTime;
        this.summaryText = summaryText;
        this.creditsApplied = creditsApplied;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isPendingPayment() {
        return pendingPayment;
    }

    public void setPendingPayment(boolean pendingPayment) {
        this.pendingPayment = pendingPayment;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getTimeOrdered() {
        return timeOrdered;
    }

    public void setTimeOrdered(String timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public String getDayOrdered() {
        return dayOrdered;
    }

    public void setDayOrdered(String dayOrdered) {
        this.dayOrdered = dayOrdered;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public Integer getCreditsApplied() {
        return creditsApplied;
    }

    public void setCreditsApplied(Integer creditsApplied) {
        this.creditsApplied = creditsApplied;
    }
}
