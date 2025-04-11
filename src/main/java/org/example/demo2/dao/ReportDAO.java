package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.*;
import java.util.List;

public class ReportDAO {

    public int insertReport(Connection conn, String name, int age, String programNo, String startingDate, String expireDate, String frequency,
                            int timesPerWeek, int maxHeartRate, int bpm65, int bpm75, int bpm85,
                            double waistCircumference, double bodyWeight, double height, double fatPercentage,
                            double bmr, String goal, String warm_up, String flexibility, String cardio,
                            String remarks, String userEmail, double targetWeight) throws SQLException {

        String query = "INSERT INTO user_reports (name, age, program_no, starting_date, expire_date, frequency, "
                + "times_per_week, max_heart_rate, bpm_65, bpm_75, bpm_85, waist_circumference, body_weight, "
                + "height, fat_percentage, bmr, goal, warm_up, flexibility, cardio, remarks, email, target_weight) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
            pstmt.setString(18, warm_up);
            pstmt.setString(19, flexibility);
            pstmt.setString(20, cardio);
            pstmt.setString(21, remarks);
            pstmt.setString(22, userEmail);
            pstmt.setDouble(23, targetWeight);

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // report_id
                }
            }
        }

        return -1;
    }

    public void insertExercises(Connection conn, int reportId, List<String> names, List<Integer> reps, List<Integer> sets,
                                List<String> dates, List<String> rests, List<Double> weights, String email) throws SQLException {

        String query = "INSERT INTO user_exercises  (report_id, exercise_name, reps, sets, exercise_date, rest, weight, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < names.size(); i++) {
                pstmt.setInt(1, reportId);
                pstmt.setString(2, names.get(i));
                pstmt.setInt(3, reps.get(i));
                pstmt.setInt(4, sets.get(i));
                pstmt.setDate(5, dates.get(i).isEmpty() ? null : Date.valueOf(dates.get(i)));
                pstmt.setString(6, rests.get(i));
                pstmt.setDouble(7, weights.get(i));
                pstmt.setString(8, email);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}
