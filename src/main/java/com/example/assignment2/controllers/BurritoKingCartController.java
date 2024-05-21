package com.example.assignment2.controllers;

import com.example.assignment2.models.Food;
import com.example.assignment2.models.FoodType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;

// This is the controller Class for cart View
public class BurritoKingCartController extends CommonFunctions{
    private String st;
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
    public void initData(String summaryText) {
        st = summaryText;
        paymentSummaryLabel.setText(st);
    }
}
