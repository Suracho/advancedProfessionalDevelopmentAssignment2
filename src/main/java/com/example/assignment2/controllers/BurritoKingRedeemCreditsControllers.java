package com.example.assignment2.controllers;

import com.example.assignment2.models.Credits;
import com.example.assignment2.models.DaoOrders;
import com.example.assignment2.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// Controller class for redeeming credits page in Burrito King application
public class BurritoKingRedeemCreditsControllers extends CommonFunctions{

    private Double paymentAmount; // Payment amount field

    private Credits credits; // Credits field

    @FXML
    private Button redeemCreditsButton; // Redeem credits button

    @FXML
    private Label creditsNumberLabel; // Label for displaying credits

    @FXML
    private Label notEnoughCreditsLabel; // Label for displaying not enough credits message

    // Setter method for paymentAmount
    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    // Setter method for credits
    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    @FXML
    public void initialize() {
        setValues(); // Initialize method setting initial values
    }

    @FXML
    public void redeemCreditsClickMethod() throws Exception {
        // Method to handle redeem credits button click
        Integer creditsAvailable = this.credits.getCredits();
        // Checking if user has enough credits to redeem
        if (creditsAvailable < this.paymentAmount || creditsAvailable < 100){
            notEnoughCreditsLabel.setText("You don't have enough credits, please proceed to payment page.");
            return;
        }
        redeemCredits(this.paymentAmount, this.credits.getCredits()); // Redeeming credits
        setValues(); // Updating UI
    }

    @FXML
    public void proceedToPaymentScreen() throws Exception {
        // Method to proceed to payment screen
        changeScreenFromRedeemToPayment((int) Math.floor(this.paymentAmount));
    }

    // Method to set initial values
    private void setValues(){
        User user = getIsLoggedInUser();
        int userId = user.getUserId();
        Credits credits = getCreditsForUser(userId);
        setCredits(credits); // Setting credits
        DaoOrders daoOrders = getOrderWithPendingPayment(userId);
        this.setPaymentAmount(daoOrders.getTotalPrice());
        // Setting text for creditsNumberLabel
        creditsNumberLabel.setText("Credits: " + this.credits.getCredits().toString() + " and your final payment amount is " + this.paymentAmount);
    }
}
