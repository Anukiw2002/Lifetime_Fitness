package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/processReport")
public class ReportServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/auth_db";  // Update with your DB URL
    private static final String USER = "postgres";  // Update with your DB username
    private static final String PASS = "Ishn@2002";  // Update with your DB password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data (User's report details
        String name = request.getParameter("name") != null ? request.getParameter("name") : "";
        int age = request.getParameter("age") != null && !request.getParameter("age").isEmpty() ? Integer.parseInt(request.getParameter("age")) : 0;
        String programNo = request.getParameter("program_no") != null ? request.getParameter("program_no") : "";
        String startingDate = request.getParameter("starting_date") != null && !request.getParameter("starting_date").isEmpty() ? request.getParameter("starting_date") : "";
        String expireDate = request.getParameter("expire_date") != null && !request.getParameter("expire_date").isEmpty() ? request.getParameter("expire_date") : "";
        String frequency = request.getParameter("frequency") != null ? request.getParameter("frequency") : "";
        int timesPerWeek = request.getParameter("times_per_week") != null && !request.getParameter("times_per_week").isEmpty() ? Integer.parseInt(request.getParameter("times_per_week")) : 0;
        int maxHeartRate = request.getParameter("max_heart_rate") != null && !request.getParameter("max_heart_rate").isEmpty() ? Integer.parseInt(request.getParameter("max_heart_rate")) : 0;
        int bpm65 = request.getParameter("bpm_65") != null && !request.getParameter("bpm_65").isEmpty() ? Integer.parseInt(request.getParameter("bpm_65")) : 0;
        int bpm75 = request.getParameter("bpm_75") != null && !request.getParameter("bpm_75").isEmpty() ? Integer.parseInt(request.getParameter("bpm_75")) : 0;
        int bpm85 = request.getParameter("bpm_85") != null && !request.getParameter("bpm_85").isEmpty() ? Integer.parseInt(request.getParameter("bpm_85")) : 0;
        double waistCircumference = request.getParameter("waist_circumference") != null && !request.getParameter("waist_circumference").isEmpty() ? Double.parseDouble(request.getParameter("waist_circumference")) : 0.0;
        double bodyWeight = request.getParameter("body_weight") != null && !request.getParameter("body_weight").isEmpty() ? Double.parseDouble(request.getParameter("body_weight")) : 0.0;
        double height = request.getParameter("height") != null && !request.getParameter("height").isEmpty() ? Double.parseDouble(request.getParameter("height")) : 0.0;
        double fat = request.getParameter("fat") != null && !request.getParameter("fat").isEmpty() ? Double.parseDouble(request.getParameter("fat")) : 0.0;
        double bmr = request.getParameter("bmr") != null && !request.getParameter("bmr").isEmpty() ? Double.parseDouble(request.getParameter("bmr")) : 0.0;
        String goal = request.getParameter("goal") != null ? request.getParameter("goal") : "";
        String warmUp = request.getParameter("warm_up") != null ? request.getParameter("warm_up") : "";
        String flexibility = request.getParameter("flexibility") != null ? request.getParameter("flexibility") : "";

        // Retrieve the resistance training data dynamically
        List<String> exerciseNames = new ArrayList<>();
        List<Integer> reps = new ArrayList<>();
        List<Integer> sets = new ArrayList<>();
        List<String> exerciseDates = new ArrayList<>();
        List<String> rests = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        // Loop through the dynamically generated form fields
        int i = 1;
        while (request.getParameter("exercise_" + i) != null) {
            exerciseNames.add(request.getParameter("exercise_" + i) != null ? request.getParameter("exercise_" + i) : "");
            reps.add(request.getParameter("reps_" + i) != null && !request.getParameter("reps_" + i).isEmpty() ? Integer.parseInt(request.getParameter("reps_" + i)) : 0);
            sets.add(request.getParameter("sets_" + i) != null && !request.getParameter("sets_" + i).isEmpty() ? Integer.parseInt(request.getParameter("sets_" + i)) : 0);
            exerciseDates.add(request.getParameter("date_" + i) != null ? request.getParameter("date_" + i) : "");
            rests.add(request.getParameter("rest_" + i) != null ? request.getParameter("rest_" + i) : "");
            weights.add(request.getParameter("weight_" + i) != null && !request.getParameter("weight_" + i).isEmpty() ? Double.parseDouble(request.getParameter("weight_" + i)) : 0.0);
            i++;
        }

        // Retrieve remaining fields (Cardio, Flexibility, and Remarks)
        String cardio = request.getParameter("cardio") != null ? request.getParameter("cardio") : "";
        String flexibility2 = request.getParameter("flexibility_2") != null ? request.getParameter("flexibility_2") : "";
        String remarks = request.getParameter("remarks") != null ? request.getParameter("remarks") : "";

        // Process data (e.g., save to database)
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Step 1: Insert the user report data into the user_reports table
            String insertReportQuery = "INSERT INTO user_reports (name, age, program_no, starting_date, expire_date, frequency, " +
                    "times_per_week, max_heart_rate, bpm_65, bpm_75, bpm_85, waist_circumference, body_weight, height, fat, bmr, goal, " +
                    "warm_up, flexibility, cardio, flexibility_2, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(insertReportQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.setString(3, programNo);
                pstmt.setDate(4, startingDate.isEmpty() ? null : Date.valueOf(startingDate));
                pstmt.setDate(5, expireDate.isEmpty() ? null : Date.valueOf(expireDate));
                pstmt.setString(6, frequency);
                pstmt.setInt(7, timesPerWeek);
                pstmt.setInt(8, maxHeartRate);
                pstmt.setInt(9, bpm65);
                pstmt.setInt(10, bpm75);
                pstmt.setInt(11, bpm85);
                pstmt.setDouble(12, waistCircumference);
                pstmt.setDouble(13, bodyWeight);
                pstmt.setDouble(14, height);
                pstmt.setDouble(15, fat);
                pstmt.setDouble(16, bmr);
                pstmt.setString(17, goal);
                pstmt.setString(18, warmUp);
                pstmt.setString(19, flexibility);
                pstmt.setString(20, cardio);
                pstmt.setString(21, flexibility2);
                pstmt.setString(22, remarks);

                pstmt.executeUpdate();

                // Retrieve the generated report ID (auto-generated primary key)
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                int reportId = 0;
                if (generatedKeys.next()) {
                    reportId = generatedKeys.getInt(1);
                }

                // Step 2: Insert each exercise entry into the user_exercises table
                String insertExerciseQuery = "INSERT INTO user_exercises (report_id, exercise_name, reps, sets, exercise_date, rest, weight) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                for (int j = 0; j < exerciseNames.size(); j++) {
                    try (PreparedStatement exerciseStmt = conn.prepareStatement(insertExerciseQuery)) {
                        exerciseStmt.setInt(1, reportId);
                        exerciseStmt.setString(2, exerciseNames.get(j));
                        exerciseStmt.setInt(3, reps.get(j));
                        exerciseStmt.setInt(4, sets.get(j));
                        exerciseStmt.setDate(5, exerciseDates.get(j).isEmpty() ? null : Date.valueOf(exerciseDates.get(j)));
                        exerciseStmt.setString(6, rests.get(j));
                        exerciseStmt.setDouble(7, weights.get(j));
                        exerciseStmt.executeUpdate();
                    }
                }
            }

            // Set a success message as a request attribute
            request.setAttribute("message", "User report submitted successfully!");

        } catch (SQLException e) {
            // Print the stack trace for debugging
            e.printStackTrace();

            // Log the error message to the request attribute for better feedback to the user
            request.setAttribute("message", "Error occurred while submitting the report: " + e.getMessage());

            // Optionally, log the error to a file (you could set up a logger for that)
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();  // Log the full stack trace to the console or log file
        }

        // Redirect to a confirmation page or show success message
        request.getRequestDispatcher("/jsp/reportConfirmation.jsp").forward(request, response);
    }
}

