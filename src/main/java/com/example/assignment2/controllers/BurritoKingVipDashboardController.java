package com.example.assignment2.controllers;

import com.example.assignment2.models.Credits;
import com.example.assignment2.models.OrderStatus;
import com.example.assignment2.models.Orders;
import com.example.assignment2.models.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BurritoKingVipDashboardController extends CommonFunctions{
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label creditsNumberLabel;

    // Table
    @FXML
    private TableView<Orders> orderSummaryTable;

    // Columns
    @FXML
    private TableColumn<Orders, Integer> orderIdColumn;

    @FXML
    private TableColumn<Orders, String> orderSummaryColumn;

    @FXML
    private TableColumn<Orders, Double> totalPriceColumn;

    @FXML
    private TableColumn<Orders, String> orderStatusColumn;

    @FXML
    public void initialize() {
        setInputFieldsValues();

        // gets order which are awaiting collection and sets the table
        ObservableList<Orders> orders = getOrdersByStatusAndUserID(OrderStatus.AWAIT_FOR_COLLECTION.toString());

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("orderId"));
        orderSummaryColumn.setCellValueFactory(new PropertyValueFactory<Orders, String>("summaryText"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<Orders, Double>("totalPrice"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<Orders, String>("orderStatus"));

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
