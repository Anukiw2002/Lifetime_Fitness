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
import java.util.Enumeration;

@WebServlet("/instructor/createWorkout")
public class CreateWorkoutServlet extends HttpServlet {
    private final DBConnection dbConnection = new DBConnection();
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
        try {
            // Extract basic workout info
            String clientPhone = request.getParameter("clientPhone");
            String workoutName = request.getParameter("workoutName");
            Long categoryId = Long.parseLong(request.getParameter("categoryId"));

            // Debug logging
            System.out.println("Creating workout with name: " + workoutName);
            System.out.println("For client: " + clientPhone);
            System.out.println("Category ID: " + categoryId);

            // Create and save the workout
            ClientWorkout workout = new ClientWorkout(clientPhone, workoutName, categoryId, 1L); // Assuming instructor_id = 1
            ClientWorkoutDAO workoutDAO = new ClientWorkoutDAO(dbConnection);
            workout = workoutDAO.create(workout);

            if (workout != null && workout.getWorkoutId() != null) {
                System.out.println("Workout created with ID: " + workout.getWorkoutId());

                // Inside your doPost method, replace the exercise processing section with this:

// Process exercises
                List<WorkoutExercise> exercises = new ArrayList<>();
                int index = 0;
                boolean hasMoreExercises = true;

                while (hasMoreExercises) {
                    String exerciseIdParam = request.getParameter("exercises[" + index + "].exerciseId");
                    if (exerciseIdParam == null) {
                        hasMoreExercises = false;
                        continue;
                    }

                    String setNumberParam = request.getParameter("exercises[" + index + "].setNumber");
                    String repsParam = request.getParameter("exercises[" + index + "].reps");
                    String notesParam = request.getParameter("exercises[" + index + "].notes");

                    System.out.println("Processing exercise at index " + index);
                    System.out.println("Exercise ID: " + exerciseIdParam);
                    System.out.println("Sets: " + setNumberParam);
                    System.out.println("Reps: " + repsParam);

                    try {
                        WorkoutExercise exercise = new WorkoutExercise(
                                workout.getWorkoutId(),
                                Long.parseLong(exerciseIdParam),
                                Integer.parseInt(setNumberParam),
                                Integer.parseInt(repsParam)
                        );
                        exercise.setNotes(notesParam);
                        exercises.add(exercise);
                        System.out.println("Added exercise to list");
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing exercise data at index " + index);
                        e.printStackTrace();
                    }

                    index++;
                }

                System.out.println("Total exercises to save: " + exercises.size());

// Save the exercises
                WorkoutExerciseDAO exerciseDAO = new WorkoutExerciseDAO(dbConnection);
                for (WorkoutExercise exercise : exercises) {
                    try {
                        WorkoutExercise saved = exerciseDAO.create(exercise);
                        System.out.println("Saved exercise: " + saved.getWorkoutExerciseId());
                    } catch (SQLException e) {
                        System.err.println("Error saving exercise: " + e.getMessage());
                        throw e;
                    }
                }
            }

            // Redirect back to client workouts page
            response.sendRedirect("clientWorkouts?phoneNumber=" + clientPhone);

        } catch (SQLException e) {
            System.err.println("Error creating workout: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error creating workout", e);
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