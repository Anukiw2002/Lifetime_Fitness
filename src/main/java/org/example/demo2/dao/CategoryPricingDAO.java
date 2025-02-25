package org.example.demo2.dao;
import org.example.demo2.model.CategoryPricing;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPricingDAO {
    private final DBConnection dbConnection;

    public CategoryPricingDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public CategoryPricing create(CategoryPricing pricing) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO category_pricing (duration_id, category, price) VALUES (?, ?, ?) RETURNING category_pricing_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, pricing.getDurationId());
                stmt.setString(2, pricing.getCategory());
                stmt.setBigDecimal(3, pricing.getPrice());

                System.out.println("Executing SQL: " + sql);
                System.out.println("Parameters - duration_id: " + pricing.getDurationId() +
                        ", category: " + pricing.getCategory() +
                        ", price: " + pricing.getPrice());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pricing.setCategoryPricingId(rs.getLong("category_pricing_id"));
                    System.out.println("Created category pricing with ID: " + pricing.getCategoryPricingId());
                } else {
                    System.out.println("No pricing ID returned from insert");
                }
            }
            return pricing;
        } catch (SQLException e) {
            System.err.println("Error creating category pricing: " + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<CategoryPricing> findByDurationId(Long durationId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<CategoryPricing> pricings = new ArrayList<>();
            String sql = "SELECT * FROM category_pricing WHERE duration_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, durationId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    CategoryPricing pricing = new CategoryPricing();
                    pricing.setCategoryPricingId(rs.getLong("category_pricing_id"));
                    pricing.setDurationId(rs.getLong("duration_id"));
                    pricing.setCategory(rs.getString("category"));
                    pricing.setPrice(rs.getBigDecimal("price"));
                    pricings.add(pricing);
                }
            }
            return pricings;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update(CategoryPricing pricing) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "UPDATE category_pricing SET price = ? WHERE duration_id = ? AND category = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setBigDecimal(1, pricing.getPrice());
                stmt.setLong(2, pricing.getDurationId());
                stmt.setString(3, pricing.getCategory());
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(Long categoryPricingId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "DELETE FROM category_pricing WHERE category_pricing_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, categoryPricingId);
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
            String sql = "DELETE FROM category_pricing WHERE duration_id = ?";
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
