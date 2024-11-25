package org.example.demo2.servlet;

import org.example.demo2.model.BlogModel;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogController {

    // Existing method to fetch all blogs
    public static List<BlogModel> getAllBlogs() {
        List<BlogModel> blogs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to the database.");
            }

            String query = "SELECT id, name, description, link FROM blogs";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    BlogModel blog = new BlogModel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("link")
                    );
                    blogs.add(blog);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching blogs: " + e.getMessage());
        }

        return blogs;
    }

    // New method to update blog data
    public static boolean updateBlog(int id, String name, String description, String link) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to the database.");
            }

            // SQL query to update the blog data
            String query = "UPDATE blogs SET name = ?, description = ?, link = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set parameters for the prepared statement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, link);
                preparedStatement.setInt(4, id);

                // Execute the update query
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;  // Return true if the update was successful
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating blog: " + e.getMessage());
        }
    }
}
