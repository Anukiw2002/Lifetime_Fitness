package org.example.demo2;

import java.sql.Connection;
import java.sql.SQLException;
import org.example.demo2.util.DBConnection;

public class test{

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Database connected successfully!");
            } else {
                System.out.println("Failed to connect to database.");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
