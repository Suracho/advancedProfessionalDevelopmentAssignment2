package com.example.assignment2.controllers;

import com.example.assignment2.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// This is an abstract class which is extended by every screen, to access some common functions. This supports code reusability.
abstract class CommonFunctions {



    // Function to switch screen proceedToAddOrder according to vip status
    @FXML
    protected void proceedToAddOrder(ActionEvent actionEvent) throws Exception {
        int userId = getIsLoggedInUserId();
        boolean isVip = checkIsVip(userId);
        if (isVip){
            changeScreenWithEvent("/com.example.assignment2.views/BurritoKingOrderVipDashboard.fxml", actionEvent);
        } else {
            changeScreenWithEvent("/com.example.assignment2.views/BurritoKingOrderNonVipDashboard.fxml", actionEvent);
        }
    }

    // Function to switch screen payment screen
    @FXML
    protected void proceedToPaymentScreen() throws IOException {
        changeScreen("/com.example.assignment2.views/BurritoKingPaymentScreen.fxml");
    }

    // Method to change screens and pass data to the controller
    @FXML
    private void changeScreenWithController(String fxmlFile, String data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
        stage.setScene(scene);
        stage.show();

        // Pass data to the controller
        Object controller = fxmlLoader.getController();
        if (controller instanceof BurritoKingCartController) {
            ((BurritoKingCartController) controller).initData(data);
        }
    }



    // Function to switch screen proceedToHome
    @FXML
    protected void proceedToCartScreen(String summaryText) throws IOException {
        changeScreenWithController("/com.example.assignment2.views/BurritoKingCart.fxml", summaryText);
    }



    // Function to switch screen proceedToHome
    @FXML
    protected void proceedToHome() throws Exception {
        int userId = getIsLoggedInUserId();
        boolean isVip = checkIsVip(userId);

        if (isVip) {
            changeScreen("/com.example.assignment2.views/BurritoKingVipDashboard.fxml");
        } else {
            changeScreen("/com.example.assignment2.views/BurritoKingNonVipDashboard.fxml");
        }
    }


    // function to logout
    @FXML
    protected void logout(ActionEvent actionEvent) throws IOException {
        setIsLoggedIn();

        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingLogin.fxml"));
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

    // returns food item object
    protected ObservableList<Food> getFoodItems() {
        ObservableList<Food> foodItems = FXCollections.observableArrayList();
        User user = getIsLoggedInUser();
        Orders orders = getOrderWithPendingPayment(user.getUserId());

        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM Food WHERE orderId=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orders.getOrderId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int orderId = rs.getInt("orderId");
                String foodTypeStr = rs.getString("foodType");
                FoodType foodType = FoodType.valueOf(foodTypeStr.toUpperCase());
                Integer quantity = rs.getInt("quantity");
                Double unitPrice = rs.getDouble("unitPrice");

                Food food = new Food(orderId, unitPrice, foodType, quantity);
                foodItems.add(food);
            }

            connection.close();
            return foodItems;

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

    // adds order to the table and returns the autogenerated orderId or else it updates the existing order in database
    protected int upsertOrderInDb(boolean pendingPayment, Double totalPrice, Double waitingTime) {
        User loggedInUser = getIsLoggedInUser();
        int orderId = 0;

        String query;
        PreparedStatement statement;
        Orders orders = getOrderWithPendingPayment(loggedInUser.getUserId());


        try (Connection connection = BurritoKingApplication.connect()) {
            if (orders == null){
                query = "INSERT INTO Orders(userId, pendingPayment, totalPrice, waitingTime) VALUES(?, ?, ?, ?)";
                statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, loggedInUser.getUserId());
                statement.setInt(2, pendingPayment ? 1 : 0);
                statement.setDouble(3, totalPrice);
                statement.setDouble(4, waitingTime);
            } else {
                query = "UPDATE Orders SET userId = ?, pendingPayment=?, totalPrice = ?, waitingTime = ?  WHERE orderId = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, loggedInUser.getUserId());
                statement.setInt(2, pendingPayment ? 1 : 0);
                statement.setDouble(3, totalPrice);
                statement.setDouble(4, waitingTime);
                statement.setInt(5, orders.getOrderId());
            }
            assert connection != null;


            // Execute the update
            int isUpdated = statement.executeUpdate();

            // Retrieve the generated keys
            if (isUpdated > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }

        return orderId;
    }


    // adds food into the table by first for looping the linkedlist for each individual items and then getting their individual price and quantity
    // if order already exists then updates it
    protected void upsertFoodItemsInDb(Order order, Integer orderId){
        String query;
        User loggedInUser = getIsLoggedInUser();

        LinkedList<FoodItem> individualFoodItems = order.getItems();

        Orders orders = getOrderWithPendingPayment(loggedInUser.getUserId());
        boolean isUpdateFood = isFoodPresent(orders);
        PreparedStatement statement;

        for (FoodItem foodItem: individualFoodItems){
            try (Connection connection = BurritoKingApplication.connect()) {
                assert connection != null;
                if (orders == null) {
                    query = "INSERT INTO Food(unitPrice, foodType, quantity, orderId) VALUES(?, ?, ?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1,  foodItem.getTotalPrice());
                    statement.setString(2,  covertClassNameToFoodType(foodItem.getClass().getName()).toString());
                    statement.setInt(3,  foodItem.getQuantity());
                    statement.setInt(4,  orderId);
                } else if (isUpdateFood){
                    query = "UPDATE Food SET unitPrice = ?, quantity = ? WHERE orderId = ? AND foodType = ?";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1,  foodItem.getTotalPrice());
                    statement.setInt(2,  foodItem.getQuantity());
                    statement.setInt(3,  orders.getOrderId());
                    statement.setString(4,  covertClassNameToFoodType(foodItem.getClass().getName()).toString());
                } else {
                    query = "INSERT INTO Food(unitPrice, foodType, quantity, orderId) VALUES(?, ?, ?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1,  foodItem.getTotalPrice());
                    statement.setString(2,  covertClassNameToFoodType(foodItem.getClass().getName()).toString());
                    statement.setInt(3,  foodItem.getQuantity());
                    statement.setInt(4,  orderId);
                }


                int isUpdated = statement.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                Logger.getAnonymousLogger().log(
                        Level.SEVERE,
                        LocalDateTime.now() + ": " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // gets the pending payment order for the user
    protected Orders getOrderWithPendingPayment(int userId){
        Orders order = null;
        String query = "SELECT * FROM Orders WHERE userId = ? AND pendingPayment = 1";

        try (Connection connection = BurritoKingApplication.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                order = new Orders(
                        rs.getDouble("totalPrice"),
                        rs.getDouble("waitingTime"),
                        rs.getInt("pendingPayment") == 1,
                        rs.getInt("orderId"),
                        rs.getInt("userId")
                );
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }

        return order;
    }

    protected List<Food> getFoodListForOrders(Orders order) {
        List<Food> foodList = new ArrayList<Food>();

        String query = "SELECT * FROM Food WHERE orderId = ?";

        try (Connection connection = BurritoKingApplication.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getOrderId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Food food = new Food(
                        rs.getInt("orderId"),
                        rs.getDouble("unitPrice"),
                        FoodType.valueOf(rs.getString("foodType")),
                        rs.getInt("quantity")
                );
                foodList.add(food);
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }

        return foodList;
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


    // Method to change screens
    @FXML
    private void changeScreen(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
        stage.setScene(scene);
        stage.show();
    }

    // Method to change screens
    @FXML
    private void changeScreenWithEvent(String fxmlFile, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // enhanced switch case for converting classname string to FoodType
    private FoodType covertClassNameToFoodType(String className) throws Exception {
        return switch (className) {
            case "com.example.assignment2.models.Burrito" -> FoodType.BURRITO;
            case "com.example.assignment2.models.Fries" -> FoodType.FRIES;
            case "com.example.assignment2.models.Soda" -> FoodType.SODA;
            case "com.example.assignment2.models.Meal" -> FoodType.MEAL;
            default -> throw new Exception("Class name could not be converted into FoodType");
        };
    }

    // function to check whether food data exists in the table for updating it or inserting it
    private boolean isFoodPresent(Orders orders){
        boolean isFoodPresent = true;
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM Food WHERE orderId=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orders.getOrderId());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()){
                isFoodPresent = false;
            }
            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
        return isFoodPresent;
    }

}



