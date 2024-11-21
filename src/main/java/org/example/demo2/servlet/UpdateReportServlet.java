package org.example.demo2.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/updateReport")
public class UpdateReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String email = request.getParameter("email");

        if(email == null || email.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()){
            String reportQuery = "SELECT * FROM user_reports WHERE email = ?";
            PreparedStatement reportStmt = conn.prepareStatement(reportQuery);
            reportStmt.setString(1, email);

            ResultSet reportRs = reportStmt.executeQuery();
            Map<String, Object> reportDetails = new HashMap<>();

            if(reportRs.next()){
                reportDetails.put("email", email);
                reportDetails.put("name", reportRs.getString("name"));
                reportDetails.put("age", reportRs.getInt("age"));
                reportDetails.put("program_no", reportRs.getString("program_no"));
                reportDetails.put("starting_date", reportRs.getDate("starting_date"));
                reportDetails.put("expire_date", reportRs.getDate("expire_date"));
                reportDetails.put("frequency", reportRs.getString("frequency"));
                reportDetails.put("times_per_week", reportRs.getInt("times_per_week"));
                reportDetails.put("max_heart_rate", reportRs.getInt("max_heart_rate"));
                reportDetails.put("bpm_65", reportRs.getInt("bpm_65"));
                reportDetails.put("bpm_75", reportRs.getInt("bpm_75"));
                reportDetails.put("bpm_85", reportRs.getInt("bpm_85"));
                reportDetails.put("waist_circumference", reportRs.getDouble("waist_circumference"));
                reportDetails.put("body_weight", reportRs.getDouble("body_weight"));
                reportDetails.put("height", reportRs.getDouble("height"));
                reportDetails.put("fat_percentage", reportRs.getDouble("fat_percentage"));
                reportDetails.put("bmr", reportRs.getDouble("bmr"));
                reportDetails.put("goal", reportRs.getString("goal"));
                reportDetails.put("warm_up", reportRs.getString("warm_up"));
                reportDetails.put("flexibility", reportRs.getString("flexibility"));
                reportDetails.put("cardio", reportRs.getString("cardio"));
                reportDetails.put("remarks", reportRs.getString("remarks"));

            }

            request.setAttribute("reportDetails", reportDetails);

            String exerciseQuery = "SELECT * FROM user_exercises WHERE email = ?";
            PreparedStatement exerciseStmt = conn.prepareStatement(exerciseQuery);
            exerciseStmt.setString(1, email);

            ResultSet exerciseRs = exerciseStmt.executeQuery();
            List<Map<String, Object>> exercises = new ArrayList<>();

            while (exerciseRs.next()) {
                Map<String, Object> exercise = new HashMap<>();
                exercise.put("exercise_name", exerciseRs.getString("exercise_name"));
                exercise.put("reps", exerciseRs.getInt("reps"));
                exercise.put("sets", exerciseRs.getInt("sets"));
                exercise.put("exercise_date", exerciseRs.getDate("exercise_date"));
                exercise.put("rest", exerciseRs.getString("rest"));
                exercise.put("weight", exerciseRs.getDouble("weight"));
                exercises.add(exercise);
            }

            request.setAttribute("exercises", exercises);


            request.getRequestDispatcher("/jsp/updateReport.jsp").forward(request, response);

        }catch (SQLException e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching report details.");
        }

    }
}
