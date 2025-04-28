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
    private ClientDAO clientDAO;

    @Override
    public void init() throws ServletException {
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        this.workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
        this.categoryDAO = new WorkoutCategoryDAO(dbConnection);
        this.exerciseDAO = new ExerciseDAO(dbConnection);
        this.clientDAO = new ClientDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        try {
            List<WorkoutCategory> categories = categoryDAO.findAll();
            List<Exercise> exercises = exerciseDAO.findAll();

            request.setAttribute("categories", categories);
            request.setAttribute("exercises", exercises);
            request.getRequestDispatcher("/WEB-INF/views/instructor/create-workout.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error fetching categories and exercises: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Database error occurred", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;

        try {

            System.out.println("=== DEBUG: All request parameters ===");
            request.getParameterMap().forEach((k, v) -> {
                System.out.println(k + ": " + String.join(", ", v));
            });
            System.out.println("===================================");

            String clientPhone = request.getParameter("clientPhone");
            String workoutName = request.getParameter("workoutName");
            String categoryIdStr = request.getParameter("categoryId");

            System.out.println("Client Phone: " + clientPhone);
            System.out.println("Workout Name: " + workoutName);
            System.out.println("Category ID: " + categoryIdStr);

            if (clientPhone == null || clientPhone.trim().isEmpty()) {
                throw new ServletException("Client phone number is required");
            }

            if (workoutName == null || workoutName.trim().isEmpty()) {
                throw new ServletException("Workout name is required");
            }

            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                throw new ServletException("Category ID is required");
            }

            Long categoryId = Long.parseLong(categoryIdStr);
            Long instructorId = 1L;


            System.out.println("Looking up client with phone: " + clientPhone);
            Client client = clientDAO.findByPhoneNumber(clientPhone);
            if (client == null) {
                System.err.println("Client not found with phone: " + clientPhone);
                request.setAttribute("error", "Client not found with phone number: " + clientPhone);
                request.setAttribute("categories", categoryDAO.findAll());
                request.setAttribute("exercises", exerciseDAO.findAll());
                request.getRequestDispatcher("/WEB-INF/views/instructor/create-workout.jsp")
                        .forward(request, response);
                return;
            }

            Long userId = client.getUserId();
            System.out.println("Found client user ID: " + userId);


            if (clientWorkoutDAO.workoutNameExists(userId, workoutName)) {
                System.err.println("Workout name already exists: " + workoutName);
                request.setAttribute("error", "A workout with this name already exists for this client.");
                request.setAttribute("categories", categoryDAO.findAll());
                request.setAttribute("exercises", exerciseDAO.findAll());
                request.getRequestDispatcher("/WEB-INF/views/instructor/create-workout.jsp")
                        .forward(request, response);
                return;
            }


            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            System.out.println("Started transaction");

            try {

                System.out.println("Creating workout with userId: " + userId);
                ClientWorkout workout = new ClientWorkout(userId, workoutName, categoryId, instructorId);
                workout = clientWorkoutDAO.create(workout);
                System.out.println("Created workout with ID: " + workout.getWorkoutId());

                if (workout == null || workout.getWorkoutId() == null) {
                    throw new ServletException("Failed to create workout");
                }


                String[] exerciseIds = request.getParameterValues("exerciseId");
                String[] setNumbers = request.getParameterValues("setNumber");
                String[] reps = request.getParameterValues("reps");
                String[] notes = request.getParameterValues("notes");

                System.out.println("Exercise IDs: " + (exerciseIds != null ? exerciseIds.length : "null"));
                System.out.println("Set Numbers: " + (setNumbers != null ? setNumbers.length : "null"));
                System.out.println("Reps: " + (reps != null ? reps.length : "null"));

                if (exerciseIds != null) {
                    for (int i = 0; i < exerciseIds.length; i++) {
                        System.out.println("Processing exercise: " + exerciseIds[i]);
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
                System.out.println("Transaction committed successfully");


                workout.setClientPhone(clientPhone);

                response.sendRedirect("clientWorkouts?phoneNumber=" + clientPhone);
            } catch (Exception e) {

                System.err.println("Error creating workout: " + e.getMessage());
                e.printStackTrace();
                if (connection != null) {
                    try {
                        System.out.println("Rolling back transaction");
                        connection.rollback();
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                throw e;
            } finally {
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                        connection.close();
                        System.out.println("Connection closed");
                    } catch (SQLException closeEx) {
                        System.err.println("Error closing connection: " + closeEx.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error in doPost: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Database error occurred: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
            throw new ServletException("Invalid number format: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Unexpected error: " + e.getMessage(), e);
        }
    }
}