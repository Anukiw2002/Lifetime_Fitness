package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkoutLogsDAO {
    public boolean insertWorkoutLogs(int user_id, int workout_id, int exercise_id, int set_number, Double weight, int reps, String notes) {
        String sql = "INSERT INTO user_workout_logs (user_id, workout_id, exercise_id, set_number, weight, reps, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, workout_id);
            pstmt.setInt(3, exercise_id);
            pstmt.setInt(4, set_number);

            // Handle potential null weight
            if (weight != null) {
                pstmt.setDouble(5, weight);
            } else {
                pstmt.setNull(5, java.sql.Types.DECIMAL);
            }

            pstmt.setInt(6, reps);
            pstmt.setString(7, notes);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging in production
            return false;
        }
    }
}