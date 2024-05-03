package com.example.assignment2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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

    // function to switch to login screen without mouse event
    public void switchToLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKing-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // function to switch to login screen with mouse event
    public void switchToLoginScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKing-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // code to insert user into db for registration
    public void insertUserIntoDB() {

        String usernameIP = username.getText();
        String passwordIP = password.getText();
        String firstNameIP = firstName.getText();
        String lastNameIP = lastName.getText();


        if (usernameIP.equals("") || firstNameIP.equals("") || lastNameIP.equals("") || passwordIP.equals("")){
            errorLabel.setText("Please fill in all the details");
            return;
        } else{
            try (Connection connection = BurritoKingApplication.connect()){
                assert connection != null;

                PreparedStatement stmt = connection.prepareStatement("INSERT INTO User(username, firstName, lastName, password) VALUES(?, ?, ?, ?)");
                // Set the values to be inserted
                stmt.setString(1, usernameIP);
                stmt.setString(2, firstNameIP);
                stmt.setString(3, lastNameIP);
                stmt.setString(4, passwordIP);

//            String query = "SELECT * FROM User";
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet rs = statement.executeQuery();
//            System.out.println(rs);

//            while(rs.next()){
//                int userId = rs.getInt("userId");
//                String username = rs.getString("username");
//                String firstName = rs.getString("firstName");
//                String lastName = rs.getString("lastName");
//                String password = rs.getString("password");
//
//                System.out.println("User ID: " + userId);
//                System.out.println("Username: " + username);
//                System.out.println("First Name: " + firstName);
//                System.out.println("Last Name: " + lastName);
//                System.out.println("Password: " + password);
//                System.out.println("--------------------");
//            }


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
                System.out.println(e.getMessage());
                Logger.getAnonymousLogger().log(
                        Level.SEVERE,
                        LocalDateTime.now() + ": Could not insert users into database ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
    public void createUser(ActionEvent actionEvent){

    }
}
