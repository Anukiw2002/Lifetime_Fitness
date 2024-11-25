package org.example.demo2.servlet;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<WorkoutCategory> categories = categoryDAO.findAll();
            List<Exercise> exercises = exerciseDAO.findAll();

            request.setAttribute("categories", categories);
            request.setAttribute("exercises", exercises);
            request.getRequestDispatcher("/WEB-INF/views/instructor/create-workout.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters
            String clientPhone = request.getParameter("clientPhone");
            String workoutName = request.getParameter("workoutName");
            Long categoryId = Long.parseLong(request.getParameter("categoryId"));
            Long instructorId = 1L; // You might want to get this from the session

            // Check if workout name already exists for this client
            if (clientWorkoutDAO.workoutNameExists(clientPhone, workoutName)) {
                // If exists, set error message and redirect back to form
                request.setAttribute("error", "A workout with this name already exists for this client.");
                request.setAttribute("categories", categoryDAO.findAll());
                request.setAttribute("exercises", exerciseDAO.findAll());
                request.getRequestDispatcher("/WEB-INF/views/instructor/create-workout.jsp")
                        .forward(request, response);
                return;
            }

            // Start transaction
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            try {
                // Create workout
                ClientWorkout workout = new ClientWorkout(clientPhone, workoutName, categoryId, instructorId);
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

                // Commit transaction
                connection.commit();
                response.sendRedirect("clientWorkouts?phoneNumber=" + clientPhone);
            } catch (Exception e) {
                // Rollback on error
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    private List<WorkoutExercise> extractExercises(HttpServletRequest request, Long workoutId) {
        List<WorkoutExercise> exercises = new ArrayList<>();
        int index = 0;

        while (true) {
            String exerciseIdParam = request.getParameter("exercises[" + index + "].exerciseId");
            if (exerciseIdParam == null) {
                break;
            }

            String setNumberParam = request.getParameter("exercises[" + index + "].setNumber");
            String repsParam = request.getParameter("exercises[" + index + "].reps");
            String notes = request.getParameter("exercises[" + index + "].notes");

            WorkoutExercise exercise = new WorkoutExercise();
            exercise.setWorkoutId(workoutId);
            exercise.setExerciseId(Long.parseLong(exerciseIdParam));
            exercise.setSetNumber(Integer.parseInt(setNumberParam));
            exercise.setReps(Integer.parseInt(repsParam));
            exercise.setNotes(notes);

            exercises.add(exercise);
            index++;
        }

        return exercises;
    }
}