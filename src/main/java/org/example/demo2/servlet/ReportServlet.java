package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection; // Assuming this is your connection utility class

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/processReport1")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        // Forward to the form page
        request.getRequestDispatcher("/jsp/report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String userEmail = (String) request.getSession().getAttribute("userEmail");
        if (userEmail == null || userEmail.trim().isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/views/owner/first.jsp").forward(request, response);
            return;
        }

        // Collect form data
        String name = request.getParameter("name");
        int age = parseInteger(request.getParameter("age"));
        String programNo = request.getParameter("program_no");
        String startingDate = request.getParameter("starting_date");
        String expireDate = request.getParameter("expire_date");
        String frequency = request.getParameter("frequency");
        int timesPerWeek = parseInteger(request.getParameter("times_per_week"));
        int maxHeartRate = parseInteger(request.getParameter("max_heart_rate"));
        int bpm65 = parseInteger(request.getParameter("bpm_65"));
        int bpm75 = parseInteger(request.getParameter("bpm_75"));
        int bpm85 = parseInteger(request.getParameter("bpm_85"));
        double waistCircumference = parseDouble(request.getParameter("waist_circumference"));
        double bodyWeight = parseDouble(request.getParameter("body_weight"));
        double height = parseDouble(request.getParameter("height"));
        double fatPercentage = parseDouble(request.getParameter("fat"));
        double bmr = parseDouble(request.getParameter("bmr"));
        String goal = request.getParameter("goal");
        String cardio = request.getParameter("cardio");
        String remarks = request.getParameter("remarks");

        // Dynamic exercises
        List<String> exerciseNames = new ArrayList<>();
        List<Integer> reps = new ArrayList<>();
        List<Integer> sets = new ArrayList<>();
        List<String> exerciseDates = new ArrayList<>();
        List<String> rests = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        int i = 1;
        while (request.getParameter("exercise_" + i) != null) {
            exerciseNames.add(request.getParameter("exercise_" + i));
            reps.add(parseInteger(request.getParameter("reps_" + i)));
            sets.add(parseInteger(request.getParameter("sets_" + i)));
            exerciseDates.add(request.getParameter("date_" + i));
            rests.add(request.getParameter("rest_" + i));
            weights.add(parseDouble(request.getParameter("weight_" + i)));
            i++;
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Database connection successful!");
            }

            conn.setAutoCommit(false);

            try {
                // Insert report
                String insertReportQuery = "INSERT INTO user_reports (name, age, program_no, starting_date, expire_date, frequency, "
                        + "times_per_week, max_heart_rate, bpm_65, bpm_75, bpm_85, waist_circumference, body_weight, height, "
                        + "fat_percentage, bmr, goal, cardio, remarks, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertReportQuery, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, age);
                    pstmt.setString(3, programNo);
                    pstmt.setDate(4, startingDate == null || startingDate.isEmpty() ? null : Date.valueOf(startingDate));
                    pstmt.setDate(5, expireDate == null || expireDate.isEmpty() ? null : Date.valueOf(expireDate));
                    pstmt.setString(6, frequency);
                    pstmt.setInt(7, timesPerWeek);
                    pstmt.setInt(8, maxHeartRate);
                    pstmt.setInt(9, bpm65);
                    pstmt.setInt(10, bpm75);
                    pstmt.setInt(11, bpm85);
                    pstmt.setDouble(12, waistCircumference);
                    pstmt.setDouble(13, bodyWeight);
                    pstmt.setDouble(14, height);
                    pstmt.setDouble(15, fatPercentage);
                    pstmt.setDouble(16, bmr);
                    pstmt.setString(17, goal);
                    pstmt.setString(18, cardio);
                    pstmt.setString(19, remarks);
                    pstmt.setString(20, userEmail);

                    pstmt.executeUpdate();

                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    int reportId = 0;
                    if (generatedKeys.next()) {
                        reportId = generatedKeys.getInt(1);
                        System.out.println("Inserted report with ID: " + reportId);
                    }

                    // Insert exercises
                    String insertExerciseQuery = "INSERT INTO user_exercises (report_id, exercise_name, reps, sets, exercise_date, rest, weight, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement exerciseStmt = conn.prepareStatement(insertExerciseQuery)) {
                        for (int j = 0; j < exerciseNames.size(); j++) {
                            exerciseStmt.setInt(1, reportId);
                            exerciseStmt.setString(2, exerciseNames.get(j));
                            exerciseStmt.setInt(3, reps.get(j));
                            exerciseStmt.setInt(4, sets.get(j));
                            exerciseStmt.setDate(5, exerciseDates.get(j).isEmpty() ? null : Date.valueOf(exerciseDates.get(j)));
                            exerciseStmt.setString(6, rests.get(j));
                            exerciseStmt.setDouble(7, weights.get(j));
                            exerciseStmt.setString(8, userEmail);
                            exerciseStmt.addBatch();
                            System.out.println("Adding exercise: " + exerciseNames.get(j));
                        }
                        exerciseStmt.executeBatch();
                    }
                }

                conn.commit();
                System.out.println("Report and exercises inserted successfully!");
                request.setAttribute("message", "User report submitted successfully!");
                request.getRequestDispatcher("/first").forward(request, response);
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Transaction rolled back due to: " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int parseInteger(String value) {
        return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : 0;
    }

    private double parseDouble(String value) {
        return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : 0.0;
    }
}
