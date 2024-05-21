package com.example.assignment2.models;

// This is the model class for Food Entity database
public class Food {

    private Integer orderId;
    private Double unitPrice;
    private FoodType foodType;
    private Integer quantity;


    public Food(Integer orderId, Double unitPrice, FoodType foodType, Integer quantity) {
        this.orderId = orderId;
        this.unitPrice = unitPrice;
        this.foodType = foodType;
        this.quantity = quantity;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
