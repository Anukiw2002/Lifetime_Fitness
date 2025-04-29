package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;

public class OrderDAO {

    public void saveOrder(String orderId, Integer userId, String buyerName, String planName, BigDecimal amount, Integer durationId) throws SQLException {
        String sql = "INSERT INTO orders (order_id, user_id, buyer_name, plan_name, amount, duration_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderId);
            stmt.setInt(2, userId);
            stmt.setString(3, buyerName);
            stmt.setString(4, planName);
            stmt.setBigDecimal(5, amount);
            stmt.setInt(6, durationId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public int getDurationId() throws SQLException {
        int durationId = -1;
        String query = "SELECT duration_id FROM durations WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                durationId = rs.getInt("duration_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return durationId;
    }
}