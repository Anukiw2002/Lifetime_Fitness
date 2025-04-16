package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserReportDAO {

    public List<Map<String, Object>> getAllReportsForUser(String email) throws Exception {
        List<Map<String, Object>> reportList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT program_no, starting_date, expire_date, goal FROM user_reports WHERE email = ? ORDER BY starting_date DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> report = new HashMap<>();
                report.put("program_no", rs.getString("program_no"));
                report.put("starting_date", rs.getDate("starting_date"));
                report.put("expire_date", rs.getDate("expire_date"));
                report.put("goal", rs.getString("goal"));
                reportList.add(report);
            }
        }

        return reportList;
    }

    public Map<String, Object> getReportDetails(String email, String programNo) throws Exception {
        Map<String, Object> reportDetails = null;

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM user_reports WHERE email = ? AND program_no = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, programNo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reportDetails = new HashMap<>();
                reportDetails.put("name", rs.getString("name"));
                reportDetails.put("age", rs.getInt("age"));
                reportDetails.put("program_no", rs.getString("program_no"));
                reportDetails.put("starting_date", rs.getDate("starting_date"));
                reportDetails.put("expire_date", rs.getDate("expire_date"));
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
                reportDetails.put("flexibility", rs.getString("flexibility"));
                reportDetails.put("cardio", rs.getString("cardio"));
                reportDetails.put("remarks", rs.getString("remarks"));
                reportDetails.put("target_weight", rs.getDouble("target_weight"));
            }
        }

        return reportDetails;
    }

    public List<Map<String, Object>> getExercisesForReport(String email, String programNo) throws Exception {
        List<Map<String, Object>> exercises = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String exerciseQuery = "SELECT exercise_name, reps, sets, exercise_date, rest, weight FROM user_exercises " +
                    "WHERE email = ? ";
            PreparedStatement exerciseStmt = con.prepareStatement(exerciseQuery);
            exerciseStmt.setString(1, email);

            ResultSet exerciseRs = exerciseStmt.executeQuery();

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
        }

        return exercises;
    }

    public Map<String, Object> getLatestReportForUser(String email) throws Exception {
        Map<String, Object> reportDetails = null;

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM user_reports WHERE email = ? ORDER BY starting_date DESC LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reportDetails = new HashMap<>();
                reportDetails.put("name", rs.getString("name"));
                reportDetails.put("age", rs.getInt("age"));
                reportDetails.put("program_no", rs.getString("program_no"));
                reportDetails.put("starting_date", rs.getDate("starting_date"));
                reportDetails.put("expire_date", rs.getDate("expire_date"));
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
                reportDetails.put("flexibility", rs.getString("flexibility"));
                reportDetails.put("cardio", rs.getString("cardio"));
                reportDetails.put("remarks", rs.getString("remarks"));
                reportDetails.put("target_weight", rs.getDouble("target_weight"));
            }
        }

        return reportDetails;
    }



}