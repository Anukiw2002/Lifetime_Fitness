package org.example.demo2.servlet;

import org.example.demo2.model.VideoModel;
import org.example.demo2.util.DBConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoController {

    // Fetch all videos
    public static List<VideoModel> getAllVideos() {
        List<VideoModel> videos = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to the database.");
            }

            String query = "SELECT id, name, description, url, image FROM videos";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String imageString = resultSet.getString("image");
                    byte[] imageBytes = null;

                    // Check if the image column is not null
                    if (imageString != null) {
                        imageBytes = imageString.getBytes();
                    }

                    VideoModel video = new VideoModel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("url"),
                            imageBytes
                    );
                    videos.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching videos: " + e.getMessage());
        }

        return videos;
    }

    // Insert new video with image
    public static boolean insertVideoWithImage(String name, String description, String url, InputStream imageStream) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) throw new RuntimeException("Failed to connect to the database.");

            // Disable auto-commit mode to allow large objects
            connection.setAutoCommit(false);

            String query = "INSERT INTO videos (name, description, url, image) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, url);
                ps.setBinaryStream(4, imageStream);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    // Commit the transaction
                    connection.commit();
                    return true;
                } else {
                    // If insertion failed, rollback the transaction
                    connection.rollback();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Rollback the transaction in case of an exception
                connection.rollback();
                throw new RuntimeException("Error while inserting video: " + e.getMessage());
            } finally {
                // Reset the auto-commit mode to its default state
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting video: " + e.getMessage());
        }
    }

    // Update video without changing the image
    public static boolean updateVideo(int id, String name, String description, String url) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) throw new RuntimeException("Failed to connect to the database.");

            String query = "UPDATE videos SET name = ?, description = ?, url = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, url);
                ps.setInt(4, id);

                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating video: " + e.getMessage());
        }
    }

    // Update video including a new image
    public static boolean updateVideoWithImage(int id, String name, String description, String url, InputStream imageStream) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) throw new RuntimeException("Failed to connect to the database.");

            String query = "UPDATE videos SET name = ?, description = ?, url = ?, image = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, url);
                ps.setBinaryStream(4, imageStream);
                ps.setInt(5, id);

                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while updating video with image: " + e.getMessage());
        }
    }
}
