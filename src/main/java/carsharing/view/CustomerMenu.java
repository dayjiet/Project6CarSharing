package carsharing.view;

import carsharing.service.CustomerService;
import carsharing.service.ManagerService;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

/**
 * The CustomerMenu class represents the user interface for the customer operations in the car sharing application.
 * It provides methods for displaying menus and interacting with the customer.
 */
public class CustomerMenu {
    /**
     * Prints the list of customers.
     * If the customer list is empty, prompts the user to go back to the previous menu.
     * Otherwise, prompts the user to enter the customer ID to show its details.
     * Recursively calls itself if an invalid customer ID is provided.
     */
    public static void printCustomerList() {

        if (CustomerService.isCustomerTableEmpty()) {
            System.out.println("\nThe customer list is empty!");

            Menu.show();
        } else {
            CustomerService.printCustomerList();

            Scanner scanner = new Scanner(System.in);
            int customerID = scanner.nextInt();

            if (customerID == 0) {
                Menu.show();
            } else {
                if (customerID > 0 && customerID <= CustomerService.getLastCustomerID()) {
                    show(customerID);
                } else {
                    printCustomerList();
                }
            }

            scanner.close();
        }

    }

    /**
     * Displays the menu for a specific customer and prompts the customer for input.
     * Based on the customer's choice, performs the corresponding action:
     * - Rent a car
     * - Return a rented car
     * - Display the rented car
     * - Go back to the previous menu
     *
     * @param customerID the ID of the customer
     */
    public static void show(int customerID) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");

        char action = scanner.nextLine()
                .trim()
                .charAt(0);

        switch (action) {
            case '0' -> Menu.show();
            case '1' -> rentCar(customerID);
            case '2' -> returnCar(customerID);
            case '3' -> showCustomerCar(customerID);
        }

        scanner.close();
    }

    /**
     * Prompts the user to enter the name of a new customer and adds it to the system.
     */
    public static void add() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the customer name:");
        String name = scanner.nextLine()
                .trim();

        CustomerService.add(name);
        Menu.show();

        scanner.close();
    }

    /**
     * Allows the customer to rent a car.
     * Displays the list of available companies and prompts the customer to select a company.
     * If the selected company has no available cars, prompts the customer to make another choice.
     * Once the customer selects a car, the car is rented and the customer menu is shown.
     *
     * @param customerID the ID of the customer
     */
    public static void rentCar(int customerID) {

        if (!CustomerService.isCustomerCarListEmpty(customerID)) {
            System.out.println("\nYou've already rented a car!");

            show(customerID);
        } else {

            if (ManagerService.isCompanyTableEmpty()) {
                System.out.println("\nThe company list is empty!");

                show(customerID);
            } else {
                Scanner scanner = new Scanner(System.in);

                ManagerService.printCompanyList();

                int companyID = scanner.nextInt();

                if (companyID == 0) {
                    show(customerID);
                } else {
                    if (companyID > 0 && companyID <= ManagerService.getLastCompanyID()) {

                        String company = ManagerService.getCompanyByID(companyID);

                        if (ManagerService.isCarTableEmpty() || ManagerService.isCompanyCarListEmpty(companyID)) {
                            System.out.println("\nNo available cars in the '" + company + "' company\n");

                            rentCar(customerID);
                        } else {
                            Map<Integer, Integer> carList;

                            carList = CustomerService.printCarListOfCompany(companyID);

                            int carToRent = scanner.nextInt();

                            if (carToRent == 0) {
                                rentCar(customerID);
                            } else {
                                if (carToRent > 0 && carToRent <= Collections.max(carList.keySet())) {
                                    int rentedCarID = carList.get(carToRent);

                                    CustomerService.rentCar(customerID, rentedCarID);
                                    show(companyID);
                                } else {
                                    rentCar(customerID);
                                }
                            }
                        }

                    } else {
                        rentCar(customerID);
                    }
                }

                scanner.close();
            }

        }

    }

    /**
     * Allows the customer to return a rented car.
     * If the customer hasn't rented a car yet, displays a message and shows the customer menu.
     * Otherwise, returns the car and shows the customer menu.
     *
     * @param customerID the ID of the customer
     */
    public static void returnCar(int customerID) {
        if (CustomerService.isCustomerTableEmpty() || CustomerService.isCustomerCarListEmpty(customerID)) {
            System.out.println("\nYou didn't rent a car!");

            show(customerID);
        } else {
            CustomerService.returnCar(customerID);
            show(customerID);
        }
    }

    /**
     * Displays the list of cars rented by a specific customer.
     * If the customer hasn't rented a car, displays a message and shows the customer menu.
     * Otherwise, prints the list of rented cars and shows the customer menu.
     *
     * @param customerID the ID of the customer
     */
    public static void showCustomerCar(int customerID) {
        if (CustomerService.isCustomerTableEmpty() || CustomerService.isCustomerCarListEmpty(customerID)) {
            System.out.println("\nYou didn't rent a car!");

            show(customerID);
        } else {
            CustomerService.printCarListOfCustomer(customerID);
            show(customerID);
        }
    }
}
