package org.example.demo2.dao;

import jakarta.servlet.jsp.jstl.sql.SQLExecutionTag;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorDashboardDAO {
    public int getActiveMembers() throws SQLException {
        int count = -1;
        String sql = "SELECT COUNT(*) AS count FROM client_membership WHERE is_cancelled = false";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql) ){
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getWorkouts() throws SQLException{
        int count = -1;
        String sql = "SELECT COUNT(*) AS count FROM client_workouts WHERE created_at = CURRENT_DATE";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                count = rs.getInt("count");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public int getReports() throws SQLException{
        int count = -1;
        String sql = "SELECT COUNT(*) AS count FROM user_reports";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                count = rs.getInt("count");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }
}
