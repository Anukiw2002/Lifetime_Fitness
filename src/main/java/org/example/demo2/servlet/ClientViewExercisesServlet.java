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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/client/ViewExercises")
public class ClientViewExercisesServlet extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String workoutId = request.getParameter("workoutId");

        try {
            if (workoutId == null || workoutId.trim().isEmpty()) {
                response.sendRedirect("clientWorkouts");
                return;
            }

            // Load the workout with exercises
            ClientWorkout workout = clientWorkoutDAO.findWithExercises(Long.parseLong(workoutId));
            if (workout == null) {
                response.sendRedirect("clientWorkouts");
                return;
            }

            List<Exercise> availableExercises = exerciseDAO.findAll();

            request.setAttribute("workout", workout);
            request.getRequestDispatcher("/WEB-INF/views/client/clientWorkout-details.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        } catch (NumberFormatException e) {
            response.sendRedirect("clientWorkouts");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String workoutIdStr = request.getParameter("workoutId");
            if (workoutIdStr == null || workoutIdStr.trim().isEmpty()) {
                throw new ServletException("Workout ID is required");
            }
            Long workoutId = Long.parseLong(workoutIdStr);

            String[] exerciseIds = request.getParameterValues("exerciseIds");
            String[] setNumbers = request.getParameterValues("setNumbers");
            String[] reps = request.getParameterValues("reps");
            String[] notes = request.getParameterValues("notes");


            if (exerciseIds == null || setNumbers == null || reps == null || notes == null ||
                    exerciseIds.length != setNumbers.length || exerciseIds.length != reps.length ||
                    exerciseIds.length != notes.length) {
                throw new ServletException("Invalid form data submitted");
            }

            List<Long> uniqueExerciseIds = new ArrayList<>();
            for (String exerciseId : exerciseIds) {
                if (exerciseId != null && !exerciseId.trim().isEmpty()) {
                    Long parsedExerciseId = Long.parseLong(exerciseId.trim());
                    if (uniqueExerciseIds.contains(parsedExerciseId)) {
                        request.setAttribute("errorMessage", "Duplicate exercises are not allowed.");
                        doGet(request, response); // Reload the page with an error message
                        return;
                    }
                    uniqueExerciseIds.add(parsedExerciseId);
                }
            }

            workoutExerciseDAO.deleteByWorkoutId(workoutId);

            for (int i = 0; i < exerciseIds.length; i++) {

                if (exerciseIds[i] == null || exerciseIds[i].trim().isEmpty() ||
                        setNumbers[i] == null || setNumbers[i].trim().isEmpty() ||
                        reps[i] == null || reps[i].trim().isEmpty()) {
                    continue;
                }

                WorkoutExercise workoutExercise = new WorkoutExercise();
                workoutExercise.setWorkoutId(workoutId);
                workoutExercise.setExerciseId(Long.parseLong(exerciseIds[i].trim()));
                workoutExercise.setSetNumber(Integer.parseInt(setNumbers[i].trim()));
                workoutExercise.setReps(Integer.parseInt(reps[i].trim()));
                workoutExercise.setNotes(notes[i] != null ? notes[i].trim() : "");

                workoutExerciseDAO.create(workoutExercise);
            }

            // Get the workout to get client phone for redirection
            ClientWorkout workout = clientWorkoutDAO.findWithExercises(workoutId);
            response.sendRedirect(request.getContextPath() +
                    "/instructor/workoutDetails?workoutId=" + workoutId);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format in form data", e);
        }
    }
}