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
            String sql = "SELECT w.*, c.category_name FROM client_workouts w " +
                    "JOIN workout_categories c ON w.category_id = c.category_id " +
                    "WHERE w.client_phone = ? " +
                    "ORDER BY w.created_at DESC";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, clientPhone);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    ClientWorkout workout = new ClientWorkout();
                    workout.setWorkoutId(rs.getLong("workout_id"));
                    workout.setClientPhone(rs.getString("client_phone"));
                    workout.setWorkoutName(rs.getString("workout_name"));
                    workout.setCategoryId(rs.getLong("category_id"));
                    workout.setInstructorId(rs.getLong("instructor_id"));
                    workout.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    // Create and set the category
                    WorkoutCategory category = new WorkoutCategory();
                    category.setCategoryId(rs.getLong("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    workout.setCategory(category);

                    // Optionally load exercises for each workout
                    WorkoutExerciseDAO exerciseDAO = new WorkoutExerciseDAO(dbConnection);
                    List<WorkoutExercise> exercises = exerciseDAO.findByWorkoutId(workout.getWorkoutId());
                    workout.setExercises(exercises);

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
            String sql = "SELECT w.*, c.category_name " +
                    "FROM client_workouts w " +
                    "JOIN workout_categories c ON w.category_id = c.category_id " +
                    "WHERE w.workout_id = ?";

            ClientWorkout workout = null;

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workoutId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    workout = new ClientWorkout();
                    workout.setWorkoutId(rs.getLong("workout_id"));
                    workout.setClientPhone(rs.getString("client_phone"));
                    workout.setWorkoutName(rs.getString("workout_name"));
                    workout.setCategoryId(rs.getLong("category_id"));
                    workout.setInstructorId(rs.getLong("instructor_id"));
                    workout.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

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

                // Log the number of exercises found (for debugging)
                System.out.println("Found " + exercises.size() + " exercises for workout " + workoutId);
                exercises.forEach(ex -> System.out.println("Exercise: " + ex.getExercise().getExerciseName() +
                        ", Sets: " + ex.getSetNumber() +
                        ", Reps: " + ex.getReps()));
            }

            return workout;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(Long workoutId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // First delete associated exercises
            String deleteExercisesSql = "DELETE FROM workout_exercises WHERE workout_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteExercisesSql)) {
                stmt.setLong(1, workoutId);
                stmt.executeUpdate();
            }

            // Then delete the workout
            String deleteWorkoutSql = "DELETE FROM client_workouts WHERE workout_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteWorkoutSql)) {
                stmt.setLong(1, workoutId);
                stmt.executeUpdate();
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback in case of error
                } catch (SQLException ex) {
                    throw new SQLException("Error rolling back transaction", ex);
                }
            }
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset auto-commit
                    connection.close();
                } catch (SQLException e) {
                    throw new SQLException("Error closing connection", e);
                }
            }
        }
    }

    public ClientWorkout create(ClientWorkout workout) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO client_workouts (client_phone, workout_name, category_id, instructor_id) " +
                    "VALUES (?, ?, ?, ?) " +
                    "RETURNING workout_id, created_at";  // Added created_at to RETURNING clause

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, workout.getClientPhone());
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

    public boolean workoutNameExists(String clientPhone, String workoutName) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM client_workouts WHERE client_phone = ? AND workout_name = ?";

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