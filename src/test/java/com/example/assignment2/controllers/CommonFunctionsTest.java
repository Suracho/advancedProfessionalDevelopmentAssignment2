package com.example.assignment2.controllers;

import com.example.assignment2.controllers.CommonFunctions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

// this is a test class for the CommonFunctions class credits calculation functions and a calculate collect time function
class CommonFunctionsTest {

    // Concrete implementation of CommonFunctions abstract class
    private static class TestCommonFunctions extends CommonFunctions {
        // Expose package-private methods as public for testing
        @Override
        public Integer addCreditsForTest(int credits, int userId) {
            return super.addCreditsForTest(credits, userId);
        }

        @Override
        public Integer minusCreditsForTest(int credits, int userId) {
            return super.minusCreditsForTest(credits, userId);
        }

        public LocalTime calculateCollectTimeForTest(LocalTime timeOrdered, double waitingTimeInMinutes) {
            return super.calculateCollectTimeForTest(timeOrdered, waitingTimeInMinutes);
        }
    }

    // Use the concrete implementation to test package-private methods
    @Test
    void testAddCredits() {
        TestCommonFunctions commonFunctions = new TestCommonFunctions();

        int creditsToAdd = 50;

        // we have assumed 100 initial credits for the user because we are not able to get the actual credits of the user from the db in test case for now
        int initialCredits = 100;
        int expectedCredits = 150; // Assuming the user initially has 100 credits
        int actualCredits = commonFunctions.addCreditsForTest(creditsToAdd, initialCredits);

        assertEquals(expectedCredits, actualCredits, "The user's credits should be correctly added.");
    }

    @Test
    void testMinusCredits() {
        TestCommonFunctions commonFunctions = new TestCommonFunctions();

        int creditsToSubtract = 30;

        // we have assumed 100 initial credits for the user because we are not able to get the actual credits of the user from the db in test case for now
        int initialCredits = 100;
        int expectedCredits = 70; // Assuming the user initially has 100 credits
        int actualCredits = commonFunctions.minusCreditsForTest(creditsToSubtract, initialCredits);

        assertEquals(expectedCredits, actualCredits, "The user's credits should be correctly subtracted.");
    }

    @Test
    void testCalculateCollectTimeForTest() {
        TestCommonFunctions commonFunctions = new TestCommonFunctions();

        // Given an order time at 12:00 PM and a waiting time of 45 minutes
        LocalTime timeOrdered = LocalTime.of(12, 0);
        double waitingTimeInMinutes = 45.0;

        // When calculating the collect time
        LocalTime actualCollectTime = commonFunctions.calculateCollectTimeForTest(timeOrdered, waitingTimeInMinutes);

        // Then the expected collect time should be 45 minutes after the order time
        LocalTime expectedCollectTime = LocalTime.of(12, 45);
        assertEquals(expectedCollectTime, actualCollectTime, "The calculated collect time should be 45 minutes after the order time.");
    }
}
