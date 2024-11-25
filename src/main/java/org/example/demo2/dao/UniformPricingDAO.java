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

                System.out.println("Executing SQL: " + sql);
                System.out.println("Parameters - duration_id: " + pricing.getDurationId() + ", price: " + pricing.getPrice());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pricing.setPricingId(rs.getLong("pricing_id"));
                    System.out.println("Created uniform pricing with ID: " + pricing.getPricingId());
                } else {
                    System.out.println("No pricing ID returned from insert");
                }
            }
            return pricing;
        } catch (SQLException e) {
            System.err.println("Error creating uniform pricing: " + e.getMessage());
            throw e;
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

    public void update(UniformPricing pricing) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "UPDATE uniform_pricing SET price = ? WHERE duration_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setBigDecimal(1, pricing.getPrice());
                stmt.setLong(2, pricing.getDurationId());
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    public void delete(Long pricingId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "DELETE FROM uniform_pricing WHERE pricing_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, pricingId);
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    public void deleteByDurationId(Long durationId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "DELETE FROM uniform_pricing WHERE duration_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, durationId);
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
