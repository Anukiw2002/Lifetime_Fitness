package org.example.demo2.dao;

import org.example.demo2.model.MedicalHistory;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalHistoryDAO {
    private Connection conn;

    public MedicalHistoryDAO() {
        try {
            this.conn = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();

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
            ps.setString(4, history.getChestPain());
            ps.setString(5, history.getBackPain());
            ps.setString(6, history.getBoneJointProblem());
            ps.setString(7, history.getBloodPressure());
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

    public MedicalHistory getMedicalHistory(String email){
        String sql = "SELECT * FROM medical_history WHERE user_email = ?";
        MedicalHistory medicalHistory = null;

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                medicalHistory  = new MedicalHistory(
                        rs.getString("user_email"),
                        rs.getString("medical_condition"),
                        rs.getString("takes_medication"),
                        rs.getString("chest_pain"),
                        rs.getString("back_pain"),
                        rs.getString("bone_joint_problem"),
                        rs.getString("blood_pressure"),
                        rs.getString("diabetes"),
                        rs.getString("stress_level"),
                        rs.getString("smoking"),
                        rs.getString("activity_level"),
                        rs.getString("exercise_objectives"),
                        rs.getString("other_conditions")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return medicalHistory;
    }

}
