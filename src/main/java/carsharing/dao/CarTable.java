package carsharing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents the data access object for the Car table in the car sharing system.
 */
public class CarTable {
    /**
     * Creates the CAR table if it doesn't already exist.
     */
    public static void create() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS CAR " +
                "(ID IDENTITY PRIMARY KEY, " +
                "NAME VARCHAR_IGNORECASE NOT NULL UNIQUE, " +
                "COMPANY_ID INT NOT NULL, " +
                "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                ")";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new car to the CAR table with the given name and company ID.
     *
     * @param car       the name of the car
     * @param companyID the ID of the company
     */
    public static void add(String car, int companyID) {
        String sqlQuery = "INSERT INTO CAR (NAME,COMPANY_ID) VALUES (?,?)";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, car);
            preparedStatement.setInt(2, companyID);
            preparedStatement.executeUpdate();

            System.out.println("The car was added!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the CAR table is empty.
     *
     * @return true if the CAR table is empty, false otherwise
     */
    public static boolean isEmpty() {
        boolean isEmpty = false;

        String sqlQuery = "SELECT * FROM CAR";

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
     * Checks if the car list of a company is empty.
     *
     * @param companyID the ID of the company
     * @return true if the company's car list is empty, false otherwise
     */
    public static boolean isCompanyCarListEmpty(int companyID) {
        boolean isEmpty = true;

        String sqlQuery = "SELECT * FROM CAR";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                int company_id = resultSet.getInt("COMPANY_ID");

                if (companyID == company_id) {
                    isEmpty = false;
                    break;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    /**
     * Prints the list of cars for a specific company.
     *
     * @param companyID the ID of the company
     */
    public static void print(int companyID) {
        String sqlQuery = "SELECT * FROM CAR";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("\nCar list:");

            int count = 0;

            while (resultSet.next()){
                String car = resultSet.getString("NAME");
                int company_id = resultSet.getInt("COMPANY_ID");

                if (companyID == company_id) {
                    count++;

                    System.out.println(count + ". " + car);
                }
            }

            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
