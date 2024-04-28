package com.example.assignment2.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.sqlite.JDBC;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BurritoKingApplication extends Application {

    private static final String location = BurritoKingApplication.class.getResource("/com.example.assignment2.database/database.db").toExternalForm();

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(BurritoKingApplication.class.getResource("/com.example.assignment2.controllers/BurritoKing-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("file:src/BurritoKing.png");

//        // Set the stage style to transparent
        stage.getIcons().add(icon);
        stage.setTitle("Burrito King 🌯🌯🌯🌯");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }


    public static void main(String[] args) {
        checkDrivers();
        Connection connection = connect();
        launch();
    }

    private static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + BurritoKingApplication.location);

        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            BurritoKingApplication.location);
            return null;
        }
        return connection;
    }

    //checks for the driver and then registers it
    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }


//    private static void updatePersonsFromDB() {
//        try (Connection connection = BurritoKingApplication.connect()) {
////            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet rs = statement.executeQuery();
//        } catch (SQLException e) {
//            Logger.getAnonymousLogger().log(
//                    Level.SEVERE,
//                    LocalDateTime.now() + ": Could not load Persons from database ");
//        }
//    }
}