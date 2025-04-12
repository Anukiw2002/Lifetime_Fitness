package org.example.demo2.servlet;

import org.example.demo2.model.VideoModel;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoController {

    // Method to fetch all videos
    public static List<VideoModel> getAllVideos() {
        List<VideoModel> videos = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to the database.");
            }

            // ✅ Updated query to fetch 'url'
            String query = "SELECT id, name, description, url FROM videos";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    // ✅ Added 'url' to constructor
                    VideoModel video = new VideoModel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("url")
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

}
