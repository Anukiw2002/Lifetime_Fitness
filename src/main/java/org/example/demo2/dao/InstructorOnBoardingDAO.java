package org.example.demo2.dao;

import org.example.demo2.model.User;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorOnBoardingDAO {
    public boolean addInstructor(User user) {
        String sql = "INSERT INTO users (full_name, username, email, hashed_password, role) VALUES (?,?,?,?,?) RETURNING id";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getHashedPassword());
            pstmt.setString(5, user.getRole());

            // Use executeQuery when expecting results back
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                int newUserId = rs.getInt("id");
                // Set the newly created instructor's status
                return setInstructorStatus(newUserId, "in-progress");
            }
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fixed the method name typo and parameter indexing
    public boolean setInstructorStatus(int userId, String onBoardingStatus){
        String sql= "INSERT INTO instructors (userId, onboardingStatus) VALUES (?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, onBoardingStatus);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getOnBoardingStatus(int userId) throws SQLException{
        String status = "completed";
        String sql = "SELECT onBoardingStatus FROM instructors where userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1,userId);

            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("onboardingStatus");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean resetPassword(String password, int userId) {
        String sql="UPDATE users SET hashed_password = ? WHERE id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1,password);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected>0;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
