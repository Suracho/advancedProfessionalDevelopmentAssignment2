package com.example.assignment2.models;


// Food Class
public abstract class Food {
    protected Double cost;
    protected  Integer time;
    protected Integer numberOfItemsPerBatch;

    protected Integer numberOfItemPerOrder;
    // time calculated based on the numberOfItemPerOrder instance variable
    protected Integer timeTakenByNumberOfItemPerOrder;
    // cost calculated based on the numberOfItemPerOrder instance variable
    protected Double costByNumberOfItemPerOrder;

    public Food(Double cost, Integer time, Integer numberOfItemsPerBatch) {
        this.cost = cost;
        this.time = time;
        this.numberOfItemsPerBatch = numberOfItemsPerBatch;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getNumberOfItemsPerBatch() {
        return numberOfItemsPerBatch;
    }

    public void setNumberOfItemsPerBatch(Integer numberOfItemsPerBatch) {
        this.numberOfItemsPerBatch = numberOfItemsPerBatch;
    }

    public Integer getNumberOfItemPerOrder() {
        return numberOfItemPerOrder;
    }

    public Integer getTimeTakenByNumberOfItemPerOrder() {
        return timeTakenByNumberOfItemPerOrder;
    }

    public Double getCostByNumberOfItemPerOrder() {
        return costByNumberOfItemPerOrder;
    }

    public void setNumberOfItemPerOrder(Integer numberOfItemPerOrder) {
        this.numberOfItemPerOrder = numberOfItemPerOrder;
    }

    public void setTimeTakenByNumberOfItemPerOrder(Integer timeTakenByNumberOfItemPerOrder) {
        this.timeTakenByNumberOfItemPerOrder = timeTakenByNumberOfItemPerOrder;
    }

    public void setCostByNumberOfItemPerOrder(Double costByNumberOfItemPerOrder) {
        this.costByNumberOfItemPerOrder = costByNumberOfItemPerOrder;
    }
}