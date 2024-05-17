package com.example.assignment2.models;

//This is the class for Order Entity in the database
public class Orders {
    private Double totalPrice;
    private Double waitingTime;
    private boolean pendingPayment;
    private Integer orderId;
    private Integer userId;

    public Orders(Double totalPrice, Double waitingTime, boolean pendingPayment, Integer orderId, Integer userId) {
        this.totalPrice = totalPrice;
        this.waitingTime = waitingTime;
        this.pendingPayment = pendingPayment;
        this.orderId = orderId;
        this.userId = userId;
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
}
