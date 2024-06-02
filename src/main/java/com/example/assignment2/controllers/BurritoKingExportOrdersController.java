package com.example.assignment2.controllers;

import com.example.assignment2.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// This is the controller class for the Export Orders view in the Burrito King application
public class BurritoKingExportOrdersController extends CommonFunctions {

    // FXML references to the ListViews and TableView
    @FXML
    private ListView<OrderColumnCheckBox> orderColumnsListView;

    @FXML
    private TableView<DaoOrders> orderSummaryTable;

    // Table columns
    @FXML
    private TableColumn<DaoOrders, Integer> orderIdColumn;

    @FXML
    private TableColumn<DaoOrders, String> dayColumn;

    @FXML
    private TableColumn<DaoOrders, Double> totalPriceColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderStatusColumn;

    @FXML
    private TableColumn<DaoOrders, Void> selectCheckboxesColumn; // Column for checkboxes

    @FXML
    private Label errorLabel;

    private final ObservableList<Integer> selectedOrderIds = FXCollections.observableArrayList(); // List to keep track of selected order IDs

    private ObservableList<DaoOrders> orders;



    // Method to initialize the controller
    @FXML
    public void initialize() {
        // Get orders and set up the table
        this.orders = getOrdersByStatusAndUserID("");

        this.orders.sort((o1, o2) -> o2.getOrderId() - o1.getOrderId());

        // Set up the table columns with appropriate properties
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayOrdered"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        // Set the table items
        orderSummaryTable.setItems(orders);

        // Initialize the ListView for order columns
        setOrderColumnsListView();

        // Add checkboxes to the table
        addCheckboxesToTable();
    }

    // Method to set up the order columns ListView with checkboxes
    private void setOrderColumnsListView() {
        // Create an ObservableList with order column checkboxes
        ObservableList<OrderColumnCheckBox> orderColumns = FXCollections.observableArrayList(
                new OrderColumnCheckBox("Order Id"),
                new OrderColumnCheckBox("Order Time"),
                new OrderColumnCheckBox("Order Day"),
                new OrderColumnCheckBox("Total Price"),
                new OrderColumnCheckBox("Order Status"),
                new OrderColumnCheckBox("Collection Time"),
                new OrderColumnCheckBox("Summary Text")
        );

        // Set items and cell factory for the ListView
        orderColumnsListView.setItems(orderColumns);
        orderColumnsListView.setCellFactory(param -> new ListCell<OrderColumnCheckBox>() {
            @Override
            protected void updateItem(OrderColumnCheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getColumnName());
                    setGraphic(item.getCheckBox());
                }
            }
        });
    }

    // Method to add checkboxes to the table column
    private void addCheckboxesToTable() {
        selectCheckboxesColumn.setCellFactory(column -> new TableCell<DaoOrders, Void>() {
            private final CheckBox checkBox = new CheckBox(); // Create a new CheckBox

            {
                // Event listener for the CheckBox
                checkBox.setOnAction(event -> {
                    DaoOrders order = getTableView().getItems().get(getIndex()); // Get the current order
                    if (checkBox.isSelected()) {
                        selectedOrderIds.add(order.getOrderId()); // Add order ID to selected list if checked
                    } else {
                        selectedOrderIds.remove(order.getOrderId()); // Remove order ID from selected list if unchecked
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null); // No checkbox if cell is empty
                } else {
                    setGraphic(checkBox); // Display the checkbox
                    checkBox.setSelected(selectedOrderIds.contains(getTableView().getItems().get(getIndex()).getOrderId()));
                }
            }
        });
    }

    // Method to handle the export orders button click
    @FXML
    private void exportOrders(ActionEvent event) {
        // Fetch selected order columns from ListView
        ObservableList<OrderColumnCheckBox> selectedOrderColumns = FXCollections.observableArrayList();
        for (OrderColumnCheckBox columnCheckBox : orderColumnsListView.getItems()) {
            if (columnCheckBox.getCheckBox().isSelected()) {
                selectedOrderColumns.add(columnCheckBox);
            }
        }

        // Process the selected items
        processSelectedItems(selectedOrderIds, selectedOrderColumns);
    }

    // Method to process the selected order IDs and columns
    private void processSelectedItems(ObservableList<Integer> selectedOrderIds, ObservableList<OrderColumnCheckBox> selectedOrderColumns) {
        // Filter orders based on selected IDs
        List<DaoOrders> filteredOrders = orders.stream()
                .filter(order -> selectedOrderIds.contains(order.getOrderId()))
                .collect(Collectors.toList());

        // sets the error labels if no orders are selected
        if (filteredOrders.isEmpty()) {
            errorLabel.setText("No orders selected! Please select orders to export.");
            return;
        }

        // sets the error label if no columns are selected
        if (selectedOrderColumns.isEmpty()) {
            errorLabel.setText("No columns selected! Please select columns to export.");
            return;
        }

        // Export filtered orders to CSV
        exportOrdersToCSV(filteredOrders, selectedOrderColumns);
    }

    // Method to export orders to CSV
    private void exportOrdersToCSV(List<DaoOrders> filteredOrders, ObservableList<OrderColumnCheckBox> selectedOrderColumns) {
        // File chooser for getting path
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        // Set initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Set file extension filter (optional)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write column headers
                for (OrderColumnCheckBox columnCheckBox : selectedOrderColumns) {
                    writer.append(columnCheckBox.getColumnName()).append(",");
                }
                writer.append("\n");

                // Write order data
                for (DaoOrders order : filteredOrders) {
                    for (OrderColumnCheckBox columnCheckBox : selectedOrderColumns) {
                        switch (columnCheckBox.getColumnName()) {
                            case "Order Id":
                                writer.append(order.getOrderId().toString()).append(",");
                                break;
                            case "Order Time":
                                writer.append(order.getTimeOrdered()).append(",");
                                break;
                            case "Order Day":
                                writer.append(order.getDayOrdered()).append(",");
                                break;
                            case "Total Price":
                                writer.append(order.getTotalPrice().toString()).append(",");
                                break;
                            case "Order Status":
                                writer.append(order.getOrderStatus()).append(",");
                                break;
                            case "Collection Time":
                                writer.append(order.getCollectionTime()).append(",");
                                break;
                            case "Summary Text":
                                writer.append(order.getSummaryText()).append(",");
                                break;
                        }
                    }
                    writer.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
