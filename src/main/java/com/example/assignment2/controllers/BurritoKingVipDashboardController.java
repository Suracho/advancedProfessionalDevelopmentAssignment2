package com.example.assignment2.controllers;

import com.example.assignment2.models.Credits;
import com.example.assignment2.models.DaoOrders;
import com.example.assignment2.models.OrderStatus;
import com.example.assignment2.models.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// this is the controller class for Burrito King VIP Dashboard Page which shows the orders that are awaiting collection for VIP users only
public class BurritoKingVipDashboardController extends CommonFunctions{
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label creditsNumberLabel;

    // Table
    @FXML
    private TableView<DaoOrders> orderSummaryTable;

    // Columns
    @FXML
    private TableColumn<DaoOrders, Integer> orderIdColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderSummaryColumn;

    @FXML
    private TableColumn<DaoOrders, Double> totalPriceColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderStatusColumn;

    // Initialize method
    @FXML
    public void initialize() {
        setInputFieldsValues();

        // gets order which are awaiting collection and sets the table
        ObservableList<DaoOrders> orders = getOrdersByStatusAndUserID(OrderStatus.AWAIT_FOR_COLLECTION.toString());
        orders.sort(DaoOrders::compareByDayTime);


        orderIdColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, Integer>("orderId"));
        orderSummaryColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("summaryText"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, Double>("totalPrice"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("orderStatus"));

        orderSummaryTable.setItems(orders);
    }


    // function to set the fields required to show on the dashboard
    private void setInputFieldsValues(){
        User user = getIsLoggedInUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        // gets credits and then sets it
        Credits credits = getCreditsForUser(user.getUserId());
        creditsNumberLabel.setText("Credits: " + credits.getCredits().toString());
    }
}
