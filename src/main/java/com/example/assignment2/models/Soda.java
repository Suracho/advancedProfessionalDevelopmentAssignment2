package com.example.assignment2.models;

// Soda Class
public class Soda extends Food{

    private static Double price = 4.0;



    public Soda(Integer numberOfItemPerOrder) {
        super(Soda.getPrice(), 0, 0);
        this.numberOfItemPerOrder = numberOfItemPerOrder;
        // calculates time and sets it;
        this.timeTakenByNumberOfItemPerOrder = 0;
        this.costByNumberOfItemPerOrder = numberOfItemPerOrder * Soda.getPrice();
    }

    public static Double getPrice() {
        return price;
    }

    public static void setPrice(Double price) {
        Soda.price = price;
    }


}
