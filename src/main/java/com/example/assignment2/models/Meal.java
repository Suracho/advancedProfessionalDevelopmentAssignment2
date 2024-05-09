package com.example.assignment2.models;

import java.util.ArrayList;

// Meal Class
public class Meal extends Food{
    private ArrayList<Fries> fries;
    private Soda soda;
    private Burrito burrito;

    public Meal(Integer numberOfItemPerOrder) {
        super(0.0, 9, numberOfItemPerOrder);
        Double price = this.computeCost(numberOfItemPerOrder);
        this.setCost(price);

        Integer time = this.computeTime(numberOfItemPerOrder);
        this.setTime(time);
    }

    public ArrayList<Fries> getFries() {
        return fries;
    }

    public void setFries(ArrayList<Fries> fries) {
        this.fries = fries;
    }

    public Soda getSoda() {
        return soda;
    }

    public void setSoda(Soda soda) {
        this.soda = soda;
    }

    public Burrito getBurrito() {
        return burrito;
    }

    public void setBurrito(Burrito burrito) {
        this.burrito = burrito;
    }

    private Double computeCost(Integer numberOfItemPerOrder){
        return ((Fries.getPrice() * numberOfItemPerOrder) - numberOfItemPerOrder) + ((Burrito.getPrice() * numberOfItemPerOrder) - numberOfItemPerOrder) + ((Soda.getPrice() * numberOfItemPerOrder) - numberOfItemPerOrder);
    }

    private Integer computeTime(Integer numberOfItemPerOrder){
        return (Integer) (int) (9 * Math.ceil( (double) numberOfItemPerOrder / 2));
    }
}
