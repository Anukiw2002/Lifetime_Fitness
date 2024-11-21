package org.example.demo2.dao;

import org.example.demo2.model.UniformPricing;
import org.example.demo2.util.DBConnection;
import java.sql.*;

public class UniformPricingDAO {
    private final DBConnection dbConnection;

    public UniformPricingDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public UniformPricing create(UniformPricing pricing) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO uniform_pricing (duration_id, price) VALUES (?, ?) RETURNING pricing_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, pricing.getDurationId());
                stmt.setBigDecimal(2, pricing.getPrice());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pricing.setPricingId(rs.getLong("pricing_id"));
                }
            }
            return pricing;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public UniformPricing findByDurationId(Long durationId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT * FROM uniform_pricing WHERE duration_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, durationId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    UniformPricing pricing = new UniformPricing();
                    pricing.setPricingId(rs.getLong("pricing_id"));
                    pricing.setDurationId(rs.getLong("duration_id"));
                    pricing.setPrice(rs.getBigDecimal("price"));
                    return pricing;
                }
            }
            return null;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}