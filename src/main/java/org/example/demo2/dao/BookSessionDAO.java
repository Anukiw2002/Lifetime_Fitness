package org.example.demo2.dao;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
