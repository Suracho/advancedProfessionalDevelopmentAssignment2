package com.example.assignment2.models;

// This is enum class for OrderStatus like "await for collection", "collected", "cancelled" etc
public enum OrderStatus {
    AWAIT_FOR_COLLECTION("await for collection"),
    COLLECTED("collected"),
    CANCELLED("cancelled");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
