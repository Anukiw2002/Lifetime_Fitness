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

    public boolean workoutNameExists(String clientPhone, String workoutName) throws SQLException {
        String query = "SELECT COUNT(*) FROM client_workouts WHERE client_phone = ? AND workout_name = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clientPhone);
            statement.setString(2, workoutName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    public ClientWorkout create(ClientWorkout workout) throws SQLException {
        String query = "INSERT INTO client_workouts (client_phone, workout_name, category_id, instructor_id) " +
                "VALUES (?, ?, ?, ?) RETURNING workout_id";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, workout.getClientPhone());
            statement.setString(2, workout.getWorkoutName());
            statement.setLong(3, workout.getCategoryId());
            statement.setLong(4, workout.getInstructorId());

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
                    ClientWorkout workout = new ClientWorkout(
                            workoutResultSet.getString("client_phone"),
                            workoutResultSet.getString("workout_name"),
                            workoutResultSet.getLong("category_id"),
                            workoutResultSet.getLong("instructor_id")
                    );
                    workout.setWorkoutId(workoutResultSet.getLong("workout_id"));

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

    public List<ClientWorkout> findByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM client_workouts WHERE user_id = ?";
        List<ClientWorkout> workouts = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ClientWorkout workout = new ClientWorkout(
                            resultSet.getString("client_phone"),
                            resultSet.getString("workout_name"),
                            resultSet.getLong("category_id"),
                            resultSet.getLong("instructor_id")
                    );
                    workout.setWorkoutId(resultSet.getLong("workout_id"));
                    workouts.add(workout);
                }
            }
        }
        return workouts;
    }

    public List<ClientWorkout> findByClientPhone(String phoneNumber) throws SQLException {
        String query = "SELECT * FROM client_workouts WHERE client_phone = ?";
        List<ClientWorkout> workouts = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, phoneNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ClientWorkout workout = new ClientWorkout(
                            resultSet.getString("client_phone"),
                            resultSet.getString("workout_name"),
                            resultSet.getLong("category_id"),
                            resultSet.getLong("instructor_id")
                    );
                    workout.setWorkoutId(resultSet.getLong("workout_id"));
                    workouts.add(workout);
                }
            }
        }
        return workouts;
    }

    public void delete(Long workoutId) throws SQLException {
        String query = "DELETE FROM client_workouts WHERE workout_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, workoutId);
            statement.executeUpdate();
        }
    }


    public ClientWorkout findById(Long workoutId) {
        return null;
    }
}
