package com.example.assignment2.controllers;

import com.example.assignment2.models.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import static com.example.assignment2.controllers.BurritoKingApplication.getMenu;

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
    private TextField friesCount;

    @FXML
    private TextField sodaCount;

    @FXML
    private TextField burritoCount;


    @FXML
    public void initialize() {
        addTextFieldListeners();
    }

    // Adds listeners to text fields to disable plus or minus buttons based on value changes
    private void addTextFieldListeners() {
        addListenerToTextField(burritoCount, burritoPlus, burritoMinus);
        addListenerToTextField(friesCount, friesPlus, friesMinus);
        addListenerToTextField(sodaCount, sodaPlus, sodaMinus);
    }

    private void addListenerToTextField(TextField textField, Button plusButton, Button minusButton) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            disablePlusButton(plusButton, textField);
            disableMinusButton(minusButton, textField);
        });
    }


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

    // Adds order to the database after fetching values from the count label
    @FXML
    protected void addOrderToDatabase(){
        int sodaCounter = Integer.parseInt(sodaCount.getText());
        int friesCounter = Integer.parseInt(friesCount.getText());
        int burritoCounter = Integer.parseInt(burritoCount.getText());

        Pair<Double, String> paymentInfo = BurritoKingApplication.getMenu().order(burritoCounter, friesCounter, sodaCounter, 0);
        Double totalPrice = paymentInfo.getKey();
        String summaryText = paymentInfo.getValue();
        Double waitingTime = Restaurant.getAllOrders().getLast().getPrepTime(BurritoKingApplication.getRestaurant());
        insertOrderInDb(true, totalPrice, waitingTime);
    }

    // Disables the plus button if the label's text is "100".
    @FXML
    private void disablePlusButton(Button button, TextField textField) {
        int value = Integer.parseInt(textField.getText());
        if (value > 100) {
            textField.setText("100");
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }

    // Disables the minus button if the label's text is "0".
    @FXML
    private void disableMinusButton(Button button, TextField textField) {
        int value = Integer.parseInt(textField.getText());
        if (value < 0) {
            textField.setText("0");
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }
}
