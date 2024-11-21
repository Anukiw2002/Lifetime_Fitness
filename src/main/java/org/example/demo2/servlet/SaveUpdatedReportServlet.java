package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/saveUpdatedReport")
public class SaveUpdatedReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Step 1: Retrieve updated report details
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String programNo = request.getParameter("program_no");
        String startingDate = request.getParameter("starting_date");
        String expireDate = request.getParameter("expire_date");
        String frequency = request.getParameter("frequency");
        int timesPerWeek = Integer.parseInt(request.getParameter("times_per_week"));
        int maxHeartRate = Integer.parseInt(request.getParameter("max_heart_rate"));
        int bpm65 = Integer.parseInt(request.getParameter("bpm_65"));
        int bpm75 = Integer.parseInt(request.getParameter("bpm_75"));
        int bpm85 = Integer.parseInt(request.getParameter("bpm_85"));
        double waistCircumference = Double.parseDouble(request.getParameter("waist_circumference"));
        double bodyWeight = Double.parseDouble(request.getParameter("body_weight"));
        double height = Double.parseDouble(request.getParameter("height"));
        double fatPercentage = Double.parseDouble(request.getParameter("fat_percentage"));
        double bmr = Double.parseDouble(request.getParameter("bmr"));
        String goal = request.getParameter("goal");
        String warmUp = request.getParameter("warm_up");
        String flexibility = request.getParameter("flexibility");
        String cardio = request.getParameter("cardio");
        String remarks = request.getParameter("remarks");

        // Step 2: Retrieve updated exercise details
        String[] exerciseNames = request.getParameterValues("exercise_name[]");
        String[] reps = request.getParameterValues("reps[]");
        String[] sets = request.getParameterValues("sets[]");
        String[] exerciseDates = request.getParameterValues("exercise_date[]");
        String[] rests = request.getParameterValues("rest[]");
        String[] weights = request.getParameterValues("weight[]");

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // Step 3: Update the report details in `user_reports`
                String updateReportQuery = "UPDATE user_reports SET name = ?, age = ?, program_no = ?, starting_date = ?, expire_date = ?, " +
                        "frequency = ?, times_per_week = ?, max_heart_rate = ?, bpm_65 = ?, bpm_75 = ?, bpm_85 = ?, " +
                        "waist_circumference = ?, body_weight = ?, height = ?, fat_percentage = ?, bmr = ?, goal = ?, " +
                        "warm_up = ?, flexibility = ?, cardio = ?, remarks = ? WHERE email = ?";
                PreparedStatement reportStmt = conn.prepareStatement(updateReportQuery);
                reportStmt.setString(1, name);
                reportStmt.setInt(2, age);
                reportStmt.setString(3, programNo);
                reportStmt.setDate(4, startingDate != null && !startingDate.isEmpty() ? Date.valueOf(startingDate) : null);
                reportStmt.setDate(5, expireDate != null && !expireDate.isEmpty() ? Date.valueOf(expireDate) : null);
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
                reportStmt.setString(18, warmUp);
                reportStmt.setString(19, flexibility);
                reportStmt.setString(20, cardio);
                reportStmt.setString(21, remarks);
                reportStmt.setString(22, email);

                int rowsUpdated = reportStmt.executeUpdate();
                System.out.println("Updated rows in user_reports: " + rowsUpdated);

                // Step 4: Update or insert exercises in `user_exercises`
                String updateExerciseQuery = "UPDATE user_exercises SET reps = ?, sets = ?, exercise_date = ?, rest = ?, weight = ? " +
                        "WHERE exercise_name = ? AND email = ?";
                PreparedStatement exerciseStmt = conn.prepareStatement(updateExerciseQuery);

                for (int i = 0; i < exerciseNames.length; i++) {
                    exerciseStmt.setInt(1, Integer.parseInt(reps[i]));
                    exerciseStmt.setInt(2, Integer.parseInt(sets[i]));
                    exerciseStmt.setDate(3, exerciseDates[i] != null && !exerciseDates[i].isEmpty() ? Date.valueOf(exerciseDates[i]) : null);
                    exerciseStmt.setString(4, rests[i]);
                    exerciseStmt.setDouble(5, Double.parseDouble(weights[i]));
                    exerciseStmt.setString(6, exerciseNames[i]);
                    exerciseStmt.setString(7, email);
                    exerciseStmt.addBatch();
                }
                int[] exerciseUpdates = exerciseStmt.executeBatch();
                System.out.println("Updated rows in user_exercises: " + exerciseUpdates.length);

                conn.commit(); // Commit transaction
                response.sendRedirect(request.getContextPath() + "/viewReport?email=" + email);
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating report.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error.");
        }
    }
}
