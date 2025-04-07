package org.example.demo2.dao;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;

import java.sql.*;

public class BookSessionDAO {

    public void getStartEndTime(int userId, HttpSession session) {
        String sql = "SELECT mp.start_time, mp.end_time FROM membership_plans mp " +
                "INNER JOIN durations d ON mp.plan_id = d.plan_id " +
                "INNER JOIN client_membership cm ON cm.duration_id = d.duration_id " +
                "WHERE cm.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");

                // Set these in session
                session.setAttribute("startTime", startTime);
                session.setAttribute("endTime", endTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createSessionBooking(Date date, Time timeSlot, String status, int userId){
        String sql = "INSERT INTO bookings (date, timeSlot, status, userId) VALUES (?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setDate(1, date);
            pstmt.setTime(2, timeSlot);
            pstmt.setString(3, status);
            pstmt.setInt(4, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logger in real apps
            return false;
        }
    }

}

