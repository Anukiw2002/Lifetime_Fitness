package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {

    public void saveOrder(String orderId, Integer userId, String buyerName, String planName, BigDecimal amount) throws SQLException {
        String sql = "INSERT INTO orders (order_id, user_id, buyer_name, plan_name, amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderId);
            stmt.setInt(2, userId);
            stmt.setString(3, buyerName);
            stmt.setString(4, planName);
            stmt.setBigDecimal(5, amount);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // optional: rethrow if you want to handle it higher up
        }
    }


}
