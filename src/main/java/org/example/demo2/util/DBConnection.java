package org.example.demo2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/auth_db", "postgres", "Ishn@2002");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL driver not found.", e);
        }
    }
}