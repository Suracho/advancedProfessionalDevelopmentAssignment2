package com.example.assignment2.controllers;

import com.example.assignment2.models.OrderStatus;
import com.example.assignment2.models.Orders;
import com.example.assignment2.models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

// This is the Controller class for managing the Burrito King Non-VIP Dashboard. It contains logic for buttons and table for orders
public class BurritoKingNonVipDashboardController extends CommonFunctions{

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

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


    // function to switch to edit profile screens
    @FXML
    protected void switchToEditProfileScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingEditProfile.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // function to switch to Non Vip Order Dashboard screen
    @FXML
    protected void switchToNonVipOrderDashboardScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingOrderNonVipDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
