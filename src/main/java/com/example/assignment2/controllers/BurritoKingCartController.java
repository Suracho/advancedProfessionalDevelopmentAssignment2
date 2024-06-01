package com.example.assignment2.controllers;

import com.example.assignment2.models.Food;
import com.example.assignment2.models.FoodType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.LinkedList;

// This is the controller Class for cart View
public class BurritoKingCartController extends CommonFunctions{
    // summary text string
    private String st;
    // final payment amount variable
    private Double paymentAmount;
    private Double waitingTime;
    // Table
    @FXML
    private TableView<Food> foodSummaryTable;

    // Columns
    @FXML
    private TableColumn<Food, Integer> orderIdColumn;

    @FXML
    private TableColumn<Food, FoodType> foodTypeColumn;

    @FXML
    private TableColumn<Food, Double> priceColumn;

    @FXML
    private TableColumn<Food, Integer> quantityColumn;


    @FXML
    private Label paymentSummaryLabel;

    @FXML
    private Label waitingTimeSummaryLabel;


    // initialize method to fetch the food items which the customer had ordered in the previous page.
    @FXML
    public void initialize() {
        //
        ObservableList<Food> foodItems = getFoodItems();
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("orderId"));
        foodTypeColumn.setCellValueFactory(new PropertyValueFactory<Food, FoodType>("foodType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Food, Double>("unitPrice"));

        foodSummaryTable.setItems(foodItems);
        paymentSummaryLabel.setText(st);
    }

    // Method to receive data from previous scene
    public void initData(String summaryText, Double paymentAmount, Double waitingTime) {
        st = summaryText;
        paymentSummaryLabel.setText(st);
        this.paymentAmount = paymentAmount;

        this.waitingTime = waitingTime;
        waitingTimeSummaryLabel.setText("Estimated waiting time: " + convertToHoursAndMinutes(this.waitingTime));
    }



    // Function to switch screen payment screen
    @FXML
    protected void proceedToPaymentScreen() throws Exception {
        changeScreen((int) Math.floor(this.paymentAmount), this.paymentAmount);
    }

    //    function to convert double value to hours and minutes
    private String convertToHoursAndMinutes(Double time) {
        int hours = (int) Math.floor(time / 60);
        int minutes = (int) Math.floor(time % 60);
        return hours + " hours " + minutes + " minutes";
    }

    //    function to convert double value to hours and minutes
    String convertToHoursAndMinutesForTest(Double time) {
        int hours = (int) Math.floor(time / 60);
        int minutes = (int) Math.floor(time % 60);
        return hours + " hours " + minutes + " minutes";
    }
}
