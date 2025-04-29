package org.example.demo2.servlet;

import org.example.demo2.model.BlogModel;
import org.example.demo2.util.DBConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogController {


    public static List<BlogModel> getAllBlogs() {
        List<BlogModel> blogs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to the database.");
            }

            String query = "SELECT id, name, description, content, image FROM blogs";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    BlogModel blog = new BlogModel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("content"),
                            resultSet.getBytes("image")
                    );
                    blogs.add(blog);

                    System.out.println("Blog ID: " + blog.getId());
                    System.out.println("Name: " + blog.getName());
                    System.out.println("Description: " + blog.getDescription());
                    System.out.println("Content: " + blog.getContent());
                    System.out.println("---------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching blogs: " + e.getMessage());
        }

        return blogs;
    }

    public static boolean insertBlogWithImage(String name, String description, String content, InputStream imageStream) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) throw new RuntimeException("Failed to connect to the database.");

            connection.setAutoCommit(false);

            String query = "INSERT INTO blogs (name, description, content, image) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, content);
                ps.setBinaryStream(4, imageStream);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    return true;
                } else {

                    connection.rollback();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();

                connection.rollback();
                throw new RuntimeException("Error while inserting blog: " + e.getMessage());
            } finally {

                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting blog: " + e.getMessage());
        }
    }


    public static boolean updateBlog(int id, String name, String description, String content) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) throw new RuntimeException("Failed to connect to the database.");

            String query = "UPDATE blogs SET name = ?, description = ?, content = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, content);
                ps.setInt(4, id);

                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating blog: " + e.getMessage());
        }
    }


    public static boolean updateBlogWithImage(int id, String name, String description, String content, InputStream imageStream) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) throw new RuntimeException("Failed to connect to the database.");

            String query = "UPDATE blogs SET name = ?, description = ?, content = ?, image = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, content);
                ps.setBinaryStream(4, imageStream);
                ps.setInt(5, id);

                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating blog with image: " + e.getMessage());
        }
    }
}
