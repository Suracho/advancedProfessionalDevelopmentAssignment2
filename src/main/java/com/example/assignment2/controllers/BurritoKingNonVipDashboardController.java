package com.example.assignment2.controllers;

import com.example.assignment2.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingEditProfile.fxml"));

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
