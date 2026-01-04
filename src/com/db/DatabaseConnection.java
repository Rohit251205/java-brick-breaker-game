package com.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection initializeDatabase() {
        if (connection != null) {
            return connection;
        }

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection URL, username and password
            String url = "jdbc:mysql://localhost:3306/javagameweb";
            String username = "root"; // Change if different
            String password = "";     // Change if you have a password

            // Create connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}