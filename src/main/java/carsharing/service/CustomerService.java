package carsharing.service;

import carsharing.dao.CustomerTable;
import carsharing.view.CustomerMenu;
import carsharing.view.Menu;

import java.util.Map;

/**
 * The CustomerService class provides methods for managing customers and their rented cars in the car sharing application.
 * It interacts with the DAO classes to perform CRUD operations on customer and car data.
 */
public class CustomerService {
    /**
     * Creates a customer table by initializing the CustomerTable.
     */
    public static void createCustomer() {
        CustomerTable.create();
    }
    /**
     * Adds a new customer to the customer table.
     *
     * @param name the name of the customer to add
     */
    public static void add(String name) {
        CustomerTable.add(name);
    }
    /**
     * Returns the ID of the last customer in the customer table.
     *
     * @return the last customer ID
     */
    public static int getLastCustomerID() {
        return CustomerTable.checkID();
    }
    /**
     * Prints the list of customers from the customer table.
     */
    public static void printCustomerList() {
        CustomerTable.print();
    }

    /**
     * Rents a car for the specified customer.
     *
     * @param customerID   the ID of the customer
     * @param rentedCarID  the ID of the rented car
     */
    public static void rentCar(int customerID, int rentedCarID) {
        CustomerTable.addCarToCustomer(customerID, rentedCarID);
    }
    /**
     * Returns a rented car for the specified customer.
     *
     * @param customerID  the ID of the customer
     */
    public static void returnCar(int customerID) {
        CustomerTable.delete(customerID);
    }
    /**
     * Prints the list of available cars for a specific company and returns the car list.
     *
     * @param companyID  the ID of the company
     * @return a map containing the available cars with their corresponding IDs
     */
    public static Map<Integer, Integer> printCarListOfCompany(int companyID) {
        return CustomerTable.printCarListOfCompany(companyID);
    }
    /**
     * Prints the list of cars rented by the specified customer.
     *
     * @param customerID  the ID of the customer
     */
    public static void printCarListOfCustomer(int customerID) {
        CustomerTable.printCarListOfCustomer(customerID);
    }

    /**
     * Checks if the customer table is empty.
     *
     * @return true if the customer table is empty, false otherwise
     */
    public static boolean isCustomerTableEmpty() {
        return CustomerTable.isEmpty();
    }
    /**
     * Checks if the car list for the specified customer is empty.
     *
     * @param customerID  the ID of the customer
     * @return true if the car list is empty for the given customer, false otherwise
     */
    public static boolean isCustomerCarListEmpty(int customerID) {
        return CustomerTable.isCustomerCarListEmpty(customerID);
    }
    /**
     * Displays the customer menu if the user is logged in as a customer, or the main menu otherwise.
     *
     * @param loggedIn true if the user is logged in as a customer, false otherwise
     */
    public static void isLoggedIn(boolean loggedIn) {
        if (loggedIn) {
            CustomerMenu.printCustomerList();
        } else {
            Menu.show();
        }
    }
}
