package org.example.demo2.dao;

import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientWorkoutDAO {
    private final DBConnection dbConnection;

    public ClientWorkoutDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<ClientWorkout> findByClientPhone(String clientPhone) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<ClientWorkout> workouts = new ArrayList<>();
            String sql = "SELECT w.*, c.category_name, cd.phone_number, CONCAT(u.full_name, ' ', u.username) AS client_name " +
                    "FROM client_workouts w " +
                    "JOIN workout_categories c ON w.category_id = c.category_id " +
                    "JOIN client_details cd ON w.user_id = cd.user_id " +
                    "JOIN users u ON cd.user_id = u.id " +
                    "WHERE cd.phone_number = ? " +
                    "ORDER BY w.created_at DESC";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, clientPhone);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    ClientWorkout workout = new ClientWorkout();
                    workout.setWorkoutId(rs.getLong("workout_id"));
                    workout.setUserId(rs.getLong("user_id"));
                    workout.setWorkoutName(rs.getString("workout_name"));
                    workout.setCategoryId(rs.getLong("category_id"));
                    workout.setInstructorId(rs.getLong("instructor_id"));
                    workout.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    workout.setClientPhone(rs.getString("phone_number"));
                    workout.setClientName(rs.getString("client_name"));

                    // Create and set the category
                    WorkoutCategory category = new WorkoutCategory();
                    category.setCategoryId(rs.getLong("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    workout.setCategory(category);

                    workouts.add(workout);
                }
            }
            return workouts;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<ClientWorkout> findByUserId(int userId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<ClientWorkout> workouts = new ArrayList<>();
            String sql = "SELECT w.*, c.category_name, cd.phone_number, CONCAT(u.full_name, ' ', u.username) AS client_name " +
                    "FROM client_workouts w " +
                    "JOIN workout_categories c ON w.category_id = c.category_id " +
                    "JOIN client_details cd ON w.user_id = cd.user_id " +
                    "JOIN users u ON cd.user_id = u.id " +
                    "WHERE w.user_id = ? " +
                    "ORDER BY w.created_at DESC";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    ClientWorkout workout = new ClientWorkout();
                    workout.setWorkoutId(rs.getLong("workout_id"));
                    workout.setUserId(rs.getLong("user_id"));
                    workout.setWorkoutName(rs.getString("workout_name"));
                    workout.setCategoryId(rs.getLong("category_id"));
                    workout.setInstructorId(rs.getLong("instructor_id"));
                    workout.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    workout.setClientPhone(rs.getString("phone_number"));
                    workout.setClientName(rs.getString("client_name"));

                    // Create and set the category
                    WorkoutCategory category = new WorkoutCategory();
                    category.setCategoryId(rs.getLong("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    workout.setCategory(category);

                    workouts.add(workout);
                }
            }
            return workouts;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public ClientWorkout findWithExercises(Long workoutId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();

            // First get the workout with complete details
            String sql = "SELECT w.*, c.category_name, cd.phone_number, CONCAT(u.full_name, ' ', u.username) AS client_name " +
                    "FROM client_workouts w " +
                    "JOIN workout_categories c ON w.category_id = c.category_id " +
                    "JOIN client_details cd ON w.user_id = cd.user_id " +
                    "JOIN users u ON cd.user_id = u.id " +
                    "WHERE w.workout_id = ?";

            ClientWorkout workout = null;

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workoutId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    workout = new ClientWorkout();
                    workout.setWorkoutId(rs.getLong("workout_id"));
                    workout.setUserId(rs.getLong("user_id"));
                    workout.setWorkoutName(rs.getString("workout_name"));
                    workout.setCategoryId(rs.getLong("category_id"));
                    workout.setInstructorId(rs.getLong("instructor_id"));
                    workout.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    workout.setClientPhone(rs.getString("phone_number"));
                    workout.setClientName(rs.getString("client_name"));

                    // Set the category
                    WorkoutCategory category = new WorkoutCategory();
                    category.setCategoryId(rs.getLong("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    workout.setCategory(category);
                }
            }

            if (workout != null) {
                // Get exercises for this workout using WorkoutExerciseDAO
                WorkoutExerciseDAO exerciseDAO = new WorkoutExerciseDAO(dbConnection);
                List<WorkoutExercise> exercises = exerciseDAO.findByWorkoutId(workoutId);
                workout.setExercises(exercises);
            }

            return workout;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }


    public boolean delete(Long workoutId) throws SQLException {
        String sql = "DELETE FROM client_workouts WHERE workout_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, workoutId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error in ClientWorkoutDAO.delete: " + e.getMessage());
            throw e;
        }
    }

    public ClientWorkout create(ClientWorkout workout) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO client_workouts (user_id, workout_name, category_id, instructor_id) " +
                    "VALUES (?, ?, ?, ?) " +
                    "RETURNING workout_id, created_at";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workout.getUserId());
                stmt.setString(2, workout.getWorkoutName());
                stmt.setLong(3, workout.getCategoryId());
                stmt.setLong(4, workout.getInstructorId());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    workout.setWorkoutId(rs.getLong("workout_id"));
                    workout.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
            return workout;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean workoutNameExists(Long userId, String workoutName) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM client_workouts WHERE user_id = ? AND workout_name = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, userId);
                stmt.setString(2, workoutName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    // Keep this for backward compatibility
    public boolean workoutNameExists(String clientPhone, String workoutName) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM client_workouts cw " +
                    "JOIN client_details cd ON cw.user_id = cd.user_id " +
                    "WHERE cd.phone_number = ? AND cw.workout_name = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, clientPhone);
                stmt.setString(2, workoutName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}