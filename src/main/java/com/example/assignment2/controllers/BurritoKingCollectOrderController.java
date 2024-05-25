package com.example.assignment2.controllers;

import com.example.assignment2.models.ActionButtonTableCell;
import com.example.assignment2.models.OrderStatus;
import com.example.assignment2.models.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.function.Consumer;

// This is the controller class for Collect order View
public class BurritoKingCollectOrderController extends CommonFunctions {
    @FXML
    private TableView<Orders> collectOrderTable;

    @FXML
    private TableColumn<Orders, Integer> orderIdColumn;

    @FXML
    private TableColumn<Orders, String> orderSummaryColumn;

    @FXML
    private TableColumn<Orders, Double> totalPriceColumn;

    @FXML
    private TableColumn<Orders, Void> collectOrderColumn;

    @FXML
    private TableColumn<Orders, Void> cancelOrderColumn;

    @FXML
    public void initialize() {
        // Initialize columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderSummaryColumn.setCellValueFactory(new PropertyValueFactory<>("summaryText"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Define actions for collect and cancel buttons
        Consumer<Integer> collectAction = orderId -> {
            // Handle collect order logic here
            updateOrderStatus(OrderStatus.COLLECTED.toString(), orderId);
            refreshTable();
        };

        Consumer<Integer> cancelAction = orderId -> {
            // Handle cancel order logic here
            updateOrderStatus(OrderStatus.CANCELLED.toString(), orderId);
            refreshTable();
        };

        // Add custom cell factory for collect and cancel columns
        collectOrderColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Orders, Void> call(final TableColumn<Orders, Void> param) {
                return new ActionButtonTableCell(collectAction, "Collect");
            }
        });

        cancelOrderColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Orders, Void> call(final TableColumn<Orders, Void> param) {
                return new ActionButtonTableCell(cancelAction, "Cancel");
            }
        });

        // Load data into table
        refreshTable();
    }

    private void refreshTable() {
        ObservableList<Orders> orders = getOrdersByStatusAndUserID(OrderStatus.AWAIT_FOR_COLLECTION.toString());
        collectOrderTable.setItems(orders);
    }
}
