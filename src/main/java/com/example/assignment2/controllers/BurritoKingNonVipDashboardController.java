package com.example.assignment2.controllers;

import com.example.assignment2.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

// This is the Controller class for managing the Burrito King Non-VIP Dashboard. It contains logic for buttons and table for orders
public class BurritoKingNonVipDashboardController extends CommonFunctions{

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    public void initialize() {
        setInputFieldsValues();
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

    // function to set the fields required to show on the dashboard
    private void setInputFieldsValues(){
        User user = getIsLoggedInUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
    }


}
