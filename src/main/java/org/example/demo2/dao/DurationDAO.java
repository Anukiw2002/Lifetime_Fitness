package org.example.demo2.dao;

import org.example.demo2.model.Duration;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DurationDAO {
    private final DBConnection dbConnection;

    public DurationDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Duration create(Duration duration) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO durations (plan_id, duration_value, duration_type) VALUES (?, ?, ?) RETURNING duration_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, duration.getPlanId());
                stmt.setInt(2, duration.getDurationValue());
                stmt.setString(3, duration.getDurationType());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    duration.setDurationId(rs.getLong("duration_id"));
                }
            }
            return duration;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Duration> findByPlanId(Long planId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<Duration> durations = new ArrayList<>();
            String sql = "SELECT * FROM durations WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, planId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Duration duration = new Duration();
                    duration.setDurationId(rs.getLong("duration_id"));
                    duration.setPlanId(rs.getLong("plan_id"));
                    duration.setDurationValue(rs.getInt("duration_value"));
                    duration.setDurationType(rs.getString("duration_type"));
                    durations.add(duration);
                }
            }
            return durations;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
