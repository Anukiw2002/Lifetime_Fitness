package org.example.demo2.dao;

import org.example.demo2.model.MembershipPlan;
import org.example.demo2.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipPlanDAO {
    private final DBConnection dbConnection;

    public MembershipPlanDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public MembershipPlan create(MembershipPlan plan) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "INSERT INTO membership_plans (plan_name, start_time, end_time, pricing_type, colour) VALUES (?, ?, ?, ?, ?) RETURNING plan_id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, plan.getPlanName());
                stmt.setTime(2, Time.valueOf(plan.getStartTime()));
                stmt.setTime(3, Time.valueOf(plan.getEndTime()));
                stmt.setString(4, plan.getPricingType());
                stmt.setString(5, plan.getColour());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    plan.setPlanId(rs.getLong("plan_id"));
                }
            }
            return plan;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public MembershipPlan findById(Long planId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT * FROM membership_plans WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, planId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    MembershipPlan plan = new MembershipPlan();
                    plan.setPlanId(rs.getLong("plan_id"));
                    plan.setPlanName(rs.getString("plan_name"));
                    plan.setStartTime(rs.getTime("start_time").toLocalTime());
                    plan.setEndTime(rs.getTime("end_time").toLocalTime());
                    plan.setPricingType(rs.getString("pricing_type"));
                    plan.setStatus(rs.getString("status")); // Make sure to get the status
                    plan.setColour(rs.getString("colour"));
                    return plan;
                }
            }
            return null;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<MembershipPlan> findAll() throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            List<MembershipPlan> plans = new ArrayList<>();
            String sql = "SELECT * FROM membership_plans";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    MembershipPlan plan = new MembershipPlan();
                    plan.setPlanId(rs.getLong("plan_id"));
                    plan.setPlanName(rs.getString("plan_name"));
                    plan.setStartTime(rs.getTime("start_time").toLocalTime());
                    plan.setEndTime(rs.getTime("end_time").toLocalTime());
                    plan.setPricingType(rs.getString("pricing_type"));
                    plan.setColour(rs.getString("colour"));
                    plan.setStatus(rs.getString("status"));
                    plans.add(plan);
                }
            }
            return plans;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update(MembershipPlan plan) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "UPDATE membership_plans SET plan_name = ?, start_time = ?, end_time = ?, pricing_type = ?, colour = ? WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, plan.getPlanName());
                stmt.setTime(2, Time.valueOf(plan.getStartTime()));
                stmt.setTime(3, Time.valueOf(plan.getEndTime()));
                stmt.setString(4, plan.getPricingType());
                stmt.setString(5, plan.getColour());
                stmt.setLong(6, plan.getPlanId());
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(Long planId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "DELETE FROM membership_plans WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, planId);
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    public boolean canDeletePlan(Long planId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            // Check for any associated records (adjust tables based on your schema)
            String sql = "SELECT EXISTS (" +
                    "SELECT 1 FROM memberships WHERE plan_id = ? " +
                    "UNION " +
                    "SELECT 1 FROM membership_transactions WHERE plan_id = ?" +
                    ")";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, planId);
                stmt.setLong(2, planId);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                return !rs.getBoolean(1); // Return true if no associated records exist
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deactivatePlan(Long planId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "UPDATE membership_plans SET status = 'INACTIVE' WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, planId);
                stmt.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean isPlanNameExists(String planName) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM membership_plans WHERE plan_name = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, planName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public MembershipPlan findByPlanName(String planName) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT * FROM membership_plans WHERE plan_name = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, planName);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    MembershipPlan plan = new MembershipPlan();
                    plan.setPlanId(rs.getLong("plan_id"));
                    plan.setPlanName(rs.getString("plan_name"));
                    plan.setStartTime(rs.getTime("start_time").toLocalTime());
                    plan.setEndTime(rs.getTime("end_time").toLocalTime());
                    plan.setPricingType(rs.getString("pricing_type"));
                    return plan;
                }
            }
            return null;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void updateStatus(Long planId, String status) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            // Begin transaction
            connection.setAutoCommit(false);

            String sql = "UPDATE membership_plans SET status = ? WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setLong(2, planId);

                int rowsAffected = stmt.executeUpdate();
                System.out.println("Rows affected by status update: " + rowsAffected);

                if (rowsAffected == 0) {
                    throw new SQLException("Failed to update status - no rows affected");
                }

                // Commit the transaction
                connection.commit();
            } catch (SQLException e) {
                // Rollback in case of error
                if (connection != null) {
                    connection.rollback();
                }
                throw e;
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // Reset auto-commit
                connection.close();
            }
        }
    }
    public String getStatus(Long planId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT status FROM membership_plans WHERE plan_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, planId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getString("status");
                }
                return null;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
