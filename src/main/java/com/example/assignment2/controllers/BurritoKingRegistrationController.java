package com.example.assignment2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

public class BurritoKingRegistrationController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Label errorLabel;

    @FXML
    // function to switch to login screen without mouse event
    public void switchToLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingLogin.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    // function to switch to login screen with mouse event
    protected void switchToLoginScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingLogin.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    // code to insert user into db for registration
    protected void insertUserIntoDB() {

        String usernameIP = username.getText();
        String passwordIP = password.getText();
        String firstNameIP = firstName.getText();
        String lastNameIP = lastName.getText();


        if (usernameIP.isEmpty() || firstNameIP.isEmpty() || lastNameIP.isEmpty() || passwordIP.isEmpty()){
            errorLabel.setText("Please fill in all the details");
            return;
        } else{
            try (Connection connection = BurritoKingApplication.connect()){
                assert connection != null;

                PreparedStatement stmt = connection.prepareStatement("INSERT INTO User(username, firstName, lastName, password, isVipPermissionAsked, isLoggedIn) VALUES(?, ?, ?, ?, ?, ?)");
                // Set the values to be inserted
                stmt.setString(1, usernameIP);
                stmt.setString(2, firstNameIP);
                stmt.setString(3, lastNameIP);
                stmt.setString(4, passwordIP);
                stmt.setInt(5, 0);
                stmt.setInt(6, 0);

                int f = stmt.executeUpdate();
                System.out.println(f);
                System.out.println("Data inserted successfully.");
                connection.close();

                //TO DO ADD TIMER AND GREEN MESSAGE
//                errorLabel.setText("You have successfully registered! Redirecting to login screen.");
//                errorLabel.setTextFill(Color.GREEN);
//
//                Thread.sleep(3000);
                switchToLoginScreen();


            } catch (SQLException e) {
                if (e.getErrorCode() == 19){
                    errorLabel.setText("Username already exists");
                }
                Logger.getAnonymousLogger().log(
                        Level.SEVERE,
                        LocalDateTime.now() + ": " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
