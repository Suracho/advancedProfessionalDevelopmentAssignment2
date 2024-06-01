package com.example.assignment2.models;


import javafx.scene.control.CheckBox;

// Custom class to hold the order column name and its selection state
public class OrderColumnCheckBox {
    private final String columnName;
    private final CheckBox checkBox;

    public OrderColumnCheckBox(String columnName) {
        this.columnName = columnName;
        this.checkBox = new CheckBox();
    }

    public String getColumnName() {
        return columnName;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}