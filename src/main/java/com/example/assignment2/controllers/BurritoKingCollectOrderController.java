package com.example.assignment2.controllers;

import com.example.assignment2.models.ActionButtonTableCell;
import com.example.assignment2.models.DaoOrders;
import com.example.assignment2.models.OrderStatus;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

// This is the controller class for Collect order View
public class BurritoKingCollectOrderController extends CommonFunctions {
    @FXML
    private TableView<DaoOrders> collectOrderTable;

    @FXML
    private TableColumn<DaoOrders, Integer> orderIdColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderSummaryColumn;

    @FXML
    private TableColumn<DaoOrders, Double> totalPriceColumn;

    @FXML
    private TableColumn<DaoOrders, Void> collectOrderColumn;

    @FXML
    private TableColumn<DaoOrders, Void> cancelOrderColumn;

    @FXML
    public void initialize() {
        // Initialize columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderSummaryColumn.setCellValueFactory(new PropertyValueFactory<>("summaryText"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Define actions for collect and cancel buttons
        Consumer<Integer> collectAction = orderId -> {
            // Handle collect order logic here
            String displayedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            showConfirmationAlertDuringCollection(displayedTime, orderId);
            updateOrderStatus(OrderStatus.COLLECTED.toString(), orderId);
            refreshTable();
        };

        Consumer<Integer> cancelAction = orderId -> {
            // Handle cancel order logic here
            updateOrderStatus(OrderStatus.CANCELLED.toString(), orderId);
            try {
                if (checkIsVip(getIsLoggedInUser().getUserId())){
                    refundCreditsAfterCancellation(orderId);
                    reduceOrderCreditsAfterCancellation(orderId);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            refreshTable();
        };

        // Add custom cell factory for collect and cancel columns
        collectOrderColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<DaoOrders, Void> call(final TableColumn<DaoOrders, Void> param) {
                return new ActionButtonTableCell(collectAction, "Collect");
            }
        });

        cancelOrderColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<DaoOrders, Void> call(final TableColumn<DaoOrders, Void> param) {
                return new ActionButtonTableCell(cancelAction, "Cancel");
            }
        });

        // Load data into table
        refreshTable();
    }

    private void refreshTable() {
        ObservableList<DaoOrders> orders = getOrdersByStatusAndUserID(OrderStatus.AWAIT_FOR_COLLECTION.toString());

        orders.sort(DaoOrders::compareByDayTime);
        collectOrderTable.setItems(orders);
    }
}
