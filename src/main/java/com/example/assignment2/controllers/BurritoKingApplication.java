package com.example.assignment2.controllers;

import com.example.assignment2.models.Menu;
import com.example.assignment2.models.Restaurant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.sqlite.JDBC;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

// this is the main burrito king application class which extends Application, our program runs from here
public class BurritoKingApplication extends Application {

    private static Menu menu; // Static variable to hold the menu instance
    private static final Restaurant restaurant = Restaurant.getInstance("BurritoKing Restaurant"); // Singleton restaurant instance
    private static final String location = Objects.requireNonNull(
            BurritoKingApplication.class.getResource("/com.example.assignment2.database/database.db")
    ).toExternalForm(); // Database location

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.views/BurritoKingLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load()); // Load the login FXML file
        Image icon = new Image("file:src/BurritoKing.png"); // Load the application icon

        stage.getIcons().add(icon); // Set the application icon
        stage.setTitle("Burrito King 🌯🌯🌯🌯");
        stage.setScene(scene); // Set the scene to the stage
        stage.setResizable(false); // Make the stage non-resizable
        stage.show(); // Display the stage
    }

    public static void main(String[] args) {
        checkDrivers(); // Check and register JDBC drivers
        Connection connection = connect(); // Connect to the database
        menu = new Menu(restaurant); // Create a new menu instance
        launch(); // Launch the application
    }

    public static Connection connect() {
        String dbPrefix = "jdbc:sqlite:"; // Database connection prefix
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location); // Establish a connection
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " + location);
            return null; // Return null if connection fails
        }
        return connection; // Return the established connection
    }

    private static void checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver class
            DriverManager.registerDriver(new JDBC()); // Register the JDBC driver
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
        }
    }

    // Return the singleton restaurant instance
    public static Restaurant getRestaurant() {
        return restaurant;
    }

    // Return the menu instance
    public static Menu getMenu() {
        return menu;
    }
}
