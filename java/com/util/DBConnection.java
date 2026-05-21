package com.util;

// Importing Connection class to represent database connection
import java.sql.Connection;

// Importing DriverManager to create connection with MySQL database
import java.sql.DriverManager;

/**
 * DBConnection is a utility class used to connect the Java application
 * with the MySQL database.
 *
 * This class provides a reusable getConnection() method so DAO classes
 * can connect to the database whenever needed.
 */
public class DBConnection {

    // Database URL with database name
    private static final String URL = "jdbc:mysql://localhost:3306/bookyourground";

    // Database username
    private static final String USER = "root";

    // Database password
    private static final String PASSWORD = "";

    /**
     * Creates and returns a database connection.
     *
     * @return Connection object if connection is successful, otherwise null
     */
    public static Connection getConnection() {
        // Connection object initially set to null
        Connection conn = null;

        try {
            // Loads MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Creates connection using database URL, username, and password
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prints success message in console
            System.out.println("Connected to database successfully.");

        } catch (Exception e) {
            // Prints failure message if database connection fails
            System.out.println("Database connection failed.");

            // Prints full error details for debugging
            e.printStackTrace();
        }

        // Returns database connection object
        return conn;
    }
}
