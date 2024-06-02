package com.example.assignment2.controllers;

import com.example.assignment2.models.Food;
import com.example.assignment2.models.FoodType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// This is the controller class for the cart view
public class BurritoKingCartController extends CommonFunctions {

    // Summary text string
    private String st;

    // Final payment amount
    private Double paymentAmount;

    // Waiting time
    private Double waitingTime;

    // Table view to display food items
    @FXML
    private TableView<Food> foodSummaryTable;

    // Table columns
    @FXML
    private TableColumn<Food, Integer> orderIdColumn;
    @FXML
    private TableColumn<Food, FoodType> foodTypeColumn;
    @FXML
    private TableColumn<Food, Double> priceColumn;
    @FXML
    private TableColumn<Food, Integer> quantityColumn;

    // Labels for payment and waiting time summary
    @FXML
    private Label paymentSummaryLabel;
    @FXML
    private Label waitingTimeSummaryLabel;

    // Initialize method to fetch the food items which the customer ordered in the previous page
    @FXML
    public void initialize() {
        ObservableList<Food> foodItems = getFoodItems();
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        foodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("foodType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        foodSummaryTable.setItems(foodItems);
        paymentSummaryLabel.setText(st);
    }

    // Method to receive data from the previous scene
    public void initData(String summaryText, Double paymentAmount, Double waitingTime) {
        this.st = summaryText;
        paymentSummaryLabel.setText(st);
        this.paymentAmount = paymentAmount;
        this.waitingTime = waitingTime;
        waitingTimeSummaryLabel.setText("Estimated waiting time: " + convertToHoursAndMinutes(this.waitingTime));
    }

    // Function to switch to the payment screen
    @FXML
    protected void proceedToPaymentScreen() throws Exception {
        changeScreen((int) Math.floor(this.paymentAmount), this.paymentAmount);
    }

    // Function to convert double value to hours and minutes
    private String convertToHoursAndMinutes(Double time) {
        int hours = (int) Math.floor(time / 60);
        int minutes = (int) Math.floor(time % 60);
        return hours + " hours " + minutes + " minutes";
    }

    // Function to convert double value to hours and minutes for testing
    String convertToHoursAndMinutesForTest(Double time) {
        int hours = (int) Math.floor(time / 60);
        int minutes = (int) Math.floor(time % 60);
        return hours + " hours " + minutes + " minutes";
    }
}
