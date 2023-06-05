package carsharing.view;

import carsharing.service.CustomerService;
import carsharing.service.ManagerService;

import java.util.Scanner;

/**
 * The Menu class represents the user interface of the car sharing application.
 * It provides methods for displaying menus and interacting with the user.
 */
public class Menu {
    /**
     * Displays the main menu options and prompts the user for input.
     * Based on the user's choice, performs the corresponding action:
     * - Log in as a manager
     * - Log in as a customer
     * - Create a customer
     * - Exit the application
     * Recursively calls itself if an invalid input is provided.
     */
    public static void show() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");

        char action = scanner.nextLine()
                .trim()
                .charAt(0);

        switch (action) {
            case '0' -> {
                return;
            }
            case '1' -> ManagerService.isLoggedIn(true);
            case '2' -> CustomerService.isLoggedIn(true);
            case '3' -> CustomerMenu.add();
            default -> show();
        }

        scanner.close();
    }
}
