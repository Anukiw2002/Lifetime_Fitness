package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/saveUpdatedReport")
public class SaveUpdatedReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            System.out.println("Email is missing in the request.");
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Email parameter is required.");
            sendJsonResponse(response, jsonResponse);
            return;
        }

        System.out.println("Email being used for update: " + email);

        // Retrieve updated report details
        String name = request.getParameter("name");
        int age = parseInteger(request.getParameter("age"), 0);
        String programNo = request.getParameter("program_no");
        String startingDate = request.getParameter("starting_date");
        String expireDate = request.getParameter("expire_date");

        int maxHeartRate = parseInteger(request.getParameter("max_heart_rate"), 0);
        int bpm65 = parseInteger(request.getParameter("bpm_65"), 0);
        int bpm75 = parseInteger(request.getParameter("bpm_75"), 0);
        int bpm85 = parseInteger(request.getParameter("bpm_85"), 0);
        double waistCircumference = parseDouble(request.getParameter("waist_circumference"), 0.0);
        double bodyWeight = parseDouble(request.getParameter("body_weight"), 0.0);
        double height = parseDouble(request.getParameter("height"), 0.0);
        double fatPercentage = parseDouble(request.getParameter("fat_percentage"), 0.0);
        double bmr = parseDouble(request.getParameter("bmr"), 0.0);
        double targetWeight = parseDouble(request.getParameter("target_weight"), 0.0);
        String goal = request.getParameter("goal");
        String warmUp = request.getParameter("warm_up");
        String flexibility = request.getParameter("flexibility");
        String cardio = request.getParameter("cardio");
        String remarks = request.getParameter("remarks");

        // Get exercise data
        String[] exerciseDates = request.getParameterValues("exercise_date[]");
        String[] weights = request.getParameterValues("weight[]");

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // First, get the report_id for this user
                int reportId = 0;
                String getReportIdQuery = "SELECT id FROM user_reports WHERE email = ?";
                try (PreparedStatement idStmt = conn.prepareStatement(getReportIdQuery)) {
                    idStmt.setString(1, email);
                    try (ResultSet rs = idStmt.executeQuery()) {
                        if (rs.next()) {
                            reportId = rs.getInt("id");
                            System.out.println("Found report_id: " + reportId);
                        } else {
                            throw new SQLException("No report found for email: " + email);
                        }
                    }
                }

                // Update the report details
                String updateReportQuery = "UPDATE user_reports SET name = ?, age = ?, program_no = ?, " +
                        "starting_date = ?, expire_date = ?, max_heart_rate = ?, bpm_65 = ?, " +
                        "bpm_75 = ?, bpm_85 = ?, waist_circumference = ?, body_weight = ?, " +
                        "height = ?, fat_percentage = ?, bmr = ?, goal = ?, warm_up = ?, " +
                        "flexibility = ?, cardio = ?, remarks = ?, target_weight = ? WHERE email = ?";

                try (PreparedStatement reportStmt = conn.prepareStatement(updateReportQuery)) {
                    reportStmt.setString(1, name);
                    reportStmt.setInt(2, age);
                    reportStmt.setString(3, programNo);
                    reportStmt.setDate(4, parseDate(startingDate));
                    reportStmt.setDate(5, parseDate(expireDate));
                    reportStmt.setInt(6, maxHeartRate);
                    reportStmt.setInt(7, bpm65);
                    reportStmt.setInt(8, bpm75);
                    reportStmt.setInt(9, bpm85);
                    reportStmt.setDouble(10, waistCircumference);
                    reportStmt.setDouble(11, bodyWeight);
                    reportStmt.setDouble(12, height);
                    reportStmt.setDouble(13, fatPercentage);
                    reportStmt.setDouble(14, bmr);
                    reportStmt.setString(15, goal);
                    reportStmt.setString(16, warmUp);
                    reportStmt.setString(17, flexibility);
                    reportStmt.setString(18, cardio);
                    reportStmt.setString(19, remarks);
                    reportStmt.setDouble(20, targetWeight);
                    reportStmt.setString(21, email);

                    int rowsUpdated = reportStmt.executeUpdate();
                    System.out.println("Updated rows in user_reports: " + rowsUpdated);
                }

                // Handle exercise data
                if (exerciseDates != null && weights != null && exerciseDates.length > 0) {
                    // Delete existing exercises for this report
                    String deleteExercisesQuery = "DELETE FROM user_exercises WHERE email = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteExercisesQuery)) {
                        deleteStmt.setString(1, email);
                        deleteStmt.executeUpdate();
                    }

                    // Insert new exercise entries with report_id
                    String insertExerciseQuery = "INSERT INTO user_exercises (email, exercise_date, weight, report_id) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertExerciseQuery)) {
                        for (int i = 0; i < exerciseDates.length; i++) {
                            // Skip empty entries
                            if (exerciseDates[i] == null || exerciseDates[i].trim().isEmpty()) {
                                continue;
                            }

                            insertStmt.setString(1, email);
                            insertStmt.setDate(2, parseDate(exerciseDates[i]));
                            insertStmt.setDouble(3, parseDouble(weights[i], 0.0));
                            insertStmt.setInt(4, reportId);  // Set the report_id
                            insertStmt.addBatch();
                        }
                        int[] insertResults = insertStmt.executeBatch();
                        System.out.println("Inserted exercise entries: " + insertResults.length);
                    }
                }

                conn.commit(); // Commit transaction

                // Success response
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Report updated successfully!");
                jsonResponse.put("redirectUrl", request.getContextPath() + "/viewReport?email=" + email);

            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                e.printStackTrace();
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Database error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Database connection error: " + e.getMessage());
        }

        // Send JSON response
        sendJsonResponse(response, jsonResponse);
    }

    private void sendJsonResponse(HttpServletResponse response, JSONObject jsonResponse) throws IOException {
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
            System.out.println("Invalid date format: " + value);
            return null;
        }
    }
}