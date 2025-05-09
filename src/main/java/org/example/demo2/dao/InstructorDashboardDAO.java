package org.example.demo2.dao;

import jakarta.servlet.jsp.jstl.sql.SQLExecutionTag;
import org.example.demo2.model.WorkoutCounts;
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
        String sql = "SELECT COUNT(*) AS count FROM bookings WHERE date = CURRENT_DATE ";
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

    public WorkoutCounts getDayWorkouts() throws SQLException{
        String sql = "SELECT " +
                "COUNT(*) FILTER (WHERE date = CURRENT_DATE) AS todays_count, " +
                "COUNT(*) FILTER (WHERE date = CURRENT_DATE - INTERVAL '1 day') AS yesterdays_count, " +
                "COUNT(*) FILTER (WHERE date = CURRENT_DATE + INTERVAL '1 day') AS tomorrows_count " +
                "FROM bookings";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            if (rs.next()){
                int today = rs.getInt("todays_count");
                int yesterday = rs.getInt("yesterdays_count");
                int tomorrow = rs.getInt("tomorrows_count");
                return new WorkoutCounts(today,yesterday, tomorrow);
            }
        }
        return new WorkoutCounts(0,0,0);
    }
}
