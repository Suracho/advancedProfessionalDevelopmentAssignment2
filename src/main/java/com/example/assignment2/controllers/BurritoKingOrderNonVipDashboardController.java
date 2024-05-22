package com.example.assignment2.controllers;

import com.example.assignment2.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

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
        List<Food> foodList;
        addTextFieldListeners();
        int userId = getIsLoggedInUser().getUserId();
        Orders order = getOrderWithPendingPayment(userId);
        if (order == null){
            setCounts(false, null);
        } else {
            foodList = getFoodListForOrders(order);
            setCounts(true, foodList);
        }
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
    protected void addOrderToDatabase() throws IOException {
        int sodaCounter = Integer.parseInt(sodaCount.getText());
        int friesCounter = Integer.parseInt(friesCount.getText());
        int burritoCounter = Integer.parseInt(burritoCount.getText());

        Pair<Double, String> paymentInfo = BurritoKingApplication.getMenu().order(burritoCounter, friesCounter, sodaCounter, 0);
        Double totalPrice = paymentInfo.getKey();
        String summaryText = paymentInfo.getValue();
        Order order = Restaurant.getAllOrders().getLast();
        Double waitingTime = order.getPrepTime(BurritoKingApplication.getRestaurant());

        int orderId = upsertOrderInDb(true, totalPrice, waitingTime, summaryText);
        upsertFoodItemsInDb(order, orderId);
        proceedToCartScreen(summaryText);
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

    // method to set the counts of the variables
    private void setCounts(boolean isUpdateCart, List<Food> food){
        if (!isUpdateCart){
            friesCount.setText("0");
            burritoCount.setText("0");
            sodaCount.setText("0");
        }else {
            for (Food food1 : food){
                if (food1.getFoodType().equals(FoodType.BURRITO)){
                    burritoCount.setText(String.valueOf(food1.getQuantity()));
                } else if (food1.getFoodType().equals(FoodType.FRIES)) {
                    friesCount.setText(String.valueOf(food1.getQuantity()));
                } else {
                    sodaCount.setText(String.valueOf(food1.getQuantity()));
                }
            }
        }
    }
}
