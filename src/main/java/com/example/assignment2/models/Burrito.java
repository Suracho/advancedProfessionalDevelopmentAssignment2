package com.example.assignment2.models;

// Burrito Class
public class Burrito extends Food{


    private static Double price = 7.0;

    public Burrito(Integer numberOfItemPerOrder) {
        super(Burrito.getPrice(), 9, 2);
        this.numberOfItemPerOrder = numberOfItemPerOrder;

        // calculates time and sets it;
        timeTakenByNumberOfItemPerOrder = (int) (time * Math.ceil( (double) numberOfItemPerOrder / numberOfItemsPerBatch));
        costByNumberOfItemPerOrder = numberOfItemPerOrder * Burrito.getPrice();
    }

    public static Double getPrice() {
        return price;
    }

    public static void setPrice(Double price) {
        Burrito.price = price;
    }
}
