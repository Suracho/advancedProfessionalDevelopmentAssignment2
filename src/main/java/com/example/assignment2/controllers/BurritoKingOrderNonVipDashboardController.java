package com.example.assignment2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// This is the Controller class for managing the Burrito King Order Non-VIP Dashboard. It contains increment decrement logic and add to cart logic
public class BurritoKingOrderNonVipDashboardController extends CommonFunctions {

    @FXML
    private Button burritoPlus;

    @FXML
    private Button burritoMinus;

    @FXML
    private Button friesPlus;

    @FXML
    private Button friesMinus;

    @FXML
    private Button sodaPlus;

    @FXML
    private Button sodaMinus;

    @FXML
    private Label friesCount;

    @FXML
    private Label sodaCount;

    @FXML
    private Label burritoCount;



    // Increments the burrito count and updates the label, disabling buttons as needed.
    @FXML
    protected void incrementBurrito() {
        int count = Integer.parseInt(burritoCount.getText());
        count++;
        burritoCount.setText(String.valueOf(count));
        disablePlusButton(burritoPlus, burritoCount);
        disableMinusButton(burritoMinus, burritoCount);
    }

    // Decrements the burrito count and updates the label, disabling buttons as needed.
    @FXML
    protected void decrementBurrito() {
        int count = Integer.parseInt(burritoCount.getText());
        count--;
        burritoCount.setText(String.valueOf(count));
        disablePlusButton(burritoPlus, burritoCount);
        disableMinusButton(burritoMinus, burritoCount);
    }

    // Increments the fries count and updates the label, disabling buttons as needed.
    @FXML
    protected void incrementFries() {
        int count = Integer.parseInt(friesCount.getText());
        count++;
        friesCount.setText(String.valueOf(count));
        disablePlusButton(friesPlus, friesCount);
        disableMinusButton(friesMinus, friesCount);
    }

    // Decrements the fries count and updates the label, disabling buttons as needed.
    @FXML
    protected void decrementFries() {
        int count = Integer.parseInt(friesCount.getText());
        count--;
        friesCount.setText(String.valueOf(count));
        disablePlusButton(friesPlus, friesCount);
        disableMinusButton(friesMinus, friesCount);
    }

    // Increments the soda count and updates the label, disabling buttons as needed.
    @FXML
    protected void incrementSoda() {
        int count = Integer.parseInt(sodaCount.getText());
        count++;
        sodaCount.setText(String.valueOf(count));
        disablePlusButton(sodaPlus, sodaCount);
        disableMinusButton(sodaMinus, sodaCount);
    }

    // Decrements the soda count and updates the label, disabling buttons as needed.
    @FXML
    protected void decrementSoda() {
        int count = Integer.parseInt(sodaCount.getText());
        count--;
        sodaCount.setText(String.valueOf(count));
        disablePlusButton(sodaPlus, sodaCount);
        disableMinusButton(sodaMinus, sodaCount);
    }

    // Disables the plus button if the label's text is "100".
    @FXML
    private void disablePlusButton(Button button, Label label) {
        if (label.getText().equals("100")) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }

    // Disables the minus button if the label's text is "0".
    @FXML
    private void disableMinusButton(Button button, Label label) {
        if (label.getText().equals("0")) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }
}
