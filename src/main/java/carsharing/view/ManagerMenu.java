package carsharing.view;

import carsharing.service.ManagerService;

import java.util.Scanner;

/**
 * The ManagerMenu class represents the user interface for the manager operations in the car sharing application.
 * It provides methods for displaying menus and interacting with the manager.
 */
public class ManagerMenu {
    /**
     * Displays the manager menu options and prompts the manager for input.
     * Based on the manager's choice, performs the corresponding action:
     * - Display the company list
     * - Create a new company
     * - Go back to the previous menu
     * Recursively calls itself if an invalid input is provided.
     */
    public static void show() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");

        char action = scanner.nextLine()
                .trim()
                .charAt(0);

        switch (action) {
            case '0' -> ManagerService.isLoggedIn(false);
            case '1' -> printCompanyList();
            case '2' -> addCompany();
            default ->  show();
        }

        scanner.close();
    }

    /**
     * Prints the list of companies.
     * If the company list is empty, prompts the manager to go back to the previous menu.
     * Otherwise, prompts the manager to enter the company ID to show its details.
     * Recursively calls itself if an invalid company ID is provided.
     */
    public static void printCompanyList() {
        if (ManagerService.isCompanyTableEmpty()) {
            System.out.println("\nThe company list is empty!");

            show();
        } else {
            ManagerService.printCompanyList();

            Scanner scanner = new Scanner(System.in);
            int companyID = scanner.nextInt();

            if (companyID == 0) {
                show();
            } else {

                if (companyID > 0 && companyID <= ManagerService.getLastCompanyID()) {
                    String company = ManagerService.getCompanyByID(companyID);
                    System.out.println("\n'" + company + "' company:");

                    showCompanyMenu(companyID);
                } else {
                    printCompanyList();
                }

            }

            scanner.close();
        }
    }

    /**
     * Displays the menu for a specific company and prompts the manager for input.
     * Based on the manager's choice, performs the corresponding action:
     * - Display the car list for the company
     * - Create a new car for the company
     * - Go back to the previous menu
     *
     * @param companyID the ID of the company
     */
    public static void showCompanyMenu(int companyID) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");

        char action = scanner.nextLine()
                .trim()
                .charAt(0);

        switch (action) {
            case '0' -> show();
            case '1' -> printCarList(companyID);
            case '2' -> addCar(companyID);
        }

        scanner.close();
    }

    /**
     * Prompts the manager to enter the name of a new company and adds it to the system.
     */
    public static void addCompany() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the company name:");
        String company = scanner.nextLine()
                .trim();

        ManagerService.addCompany(company);

        scanner.close();
    }

    /**
     * Prints the list of cars for a specific company.
     * If the car list is empty, prompts the manager to go back to the company menu.
     *
     * @param companyID the ID of the company
     */
    public static void printCarList(int companyID) {

        if (ManagerService.isCarTableEmpty() || ManagerService.isCompanyCarListEmpty(companyID)) {
            System.out.println("\nThe car list is empty!\n");

            showCompanyMenu(companyID);
        } else {
            ManagerService.printCarList(companyID);
            ManagerMenu.showCompanyMenu(companyID);
        }

    }

    /**
     * Prompts the manager to enter the name of a new car for a specific company and adds it to the system.
     *
     * @param companyID the ID of the company
     */
    public static void addCar(int companyID) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter the car name:");
        String car = scanner.nextLine()
                .trim();

        ManagerService.addCar(car, companyID);
        showCompanyMenu(companyID);

        scanner.close();
    }
}
