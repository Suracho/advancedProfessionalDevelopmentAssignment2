package com.example.assignment2.models;

public interface Cookable {
	/**
	 * The method to calculate the total preparation time of a food item
	 */
	public abstract int getPreparationTime(Restaurant restaurant);
	/**
	 * The method to calculate the actual number of items that need to be cooked
	 */
	public abstract int getActualQuantityCooked(Restaurant restaurant);
}
