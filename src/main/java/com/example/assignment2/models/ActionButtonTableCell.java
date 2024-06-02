package com.example.assignment2.models;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

// Custom TableCell for displaying action buttons in a TableView
public class ActionButtonTableCell extends TableCell<DaoOrders, Void> {
    // Button for the action
    private final Button button = new Button();
    // Container for the button
    private final HBox container = new HBox(200, button);

    // Constructor for ActionButtonTableCell
    public ActionButtonTableCell(Consumer<Integer> action, String actionName) {
        // Depending on the actionName, set different icons and action handlers for the button
        if (actionName.equals("Collect")){
            // Set collect icon
            ImageView collectIcon = new ImageView(new Image("file:src/tick.png"));
            collectIcon.setFitHeight(16);
            collectIcon.setFitWidth(16);
            button.setGraphic(collectIcon);
            button.setStyle("-fx-background-color: transparent;");
            // Add action listeners for the collect button
            button.setOnAction(event -> {
                DaoOrders order = getTableView().getItems().get(getIndex());
                action.accept(order.getOrderId());
            });
        } else {
            // Set cancel icon
            ImageView cancelIcon = new ImageView(new Image("file:src/cross.png"));
            cancelIcon.setFitHeight(16);
            cancelIcon.setFitWidth(16);
            button.setGraphic(cancelIcon);
            button.setStyle("-fx-background-color: transparent;");
            // Add action listeners for the cancel button
            button.setOnAction(event -> {
                DaoOrders order = getTableView().getItems().get(getIndex());
                action.accept(order.getOrderId());
            });
        }
    }

    // Override updateItem method to update cell contents
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        // Set the graphic of the cell based on whether it is empty or not
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(container);
        }
    }
}
