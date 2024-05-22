package com.example.assignment2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

// This is the Controller class for Payment. It contains validation logics for credit card text fields
public class BurritoKingPaymentScreen extends CommonFunctions{
    @FXML
    private DatePicker expiryDate;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cvv;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

    public void initialize() {
        // CVV listener
        cvv.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new value contains only digits
            if (!newValue.matches("\\d*")) {
                showErrorAlert("Error", "CVV must contain only digits (0-9).");
                cvv.setText(oldValue);
            } else if (newValue.length() > 3) {
                showErrorAlert("Error", "CVV must be 3 digits long.");
                cvv.setText(oldValue);
            }
        });

        // Card number listener
        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new value contains only digits
            if (!newValue.matches("\\d*")) {
                showErrorAlert("Error", "Card number must contain only digits (0-9).");
                cardNumber.setText(oldValue);
            } else if (newValue.length() > 16) {
                showErrorAlert("Error", "Card number must be 16 digits long.");
                cardNumber.setText(oldValue);
            }
        });

        // Set a custom StringConverter to display only the month and year
        expiryDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return YearMonth.parse(string, dateFormatter).atDay(1);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });

        // Set a DayCellFactory to disable dates before today
        expiryDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(LocalDate.now()));
                    }
                };
            }
        });
    }

    // Function to fetch the card details and confirm the payment as done in database
    @FXML
    protected void confirmPayment() throws Exception {
        String cardNumberText = cardNumber.getText();
        String cvvText = cvv.getText();
        LocalDate expiryDateValue = expiryDate.getValue();
        String expiryDateText = expiryDateValue != null ? dateFormatter.format(expiryDateValue) : "";

        if (cardNumberText.length() != 16){
            return;
        } else if (cvvText.length() != 3){
            return;
        } else if (expiryDateText.isEmpty()){
            return;
        }


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a"); // Use 'a' for AM/PM
        String formattedTime = formatter.format(now);
        LocalTime lt = showConfirmationAlertForFetchingCurrentTime("Time Confirmation", "The time right now is " + formattedTime + ". Do you want to change the time?", formattedTime);
        String formattedLt = lt.toString();

        confirmPayment(formattedLt);
        proceedToHome();
    }
}
