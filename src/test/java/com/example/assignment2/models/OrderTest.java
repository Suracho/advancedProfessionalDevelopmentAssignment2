package com.example.assignment2.models;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// this is the test class for orderTest
public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        // Initialize a new order before each test
        order = new Order();
    }

    /**
     * Test adding food items to the order.
     * Ensures that quantities are updated correctly when the same type of food item is added.
     */
    @Test
    public void testAddFoodItem() {
        // Add a burrito to the order
        FoodItem burrito = new Burrito(Restaurant.getPrice("Burrito"), 2);
        order.addFoodItem(burrito);
        LinkedList<FoodItem> items = order.getItems();

        // Verify that the item was added correctly
        assertEquals(1, items.size(), "Should have exactly one item after adding one.");
        assertInstanceOf(Burrito.class, items.get(0), "The item should be an instance of Burrito.");
        assertEquals(2, items.get(0).getQuantity(), "The quantity of the burrito should be 2.");

        // Add more of the same item and verify the quantity update
        order.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 3));
        assertEquals(1, items.size(), "Should still have one item after adding more of the same type.");
        assertEquals(5, items.get(0).getQuantity(), "The quantity of the burrito should now be 5.");
    }

    /**
     * Test adding multiple different food items to the order.
     * Ensures that different types of items can be added.
     */
    @Test
    public void testAddMultipleDifferentItems() {
        // Add a burrito and fries to the order
        order.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 2));
        order.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 3));

        LinkedList<FoodItem> items = order.getItems();
        // Verify that both items were added correctly
        assertEquals(2, items.size(), "Should have two different items.");
    }

    /**
     * Test calculating the total price of the order.
     * Adds different food items to the order and checks if the total price is calculated correctly.
     */
    @Test
    public void testGetTotalPrice() {
        // Add a burrito and fries to the order
        order.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 2));
        order.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 3));

        // Verify the total price calculation
        assertEquals(16.0, order.getTotalPrice(), 0.01, "Total price should be calculated correctly.");
    }

    /**
     * Test calculating the total price with no items added.
     * Ensures that the total price is zero when no items are added to the order.
     */
    @Test
    public void testGetTotalPriceWithNoItems() {
        // Verify that the total price is zero when no items are added
        assertEquals(0.0, order.getTotalPrice(), 0.01, "Total price should be 0.0 when no items are added.");
    }

    /**
     * Test calculating the total preparation time of the order.
     * Adds different food items to the order and checks if the preparation time is calculated correctly.
     */
    @Test
    public void testGetPrepTime() {
        Restaurant restaurant = Restaurant.getInstance("BurritoKing");

        // Add a burrito and fries to the order
        order.addFoodItem(new Burrito(Restaurant.getPrice("Burrito"), 2));
        order.addFoodItem(new Fries(Restaurant.getPrice("Fries"), 3));

        // Verify the preparation time calculation
        assertEquals(10.0, order.getPrepTime(restaurant), 0.01, "Total preparation time should be calculated correctly.");
    }

    /**
     * Test calculating the total preparation time with no items added.
     * Ensures that the preparation time is zero when no items are added to the order.
     */
    @Test
    public void testGetPrepTimeWithNoItems() {
        Restaurant restaurant = Restaurant.getInstance("BurritoKing");

        // Verify that the preparation time is zero when no items are added
        assertEquals(0.0, order.getPrepTime(restaurant), 0.01, "Preparation time should be 0.0 when no items are added.");
    }

}
