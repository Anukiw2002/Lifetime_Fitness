package org.example.demo2.dao;

import org.example.demo2.model.WorkoutExercise;
import org.example.demo2.model.Exercise;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutExerciseDAO {
    private final DBConnection dbConnection;

    public WorkoutExerciseDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public WorkoutExercise create(WorkoutExercise workoutExercise) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();

            // Debug output
            System.out.println("Saving workout exercise:");
            System.out.println("Workout ID: " + workoutExercise.getWorkoutId());
            System.out.println("Exercise ID: " + workoutExercise.getExerciseId());
            System.out.println("Set Number: " + workoutExercise.getSetNumber());
            System.out.println("Reps: " + workoutExercise.getReps());

            String sql = "INSERT INTO workout_exercises (workout_id, exercise_id, set_number, reps, notes) " +
                    "VALUES (?, ?, ?, ?, ?) " +
                    "RETURNING workout_exercise_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workoutExercise.getWorkoutId());
                stmt.setLong(2, workoutExercise.getExerciseId());
                stmt.setInt(3, workoutExercise.getSetNumber());
                stmt.setInt(4, workoutExercise.getReps());
                stmt.setString(5, workoutExercise.getNotes());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    workoutExercise.setWorkoutExerciseId(rs.getLong("workout_exercise_id"));
                    System.out.println("Successfully created workout exercise with ID: " +
                            workoutExercise.getWorkoutExerciseId());
                    return workoutExercise;
                }
                System.out.println("No workout exercise ID returned from insert");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error creating workout exercise: " + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<WorkoutExercise> findByWorkoutId(Long workoutId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<WorkoutExercise> exercises = new ArrayList<>();
            String sql = "SELECT we.*, e.exercise_name, e.description " +
                    "FROM workout_exercises we " +
                    "JOIN exercises e ON we.exercise_id = e.exercise_id " +
                    "WHERE we.workout_id = ? " +
                    "ORDER BY we.set_number";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workoutId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    WorkoutExercise workoutExercise = new WorkoutExercise();
                    workoutExercise.setWorkoutExerciseId(rs.getLong("workout_exercise_id"));
                    workoutExercise.setWorkoutId(rs.getLong("workout_id"));
                    workoutExercise.setExerciseId(rs.getLong("exercise_id"));
                    workoutExercise.setSetNumber(rs.getInt("set_number"));
                    workoutExercise.setReps(rs.getInt("reps"));
                    workoutExercise.setNotes(rs.getString("notes"));

                    Exercise exercise = new Exercise();
                    exercise.setExerciseId(rs.getLong("exercise_id"));
                    exercise.setExerciseName(rs.getString("exercise_name"));
                    exercise.setDescription(rs.getString("description"));
                    workoutExercise.setExercise(exercise);

                    exercises.add(workoutExercise);
                }
            }
            return exercises;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteByWorkoutId(Long workoutId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "DELETE FROM workout_exercises WHERE workout_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workoutId);
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}