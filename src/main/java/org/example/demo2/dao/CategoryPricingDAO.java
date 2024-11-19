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

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pricing.setCategoryPricingId(rs.getLong("category_pricing_id"));
                }
            }
            return pricing;
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
}
