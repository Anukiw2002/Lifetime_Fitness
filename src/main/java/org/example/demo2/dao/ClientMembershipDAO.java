package org.example.demo2.dao;

import org.example.demo2.model.ClientMembership;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientMembershipDAO {
    private final DBConnection dbConnection;

    public ClientMembershipDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<ClientMembership> getAllMemberships() throws SQLException {
        List<ClientMembership> memberships = new ArrayList<>();

        String sql = "SELECT cm.membership_id, cm.user_id, CONCAT (u.full_name, ' ',    u.username)  AS client_name, " +
                "mp.plan_name, cm.start_date, d.duration_value, d.duration_type, cm.is_cancelled " +
                "FROM client_membership cm " +
                "JOIN users u ON u.id = cm.user_id " +
                "JOIN membership_plans mp ON cm.plan_id = mp.plan_id " +
                "JOIN durations d ON mp.plan_id = d.plan_id " +
                "WHERE u.role = 'client'";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClientMembership cm = new ClientMembership();
                cm.setMembershipId(rs.getInt("membership_id"));
                cm.setUserId(rs.getInt("user_id"));
                cm.setClientName(rs.getString("client_name"));
                cm.setPlanName(rs.getString("plan_name"));

                // Convert SQL Date to LocalDate
                Date sqlStartDate = rs.getDate("start_date");
                LocalDate startDate = sqlStartDate.toLocalDate();
                cm.setStartDate(startDate);

                // Compute end date based on duration info
                int durationValue = rs.getInt("duration_value");
                String durationType = rs.getString("duration_type");
                LocalDate computedEndDate;
                if ("years".equalsIgnoreCase(durationType)) {
                    computedEndDate = startDate.plusYears(durationValue);
                } else if ("months".equalsIgnoreCase(durationType)) {
                    computedEndDate = startDate.plusMonths(durationValue);
                } else if ("days".equalsIgnoreCase(durationType)) {
                    computedEndDate = startDate.plusDays(durationValue);
                } else {
                    computedEndDate = startDate;
                }
                cm.setEndDate(computedEndDate);

                // Determine membership status dynamically
                boolean isCancelled = rs.getBoolean("is_cancelled");
                String status;
                if (isCancelled) {
                    status = "cancelled";
                } else if (LocalDate.now().isAfter(computedEndDate)) {
                    status = "expired";
                } else {
                    status = "active";
                }
                cm.setStatus(status);

                memberships.add(cm);
            }
        }

        return memberships;
    }
}
