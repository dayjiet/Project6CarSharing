package carsharing;

import carsharing.service.CustomerService;
import carsharing.service.ManagerService;
import carsharing.view.Menu;

/**
 * The CarSharing class represents the main class of the car sharing application.
 * It provides methods for reading command line arguments and initializing the application.
 */
public class CarSharing {
    /**
     * Reads the command line arguments to retrieve the database file name.
     * If the file name is not provided or empty, a default file name "temp" is used.
     * Initializes the company, creates a car, creates a customer, and shows the main menu.
     *
     * @param args the command line arguments
     */
    public static void read(String[] args) {
        String fileName = "";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-databaseFileName")) {
                fileName = args[i + 1];
                break;
            }
        }

        if (fileName.isBlank() || fileName.isEmpty()) {
            fileName = "temp";
        }

        ManagerService.createCompany(fileName);
        ManagerService.createCar();
        CustomerService.createCustomer();
        Menu.show();
    }

    /**
     * The entry point of the car sharing application.
     * Calls the read method to initialize the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        read(args);
    }
}