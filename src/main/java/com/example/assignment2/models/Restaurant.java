package com.example.assignment2.models;

import java.util.ArrayList;
import java.util.Scanner;

// restaurant class
public class Restaurant {
    private String name;

    // contains a list of orders
    private ArrayList<Order> report = new ArrayList<Order>();

    // fries tray if customer wants to order fries
    static final FriesTray friesTray = new FriesTray();

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // main method which runs our program, has the main menu function
    public void run(){

        Scanner sc = new Scanner(System.in);
        String choice = "w";
        while(!choice.equals("d")){


            boolean correctInput = false;
            while (!correctInput){
                try {
                    System.out.println();
                    System.out.println("===============================================================");
                    System.out.println("Burrito King");
                    System.out.println("===============================================================");
                    System.out.println("a. Order");
                    System.out.println("b. Show sales report");
                    System.out.println("c. Update prices");
                    System.out.println("d. Exit");
                    System.out.print("Please select: ");
                    choice = sc.nextLine();
                    if (choice.length() > 1 || !choice.matches("[a-d]")){
                        throw new Exception("Choice is incorrect, please enter correct input");
                    }
                    correctInput = true;
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
            switch (choice){
                case "a":
                    Order order = new Order();
                    Scanner foodChoiceScanner = new Scanner(System.in);
                    String foodChoice = "0";
                    while (!foodChoice.equals("5")){


                        boolean correctNumberInput = false;
                        while (!correctNumberInput){
                            try {
                                System.out.println();
                                System.out.println("> Select the food item");
                                System.out.println("1. Burrito");
                                System.out.println("2. Fries");
                                System.out.println("3. Soda");
                                System.out.println("4. Meal");
                                System.out.println("5. No more");
                                System.out.print("Please select: ");
                                foodChoice = foodChoiceScanner.nextLine();
                                if (foodChoice.length() > 1 || !foodChoice.matches("[1-5]")){
                                    throw new Exception("Choice is incorrect, please enter correct input");
                                }
                                correctNumberInput = true;
                            } catch (Exception ex){
                                System.out.println(ex.getMessage());
                            }
                        }

                        switch (foodChoice){
                            case "1":
                                order.addBurritosToOrder();
                                break;
                            case "2":
                                order.addFriesToOrder();
                                break;
                            case "3":
                                order.addSodaToOrder();
                                break;
                            case "4":
                                order.addMealToOrder();
                                break;
                            case "5":
                                order.computeCostAndTime();
                                break;
                            default:
                                break;
                        }
                    }
                    report.add(order);
                    break;
                case "b":

                    this.computeSalesReport();
                    break;
                case "c":
                    updatePrices();
                    break;
                case "d":
                    System.out.println("Thank you for using Burrito King, Bye Bye.");
                    break;
            }



        }
        sc.close();
    }

    // computes sales report by looping through the reports arraylist
    private void computeSalesReport(){
        Integer totalBurritoCount = 0;
        Integer totalFriesCount = 0;
        Integer totalSodaCount = 0;
        Double totalBurritoCost = 0.0;
        Double totalFriesCost = 0.0;
        Double totalSodaCost = 0.0;

        for (Order order: this.report){
            for (ArrayList<Food> food: order.getOrderList()){
                Food food1 = food.get(0);
                if (food1 instanceof Burrito) {
                    // computes count
                    Integer burritoInstanceCount = ((Burrito) food1).getNumberOfItemPerOrder();
                    totalBurritoCount += burritoInstanceCount;

                    // computes cost
                    Double burritoInstanceCost = ((Burrito) food1).getCostByNumberOfItemPerOrder();
                    totalBurritoCost += burritoInstanceCost;
                } else if (food1 instanceof Fries) {
                    // computes count
                    Integer friesInstanceCount = ((Fries) food1).getNumberOfItemPerOrder();
                    totalFriesCount += friesInstanceCount;

                    // computes cost
                    Double friesInstanceCost = ((Fries) food1).getCostByNumberOfItemPerOrder();
                    totalFriesCost += friesInstanceCost;
                } else if (food1 instanceof Soda){
                    // computes count
                    Integer sodaInstanceCount = ((Soda) food1).getNumberOfItemPerOrder();
                    totalSodaCount += sodaInstanceCount;

                    // computes cost
                    Double sodaInstanceCost = ((Soda) food1).getCostByNumberOfItemPerOrder();
                    totalSodaCost += sodaInstanceCost;
                } else if (food1 instanceof Meal) {
                    // computes count
                    Integer mealInstanceCount = ((Meal) food1).getNumberOfItemsPerBatch();
                    totalSodaCount += mealInstanceCount;
                    totalFriesCount += mealInstanceCount;
                    totalBurritoCount += mealInstanceCount;

                    // computes cost
                    totalSodaCost += mealInstanceCount * (((Meal) food1).getSoda().getCostByNumberOfItemPerOrder() - 1);
                    totalFriesCost += mealInstanceCount * (((Meal) food1).getFries().get(0).getCostByNumberOfItemPerOrder() - 1);
                    totalBurritoCost += mealInstanceCount * (((Meal) food1).getBurrito().getCostByNumberOfItemPerOrder() - 1);
                }
            }
        }

        printComputeSalesReport(totalBurritoCount, totalFriesCount, totalSodaCount, totalBurritoCost, totalFriesCost, totalSodaCost);

    }



    // prints sales report
    private void printComputeSalesReport(Integer totalBurritoCount, Integer totalFriesCount, Integer totalSodaCount,Double totalBurritoCost, Double totalFriesCost, Double totalSodaCost){
        int totalCount = totalBurritoCount + totalSodaCount + totalFriesCount;
        double totalCost = totalBurritoCost + totalFriesCost + totalSodaCost;

        System.out.println("Unsold Serves of Fries: " + friesTray.getFriesTray().size()) ;
        System.out.println();
        System.out.println("Total Sales:");
        System.out.printf("%-20s : %s $%s\n", "Burritos: ", totalBurritoCount, totalBurritoCost);
        System.out.printf("%-20s : %s $%s\n", "Fries: ", totalFriesCount, totalFriesCost);
        System.out.printf("%-20s : %s $%s\n", "Soda: ", totalSodaCount, totalSodaCost);
        System.out.println("------------------------------\n");
        System.out.printf("%-20s : %s $%s\n", "Total:", totalCount, totalCost);

    }

    // funciton which can update prices for soda, fries, burritos
    private void updatePrices(){
        System.out.println();
        System.out.println("> Select the food item");
        System.out.println("1. Burrito");
        System.out.println("2. Fries");
        System.out.println("3. Soda");
        System.out.println("4. Go Back");
        System.out.print("Please select: ");
        Scanner updatePriceScanner = new Scanner(System.in);
        Scanner updateIndexScanner = new Scanner(System.in);
        int updateIndex = 0;

        boolean correctNumberInput = false;
        while (!correctNumberInput){
            try {
                String updateIndexChoice = updateIndexScanner.nextLine();
                if (updateIndexChoice.length() > 1 || !updateIndexChoice.matches("[1-4]")){
                    throw new Exception("Choice is incorrect, please enter correct input");
                }
                correctNumberInput = true;
                updateIndex = Integer.parseInt(updateIndexChoice);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }



        Double updatePrice = 0.0;
        boolean correctNumberDoubleInput = false;
        while (!correctNumberDoubleInput){
            try {
                System.out.println();
                System.out.println("Please enter the price you will like to update");

                String updatePriceString = updateIndexScanner.nextLine();
                if (updatePriceString.equals("0")){
                    throw new Exception("Price cannot be 0");
                }

                updatePrice = Double.parseDouble(updatePriceString);
                correctNumberDoubleInput = true;
            } catch (Exception ex){
                System.out.println("Choice is incorrect, please enter correct input");
            }


        }
        switch (updateIndex){
            case 1:
                System.out.println("Price of Burrito updated from $" + Burrito.getPrice() + " to $" + updatePrice);
                Burrito.setPrice(updatePrice);
                break;
            case 2:
                System.out.println("Price of Fries updated from $" + Fries.getPrice() + " to $" + updatePrice);
                Fries.setPrice(updatePrice);
                break;
            case 3:
                System.out.println("Price of Soda updated from $" + Soda.getPrice() + " to $" + updatePrice);
                Soda.setPrice(updatePrice);
                break;
            default:
                System.out.println("You have entered the wrong input, exiting");
                break;
        }
    }

    // test class to test updatePrices
    public void testUpdatePricesBurritosFromSevenToFive(){
        int updateIndex = 1;
        Double updatePrice = 5.0;
        switch (updateIndex){
            case 1:
                System.out.println("Price of Burrito updated from $" + Burrito.getPrice() + " to $" + updatePrice);
                Burrito.setPrice(updatePrice);
                break;
            case 2:
                System.out.println("Price of Fries updated from $" + Fries.getPrice() + " to $" + updatePrice);
                Fries.setPrice(updatePrice);
                break;
            case 3:
                System.out.println("Price of Soda updated from $" + Soda.getPrice() + " to $" + updatePrice);
                Soda.setPrice(updatePrice);
                break;
            default:
                System.out.println("You have entered the wrong input, exiting");
                break;
        }
    }

    // test class to test updatePrices
    public void testUpdatePricesBurritosFromFiveToSeven(){
        int updateIndex = 1;
        Double updatePrice = 7.0;
        switch (updateIndex){
            case 1:
                System.out.println("Price of Burrito updated from $" + Burrito.getPrice() + " to $" + updatePrice);
                Burrito.setPrice(updatePrice);
                break;
            case 2:
                System.out.println("Price of Fries updated from $" + Fries.getPrice() + " to $" + updatePrice);
                Fries.setPrice(updatePrice);
                break;
            case 3:
                System.out.println("Price of Soda updated from $" + Soda.getPrice() + " to $" + updatePrice);
                Soda.setPrice(updatePrice);
                break;
            default:
                System.out.println("You have entered the wrong input, exiting");
                break;
        }
    }
}
