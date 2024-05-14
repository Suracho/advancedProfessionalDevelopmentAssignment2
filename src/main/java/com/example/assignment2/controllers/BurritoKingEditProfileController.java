package com.example.assignment2.controllers;

import com.example.assignment2.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// This is the controller Class for Edit profile View which contains an initialize method to display the user values fetched from db
public class BurritoKingEditProfileController extends CommonFunctions{

    @FXML
    private TextField username;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField password;
    @FXML
    private Label updateLabel;

    @FXML
    public void initialize() {
        setInputFieldsValues();
    }

    @FXML
    protected void editProfile(){
        String inputUsername = username.getText();
        String inputPassword = password.getText();
        String inputFirstName = firstName.getText();
        String inputLastName = lastName.getText();

        User user = new User(inputUsername, inputFirstName, inputLastName, inputPassword);
        try {
            updateUser(user);
            updateLabel.setText("Profile updated successfully, please proceed to the home page!");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            updateLabel.setText("Error updating profile.");
        }

    }

    private void setInputFieldsValues(){
        User user = getIsLoggedInUser();
        username.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        password.setText(user.getPassword());
    }


}
