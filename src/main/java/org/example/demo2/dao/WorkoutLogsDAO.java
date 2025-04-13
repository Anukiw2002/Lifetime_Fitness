package org.example.demo2.dao;

import org.example.demo2.model.WorkoutLogs;
import org.example.demo2.model.WorkoutSession;
import org.example.demo2.model.WorkoutStats;
import org.example.demo2.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutLogsDAO {

    public boolean insertWorkoutLogs(int session_id, int user_id, int workout_id, int exercise_id, int set_number, Double weight, int reps, String notes) {
        String sql = "INSERT INTO user_workout_logs (session_id, user_id, workout_id, exercise_id, set_number, weight, reps, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, session_id);
            pstmt.setInt(2, user_id);
            pstmt.setInt(3, workout_id);
            pstmt.setInt(4, exercise_id);
            pstmt.setInt(5, set_number);

            if (weight != null) {
                pstmt.setDouble(6, weight);
            } else {
                pstmt.setNull(6, java.sql.Types.DECIMAL);
            }

            pstmt.setInt(7, reps);
            pstmt.setString(8, notes);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public WorkoutStats getStats(int userId, int workoutId) {
        WorkoutStats stats = new WorkoutStats();
        String sql = "SELECT SUM(weight * reps) AS totalWeight, SUM(reps) AS totalReps, COUNT(*) AS totalSets " +
                "FROM user_workout_logs WHERE user_id = ? AND workout_id = ? AND date(created_at) = CURDATE()";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, workoutId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double weight = rs.getDouble("totalWeight");
                    int reps = rs.getInt("totalReps");
                    int sets = rs.getInt("totalSets");

                    System.out.println("DEBUG: totalWeight=" + weight + ", totalReps=" + reps + ", totalSets=" + sets);

                    stats.setUserId(userId);
                    stats.setWorkoutId(workoutId);
                    stats.setTotalWeight(weight);
                    stats.setTotalReps(reps);
                    stats.setTotalSets(sets);
                } else {
                    System.out.println("DEBUG: No stats found for userId=" + userId + ", workoutId=" + workoutId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging
        }

        return stats;
    }

    public int createWorkoutSession(int userId, int workoutId) {
        String sql = "INSERT INTO workout_sessions (user_id, workout_id) VALUES (?, ?) RETURNING session_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, workoutId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("session_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<WorkoutLogs> getWorkoutLogsBySessionId(int sessionId) {
        List<WorkoutLogs> logs = new ArrayList<>();
        String sql = "SELECT * FROM user_workout_logs WHERE session_id = ? ORDER BY exercise_id, set_number";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WorkoutLogs log = new WorkoutLogs();
                    log.setUser_id(rs.getInt("user_id"));
                    log.setWorkout_id(rs.getInt("workout_id"));
                    log.setExercise_id(rs.getInt("exercise_id"));
                    log.setSet_number(rs.getInt("set_number"));

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
            e.printStackTrace();
        }

        return logs;
    }

    public boolean insertWorkoutLogsWithSession(int sessionId, int userId, int workoutId, int exerciseId, int setNumber, Double weight, int reps, String notes) {

        String sql = "INSERT INTO user_workout_logs (session_id, user_id, workout_id, exercise_id, set_number, weight, reps, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, workoutId);
            pstmt.setInt(4, exerciseId);
            pstmt.setInt(5, setNumber);

            if (weight != null) {
                pstmt.setDouble(6, weight);
            } else {
                pstmt.setNull(6, java.sql.Types.DOUBLE);
            }

            pstmt.setInt(7, reps);
            pstmt.setString(8, notes);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public WorkoutStats getStatsForSession(int userId, int workoutId, int sessionId) {
        String sql = "SELECT COUNT(*) AS total_sets, SUM(weight*reps) AS total_weight, SUM(reps) AS total_reps " +
                "FROM user_workout_logs WHERE user_id = ? AND workout_id = ? AND session_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, workoutId);
            pstmt.setInt(3, sessionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int sets = rs.getInt("total_sets");
                    double totalWeight = rs.getDouble("total_weight");
                    int totalReps = rs.getInt("total_reps");

                    WorkoutStats stats = new WorkoutStats(); // Use default constructor
                    stats.setTotalSets(sets);
                    stats.setTotalWeight(totalWeight);
                    stats.setTotalReps(totalReps);

                    return stats;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateWorkoutEndTime(int sessionId) {
        String sql = "UPDATE workout_sessions SET ended_at = CURRENT_TIMESTAMP WHERE session_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public WorkoutSession getWorkoutSessionDetails(int sessionId) {
        String sql = "SELECT session_id, user_id, started_at, ended_at, notes FROM workout_sessions WHERE session_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    Date startTime = rs.getTimestamp("started_at");
                    Date endTime = rs.getTimestamp("ended_at");
                    String notes = rs.getString("notes");

                    return new WorkoutSession(sessionId, userId, startTime, endTime, notes);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateWorkoutSessionEndTime(int sessionId) {
        String sql = "UPDATE workout_sessions SET ended_at = CURRENT_TIMESTAMP WHERE session_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}