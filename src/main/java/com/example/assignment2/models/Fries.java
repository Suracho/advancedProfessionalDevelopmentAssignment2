package com.example.assignment2.models;

//Fries Class
public class Fries  extends Food{
    private static Double price = 4.0;
    public Fries() {
        super(Fries.getPrice(), 8, 5);
    }

    public Fries(Integer numberOfItemPerOrder) {
        super(Fries.getPrice(), 8, 5);
        this.numberOfItemPerOrder = numberOfItemPerOrder;
        // calculates time and sets it;
        this.timeTakenByNumberOfItemPerOrder = (int) (time * Math.ceil( (double) numberOfItemPerOrder / numberOfItemsPerBatch));
        this.costByNumberOfItemPerOrder = numberOfItemPerOrder * Fries.getPrice();
    }

    public static Double getPrice() {
        return price;
    }

    public static void setPrice(Double price) {
        Fries.price = price;
    }


    public void setNumberOfItemPerOrder(Integer numberOfItemPerOrder, boolean friesAvailableInTray) {
        this.numberOfItemPerOrder = numberOfItemPerOrder;
        // calculates time and sets it;
        if (friesAvailableInTray){
            this.timeTakenByNumberOfItemPerOrder = 0;
        } else {
            this.timeTakenByNumberOfItemPerOrder = (int) (time * Math.ceil( (double) numberOfItemPerOrder / numberOfItemsPerBatch));
        }
        this.costByNumberOfItemPerOrder = numberOfItemPerOrder * Fries.getPrice();
    }
}
