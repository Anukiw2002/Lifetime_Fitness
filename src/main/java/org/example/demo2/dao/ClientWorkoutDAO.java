package org.example.demo2.dao;

import org.example.demo2.model.ClientWorkout;
import org.example.demo2.model.WorkoutExercise;
import org.example.demo2.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientWorkoutDAO {
    private final DBConnection dbConnection;

    public ClientWorkoutDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // In ClientWorkoutDAO.java
    public boolean workoutNameExists(Long userId, String workoutName) throws SQLException {
        String query = "SELECT COUNT(*) FROM client_workouts WHERE user_id = ? AND workout_name = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);  // Changed from setString to setLong
            statement.setString(2, workoutName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    public ClientWorkout create(ClientWorkout workout) throws SQLException {
        String query = "INSERT INTO client_workouts (user_id, workout_name, category_id, instructor_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING workout_id";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, workout.getUserId());
            statement.setString(2, workout.getWorkoutName());
            statement.setLong(3, workout.getCategoryId());
            statement.setLong(4, workout.getInstructorId());
            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    workout.setWorkoutId(resultSet.getLong(1));
                }
            }
        }
        return workout;
    }

    public ClientWorkout findWithExercises(Long workoutId) throws SQLException {
        String workoutQuery = "SELECT * FROM client_workouts WHERE workout_id = ?";
        String exercisesQuery = "SELECT * FROM workout_exercises WHERE workout_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement workoutStatement = connection.prepareStatement(workoutQuery);
             PreparedStatement exercisesStatement = connection.prepareStatement(exercisesQuery)) {

            workoutStatement.setLong(1, workoutId);
            exercisesStatement.setLong(1, workoutId);

            try (ResultSet workoutResultSet = workoutStatement.executeQuery()) {
                if (workoutResultSet.next()) {
                    ClientWorkout workout = new ClientWorkout();
                    workout.setWorkoutId(workoutResultSet.getLong("workout_id"));
                    workout.setUserId(workoutResultSet.getLong("user_id"));
                    workout.setWorkoutName(workoutResultSet.getString("workout_name"));
                    workout.setCategoryId(workoutResultSet.getLong("category_id"));
                    workout.setInstructorId(workoutResultSet.getLong("instructor_id"));
                    workout.setCreatedAt(workoutResultSet.getTimestamp("created_at"));

                    List<WorkoutExercise> exercises = new ArrayList<>();
                    try (ResultSet exercisesResultSet = exercisesStatement.executeQuery()) {
                        while (exercisesResultSet.next()) {
                            WorkoutExercise exercise = new WorkoutExercise();
                            exercise.setWorkoutId(workoutId);
                            exercise.setExerciseId(exercisesResultSet.getLong("exercise_id"));
                            exercise.setSetNumber(exercisesResultSet.getInt("set_number"));
                            exercise.setReps(exercisesResultSet.getInt("reps"));
                            exercise.setNotes(exercisesResultSet.getString("notes"));
                            exercises.add(exercise);
                        }
                    }
                    workout.setExercises(exercises);
                    return workout;
                }
            }
        }
        return null;
    }

    public List<ClientWorkout> findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM client_workouts WHERE user_id = ?";
        List<ClientWorkout> workouts = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClientWorkout workout = new ClientWorkout();
                workout.setWorkoutId(rs.getLong("workout_id"));
                workout.setUserId(rs.getLong("user_id"));
                workout.setWorkoutName(rs.getString("workout_name"));
                workout.setCategoryId(rs.getLong("category_id"));
                workout.setInstructorId(rs.getLong("instructor_id"));
                workout.setCreatedAt(rs.getTimestamp("created_at"));
                workouts.add(workout);
            }
        }
        return workouts;
    }

    private ClientWorkout mapResultSetToWorkout(ResultSet rs) throws SQLException {
        ClientWorkout workout = new ClientWorkout();
        workout.setWorkoutId(rs.getLong("workout_id"));
        workout.setUserId(rs.getLong("user_id"));  // Ensure this matches your model
        workout.setWorkoutName(rs.getString("workout_name"));
        // Set other fields...
        return workout;
    }

    public ClientWorkout findById(Long workoutId) throws SQLException {
        String query = "SELECT * FROM client_workouts WHERE workout_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, workoutId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ClientWorkout workout = new ClientWorkout();
                    workout.setWorkoutId(resultSet.getLong("workout_id"));
                    workout.setUserId(resultSet.getLong("user_id"));
                    workout.setWorkoutName(resultSet.getString("workout_name"));
                    workout.setCategoryId(resultSet.getLong("category_id"));
                    workout.setInstructorId(resultSet.getLong("instructor_id"));
                    workout.setCreatedAt(resultSet.getTimestamp("created_at"));
                    return workout;
                }
            }
        }
        return null;
    }

    public void delete(Long workoutId) throws SQLException {
        // First delete exercises to maintain referential integrity
        String deleteExercisesQuery = "DELETE FROM workout_exercises WHERE workout_id = ?";
        String deleteWorkoutQuery = "DELETE FROM client_workouts WHERE workout_id = ?";

        try (Connection connection = dbConnection.getConnection()) {
            // Start transaction
            connection.setAutoCommit(false);

            try (PreparedStatement deleteExercises = connection.prepareStatement(deleteExercisesQuery);
                 PreparedStatement deleteWorkout = connection.prepareStatement(deleteWorkoutQuery)) {

                deleteExercises.setLong(1, workoutId);
                deleteExercises.executeUpdate();

                deleteWorkout.setLong(1, workoutId);
                deleteWorkout.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}