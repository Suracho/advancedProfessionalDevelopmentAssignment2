package com.example.assignment2.controllers;

import com.example.assignment2.models.Orders;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// this is the controller class for View All Order Page
public class BurritoKingViewAllOrdersDashboardController extends CommonFunctions{

    // Table
    @FXML
    private TableView<Orders> orderSummaryTable;

    // Columns
    @FXML
    private TableColumn<Orders, Integer> orderIdColumn;

    @FXML
    private TableColumn<Orders, String> timeColumn;

    @FXML
    private TableColumn<Orders, String> dayColumn;


    @FXML
    private TableColumn<Orders, Double> totalPriceColumn;

    @FXML
    private TableColumn<Orders, String> orderStatusColumn;

    @FXML
    public void initialize() {

        // gets order which are awaiting collection and sets the table
        ObservableList<Orders> orders = getOrdersByStatusAndUserID("");

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("orderId"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Orders, String>("timeOrdered"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<Orders, String>("dayOrdered"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<Orders, Double>("totalPrice"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<Orders, String>("orderStatus"));

        orderSummaryTable.setItems(orders);
    }
}
