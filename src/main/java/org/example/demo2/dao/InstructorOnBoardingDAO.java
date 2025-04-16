package org.example.demo2.dao;

import org.example.demo2.model.User;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstructorOnBoardingDAO {
    public boolean addInstructor(User user) {
        String sql = "INSERT INTO users (full_name, username, email, hashed_password, role) VALUES (?,?,?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getHashedPassword());
            pstmt.setString(5, user.getRole());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
