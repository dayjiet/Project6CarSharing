package carsharing.service;

import carsharing.dao.CarTable;
import carsharing.dao.CompanyTable;
import carsharing.view.ManagerMenu;
import carsharing.view.Menu;

/**
 * The ManagerService class provides methods for managing companies and cars in the car sharing application.
 * It interacts with the DAO classes to perform CRUD operations on company and car data.
 */
public class ManagerService {
    /**
     * Creates a company table by initializing the CompanyTable.
     *
     * @param fileName the name of the file to store the company data
     */
    public static void createCompany(String fileName) {
        CompanyTable.create(fileName);
    }
    /**
     * Adds a new company to the company table.
     * After adding the company, displays the manager menu.
     *
     * @param company the name of the company to add
     */
    public static void addCompany(String company) {
        CompanyTable.add(company);
        ManagerMenu.show();
    }
    /**
     * Prints the list of companies from the company table.
     */
    public static void printCompanyList() {
        CompanyTable.print();
    }
    /**
     * Retrieves the company name based on the given company ID.
     *
     * @param companyID the ID of the company
     * @return the name of the company
     */
    public static String getCompanyByID(int companyID) {
        return CompanyTable.getCompany(companyID);
    }
    /**
     * Returns the ID of the last company in the company table.
     *
     * @return the last company ID
     */
    public static int getLastCompanyID() {
        return CompanyTable.checkID();
    }
    /**
     * Prints the list of cars for a specific company.
     *
     * @param companyID the ID of the company
     */
    public static void printCarList(int companyID) {
        CarTable.print(companyID);
    }

    /**
     * Creates a car table by initializing the CarTable.
     */
    public static void createCar() {
        CarTable.create();
    }
    /**
     * Adds a new car to the car table for a specific company.
     *
     * @param car       the name of the car to add
     * @param companyID the ID of the company
     */
    public static void addCar(String car, int companyID) {
        CarTable.add(car, companyID);
    }

    /**
     * Checks if the company table is empty.
     *
     * @return true if the company table is empty, false otherwise
     */
    public static boolean isCompanyTableEmpty() {
        return CompanyTable.isEmpty();
    }
    /**
     * Checks if the car list for a specific company is empty.
     *
     * @param companyID the ID of the company
     * @return true if the car list is empty for the given company, false otherwise
     */
    public static boolean isCompanyCarListEmpty(int companyID) {
        return CarTable.isCompanyCarListEmpty(companyID);
    }
    /**
     * Checks if the car table is empty.
     *
     * @return true if the car table is empty, false otherwise
     */
    public static boolean isCarTableEmpty() {
        return CarTable.isEmpty();
    }
    /**
     * Displays the manager menu if the user is logged in, or the main menu otherwise.
     *
     * @param loggedIn true if the user is logged in as a manager, false otherwise
     */
    public static void isLoggedIn(boolean loggedIn) {
        switch (loggedIn ? "true" : "false") {
            case "true" -> ManagerMenu.show();
            case "false" -> Menu.show();
        }
    }
}
