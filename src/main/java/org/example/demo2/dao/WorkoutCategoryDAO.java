package org.example.demo2.dao;

import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutCategoryDAO {
    private final DBConnection dbConnection;

    public WorkoutCategoryDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public WorkoutCategory create(WorkoutCategory category) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO workout_categories (category_name) VALUES (?) RETURNING category_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, category.getCategoryName());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    category.setCategoryId(rs.getLong("category_id"));
                }
            }
            return category;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<WorkoutCategory> findAll() throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<WorkoutCategory> categories = new ArrayList<>();
            String sql = "SELECT * FROM workout_categories";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    WorkoutCategory category = new WorkoutCategory();
                    category.setCategoryId(rs.getLong("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    categories.add(category);
                }
            }
            return categories;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}