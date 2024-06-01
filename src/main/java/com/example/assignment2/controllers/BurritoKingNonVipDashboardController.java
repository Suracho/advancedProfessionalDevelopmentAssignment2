package com.example.assignment2.controllers;

import com.example.assignment2.models.DaoOrders;
import com.example.assignment2.models.OrderStatus;
import com.example.assignment2.models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

// This is the Controller class for managing the Burrito King Non-VIP Dashboard. It contains logic for buttons and table for orders
public class BurritoKingNonVipDashboardController extends CommonFunctions{

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

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




    // function which upgrades the user to vip
    @FXML
    protected void upgradeToVip(ActionEvent actionEvent) throws IOException {
        User user = getIsLoggedInUser();
        showIsVipAlert(user.getUserId(), false);

        showConfirmationAlert("Logging out", "You are about to logout, please login again to enjoy Vip Features.");
        // logging out
        setIsLoggedIn();
        changeScreen("/com.example.assignment2.views/BurritoKingLogin.fxml");
    }

    // function to set the fields required to show on the dashboard
    private void setInputFieldsValues(){
        User user = getIsLoggedInUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
    }




}
