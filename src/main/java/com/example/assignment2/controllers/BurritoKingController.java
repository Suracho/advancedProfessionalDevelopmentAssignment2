package com.example.assignment2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BurritoKingController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}