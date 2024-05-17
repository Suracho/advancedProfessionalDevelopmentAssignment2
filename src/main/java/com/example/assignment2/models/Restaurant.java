package com.example.assignment2.models;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The Restaurant class tracks the state of food prices, all placed orders, and remained fries on the food stack.
 */
public class Restaurant {
	private String name;
    // keep food prices
    private static final HashMap<String, Double> priceMap = new HashMap<String, Double>();
    // track left-overs from previous order
    private int remainedFries;
    // store all historical orders
    private static final LinkedList<Order> allOrders = new LinkedList<Order>();
    private static final int mealDiscount = 3;

	// Private constructor to prevent external instantiation
	private Restaurant(String name) {
		this.name = name;

		priceMap.put("Burrito", 7.0);
		priceMap.put("Fries", 4.0);
		priceMap.put("Soda", 2.5);
		remainedFries = 0;
	}

	// volatile for thread safety
	private static volatile Restaurant instance;


	// This ensures only one instance is created and returned.
	public static Restaurant getInstance(String name) {
		if (instance == null) {
			synchronized (Restaurant.class) {
				if (instance == null) {
					instance = new Restaurant(name);
				}
			}
		}
		return instance;
	}



	public String getName() {
    	return this.name;
    }
    
    public static int getDiscount() {
    	return mealDiscount;
    }

      
    public static double getPrice(String foodName) {
		return priceMap.get(foodName);
	}
    
    public static LinkedList<Order> getAllOrders(){
    	return allOrders;
    }
    
    public int getRemainedFries() {
		return this.remainedFries;
	}
    
    /**
     * The method to update food price.
     */
	public static void updatePrice(String foodName, double newPrice) {
		priceMap.put(foodName, newPrice);
	}
	
	/**
     * The method to update remaining Fries
     */
	public boolean updateRemainingServes(Order order) {
		HashMap<String, Integer> cookables = order.mapToCookables();
		Fries friesToCook = new Fries(Restaurant.getPrice("Fries"), cookables.get("Fries"));
		int oldRemained = this.remainedFries;
		this.remainedFries = friesToCook.getActualQuantityCooked(this) + oldRemained - friesToCook.getQuantity();
		if (this.remainedFries != oldRemained) {
			return true;						
		}
		return false;
	}
	
	
	/**
	 * The method to add a new order to the list of historical orders
	 */
    public void addOrderToHistory(Order order) {
    	this.allOrders.add(order);
    }   
}
