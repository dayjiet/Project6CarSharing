package carsharing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CompanyTable class provides methods for interacting with the COMPANY table in the database.
 * It handles the creation, retrieval, and modification of company data.
 */
public class CompanyTable {
    /**
     * Creates the COMPANY table if it doesn't exist in the database.
     *
     * @param fileName the name of the database file
     */
    public static void create(String fileName) {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(ID IDENTITY PRIMARY KEY, " +
                "NAME VARCHAR_IGNORECASE NOT NULL UNIQUE" +
                ")";

        try (Connection connection = H2Database.create(fileName);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the COMPANY table is empty.
     *
     * @return true if the table is empty, false otherwise
     */
    public static boolean isEmpty() {
        boolean isEmpty = false;

        String sqlQuery = "SELECT * FROM COMPANY";

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
     * Prints the list of companies from the COMPANY table.
     */
    public static void print() {
        String sqlQuery = "SELECT * FROM COMPANY";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("\nChoose a company:");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String company = resultSet.getString("NAME");

                System.out.println(id + ". " + company);
            }

            System.out.println("0. Back");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new company to the COMPANY table.
     *
     * @param company the name of the company to add
     */
    public static void add(String company) {
        String sqlQuery = "INSERT INTO COMPANY (NAME) VALUES (?)";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, company);
            preparedStatement.executeUpdate();

            System.out.println("The company was created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the ID of the last company in the COMPANY table.
     *
     * @return the ID of the last company
     */
    public static int checkID() {
        String sqlQuery = "SELECT ID FROM COMPANY ORDER BY ID DESC LIMIT 1";

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

    /**
     * Retrieves the name of a company based on its ID from the COMPANY table.
     *
     * @param companyID the ID of the company
     * @return the name of the company
     */
    public static String getCompany(int companyID) {
        String company = "";

        String sqlQuery = "SELECT * FROM COMPANY";

        try (Connection connection = H2Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");

                if (companyID == id) {
                    company = resultSet.getString("NAME");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return company;
    }
}
