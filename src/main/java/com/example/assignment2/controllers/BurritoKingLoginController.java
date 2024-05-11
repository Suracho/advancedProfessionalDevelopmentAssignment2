package com.example.assignment2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BurritoKingLoginController extends CommonFunctions{

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    // function to switch to registration screens without mouse event
    @FXML
    protected void switchToDashboard(boolean isVip) throws IOException {
        FXMLLoader fxmlLoader;
        if (isVip){
            fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingVipDashboard.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingNonVipDashboard.fxml"));
        }

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginMessageLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

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
    protected void login(ActionEvent actionEvent){
        String usernameIP = username.getText();
        String passwordIP = password.getText();

        if (usernameIP.isEmpty() || passwordIP.isEmpty()){
            loginMessageLabel.setText("Please fill in all the details");
            return;
        } else {
            try (Connection connection = BurritoKingApplication.connect()) {
                String query = "SELECT * FROM User WHERE username=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, usernameIP);
                ResultSet rs = statement.executeQuery();


                if (!rs.next()){
                    loginMessageLabel.setText("No such User exists");
                } else {
                        int userId = rs.getInt("userId");
                        String username = rs.getString("username");
                        String firstName = rs.getString("firstName");
                        String lastName = rs.getString("lastName");
                        String password = rs.getString("password");
                        String isVipPermissionAsked = rs.getString("isVipPermissionAsked");
                        connection.close();

                        boolean isVip = checkIsVip(userId);
                        boolean isVipSelected = isVip;

                        if (password.equals(passwordIP)){
                            if (isVipPermissionAsked.equals("0")){
                                isVipSelected = showIsVipAlert(userId);
                            }
                            setIsLoggedIn(userId, "1");
                            setIsLoggedIn(userId, "0");
                            setIsVipPermissionAsked(userId);
                            switchToDashboard(isVipSelected);
                        }else {
                            loginMessageLabel.setText("Wrong password, please try again.");
                        }

                }
            } catch (Exception e) {
                Logger.getAnonymousLogger().log(
                        Level.SEVERE,
                        LocalDateTime.now() + ": " + e.getMessage());
            }
        }
    }

    // function which shows the alert box wherein we ask if the user is vip or not.
    private boolean showIsVipAlert(int userId) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("VIP Confirmation");
        alert.setHeaderText("Do you want to be a VIP user?");
        alert.setContentText("A VIP user gets extra benefits like a special 3$ discounts on a Meal. You also get special credits for every purchase you make! Please provide your email address if you want to become a VIP user.\n By providing your email address you agree to receive promotion information via email. Click Cancel if you will like to opt out.");

        // Add a VBox to hold confirmation message and email field
        VBox confirmationPane = new VBox();
        confirmationPane.getChildren().add(new Label(alert.getContentText()));

        Label emailLabel = new Label("Enter your email address:");
        TextField emailField = new TextField();
        confirmationPane.getChildren().addAll(emailLabel, emailField);

        alert.getDialogPane().setContent(confirmationPane);

        while (true){
            ButtonType bt = alert.showAndWait().get();
            if (bt == ButtonType.OK && !emailField.getText().isEmpty()){
                System.out.println("YOU ARE VIP");
                String emailAddress = emailField.getText();
                updateVipStatusInUserAndInsertAVipUser(userId, emailAddress);
                return true;
            } else if (bt == ButtonType.CANCEL) {
                return false;
            }
        }
    }

    // function to update the vip status of the user
    private void updateVipStatusInUserAndInsertAVipUser(int userId, String emailAddress) {
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "UPDATE User SET isVipPermissionAsked = '1' WHERE userId = ?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            int isUpdated = statement.executeUpdate();

            if (isUpdated == 1){
                String insertQuery = "INSERT INTO VipUsers(userId, emailAddress) VALUES(?,?)";
                PreparedStatement statement1 = connection.prepareStatement(insertQuery);
                statement1.setInt(1, userId);
                statement1.setString(2, emailAddress);
                boolean isInserted = statement1.execute();
                if (isInserted){
                    System.out.println("VIP user inserted successfully");
                } else {
                    throw new SQLException("Error inserting VIP user in database");
                }
            } else {
                throw new SQLException("Error updating VIP status in User Table");
            }
            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
    }



    // sets isLoggedIn to true for logged in user and false for everyone else
    private void setIsLoggedIn(int userId, String zeroOrOne){
        String query = "";
        try (Connection connection = BurritoKingApplication.connect()) {
            if (zeroOrOne.equals("1")){
                query = "UPDATE User SET isLoggedIn = ? WHERE userId = ?";
            } else {
                query = "UPDATE User SET isLoggedIn = ? WHERE userId != ?";
            }
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, zeroOrOne);
            statement.setInt(2, userId);
            int isUpdated = statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
    }

    // sets isVipPermissionAsked to true
    private void setIsVipPermissionAsked(int userId){
        try (Connection connection = BurritoKingApplication.connect()) {

            String query = "UPDATE User SET isVipPermissionAsked = 1 WHERE userId = ?";

            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            int isUpdated = statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
    }

}