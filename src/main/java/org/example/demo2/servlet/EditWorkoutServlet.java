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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/instructor/editWorkout")
public class EditWorkoutServlet extends HttpServlet {
    private ClientWorkoutDAO clientWorkoutDAO;
    private WorkoutExerciseDAO workoutExerciseDAO;
    private ExerciseDAO exerciseDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientWorkoutDAO = new ClientWorkoutDAO(dbConnection);
        this.workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
        this.exerciseDAO = new ExerciseDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String workoutId = request.getParameter("workoutId");

        try {
            // Load the workout with exercises
            ClientWorkout workout = clientWorkoutDAO.findWithExercises(Long.parseLong(workoutId));
            if (workout == null) {
                response.sendRedirect("clientWorkouts");
                return;
            }

            // Load all available exercises for the add exercise dropdown
            List<Exercise> availableExercises = exerciseDAO.findAll();

            request.setAttribute("workout", workout);
            request.setAttribute("availableExercises", availableExercises);
            request.getRequestDispatcher("/WEB-INF/views/instructor/edit-workout.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long workoutId = Long.parseLong(request.getParameter("workoutId"));
        String[] exerciseIds = request.getParameterValues("exerciseIds");
        String[] setNumbers = request.getParameterValues("setNumbers");
        String[] reps = request.getParameterValues("reps");
        String[] notes = request.getParameterValues("notes");

        try {
            // First, delete all existing exercises for this workout
            // You'll need to add this method to WorkoutExerciseDAO
            workoutExerciseDAO.deleteByWorkoutId(workoutId);

            // Then add all exercises from the form
            for (int i = 0; i < exerciseIds.length; i++) {
                WorkoutExercise workoutExercise = new WorkoutExercise();
                workoutExercise.setWorkoutId(workoutId);
                workoutExercise.setExerciseId(Long.parseLong(exerciseIds[i]));
                workoutExercise.setSetNumber(Integer.parseInt(setNumbers[i]));
                workoutExercise.setReps(Integer.parseInt(reps[i]));
                workoutExercise.setNotes(notes[i]);

                workoutExerciseDAO.create(workoutExercise);
            }

            // Redirect back to the workout details page
            response.sendRedirect(request.getContextPath() +
                    "/instructor/workoutDetails?workoutId=" + workoutId);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}