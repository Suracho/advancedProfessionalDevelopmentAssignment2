package com.example.assignment2.models;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ActionButtonTableCell extends TableCell<DaoOrders, Void> {
    private final Button button = new Button();
    private final HBox container = new HBox(200, button);

    public ActionButtonTableCell(Consumer<Integer> action, String actionName) {
        if (actionName.equals("Collect")){
            ImageView collectIcon = new ImageView(new Image("file:src/tick.png"));
            collectIcon.setFitHeight(16);
            collectIcon.setFitWidth(16);
            button.setGraphic(collectIcon);
            button.setStyle("-fx-background-color: transparent;");
            // Add action listeners for the buttons
            button.setOnAction(event -> {
                DaoOrders order = getTableView().getItems().get(getIndex());
                action.accept(order.getOrderId());
            });
        } else {
            ImageView cancelIcon = new ImageView(new Image("file:src/cross.png"));
            cancelIcon.setFitHeight(16);
            cancelIcon.setFitWidth(16);
            button.setGraphic(cancelIcon);
            button.setStyle("-fx-background-color: transparent;");
            button.setOnAction(event -> {
                DaoOrders order = getTableView().getItems().get(getIndex());
                action.accept(order.getOrderId());
            });
        }
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(container);
        }
    }
}
