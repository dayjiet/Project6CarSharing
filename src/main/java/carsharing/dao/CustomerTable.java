package carsharing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The CustomerTable class provides methods for interacting with the CUSTOMER table in the database.
 * It handles the creation, retrieval, and modification of customer data.
 */
public class CustomerTable {
    /**
     * Creates the CUSTOMER table if it doesn't exist in the database.
     */
    public static void create() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                "(ID IDENTITY PRIMARY KEY, " +
                "NAME VARCHAR_IGNORECASE NOT NULL UNIQUE, " +
                "RENTED_CAR_ID INT DEFAULT NULL, " +
                "CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                ")";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new customer to the CUSTOMER table.
     *
     * @param name the name of the customer to add
     */
    public static void add(String name) {
        String sqlQuery = "INSERT INTO CUSTOMER (NAME) VALUES (?)";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            System.out.println("The customer was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a rented car to a customer in the CUSTOMER table.
     *
     * @param customerID  the ID of the customer
     * @param rentedCarID the ID of the rented car
     */
    public static void addCarToCustomer(int customerID, int rentedCarID) {
        String sqlCustomerTableQuery = "UPDATE CUSTOMER " +
                "SET RENTED_CAR_ID = ? " +
                "WHERE ID = ?";

        String sqlCarTableQuery = "SELECT * FROM CAR " +
                "WHERE ID = ?";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedCustomerTableStatement = connection.prepareStatement(sqlCustomerTableQuery);
             PreparedStatement preparedCarTableStatement = connection.prepareStatement(sqlCarTableQuery)) {

            preparedCustomerTableStatement.setInt(1, rentedCarID);
            preparedCustomerTableStatement.setInt(2, customerID);
            preparedCustomerTableStatement.executeUpdate();

            preparedCarTableStatement.setInt(1, rentedCarID);
            ResultSet carTableResultSet = preparedCarTableStatement.executeQuery();

            if (carTableResultSet.next()) {
                String carName = carTableResultSet.getString("NAME");

                System.out.println("\nYou rented '" + carName + "'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the rented car from a customer in the CUSTOMER table.
     *
     * @param customerID the ID of the customer
     */
    public static void delete(int customerID) {
        String sqlQuery = "UPDATE CUSTOMER " +
                "SET RENTED_CAR_ID = NULL " +
                "WHERE ID = ?";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.executeUpdate();

            System.out.println("\nYou've returned a rented car!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the CUSTOMER table is empty.
     *
     * @return true if the table is empty, false otherwise
     */
    public static boolean isEmpty() {
        boolean isEmpty = false;

        String sqlQuery = "SELECT * FROM CUSTOMER";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (!resultSet.next()) {
                isEmpty = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    /**
     * Checks if the car list of a customer is empty.
     *
     * @param customerID the ID of the customer
     * @return true if the customer's car list is empty, false otherwise
     */
    public static boolean isCustomerCarListEmpty(int customerID) {
        String sqlQuery = "SELECT * FROM CUSTOMER " +
                "WHERE ID = ? AND RENTED_CAR_ID IS NULL";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, customerID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Prints the list of customers.
     */
    public static void print() {
        String sqlQuery = "SELECT * FROM CUSTOMER";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("\nCustomer list:");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String customer = resultSet.getString("NAME");

                System.out.println(id + ". " + customer);
            }

            System.out.println("0. Back");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the list of cars available for a specific company and returns a map of car IDs.
     *
     * @param companyID the ID of the company
     * @return a map of car IDs and their corresponding index in the list
     */
    public static Map<Integer, Integer> printCarListOfCompany(int companyID) {
        Map<Integer, Integer> carList = new HashMap<>();

        String sqlQuery = "SELECT c.ID, c.NAME FROM CAR c LEFT JOIN CUSTOMER cust ON c.ID = cust.RENTED_CAR_ID WHERE c.COMPANY_ID = ? AND cust.RENTED_CAR_ID IS NULL";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, companyID);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\nChoose a car:");

            int count = 0;

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String car = resultSet.getString("NAME");

                count++;

                carList.put(count, id);

                System.out.println(count + ". " + car);
            }

            System.out.println("0. Back");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    /**
     * Prints the rented car details of a customer.
     *
     * @param customerID the ID of the customer
     */
    public static void printCarListOfCustomer(int customerID) {
        String sqlCustomerTableQuery = "SELECT * FROM CUSTOMER WHERE ID = ?";
        String sqlCarTableQuery = "SELECT * FROM CAR WHERE ID = ?";
        String sqlCompanyTableQuery = "SELECT * FROM COMPANY WHERE ID = ?";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedCustomerTableStatement = connection.prepareStatement(sqlCustomerTableQuery);
             PreparedStatement preparedCarTableStatement = connection.prepareStatement(sqlCarTableQuery);
             PreparedStatement preparedCompanyTableStatement = connection.prepareStatement(sqlCompanyTableQuery)) {

            preparedCustomerTableStatement.setInt(1, customerID);
            ResultSet customerTableResultSet = preparedCustomerTableStatement.executeQuery();

            if (customerTableResultSet.next()) {
                System.out.println("\nYour rented car:");
                int rented_car_id = customerTableResultSet.getInt("RENTED_CAR_ID");

                preparedCarTableStatement.setInt(1, rented_car_id);
                ResultSet carTableResultSet = preparedCarTableStatement.executeQuery();

                if (carTableResultSet.next()) {
                    String carName = carTableResultSet.getString("NAME");
                    System.out.println(carName);
                    int companyID = carTableResultSet.getInt("COMPANY_ID");
                    System.out.println("Company:");

                    preparedCompanyTableStatement.setInt(1, companyID);
                    ResultSet companyTableResultSet = preparedCompanyTableStatement.executeQuery();

                    if (companyTableResultSet.next()) {
                        String companyName = companyTableResultSet.getString("NAME");
                        System.out.println(companyName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the highest ID in the CUSTOMER table.
     *
     * @return the highest ID value
     */
    public static int checkID() {
        String sqlQuery = "SELECT ID FROM CUSTOMER ORDER BY ID DESC LIMIT 1";

        int id = 0;

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                id = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
