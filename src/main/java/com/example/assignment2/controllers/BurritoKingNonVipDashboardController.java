package com.example.assignment2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BurritoKingNonVipDashboardController {





    // function to switch to registration screens
    @FXML
    protected void switchToEditProfileScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingEditProfile.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        stage.setOnShown(e -> {
            BurritoKingEditProfile.setInputFieldsValues();
        });
    }
}
