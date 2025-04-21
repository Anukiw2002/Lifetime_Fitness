package org.example.demo2.dao;

import org.example.demo2.model.BookingConstraints;
import org.example.demo2.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingConstraintsDAO {

    // Inserting the constraints (fallback if no record exists)
    public boolean insertConstraints(int cancelLimitMinutes, int rescheduleLimitMinutes, int minBookingGapMins,
                                     int maxBookingAdvanceWeeks, boolean showBookingCount, int maxBookingsPerSlot) {
        String sql = "INSERT INTO bookingConstraints (cancelLimitMinutes, rescheduleLimitMinutes, minBookingGapMins, " +
                "maxBookingAdvanceWeeks, showBookingCount, maxBookingsPerSlot) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, cancelLimitMinutes);
            pstmt.setInt(2, rescheduleLimitMinutes);
            pstmt.setInt(3, minBookingGapMins);
            pstmt.setInt(4, maxBookingAdvanceWeeks);
            pstmt.setBoolean(5, showBookingCount);
            pstmt.setInt(6, maxBookingsPerSlot);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the latest constraints
    public BookingConstraints getLatestConstraints() {
        String sql = "SELECT * FROM bookingConstraints ORDER BY constraintId DESC LIMIT 1";
        BookingConstraints constraints = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                constraints = new BookingConstraints();
                constraints.setConstraintId(rs.getInt("constraintId"));
                constraints.setCancelLimitMinutes(rs.getInt("cancelLimitMinutes"));
                constraints.setRescheduleLimitMinutes(rs.getInt("rescheduleLimitMinutes"));
                constraints.setMinBookingGapMins(rs.getInt("minBookingGapMins"));
                constraints.setMaxBookingAdvanceWeeks(rs.getInt("maxBookingAdvanceWeeks"));
                constraints.setShowBookingCount(rs.getBoolean("showBookingCount"));
                constraints.setMaxBookingsPerSlot(rs.getInt("maxBookingsPerSlot"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return constraints;
    }

    // Update existing constraints
    public boolean updateConstraints(int constraintId, int cancelLimitMinutes, int rescheduleLimitMinutes,
                                     int minBookingGapMins, int maxBookingAdvanceWeeks,
                                     boolean showBookingCount, int maxBookingsPerSlot) {
        String sql = "UPDATE bookingConstraints SET cancelLimitMinutes = ?, rescheduleLimitMinutes = ?, " +
                "minBookingGapMins = ?, maxBookingAdvanceWeeks = ?, showBookingCount = ?, " +
                "maxBookingsPerSlot = ? WHERE constraintId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, cancelLimitMinutes);
            pstmt.setInt(2, rescheduleLimitMinutes);
            pstmt.setInt(3, minBookingGapMins);
            pstmt.setInt(4, maxBookingAdvanceWeeks);
            pstmt.setBoolean(5, showBookingCount);
            pstmt.setInt(6, maxBookingsPerSlot);
            pstmt.setInt(7, constraintId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addBlockedDates(LocalDate blockDate, LocalTime startTime, LocalTime endTime, boolean isFullDay, String reason) {
        String sql = "INSERT INTO blocked_dates(block_date, start_time, end_time, is_full_day, reason) VALUES (?,?,?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setObject(1,blockDate);

            if(isFullDay) {
                pstmt.setNull(2, Types.TIME);
                pstmt.setNull(3, Types.TIME);
            }
            else {
                pstmt.setObject(2,startTime);
                pstmt.setObject(3,endTime);
            }

            pstmt.setBoolean(4,isFullDay);
            pstmt.setString(5,reason);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected >0;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}