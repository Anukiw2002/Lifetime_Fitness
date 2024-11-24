package org.example.demo2.dao;

import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO {
    private final DBConnection dbConnection;

    public ExerciseDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Exercise create(Exercise exercise) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO exercises (exercise_name, description) VALUES (?, ?) RETURNING exercise_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, exercise.getExerciseName());
                stmt.setString(2, exercise.getDescription());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    exercise.setExerciseId(rs.getLong("exercise_id"));
                }
            }
            return exercise;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Exercise> findAll() throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<Exercise> exercises = new ArrayList<>();
            String sql = "SELECT * FROM exercises";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Exercise exercise = new Exercise();
                    exercise.setExerciseId(rs.getLong("exercise_id"));
                    exercise.setExerciseName(rs.getString("exercise_name"));
                    exercise.setDescription(rs.getString("description"));
                    exercises.add(exercise);
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
