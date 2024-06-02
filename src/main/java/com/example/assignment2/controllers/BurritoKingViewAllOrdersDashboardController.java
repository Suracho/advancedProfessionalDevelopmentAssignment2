package com.example.assignment2.controllers;

import com.example.assignment2.models.DaoOrders;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// this is the controller class for View All Order Page
public class BurritoKingViewAllOrdersDashboardController extends CommonFunctions{

    // Table
    @FXML
    private TableView<DaoOrders> orderSummaryTable;

    // Columns
    @FXML
    private TableColumn<DaoOrders, Integer> orderIdColumn;

    @FXML
    private TableColumn<DaoOrders, String> timeColumn;

    @FXML
    private TableColumn<DaoOrders, String> dayColumn;


    @FXML
    private TableColumn<DaoOrders, Double> totalPriceColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderStatusColumn;

    @FXML
    public void initialize() {

        // gets order which are awaiting collection and sets the table
        ObservableList<DaoOrders> orders = getOrdersByStatusAndUserID("");

        orders.sort(DaoOrders::compareByDayTime);


        orderIdColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, Integer>("orderId"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("timeOrdered"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("dayOrdered"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, Double>("totalPrice"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("orderStatus"));

        orderSummaryTable.setItems(orders);
    }
}
