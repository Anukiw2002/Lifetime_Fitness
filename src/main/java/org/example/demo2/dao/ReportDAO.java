package org.example.demo2.dao;

import org.example.demo2.model.UserWeightData;
import org.example.demo2.model.Report;
import org.example.demo2.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
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

    public void insertExercises(Connection conn, int reportId, List<String> dates, List<Double> weights, String email) throws SQLException {

        String query = "INSERT INTO user_exercises  (report_id, exercise_date, weight, email) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 0; i < dates.size(); i++) {
                pstmt.setInt(1, reportId);
                pstmt.setDate(2, dates.get(i).isEmpty() ? null : Date.valueOf(dates.get(i)));
                pstmt.setDouble(3, weights.get(i));
                pstmt.setString(4, email);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public UserWeightData getWeightByEmail(String email){
        UserWeightData data = new UserWeightData();

        String reportQuery = "SELECT body_weight, target_weight FROM user_reports WHERE email = ? ";
        String exerciseQuery = "SELECT weight FROM user_exercises WHERE email=? ";

        try(Connection conn = DBConnection.getConnection()){
            try(PreparedStatement reportStmt = conn.prepareStatement(reportQuery)){
                reportStmt.setString(1,email);
                ResultSet rs = reportStmt.executeQuery();
                if(rs.next()){
                    data.setBeginningWeight(rs.getDouble("body_weight"));
                    data.setTargetWeight(rs.getDouble("target_weight"));
                }
            }

            try(PreparedStatement exerciseStmt = conn.prepareStatement(exerciseQuery)){
                exerciseStmt.setString(1, email);
                ResultSet rs = exerciseStmt.executeQuery();
                if (rs.next()){
                    data.setCurrentWeight(rs.getDouble("weight"));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public Report getById(int userID) {
        String sql = "SELECT r.body_weight, r.height, r.target_weight FROM users u INNER JOIN user_reports r ON r.email = u.email WHERE u.id = ?";
        Report report = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    report = new Report();
                    report.setBodyWeight(rs.getDouble("body_weight"));
                    report.setHeight(rs.getDouble("height"));
                    report.setTarget_weight(rs.getDouble("target_weight"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

}
