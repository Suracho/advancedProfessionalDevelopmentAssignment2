package com.example.assignment2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BurritoKingLogin {

    @FXML
    private Label loginMessageLabel;

    // function to switch to registration screens
    @FXML
    protected void switchToRegistrationScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingRegistration.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //method to ensure login button is clicked and display potential error messages
    @FXML
    protected void loginButtonClicked(ActionEvent actionEvent){
        loginMessageLabel.setText("You click login button");
    }
}