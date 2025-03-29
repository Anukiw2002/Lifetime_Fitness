package org.example.demo2.servlet;

import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

@WebServlet("/instructor/createWorkout")
public class CreateWorkoutServlet extends HttpServlet {
    private final DBConnection dbConnection = new DBConnection();
    private ClientWorkoutDAO clientWorkoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;
    private WorkoutCategoryDAO categoryDAO;
    private ExerciseDAO exerciseDAO;

    @Override
    public void init() throws ServletException {
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        this.workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
        this.categoryDAO = new WorkoutCategoryDAO(dbConnection);
        this.exerciseDAO = new ExerciseDAO(dbConnection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Connection connection = null;
        try {
            // Validate required parameters
            Long userId = getLongParameter(request, "userId");
            String workoutName = getRequiredParameter(request, "workoutName");
            Long categoryId = getLongParameter(request, "categoryId");
            Long instructorId = (Long) session.getAttribute("userId");

            // Check if workout name exists
            if (clientWorkoutDAO.workoutNameExists(userId, workoutName)) {
                request.setAttribute("error", "A workout with this name already exists for this user.");
                request.setAttribute("categories", categoryDAO.findAll());
                request.setAttribute("exercises", exerciseDAO.findAll());
                request.getRequestDispatcher("/WEB-INF/views/instructor/create-workout.jsp")
                        .forward(request, response);
                return;
            }

            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Create workout
            ClientWorkout workout = new ClientWorkout(userId, workoutName, categoryId, instructorId);
            workout = clientWorkoutDAO.create(workout);

            if (workout == null || workout.getWorkoutId() == null) {
                throw new ServletException("Failed to create workout");
            }

            // Process exercises
            String[] exerciseIds = request.getParameterValues("exerciseId");
            String[] setNumbers = request.getParameterValues("setNumber");
            String[] reps = request.getParameterValues("reps");
            String[] notes = request.getParameterValues("notes");

            if (exerciseIds != null) {
                validateExerciseArrays(exerciseIds, setNumbers, reps);

                for (int i = 0; i < exerciseIds.length; i++) {
                    WorkoutExercise exercise = new WorkoutExercise();
                    exercise.setWorkoutId(workout.getWorkoutId());
                    exercise.setExerciseId(Long.parseLong(exerciseIds[i]));
                    exercise.setSetNumber(Integer.parseInt(setNumbers[i]));
                    exercise.setReps(Integer.parseInt(reps[i]));
                    exercise.setNotes(notes != null && notes.length > i ? notes[i] : null);

                    workoutExerciseDAO.create(exercise);
                }
            }

            connection.commit();
            response.sendRedirect("clientWorkouts?userId=" + userId);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    // Log rollback error
                }
            }
            throw new ServletException("Error creating workout: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    // Log close error
                }
            }
        }
    }

    // Helper methods
    private Long getLongParameter(HttpServletRequest request, String name) throws ServletException {
        String value = request.getParameter(name);
        if (value == null || value.trim().isEmpty()) {
            throw new ServletException("Missing parameter: " + name);
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for parameter: " + name);
        }
    }

    private String getRequiredParameter(HttpServletRequest request, String name) throws ServletException {
        String value = request.getParameter(name);
        if (value == null || value.trim().isEmpty()) {
            throw new ServletException("Missing parameter: " + name);
        }
        return value.trim();
    }

    private void validateExerciseArrays(String[] exerciseIds, String[] setNumbers, String[] reps)
            throws ServletException {
        if (setNumbers == null || reps == null ||
                exerciseIds.length != setNumbers.length ||
                exerciseIds.length != reps.length) {
            throw new ServletException("Exercise data is incomplete or malformed");
        }
    }
}