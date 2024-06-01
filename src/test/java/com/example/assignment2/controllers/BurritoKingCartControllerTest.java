package com.example.assignment2.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// this is a test class for function used in BurritoKingCartController
public class BurritoKingCartControllerTest {

    @Test // Tests conversion for zero time (no hours)
    public void testZeroTime() {
        BurritoKingCartController controller = new BurritoKingCartController();
        double time = 0.0;
        String expectedResult = "0 hours 0 minutes";
        String actualResult = controller.convertToHoursAndMinutesForTest(time);
        assertEquals(expectedResult, actualResult);
    }

    @Test // Tests conversion for time less than one hour (minutes only)
    public void testLessThanOneHour() {
        BurritoKingCartController controller = new BurritoKingCartController();
        double time = 30.0;
        String expectedResult = "0 hours 30 minutes";
        String actualResult = controller.convertToHoursAndMinutesForTest(time);
        assertEquals(expectedResult, actualResult);
    }

    @Test // Tests for invalid input (double representing time instead of String - should fail)
    public void testInvalidInput() {
        BurritoKingCartController controller = new BurritoKingCartController();
        double time = 120.0; // Expecting String input for time
        String expectedResult = "1 hour 0 minutes";
        String actualResult = controller.convertToHoursAndMinutesForTest(time);
        assertNotEquals(expectedResult, actualResult);
    }
}
