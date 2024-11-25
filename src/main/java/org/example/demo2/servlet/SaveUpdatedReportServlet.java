package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/saveUpdatedReport")
public class SaveUpdatedReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Prepare a JSON object to send the response
        JSONObject jsonResponse = new JSONObject();
        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            System.out.println("Email is missing in the request.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email parameter is required.");
            return;
        }
        System.out.println("Email being used for update: " + email);

        // Step 1: Retrieve updated report details with validation
        String name = request.getParameter("name");
        int age = parseInteger(request.getParameter("age"), 0);
        String programNo = request.getParameter("program_no");
        String startingDate = request.getParameter("starting_date");
        String expireDate = request.getParameter("expire_date");
        String frequency = request.getParameter("frequency");
        int timesPerWeek = parseInteger(request.getParameter("times_per_week"), 0);
        int maxHeartRate = parseInteger(request.getParameter("max_heart_rate"), 0);
        int bpm65 = parseInteger(request.getParameter("bpm_65"), 0);
        int bpm75 = parseInteger(request.getParameter("bpm_75"), 0);
        int bpm85 = parseInteger(request.getParameter("bpm_85"), 0);
        double waistCircumference = parseDouble(request.getParameter("waist_circumference"), 0.0);
        double bodyWeight = parseDouble(request.getParameter("body_weight"), 0.0);
        double height = parseDouble(request.getParameter("height"), 0.0);
        double fatPercentage = parseDouble(request.getParameter("fat_percentage"), 0.0);
        double bmr = parseDouble(request.getParameter("bmr"), 0.0);
        String goal = request.getParameter("goal");
        String cardio = request.getParameter("cardio");
        String remarks = request.getParameter("remarks");

        // Step 2: Retrieve updated exercise details with validation
        String[] exerciseNames = request.getParameterValues("exercise_name[]");
        String[] reps = request.getParameterValues("reps[]");
        String[] sets = request.getParameterValues("sets[]");
        String[] exerciseDates = request.getParameterValues("exercise_date[]");
        String[] rests = request.getParameterValues("rest[]");
        String[] weights = request.getParameterValues("weight[]");

        System.out.println("Email being used for update: " + email);


        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // Step 3: Update the report details in `user_reports`
                String updateReportQuery = "UPDATE user_reports SET name = ?, age = ?, program_no = ?, starting_date = ?, expire_date = ?, " +
                        "frequency = ?, times_per_week = ?, max_heart_rate = ?, bpm_65 = ?, bpm_75 = ?, bpm_85 = ?, " +
                        "waist_circumference = ?, body_weight = ?, height = ?, fat_percentage = ?, bmr = ?, goal = ?, " +
                        " cardio = ?, remarks = ? WHERE email = ?";
                PreparedStatement reportStmt = conn.prepareStatement(updateReportQuery);
                reportStmt.setString(1, name);
                reportStmt.setInt(2, age);
                reportStmt.setString(3, programNo);
                reportStmt.setDate(4, parseDate(startingDate));
                reportStmt.setDate(5, parseDate(expireDate));
                reportStmt.setString(6, frequency);
                reportStmt.setInt(7, timesPerWeek);
                reportStmt.setInt(8, maxHeartRate);
                reportStmt.setInt(9, bpm65);
                reportStmt.setInt(10, bpm75);
                reportStmt.setInt(11, bpm85);
                reportStmt.setDouble(12, waistCircumference);
                reportStmt.setDouble(13, bodyWeight);
                reportStmt.setDouble(14, height);
                reportStmt.setDouble(15, fatPercentage);
                reportStmt.setDouble(16, bmr);
                reportStmt.setString(17, goal);
                reportStmt.setString(18, cardio);
                reportStmt.setString(19, remarks);
                reportStmt.setString(20, email);

                int rowsUpdated = reportStmt.executeUpdate();
                System.out.println("Updated rows in user_reports: " + rowsUpdated);

                // Step 4: Update or insert exercises in `user_exercises`
                String updateExerciseQuery = "UPDATE user_exercises SET reps = ?, sets = ?, exercise_date = ?, rest = ?, weight = ? " +
                        "WHERE exercise_name = ? AND email = ?";
                PreparedStatement exerciseStmt = conn.prepareStatement(updateExerciseQuery);

                for (int i = 0; i < exerciseNames.length; i++) {
                    exerciseStmt.setInt(1, parseInteger(reps[i], 0));
                    exerciseStmt.setInt(2, parseInteger(sets[i], 0));
                    exerciseStmt.setDate(3, parseDate(exerciseDates[i]));
                    exerciseStmt.setString(4, rests[i]);
                    exerciseStmt.setDouble(5, parseDouble(weights[i], 0.0));
                    exerciseStmt.setString(6, exerciseNames[i]);
                    exerciseStmt.setString(7, email);
                    exerciseStmt.addBatch();
                }
                int[] exerciseUpdates = exerciseStmt.executeBatch();
                System.out.println("Updated rows in user_exercises: " + exerciseUpdates.length);

                conn.commit(); // Commit transaction

                // Prepare success JSON response
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Report updated successfully!");
                jsonResponse.put("redirectUrl", request.getContextPath() + "/viewReport?email=" + email);

            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                e.printStackTrace();
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "An error occurred while updating the report.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Database connection error.");
        }

        // Send JSON response
        try (PrintWriter out = response.getWriter()) {
            out.write(jsonResponse.toString());
        }
    }

    // Helper method to parse integers safely
    private int parseInteger(String value, int defaultValue) {
        try {
            return value != null && !value.trim().isEmpty() ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Helper method to parse doubles safely
    private double parseDouble(String value, double defaultValue) {
        try {
            return value != null && !value.trim().isEmpty() ? Double.parseDouble(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Helper method to parse dates safely
    private Date parseDate(String value) {
        try {
            return value != null && !value.trim().isEmpty() ? Date.valueOf(value) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
