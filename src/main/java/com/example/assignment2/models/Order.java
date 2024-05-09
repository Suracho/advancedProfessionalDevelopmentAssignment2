package com.example.assignment2.models;

import java.util.ArrayList;
import java.util.Scanner;

// order Class
public class Order {

    // this is the arraylist which stores all our orders
    private ArrayList<ArrayList<Food>> orderList = new ArrayList<ArrayList<Food>>();

    public Order() {
    }


    public ArrayList<ArrayList<Food>> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<ArrayList<Food>> orderList) {
        this.orderList = orderList;
    }

    // adds burritos to the order
    public void addBurritosToOrder(){
        Scanner burritoScanner = new Scanner(System.in);

        boolean correctInputChoice = false;
        int burritoCount = 0;
        while (!correctInputChoice){
            try{
                System.out.println("How many burritos would you like to buy: ");
                String burritoCountInput = burritoScanner.next();
                if (burritoCountInput.length() > 1 || !burritoCountInput.matches("\\d+") || burritoCountInput.equals("0")){
                    throw new Exception("Choice is incorrect, please enter correct input");
                }
                correctInputChoice = true;
                burritoCount = Integer.parseInt(burritoCountInput);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        Burrito burrito = new Burrito(burritoCount);

        //adds burrito instance to orders arrayList
        ArrayList<Food> burritoList = new ArrayList<Food>();
        burritoList.add(burrito);
        this.orderList.add(burritoList);
    }

    // adds com.suraj.javaClasses.Fries to the order
    public void  addFriesToOrder(){
        Scanner friesScanner = new Scanner(System.in);


        boolean correctInputChoice = false;
        int friesCount = 0;
        while (!correctInputChoice){
            try{
                System.out.println("How many serves of fries would you like to buy: ");
                String friesCountInput = friesScanner.next();
                if (friesCountInput.length() > 1 || !friesCountInput.matches("\\d+") || friesCountInput.equals("0")){
                    throw new Exception("Choice is incorrect, please enter correct input");
                }
                correctInputChoice = true;
                friesCount = Integer.parseInt(friesCountInput);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        prepareFries(friesCount);
    }

    // adds sodas to the order
    public void  addSodaToOrder(){
        boolean correctInputChoice = false;
        int sodaCount = 0;
        Scanner sodaScanner = new Scanner(System.in);

        while (!correctInputChoice){
            try{
                System.out.println("How many sodas would you like to buy: ");
                String sodaCountInput = sodaScanner.next();
                if (sodaCountInput.length() > 1 || !sodaCountInput.matches("\\d+")  || sodaCountInput.equals("0")){
                    throw new Exception("Choice is incorrect, please enter correct input");
                }
                correctInputChoice = true;
                sodaCount = Integer.parseInt(sodaCountInput);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        Soda soda = new Soda(sodaCount);

        //adds soda instance to orders arrayList
        ArrayList<Food> sodaList = new ArrayList<Food>();
        sodaList.add(soda);
        this.orderList.add(sodaList);
    }

    //computes cost and time for the order
    public void computeCostAndTime(){
        Double totalCost = computeCost();
        Integer totalTime = computeTime();
        printTotal(totalCost, totalTime);
    }

    //adds meal to order
    public void addMealToOrder(){
        boolean correctInputChoice = false;
        Scanner mealScanner = new Scanner(System.in);
        int mealCount = 0;

        while (!correctInputChoice){
            try{
                System.out.println("How many meals would you like to buy: ");
                String mealCountInput = mealScanner.next();
                if (mealCountInput.length() > 1 || !mealCountInput.matches("\\d+") || mealCountInput.equals("0")){
                    throw new Exception("Choice is incorrect, please enter correct input");
                }
                correctInputChoice = true;
                mealCount = Integer.parseInt(mealCountInput);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        Meal meal = new Meal(mealCount);

        Burrito burrito = new Burrito(mealCount);
        ArrayList<Fries> friesToBeAdded = prepareFriesForMeal(mealCount);
        Soda soda = new Soda(mealCount);

        meal.setBurrito(burrito);
        meal.setFries(friesToBeAdded);
        meal.setSoda(soda);

        ArrayList<Food> mealList = new ArrayList<Food>();
        mealList.add(meal);
        this.orderList.add(mealList);
    }

    //prepares fries, checks if any fries in tray else prepares fries
    private void prepareFries(int friesCount){
        int sizeForFriesTray = Restaurant.friesTray.getFriesTray().size();
        ArrayList<Food> friesToBeAdded = new ArrayList<Food>();

        //checks if fries tray contains any fries
        if (sizeForFriesTray < friesCount){
            Integer counterForFriesToBeAdded = 0;
            System.out.println("Cooking fries; please be patient");
            for (int i = 0; i < 5*(Math.ceil((double)friesCount/5)); i ++){
                if (counterForFriesToBeAdded == friesCount){
                    Fries fries = new Fries();
                    Restaurant.friesTray.getFriesTray().add(fries);
                } else {
                    Fries fries = new Fries( friesCount);
                    Restaurant.friesTray.getFriesTray().add(fries);
                    counterForFriesToBeAdded++;
                }
            }
            for (int i = 0; i < friesCount; i++){
                Fries friesCostToBeModifiedInstance = Restaurant.friesTray.getFriesTray().get(i);
                friesCostToBeModifiedInstance.setNumberOfItemPerOrder(friesCount, false);
                friesToBeAdded.add(friesCostToBeModifiedInstance);
            }
        }else {
            for (int i = 0; i < friesCount; i++){
                Fries friesCostToBeModifiedInstance = Restaurant.friesTray.getFriesTray().get(i);
                friesCostToBeModifiedInstance.setNumberOfItemPerOrder(friesCount, true);
                friesToBeAdded.add(friesCostToBeModifiedInstance);
            }
        }

        Restaurant.friesTray.getFriesTray().subList(0, friesCount).clear();
        this.orderList.add(friesToBeAdded);
    }

    // computes time for the individual order
    private Integer computeTime(){
        Integer totalTime = 0;
        for(ArrayList<Food> foodItem: orderList){
            Food food = foodItem.get(0);
            if (food instanceof Burrito){
                Integer time = ((Burrito) food).getTimeTakenByNumberOfItemPerOrder();
                if (time > totalTime){
                    totalTime = time;
                }
            } else if (food instanceof Fries){
                Integer time = ((Fries) food).getTimeTakenByNumberOfItemPerOrder();
                if (time > totalTime){
                    totalTime = time;
                }
            } else if (food instanceof Meal) {
                Integer time = ((Meal) food).getTime();
                Integer friesTime = ((Meal) food).getFries().get(0).getTimeTakenByNumberOfItemPerOrder();
                if (time < friesTime){
                    time = friesTime;
                }
                if (time > totalTime){
                    totalTime = time;
                }
            } else {
                Integer time = ((Soda) food).getTimeTakenByNumberOfItemPerOrder();
                if (time > totalTime){
                    totalTime = time;
                }
            }
        }
        return totalTime;
    }

    // computes cost for the individual order
    private Double computeCost() {
        Double totalCost = 0.0;

        for (ArrayList<Food> foodItem : orderList) {
            Food food = foodItem.get(0);
            if (food instanceof Burrito) {
                Double cost = ((Burrito) food).getCostByNumberOfItemPerOrder();
                totalCost += cost;
            } else if (food instanceof Fries) {
                Double cost = ((Fries) food).getCostByNumberOfItemPerOrder();
                totalCost += cost;
            } else if (food instanceof Meal) {
                Double cost = ((Meal) food).getCost();
                totalCost += cost;
            } else {
                Double cost = ((Soda) food).getCostByNumberOfItemPerOrder();
                totalCost += cost;
            }
        }
        return totalCost;
    }

    // dynamic function to print the bill according to the customers order list
    private void printTotal(Double totalCost, Integer totalTime){
        int burritoCounter = 0;
        int friesCounter = 0;
        int sodaCounter = 0;

        for (ArrayList<Food> foodItem : orderList) {
            Food food = foodItem.get(0);
            if (food instanceof Burrito) {
                Integer burritoCount = ((Burrito) food).getNumberOfItemPerOrder();
                burritoCounter += burritoCount;
            } else if (food instanceof Fries) {
                Integer friesCount = ((Fries) food).getNumberOfItemPerOrder();
                friesCounter += friesCount;
            } else if (food instanceof Meal){
                Integer mealCount = ((Meal) food).getNumberOfItemsPerBatch();
                burritoCounter += mealCount;
                friesCounter += mealCount;
                sodaCounter += mealCount;
            }
            else {
                Integer sodaCount = ((Soda) food).getNumberOfItemPerOrder();
                sodaCounter += sodaCount;
            }
        }

        if (sodaCounter == 0 && friesCounter == 0 && burritoCounter != 0){
            String burritoNoun = burritoCounter == 1 ? "Burrito" : "Burritos";
            System.out.println("Total for " + burritoCounter + " " + burritoNoun + " is $" + totalCost);
        } else if (burritoCounter == 0 && friesCounter == 0 && sodaCounter != 0) {
            String sodaNoun = sodaCounter == 1 ? "soda" : "sodas";
            System.out.println("Total for " + sodaCounter + " " + sodaNoun + " is $" + totalCost);
        } else if (sodaCounter == 0 && burritoCounter == 0 && friesCounter != 0){
            String friesNoun = friesCounter == 1 ? "fry" : "fries";
            System.out.println("Total for " + friesCounter + " " + friesNoun + " is $" + totalCost);
        } else if (sodaCounter == 0 && friesCounter != 0 && burritoCounter != 0) {
            String burritoNoun = burritoCounter == 1 ? "Burrito" : "Burritos";
            String friesNoun = friesCounter == 1 ? "fry" : "fries";
            System.out.println("Total for " + burritoCounter + " " + burritoNoun + " and " + friesCounter + " " + friesNoun + " is $" + totalCost);
        } else if (burritoCounter == 0 && friesCounter != 0 && sodaCounter != 0) {
            String sodaNoun = sodaCounter == 1 ? "soda" : "sodas";
            String friesNoun = friesCounter == 1 ? "fry" : "fries";
            System.out.println("Total for " + sodaCounter + " " + sodaNoun + " and " + friesCounter + " " + friesNoun + " is $" + totalCost);
        } else if ( friesCounter == 0 && burritoCounter != 0 && sodaCounter != 0) {
            String sodaNoun = sodaCounter == 1 ? "soda" : "sodas";
            String burritoNoun = burritoCounter == 1 ? "Burrito" : "Burritos";
            System.out.println("Total for " + sodaCounter + " " + sodaNoun + " and " + burritoCounter + " " + burritoNoun + " is $" + totalCost);
        } else if (burritoCounter == 0 && sodaCounter == 0 && friesCounter == 0) {
            System.out.println("You haven't ordered anything");
            return;
        } else {
            String sodaNoun = sodaCounter == 1 ? "soda" : "sodas";
            String burritoNoun = burritoCounter == 1 ? "Burrito" : "Burritos";
            String friesNoun = friesCounter == 1 ? "fry" : "fries";
            System.out.println("Total for " + sodaCounter + " " + sodaNoun + ", " + friesCounter + " "  + friesNoun + " and " + burritoCounter + " " + burritoNoun + " is $" + totalCost);
        }
        handlePayment(totalCost, totalTime);
    }

    // function which handles payment
    private void handlePayment(Double totalCost, Integer totalTime) {
        Scanner paymentScanner = new Scanner(System.in);

        String enteredAmountInput = "";
        Double enteredAmount = 0.0;
        boolean correctNumberInput = false;
        while (!correctNumberInput){
            System.out.print("Please enter money: ");
            try {
                enteredAmountInput = paymentScanner.nextLine();

                enteredAmount = Double.parseDouble(enteredAmountInput);

//                if (enteredAmountInput.matches("[a-zA-Z]")){
//                    throw new Exception("Choice is incorrect, please enter correct input");
//                }
//                correctNumberInput = true;
            } catch (Exception ex){
                System.out.println("Choice is incorrect, please enter correct input");
            }

            if(enteredAmount < totalCost){
                System.out.println("Sorry, that’s not enough to pay for order");
                continue;
            }

            int changeAmount = Math.abs((int) (totalCost - enteredAmount));
            System.out.println("Change returned " + changeAmount);
            printTime(totalTime);
            break;
        }

    }

    // prints time
    private void printTime(Integer totalTime) {
        System.out.println("Time taken: " + totalTime + " minutes");
    }

    // function which prepares fries for meal
    private ArrayList<Fries> prepareFriesForMeal(int friesCount){
        int sizeForFriesTray = Restaurant.friesTray.getFriesTray().size();
        ArrayList<Fries> friesToBeAdded = new ArrayList<Fries>();

        //checks if fries tray contains any fries
        if (sizeForFriesTray < friesCount){
            Integer counterForFriesToBeAdded = 0;
            System.out.println("Cooking fries; please be patient");
            for (int i = 0; i < 5*(Math.ceil((double)friesCount/5)); i ++){
                if (counterForFriesToBeAdded == friesCount){
                    Fries fries = new Fries();
                    Restaurant.friesTray.getFriesTray().add(fries);
                } else {
                    Fries fries = new Fries( friesCount);
                    Restaurant.friesTray.getFriesTray().add(fries);
                    counterForFriesToBeAdded++;
                }
            }
            for (int i = 0; i < friesCount; i++){
                Fries friesCostToBeModifiedInstance = Restaurant.friesTray.getFriesTray().get(i);
                friesCostToBeModifiedInstance.setNumberOfItemPerOrder(friesCount, false);
                friesToBeAdded.add(friesCostToBeModifiedInstance);
            }
        }else {
            for (int i = 0; i < friesCount; i++){
                Fries friesCostToBeModifiedInstance = Restaurant.friesTray.getFriesTray().get(i);
                friesCostToBeModifiedInstance.setNumberOfItemPerOrder(friesCount, true);
                friesToBeAdded.add(friesCostToBeModifiedInstance);
            }
        }

        Restaurant.friesTray.getFriesTray().subList(0, friesCount).clear();
        return friesToBeAdded;
    }

    // junit test helper function, adds nine fries to the order
    public void testAddNineFriesToOrder(){
        prepareFries(9);
    }

    // junit test helper function, adds three burritos to the order
    public void testAddThreeBurritosToOrder(){
        Burrito burrito = new Burrito(3);

        //adds burrito instance to orders arrayList
        ArrayList<Food> burritoList = new ArrayList<Food>();
        burritoList.add(burrito);
        this.orderList.add(burritoList);
    }

    // junit test helper function, adds five sodas to the order
    public void testAddFiveSodasToOrder(){

        Soda soda = new Soda(5);

        //adds soda instance to orders arrayList
        ArrayList<Food> sodaList = new ArrayList<Food>();
        sodaList.add(soda);
        this.orderList.add(sodaList);
    }

    // test helper function to add three meal to order
    public void testAddThreeMealToOrder(){
        Meal meal = new Meal(3);

        Burrito burrito = new Burrito(3);
        ArrayList<Fries> friesToBeAdded = prepareFriesForMeal(3);
        Soda soda = new Soda(3);

        meal.setBurrito(burrito);
        meal.setFries(friesToBeAdded);
        meal.setSoda(soda);

        ArrayList<Food> mealList = new ArrayList<Food>();
        mealList.add(meal);
        this.orderList.add(mealList);
    }

    // junit test helper function, helps compute time for individual order without getting user input
    public Integer testComputeTime(){
        Integer totalTime = 0;
        for(ArrayList<Food> foodItem: orderList){
            Food food = foodItem.get(0);
            if (food instanceof Burrito){
                Integer time = ((Burrito) food).getTimeTakenByNumberOfItemPerOrder();
                if (time > totalTime){
                    totalTime = time;
                }
            } else if (food instanceof Fries){
                Integer time = ((Fries) food).getTimeTakenByNumberOfItemPerOrder();
                if (time > totalTime){
                    totalTime = time;
                }
            } else if (food instanceof Meal) {
                Integer time = ((Meal) food).getTime();
                Integer friesTime = ((Meal) food).getFries().get(0).getTimeTakenByNumberOfItemPerOrder();
                if (time < friesTime){
                    time = friesTime;
                }
                if (time > totalTime){
                    totalTime = time;
                }
            } else {
                Integer time = ((Soda) food).getTimeTakenByNumberOfItemPerOrder();
                if (time > totalTime){
                    totalTime = time;
                }
            }
        }
        return totalTime;
    }

    // junit test helper function, helps compute cost for individual order without getting user input
    public Double testComputeCost() {
        Double totalCost = 0.0;

        for (ArrayList<Food> foodItem : orderList) {
            Food food = foodItem.get(0);
            if (food instanceof Burrito) {
                Double cost = ((Burrito) food).getCostByNumberOfItemPerOrder();
                totalCost += cost;
            } else if (food instanceof Fries) {
                Double cost = ((Fries) food).getCostByNumberOfItemPerOrder();
                totalCost += cost;
            } else if (food instanceof Meal) {
                Double cost = ((Meal) food).getCost();
                totalCost += cost;
            } else {
                Double cost = ((Soda) food).getCostByNumberOfItemPerOrder();
                totalCost += cost;
            }
        }
        return totalCost;
    }

}
