package com.example.assignment2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

// This is the controller class for the registration screen
public class BurritoKingRegistrationController {

    @FXML
    private TextField username; // Username input field

    @FXML
    private TextField password; // Password input field

    @FXML
    private TextField firstName; // First name input field

    @FXML
    private TextField lastName; // Last name input field

    @FXML
    private Label errorLabel; // Label to display error messages

    @FXML
    // Function to switch to the login screen without a mouse event
    public void switchToLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingLogin.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    // Function to switch to the login screen with a mouse event
    protected void switchToLoginScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingLogin.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    // Function to insert a new user into the database for registration
    protected void insertUserIntoDB() {
        // Retrieve user input from text fields
        String usernameIP = username.getText();
        String passwordIP = password.getText();
        String firstNameIP = firstName.getText();
        String lastNameIP = lastName.getText();

        // Check if any input field is empty
        if (usernameIP.isEmpty() || firstNameIP.isEmpty() || lastNameIP.isEmpty() || passwordIP.isEmpty()) {
            errorLabel.setText("Please fill in all the details");
            return;
        } else {
            try (Connection connection = BurritoKingApplication.connect()) {
                assert connection != null;

                // Prepare SQL statement to insert user into the database
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO User(username, firstName, lastName, password, isVipPermissionAsked, isLoggedIn) VALUES(?, ?, ?, ?, ?, ?)");

                // Set the values to be inserted
                stmt.setString(1, usernameIP);
                stmt.setString(2, firstNameIP);
                stmt.setString(3, lastNameIP);
                stmt.setString(4, passwordIP);
                stmt.setInt(5, 0); // Default value for isVipPermissionAsked
                stmt.setInt(6, 0); // Default value for isLoggedIn

                // Execute the insert statement
                int f = stmt.executeUpdate();
                System.out.println(f);
                System.out.println("Data inserted successfully.");
                connection.close();

                // Show confirmation alert on successful registration
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully registered!");
                alert.showAndWait();

                // Switch to login screen after registration
                switchToLoginScreen();

            } catch (SQLException e) {
                // Handle SQL exception for unique constraint violation (e.g., username already exists)
                if (e.getErrorCode() == 19) {
                    errorLabel.setText("Username already exists");
                }
                // Log the exception
                Logger.getAnonymousLogger().log(
                        Level.SEVERE,
                        LocalDateTime.now() + ": " + e.getMessage());
            } catch (IOException e) {
                // Handle IO exception
                throw new RuntimeException(e);
            }
        }
    }
}
