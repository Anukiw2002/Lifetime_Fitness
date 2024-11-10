package org.example.demo2.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CalenderDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/auth_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Ishn@2002";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw new SQLException("Database connection error", e);  // Rethrow the exception for higher-level handling
        }
    }
}
