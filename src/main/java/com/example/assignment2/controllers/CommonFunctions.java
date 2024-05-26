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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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


    // function to switch to edit profile screens
    @FXML
    protected void proceedToEditProfileScreen(ActionEvent actionEvent) throws IOException {
        changeScreen("/com.example.assignment2.views/BurritoKingEditProfile.fxml");
    }

    // function to switch to Non Vip Order Dashboard screen
    @FXML
    protected void switchToNonVipOrderDashboardScreen(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingOrderNonVipDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    // Function to switch screen proceedToHome
    @FXML
    protected void proceedToCartScreen(String summaryText) throws IOException {
        changeScreenWithController("/com.example.assignment2.views/BurritoKingCart.fxml", summaryText);
    }

    // Function to switch screen proceedToViewAllOrders
    @FXML
    protected void proceedToViewAllOrdersScreen() throws IOException {
        changeScreen("/com.example.assignment2.views/BurritoKingViewAllOrdersDashboard.fxml");
    }

    // Function to switch screen proceedToViewAllOrders
    @FXML
    protected void proceedToCollectOrderScreen() throws IOException {
        changeScreen("/com.example.assignment2.views/BurritoKingCollectOrder.fxml");
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
                        Objects.equals(rs.getString("pendingPayment"), "1"),
                        rs.getInt("orderId"),
                        rs.getInt("userId"),
                        rs.getString("orderStatus"),
                        rs.getString("timeOrdered"),
                        rs.getString("dayOrdered"),
                        rs.getString("collectionTime"),
                        rs.getString("summaryText")
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

    // gets all the orders by status and userId. if status is "" then it fetches all the orders irrespective of the orderStatus
    protected ObservableList<Orders> getOrdersByStatusAndUserID(String status){
        ObservableList<Orders> orders = FXCollections.observableArrayList();
        int userId = getIsLoggedInUserId();

        String query;
        if (status.isEmpty()){
            query = "SELECT * FROM Orders WHERE userId = ?";
        } else {
            query = "SELECT * FROM Orders WHERE orderStatus = ? AND userId = ?";
        }

        try (Connection connection = BurritoKingApplication.connect();

             PreparedStatement statement = connection.prepareStatement(query)) {

            if (status.isEmpty()){
                statement.setInt(1, userId);
            } else {
                statement.setString(1, status);
                statement.setInt(2, userId);
            }

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getDouble("totalPrice"),
                        rs.getDouble("waitingTime"),
                        Objects.equals(rs.getString("pendingPayment"), "1"),
                        rs.getInt("orderId"),
                        rs.getInt("userId"),
                        rs.getString("orderStatus"),
                        rs.getString("timeOrdered"),
                        rs.getString("dayOrdered"),
                        rs.getString("collectionTime"),
                        rs.getString("summaryText")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }

        return orders;
    }

    // gets the pending payment order for the user
    protected Credits getCreditsForUser(int userId){
        Credits credits = null;
        String query = "SELECT * FROM Credits WHERE userId = ?";

        try (Connection connection = BurritoKingApplication.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                credits = new Credits(
                        rs.getInt("credits"),
                        rs.getInt("userId")
                );
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }

        return credits;
    }

    // updates the order by orderId and userId
    protected void updateOrderStatus(String status, int orderId){
        int userId = getIsLoggedInUserId();
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "UPDATE Orders SET orderStatus = ? WHERE userId = ? AND orderId = ?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,  status);
            statement.setInt(2,  userId);
            statement.setInt(3,  orderId);
            int isUpdated = statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
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

    // gets previous credits and after addition , updates credits for the logged in user
    protected void updateCredits(Integer credits){
        User loggedInUser = getIsLoggedInUser();

        Credits credits1 = getCreditsForUser(loggedInUser.getUserId());

        credits += credits1.getCredits();

        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "UPDATE Credits SET credits = ? WHERE userId = ?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,  credits);
            statement.setInt(2,  loggedInUser.getUserId());
            int isUpdated = statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
    }

    // adds order to the table and returns the autogenerated orderId or else it updates the existing order in database
    protected int upsertOrderInDb(Double totalPrice, Double waitingTime, String summaryText) {
        User loggedInUser = getIsLoggedInUser();
        int orderId = 0;

        String query;
        PreparedStatement statement;
        Orders orders = getOrderWithPendingPayment(loggedInUser.getUserId());


        try (Connection connection = BurritoKingApplication.connect()) {
            if (orders == null){
                query = "INSERT INTO Orders(userId, pendingPayment, totalPrice, waitingTime, summaryText) VALUES(?, ?, ?, ?, ?)";
                assert connection != null;
                statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, loggedInUser.getUserId());
                statement.setInt(2, 1);
                statement.setDouble(3, totalPrice);
                statement.setDouble(4, waitingTime);
                statement.setString(5, summaryText);
            } else {
                query = "UPDATE Orders SET userId = ?, pendingPayment=?, totalPrice = ?, waitingTime = ?, summaryText = ?  WHERE orderId = ?";
                assert connection != null;
                statement = connection.prepareStatement(query);
                statement.setInt(1, loggedInUser.getUserId());
                statement.setInt(2, 1);
                statement.setDouble(3, totalPrice);
                statement.setDouble(4, waitingTime);
                statement.setString(5, summaryText);
                statement.setInt(6, orders.getOrderId());
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

        if (orders != null){
            return orders.getOrderId();
        } else {
            return orderId;
        }
    }


    // adds food into the table by first for looping the linkedlist for each individual items and then getting their individual price and quantity
    // if order already exists then updates it
    protected void upsertFoodItemsInDb(Order order, Integer orderId){
        String query;
        User loggedInUser = getIsLoggedInUser();

        LinkedList<FoodItem> individualFoodItems = order.getItems();

        Orders orders = getOrderWithPendingPayment(loggedInUser.getUserId());
        List<FoodType> foodTypeListPresentInDB = isFoodPresent(orders);
        List<FoodType> foodTypeList = getFoodList();
        PreparedStatement statement;

        for (FoodItem foodItem: individualFoodItems){
            try (Connection connection = BurritoKingApplication.connect()) {
                assert connection != null;
                FoodType foodType = covertClassNameToFoodType(foodItem.getClass().getName());
                if (!foodTypeListPresentInDB.contains(foodType)){
                    query = "UPDATE Food SET unitPrice = ?, quantity = ? WHERE orderId = ? AND foodType = ?";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1,  foodItem.getTotalPrice());
                    statement.setInt(2,  foodItem.getQuantity());
                    statement.setInt(3,  orders.getOrderId());
                    statement.setString(4,  foodType.toString());
                    foodTypeList.remove(foodType);
                }  else {
                    query = "INSERT INTO Food(unitPrice, foodType, quantity, orderId) VALUES(?, ?, ?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setDouble(1,  foodItem.getTotalPrice());
                    statement.setString(2,  foodType.toString());
                    statement.setInt(3,  foodItem.getQuantity());
                    statement.setInt(4,  orderId);
                    foodTypeList.remove(foodType);
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
        deleteFoodTypeAfterUpsert(foodTypeList, orderId);
    }



    // Method to show an alert
    protected void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to show an alert
    protected void showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    Displays an alert to prompt the user to enter a new collection time for an order.
    protected void showConfirmationAlertDuringCollection(String displayedTime, int orderId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Collection Time");
        alert.setHeaderText(null);
        alert.setContentText("Please enter the time the order is collected");

        Optional<ButtonType> result = alert.showAndWait();
        // User chose OK, prompt for new time
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Get the order and calculate the maximum allowed collection time
            Orders orders = getOrderByOrderId(orderId);
            LocalTime timeOrdered = LocalTime.parse(orders.getTimeOrdered(), DateTimeFormatter.ofPattern("HH:mm"));
            double waitingTimeInMinutes = orders.getWaitingTime(); // get waiting time in minutes
            LocalTime minCollectTime = calculateCollectTime(timeOrdered, waitingTimeInMinutes);

            boolean validTimeEntered = false;
            while (!validTimeEntered) {
                TextInputDialog dialog = new TextInputDialog(minCollectTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                dialog.setTitle("Enter New Time");
                dialog.setHeaderText("Change Time");
                dialog.setContentText("Please enter the time you collected the order. The time cannot be earlier than " + minCollectTime.format(DateTimeFormatter.ofPattern("HH:mm")) + ":");

                Optional<String> newTimeResult = dialog.showAndWait();

                if (newTimeResult.isPresent()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        LocalTime newTime = LocalTime.parse(newTimeResult.get(), formatter);

                        if (newTime.isAfter(minCollectTime)) {
                            validTimeEntered = true;
                            updateCollectedTimeInOrders(newTime.toString(), orderId);
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Time Accepted");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("The new time has been accepted: " + newTime.format(formatter));
                            successAlert.showAndWait();
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Invalid Time");
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("The entered time is before the minimum allowed time. Please enter a time later than " + minCollectTime.format(formatter) + ".");
                            errorAlert.showAndWait();
                        }
                    } catch (Exception e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Invalid Time Format");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("The time you entered is not valid. Please enter the time in HH:mm format.");
                        errorAlert.showAndWait();
                    }
                } else {
                    // If the user cancels the input dialog, break the loop
                    break;
                }
            }
        }
    }


    // function which shows the alert box wherein we ask if the user is vip or not.
    protected boolean showIsVipAlert(int userId, boolean isLoginPage) throws IOException {
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
                updateVipStatusInUserAndInsertAVipUser(userId, emailAddress, isLoginPage);
                insertCreditsForVip(userId);
                return true;
            } else if (bt == ButtonType.CANCEL) {
                return false;
            }
        }
    }

    // sets isLoggedIn to false for all user
    protected void setIsLoggedIn() {
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

    // Method to change screens
    protected void changeScreen(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
        stage.setScene(scene);
        stage.show();
    }

    // updates orders table to set pending payment = 0 to confirm the payment and also set other meta data like day time
    protected void confirmPayment(String timeOrdered){
        int userId = getIsLoggedInUserId();

        try (Connection connection = BurritoKingApplication.connect()) {
                String query = "UPDATE Orders SET orderStatus = 'await for collection', pendingPayment = 0, timeOrdered = ?, dayOrdered = ?  WHERE userId = ? AND pendingPayment = 1";
                assert connection != null;
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, timeOrdered);
                // Get today's date in the desired format - 22 May 2024
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
                String formattedDate = today.format(formatter);

                // Set the dayOrdered parameter
                statement.setString(2, formattedDate);

                statement.setInt(3, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Shows confirmation alert and returns the time if the user wants to enter a new one
    protected LocalTime showConfirmationAlertForFetchingCurrentTime(String title, String message, String formattedTime) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK, prompt for new time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime newTime = null;

            while (newTime == null) {
                TextInputDialog dialog = new TextInputDialog(formattedTime);
                dialog.setTitle("Enter New Time");
                dialog.setHeaderText("Change Time");
                dialog.setContentText("Please enter the new time (HH:mm), example 23:23");

                Optional<String> newTimeResult = dialog.showAndWait();

                if (newTimeResult.isPresent()) {
                    try {
                        newTime = LocalTime.parse(newTimeResult.get(), formatter);
                    } catch (Exception e) {
                        // Handle invalid time format
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Invalid Time Format");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("The time you entered is not valid. Please enter the time in HH:mm format.");
                        errorAlert.showAndWait();
                    }
                } else {
                    // User cancelled the input dialog
                    break;
                }
            }

            if (newTime != null) {
                return newTime;
            }
        }

        // If user cancels or enters invalid time, return the original time
        return LocalTime.parse(formattedTime, DateTimeFormatter.ofPattern("HH:mm"));
    }


    // function to update the vip status of the user
    private void updateVipStatusInUserAndInsertAVipUser(int userId, String emailAddress, boolean isLoginPage) {
        int isUpdated = 1;
        try (Connection connection = BurritoKingApplication.connect()) {
            if (isLoginPage){
                String query = "UPDATE User SET isVipPermissionAsked = '1' WHERE userId = ?";
                assert connection != null;
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                isUpdated = statement.executeUpdate();
            }

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

    // function to update the collection Time of the order
    private void updateCollectedTimeInOrders(String collectedTime, int orderId) {
        try (Connection connection = BurritoKingApplication.connect()) {

            String query = "UPDATE Orders SET collectionTime = ? WHERE orderId = ?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, collectedTime);
            statement.setInt(2, orderId);
            statement.executeUpdate();

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

    // gets the pending payment order for the user
    private Orders getOrderByOrderId(int orderId){
        Orders order = null;
        String query = "SELECT * FROM Orders WHERE orderId = ?";

        try (Connection connection = BurritoKingApplication.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                order = new Orders(
                        rs.getDouble("totalPrice"),
                        rs.getDouble("waitingTime"),
                        Objects.equals(rs.getString("pendingPayment"), "1"),
                        rs.getInt("orderId"),
                        rs.getInt("userId"),
                        rs.getString("orderStatus"),
                        rs.getString("timeOrdered"),
                        rs.getString("dayOrdered"),
                        rs.getString("collectionTime"),
                        rs.getString("summaryText")
                );
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }

        return order;
    }


    // returns foodlist of all type
    private List<FoodType> getFoodList(){
        List<FoodType> foodTypePresentList= new ArrayList<>();

        // creates hashmap  with all the foodtype values set to false for now
        for (FoodType ft: FoodType.values()){
            foodTypePresentList.add(FoodType.valueOf(ft.name()));
        }

        return foodTypePresentList;
    }

    // Inserts a row in Credits table for the Vip User
    private void insertCreditsForVip(int userId){
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "INSERT INTO Credits(credits, userId) VALUES(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, 0);
            statement.setInt(2, userId);
            assert connection != null;
            // Execute the update
            int isUpdated = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
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
    private List<FoodType> isFoodPresent(Orders orders){
        List<FoodType> foodTypePresentList= new ArrayList<>();

        // creates hashmap  with all the foodtype values set to false for now
        for (FoodType ft: FoodType.values()){
            foodTypePresentList.add(FoodType.valueOf(ft.name()));
        }
        try (Connection connection = BurritoKingApplication.connect()) {
            String query = "SELECT * FROM Food WHERE orderId=?";
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orders.getOrderId());
            ResultSet rs = statement.executeQuery();
            // deletes the list value if the food data is present in the database
            while (rs.next()){
                System.out.println(rs.getString("foodType"));
                if (rs.getString("foodType") == null){
                    continue;
                }
                foodTypePresentList.remove(FoodType.valueOf(rs.getString("foodType")));
            }
            connection.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": " + e.getMessage());
        }
        return foodTypePresentList;
    }

    // loops through list to delete any rows of respective foodtype after upsert
    private void deleteFoodTypeAfterUpsert(List<FoodType> foodTypeList, int orderId){

        for (FoodType foodType : foodTypeList) {
                try (Connection connection = BurritoKingApplication.connect()) {
                    String query = "DELETE FROM Food WHERE orderId = ? AND foodType = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1,  orderId);
                    statement.setString(2,  foodType.toString());
                    int isUpdated = statement.executeUpdate();
                    System.out.println(isUpdated);
                    System.out.println("isUpdated");
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


    // adds  localtime and double and returns the result
    private LocalTime calculateCollectTime(LocalTime timeOrdered, double waitingTimeInMinutes) {
        Duration waitingDuration = Duration.ofMinutes((long) waitingTimeInMinutes);
        return timeOrdered.plus(waitingDuration);
    }

}



