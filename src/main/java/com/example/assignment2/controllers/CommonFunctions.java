package com.example.assignment2.controllers;

import com.example.assignment2.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class CommonFunctions {

    // function to proceed To Home with mouse event
    @FXML
    protected void proceedToHome(MouseEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader;
        int userId = getIsLoggedInUserId();
        boolean isVip = checkIsVip(userId);

        if (isVip){
            fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingVipDashboard.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingNonVipDashboard.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    // function to logout
    @FXML
    protected void logout(ActionEvent actionEvent) throws IOException {
        setIsLoggedIn();

        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKingLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    // checks if the user is vip or not
    protected boolean checkIsVip(int userId) throws Exception {
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM VipUsers WHERE userId=?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            boolean isVip = rs.next();
            connection.close();
            return isVip;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
        throw new Exception("Couldn't establish connection");
    }

    // checks if the user is vip or not
    protected boolean getUser(int userId) throws Exception {
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM VipUsers WHERE userId=?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            boolean isVip = rs.next();
            connection.close();
            return isVip;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
        throw new Exception("Couldn't establish connection");
    }

    // returns isLoggedIn user object
    protected User getIsLoggedInUser() {
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM User WHERE isLoggedIn=1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            int userId = rs.getInt("userId");
            String username = rs.getString("username");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String password = rs.getString("password");
            String isVipPermissionAsked = rs.getString("isVipPermissionAsked");
            String isLoggedIn = rs.getString("isLoggedIn");
            User user = new User(username, userId, firstName, lastName, password, isLoggedIn, isVipPermissionAsked);
            connection.close();
            return user;

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
        return null;
    }

    // updates user object
    protected void updateUser(User user){
        User loggedInUser = getIsLoggedInUser();
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "UPDATE User SET username = ?, password = ?, firstName = ?, lastName = ? WHERE userId = ?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,  user.getUsername());
            statement.setString(2,  user.getPassword());
            statement.setString(3,  user.getFirstName());
            statement.setString(4,  user.getLastName());
            statement.setInt(5,  loggedInUser.getUserId());
            int isUpdated = statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
    }

    // sets isLoggedIn to false for all user
    private void setIsLoggedIn() {
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "UPDATE User SET isLoggedIn = ?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "0");
            int isUpdated = statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
    }

    // sets isLoggedIn to false for all user
    private int getIsLoggedInUserId() {
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM User WHERE isLoggedIn=1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            int userId = rs.getInt("userId");
            connection.close();
            return userId;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
        return 0;
    }





}



