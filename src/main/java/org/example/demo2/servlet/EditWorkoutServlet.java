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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String workoutIdStr = request.getParameter("workoutId");

        try {
            if (workoutIdStr == null || workoutIdStr.trim().isEmpty() || !workoutIdStr.matches("\\d+")) {
                response.sendRedirect(request.getContextPath() + "/instructor/clientWorkouts");
                return;
            }
            Long workoutId = Long.parseLong(workoutIdStr);

            // Load workout with exercises
            ClientWorkout workout = clientWorkoutDAO.findById(workoutId);
            if (workout == null) {
                response.sendRedirect(request.getContextPath() + "/instructor/clientWorkouts");
                return;
            }

            List<WorkoutExercise> workoutExercises = workoutExerciseDAO.findByWorkoutId(workoutId);
            List<Exercise> availableExercises = exerciseDAO.findAll();

            request.setAttribute("workout", workout);
            request.setAttribute("workoutExercises", workoutExercises);
            request.setAttribute("availableExercises", availableExercises);
            request.getRequestDispatcher("/WEB-INF/views/instructor/edit-workout.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/instructor/clientWorkouts");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String workoutIdStr = request.getParameter("workoutId");
            if (workoutIdStr == null || workoutIdStr.trim().isEmpty() || !workoutIdStr.matches("\\d+")) {
                throw new ServletException("Invalid Workout ID");
            }
            Long workoutId = Long.parseLong(workoutIdStr);

            String[] exerciseIds = request.getParameterValues("exerciseIds");
            String[] setNumbers = request.getParameterValues("setNumbers");
            String[] reps = request.getParameterValues("reps");
            String[] notes = request.getParameterValues("notes");

            // Validate required fields
            if (exerciseIds == null || setNumbers == null || reps == null || notes == null ||
                    exerciseIds.length != setNumbers.length ||
                    exerciseIds.length != reps.length ||
                    exerciseIds.length != notes.length) {
                request.setAttribute("errorMessage", "All fields must be filled correctly.");
                doGet(request, response);
                return;
            }

            // Check for duplicate exercises
            List<Long> uniqueExerciseIds = new ArrayList<>();
            for (String exerciseId : exerciseIds) {
                if (exerciseId != null && !exerciseId.trim().isEmpty()) {
                    Long parsedExerciseId = Long.parseLong(exerciseId.trim());
                    if (uniqueExerciseIds.contains(parsedExerciseId)) {
                        request.setAttribute("errorMessage", "Duplicate exercises are not allowed.");
                        doGet(request, response);
                        return;
                    }
                    uniqueExerciseIds.add(parsedExerciseId);
                }
            }

            // Delete existing exercises
            workoutExerciseDAO.deleteByWorkoutId(workoutId);

            // Add exercises from the form
            for (int i = 0; i < exerciseIds.length; i++) {
                if (exerciseIds[i].trim().isEmpty() ||
                        setNumbers[i].trim().isEmpty() ||
                        reps[i].trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Exercise fields cannot be empty.");
                    doGet(request, response);
                    return;
                }

                WorkoutExercise workoutExercise = new WorkoutExercise();
                workoutExercise.setWorkoutId(workoutId);
                workoutExercise.setExerciseId(Long.parseLong(exerciseIds[i].trim()));
                workoutExercise.setSetNumber(Integer.parseInt(setNumbers[i].trim()));
                workoutExercise.setReps(Integer.parseInt(reps[i].trim()));
                workoutExercise.setNotes(notes[i] != null ? notes[i].trim() : "");

                workoutExerciseDAO.create(workoutExercise);
            }

            response.sendRedirect(request.getContextPath() + "/instructor/workoutDetails?workoutId=" + workoutId);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format in form data.");
            doGet(request, response);
        }
    }
}
