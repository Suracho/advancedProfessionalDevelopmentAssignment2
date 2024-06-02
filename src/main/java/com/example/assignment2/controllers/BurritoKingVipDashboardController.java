package com.example.assignment2.controllers;

import com.example.assignment2.models.Credits;
import com.example.assignment2.models.DaoOrders;
import com.example.assignment2.models.OrderStatus;
import com.example.assignment2.models.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BurritoKingVipDashboardController extends CommonFunctions{
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label creditsNumberLabel;

    // Table
    @FXML
    private TableView<DaoOrders> orderSummaryTable;

    // Columns
    @FXML
    private TableColumn<DaoOrders, Integer> orderIdColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderSummaryColumn;

    @FXML
    private TableColumn<DaoOrders, Double> totalPriceColumn;

    @FXML
    private TableColumn<DaoOrders, String> orderStatusColumn;

    @FXML
    public void initialize() {
        setInputFieldsValues();

        // gets order which are awaiting collection and sets the table
        ObservableList<DaoOrders> orders = getOrdersByStatusAndUserID(OrderStatus.AWAIT_FOR_COLLECTION.toString());

//        // Sort by day and then by time
//        orders.sort((o1, o2) -> {
//            // Parse day order strings to LocalDate objects for comparison
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
//            LocalDate date1 = LocalDate.parse(o1.getDayOrdered(), formatter);
//            LocalDate date2 = LocalDate.parse(o2.getDayOrdered(), formatter);
//
//            int dateCompare = date1.compareTo(date2);
//            if (dateCompare != 0) {
//                return dateCompare;
//            } else {
//                // Parse time strings to LocalTime for comparison
//                LocalTime time1 = LocalTime.parse(o1.getTimeOrdered());
//                LocalTime time2 = LocalTime.parse(o2.getTimeOrdered());
//                return time2.compareTo(time1);  // Descending order for time (latest first)
//            }
//        });

        orders.sort(DaoOrders::compareByDayTime);


        orderIdColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, Integer>("orderId"));
        orderSummaryColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("summaryText"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, Double>("totalPrice"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<DaoOrders, String>("orderStatus"));

        orderSummaryTable.setItems(orders);
    }


    // function to set the fields required to show on the dashboard
    private void setInputFieldsValues(){
        User user = getIsLoggedInUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        // gets credits and then sets it
        Credits credits = getCreditsForUser(user.getUserId());
        creditsNumberLabel.setText("Credits: " + credits.getCredits().toString());
    }
}
