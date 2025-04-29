package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientDashboardDAO {
    public int getActiveMembers() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM client_membership WHERE is_cancelled= false";
        int count = -1;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                count =  rs.getInt("count");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<String> getMembershipsExpiringSoon() throws SQLException {
        String sql = "SELECT user_id, expiration_date FROM client_membership " +
                "WHERE is_cancelled = false AND expiration_date BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY)";
        List<String> expiringUsers = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                Date expirationDate = rs.getDate("expiration_date");
                expiringUsers.add("User ID " + userId + " membership expires on " + expirationDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expiringUsers;
    }


}
