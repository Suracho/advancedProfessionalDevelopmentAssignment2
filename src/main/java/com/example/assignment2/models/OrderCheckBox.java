package com.example.assignment2.models;

import javafx.scene.control.CheckBox;

// Custom class to hold the order id and its selection state
public class OrderCheckBox {
    private final String orderId;
    private final CheckBox checkBox;

    public OrderCheckBox(String orderId) {
        this.orderId = orderId;
        this.checkBox = new CheckBox();
    }

    public String getOrderId() {
        return orderId;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}