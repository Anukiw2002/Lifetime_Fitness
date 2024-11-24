package org.example.demo2.dao;

import org.example.demo2.model.*;
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
            String sql = "INSERT INTO workout_exercises (workout_id, exercise_id, set_number, reps, notes) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING workout_exercise_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, workoutExercise.getWorkoutId());
                stmt.setLong(2, workoutExercise.getExerciseId());
                stmt.setInt(3, workoutExercise.getSetNumber());
                stmt.setInt(4, workoutExercise.getReps());
                stmt.setString(5, workoutExercise.getNotes());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    workoutExercise.setWorkoutExerciseId(rs.getLong("workout_exercise_id"));
                }
            }
            return workoutExercise;
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
            String sql = "SELECT we.*, e.exercise_name, e.description FROM workout_exercises we " +
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
}