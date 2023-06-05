package carsharing.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides utility methods to interact with the H2 database for the car sharing system.
 */
public class H2Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    private static String DB_URL;

    /**
     * Creates and returns a connection to the H2 database with the specified file name.
     *
     * @param fileName the name of the database file
     * @return a connection to the H2 database
     */
    public static Connection create(String fileName) {

        DB_URL = "jdbc:h2:./src/carsharing/db/" + fileName;

        Connection connection = null;

        try {
            // Register the JDBC database driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL);

            // Set auto-commit to true
            connection.setAutoCommit(true);

            // Handle errors for JDBC and Class.forName
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Connection connect() {
        Connection connection = null;

        try {
            // Register the JDBC database driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(DB_URL);

            // Set auto-commit to true
            connection.setAutoCommit(true);

            // Handle errors for JDBC and Class.forName
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
