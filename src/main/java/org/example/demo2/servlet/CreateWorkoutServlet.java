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

@WebServlet("/instructor/createWorkout")
public class CreateWorkoutServlet extends HttpServlet {
    private ClientWorkoutDAO clientWorkoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;
    private WorkoutCategoryDAO categoryDAO;
    private ExerciseDAO exerciseDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        this.workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
        this.categoryDAO = new WorkoutCategoryDAO(dbConnection);
        this.exerciseDAO = new ExerciseDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Load categories and exercises for the form
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
        String clientPhone = request.getParameter("clientPhone");
        String workoutName = request.getParameter("workoutName");
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));

        try {
            // Create the workout
            ClientWorkout workout = new ClientWorkout();
            workout.setClientPhone(clientPhone);
            workout.setWorkoutName(workoutName);
            workout.setCategoryId(categoryId);
            workout.setCreatedAt(LocalDateTime.now());

            // Set instructor ID (you should get this from your session or authentication system)
            Long instructorId = 1L; // Replace this with actual instructor ID from your session
            workout.setInstructorId(instructorId);

            // Save the workout and get the generated ID
            workout = clientWorkoutDAO.create(workout);

            // Process exercises
            String[] exerciseIds = request.getParameterValues("exercises[0].exerciseId");
            String[] setNumbers = request.getParameterValues("exercises[0].setNumber");
            String[] reps = request.getParameterValues("exercises[0].reps");
            String[] notes = request.getParameterValues("exercises[0].notes");

            if (exerciseIds != null) {
                for (int i = 0; i < exerciseIds.length; i++) {
                    WorkoutExercise exercise = new WorkoutExercise();
                    exercise.setWorkoutId(workout.getWorkoutId());
                    exercise.setExerciseId(Long.parseLong(exerciseIds[i]));
                    exercise.setSetNumber(Integer.parseInt(setNumbers[i]));
                    exercise.setReps(Integer.parseInt(reps[i]));
                    if (notes != null && notes.length > i) {
                        exercise.setNotes(notes[i]);
                    }
                    workoutExerciseDAO.create(exercise);
                }
            }

            // Redirect back to client workouts page
            response.sendRedirect("clientWorkouts?phoneNumber=" + clientPhone);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}