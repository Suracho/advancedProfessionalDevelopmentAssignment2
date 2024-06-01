package com.example.assignment2.controllers;

import com.example.assignment2.models.Credits;
import com.example.assignment2.models.DaoOrders;
import com.example.assignment2.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BurritoKingRedeemCreditsControllers extends CommonFunctions{

    private Double paymentAmount;

    private Credits credits;

    @FXML
    private Button redeemCreditsButton;

    @FXML
    private Label creditsNumberLabel;

    @FXML
    private Label notEnoughCreditsLabel;

    // setter method for paymentAmount
    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    // setter method for credits
    public void setCredits(Credits credits) {
        this.credits = credits;
    }


    @FXML
    public void redeemCreditsClickMethod() throws Exception {
        Integer creditsAvailable = this.credits.getCredits();
        if (creditsAvailable < this.paymentAmount || creditsAvailable < 100){
            notEnoughCreditsLabel.setText("You don't have enough credits, please proceed to payment page.");
            return;
        }
        redeemCredits(this.paymentAmount, this.credits.getCredits());
        setValues();
    }


    @FXML
    public void initialize() {
        setValues();
    }

    @FXML
    public void proceedToPaymentScreen() throws Exception {

        changeScreenFromRedeemToPayment((int) Math.floor(this.paymentAmount));
    }

    private void setValues(){
        User user = getIsLoggedInUser();
        int userId = user.getUserId();
        // gets credits and then sets it
        Credits credits = getCreditsForUser(userId);
        setCredits(credits);

        DaoOrders daoOrders = getOrderWithPendingPayment(userId);
        this.setPaymentAmount(daoOrders.getTotalPrice());
        creditsNumberLabel.setText("Credits: " + this.credits.getCredits().toString() + " and your final payment amount is " + this.paymentAmount);
    }

}
