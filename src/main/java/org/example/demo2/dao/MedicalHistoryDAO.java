package org.example.demo2.dao;

import org.example.demo2.model.MedicalHistory;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicalHistoryDAO {
    private Connection conn;

    public MedicalHistoryDAO() {
        try {
            this.conn = DBConnection.getConnection(); // Establish the connection within a try block
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., logging it or throwing a runtime exception
        }
    }
    public boolean insertMedicalHistory(MedicalHistory history) {
        String sql = "INSERT INTO medical_history (user_email, medical_condition, takes_medication, chest_pain, back_pain," +
                "bone_joint_problem, blood_pressure, diabetes, stress_level, smoking, activity_level, exercise_objectives, other_conditions)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, history.getUserEmail());
            ps.setString(2, history.getMedicalCondition());
            ps.setString(3, history.getTakeMedication());
            ps.setString(4, history.isChestPain());
            ps.setString(5, history.isBackPain());
            ps.setString(6, history.getBoneJointProblem());
            ps.setString(7, history.isBloodPressure());
            ps.setString(8, history.getDiabetes());
            ps.setString(9, history.getStressLevel());
            ps.setString(10, history.getSmoking());
            ps.setString(11, history.getActivityLevel());
            ps.setString(12, history.getExerciseObjectives());
            ps.setString(13, history.getOtherConditions());

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }

}
