package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


@WebServlet("/viewReport")
public class ViewReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        String email = request.getParameter("email");


        if (email == null || email.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM user_reports WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                Map<String, Object> reportDetails = new HashMap<>();
                reportDetails.put("email", email); // Include email explicitly
                reportDetails.put("name", rs.getString("name"));
                reportDetails.put("age", rs.getInt("age"));
                reportDetails.put("program_no", rs.getString("program_no"));
                reportDetails.put("starting_date", rs.getDate("starting_date"));
                reportDetails.put("expire_date", rs.getDate("expire_date"));
                reportDetails.put("frequency", rs.getString("frequency"));
                reportDetails.put("times_per_week", rs.getInt("times_per_week"));
                reportDetails.put("max_heart_rate", rs.getInt("max_heart_rate"));
                reportDetails.put("bpm_65", rs.getInt("bpm_65"));
                reportDetails.put("bpm_75", rs.getInt("bpm_75"));
                reportDetails.put("bpm_85", rs.getInt("bpm_85"));
                reportDetails.put("waist_circumference", rs.getDouble("waist_circumference"));
                reportDetails.put("body_weight", rs.getDouble("body_weight"));
                reportDetails.put("height", rs.getDouble("height"));
                reportDetails.put("fat_percentage", rs.getDouble("fat_percentage"));
                reportDetails.put("bmr", rs.getDouble("bmr"));
                reportDetails.put("goal", rs.getString("goal"));
                reportDetails.put("warm_up", rs.getString("warm_up"));
                reportDetails.put("cardio", rs.getString("cardio"));
                reportDetails.put("remarks", rs.getString("remarks"));
                reportDetails.put("target_weight", rs.getDouble("target_weight"));


                request.setAttribute("reportDetails", reportDetails);

                String exerciseQuery = "SELECT  exercise_date, weight FROM user_exercises WHERE email = ?";
                PreparedStatement exerciseStmt = con.prepareStatement(exerciseQuery);
                exerciseStmt.setString(1, email);

                ResultSet exerciseRs = exerciseStmt.executeQuery();
                List<Map<String, Object>> exercises = new ArrayList<>();

                while (exerciseRs.next()) {
                    Map<String, Object> exercise = new HashMap<>();
                    exercise.put("exercise_date", exerciseRs.getDate("exercise_date"));
                    exercise.put("weight", exerciseRs.getDouble("weight"));
                    exercises.add(exercise);
                }

                request.setAttribute("exercises", exercises);


                request.getRequestDispatcher("/jsp/filledReport.jsp").forward(request, response);
            } else {

                request.setAttribute("errorMessage", "No report found for the given email.");
                request.getRequestDispatcher("/WEB-INF/views/owner/first.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching report details.");
        }
    }
}
