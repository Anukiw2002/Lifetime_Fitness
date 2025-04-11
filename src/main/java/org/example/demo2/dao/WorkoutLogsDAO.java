package org.example.demo2.dao;

import org.example.demo2.model.WorkoutLogs;
import org.example.demo2.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutLogsDAO {
    /**
     * Insert workout log entry into the database
     */
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

    /**
     * Get all workout logs for a specific user and workout
     */
    public List<WorkoutLogs> getWorkoutLogsByUserAndWorkout(int userId, int workoutId) {
        List<WorkoutLogs> logs = new ArrayList<>();
        String sql = "SELECT * FROM user_workout_logs WHERE user_id = ? AND workout_id = ? ORDER BY exercise_id, set_number";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, workoutId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WorkoutLogs log = new WorkoutLogs();
                    log.setUser_id(rs.getInt("user_id"));
                    log.setWorkout_id(rs.getInt("workout_id"));
                    log.setExercise_id(rs.getInt("exercise_id"));
                    log.setSet_number(rs.getInt("set_number"));

                    // Handle potentially null weight
                    double weight = rs.getDouble("weight");
                    if (!rs.wasNull()) {
                        log.setWeight(weight);
                    }

                    log.setReps(rs.getInt("reps"));
                    log.setNotes(rs.getString("notes"));

                    logs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging in production
        }

        return logs;
    }
}