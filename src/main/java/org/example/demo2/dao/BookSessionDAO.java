package org.example.demo2.dao;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.BookSession;
import org.example.demo2.util.DBConnection;
import org.example.demo2.model.BookingConstraints;


import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public boolean createSessionBooking(Date date, Time timeSlot, String status, int userId) {

        BookingConstraintsDAO constraintsDAO = new BookingConstraintsDAO();
        BookingConstraints constraints = constraintsDAO.getLatestConstraints();

        int maxBookings = constraints.getMaxBookingsPerSlot();
        String countQuery = "SELECT COUNT(*) FROM bookings WHERE date = ? AND timeSlot = ? AND status = 'booked'";

        String insertQuery = "INSERT INTO bookings (date, timeSlot, status, userId) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            // Step 1: Check existing bookings
            try (PreparedStatement countStmt = con.prepareStatement(countQuery)) {
                countStmt.setDate(1, date);
                countStmt.setTime(2, timeSlot);

                ResultSet rs = countStmt.executeQuery();
                if (rs.next()) {
                    int currentBookings = rs.getInt(1);

                    // Step 2: Compare against the limit
                    if (currentBookings < maxBookings) {
                        // Step 3: Insert the booking
                        try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                            insertStmt.setDate(1, date);
                            insertStmt.setTime(2, timeSlot);
                            insertStmt.setString(3, status);
                            insertStmt.setInt(4, userId);

                            int rowsAffected = insertStmt.executeUpdate();
                            return rowsAffected > 0;
                        }
                    } else {
                        System.out.println("Maximum number of bookings for that session has been reached");
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }

        return false;
    }


    public boolean createRecurringSessionBooking(Date startDate, Time timeSlot, String status, int userId, String frequency) {
        BookingConstraintsDAO constraintsDAO = new BookingConstraintsDAO();
        BookingConstraints constraints = constraintsDAO.getLatestConstraints();

        if (constraints == null) {
            System.out.println("No booking constraints found.");
            return false;
        }

        int maxWeeks = constraints.getMaxBookingAdvanceWeeks();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        Calendar latestAllowed = Calendar.getInstance();
        latestAllowed.setTime(new Date(System.currentTimeMillis()));
        latestAllowed.add(Calendar.WEEK_OF_YEAR, maxWeeks);

        boolean success = true;

        while (!calendar.after(latestAllowed)) {
            boolean booked = createSessionBooking(
                    new java.sql.Date(calendar.getTimeInMillis()), timeSlot, status, userId);

            if (!booked) {
                success = false;
                // Optional: log or collect failed dates
            }

            switch (frequency.toLowerCase()) {
                case "daily":
                    calendar.add(Calendar.DATE, 1);
                    break;
                case "every-other-day":
                    calendar.add(Calendar.DATE, 2);
                    break;
                case "weekly":
                    calendar.add(Calendar.DATE, 7);
                    break;
                case "one-time":
                default:
                    return success;
            }
        }

        return success;
    }

    public List<BookSession> getAllBookingsForClient(int userId) {
        List<BookSession> sessionList = new ArrayList<>();
        String sql = "SELECT date, timeSlot FROM bookings WHERE userId = ?  AND status = 'booked' " +
                "AND (date > CURRENT_DATE OR (date = CURRENT_DATE AND timeSlot > CURRENT_TIME)) " +
                "ORDER BY date, timeSlot ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BookSession b = new BookSession(
                            rs.getDate("date"),
                            rs.getTime("timeSlot")
                    );
                    sessionList.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessionList;
    }

    public boolean cancelSession(int bookingId){
        String sql = "UPDATE bookings SET status = 'cancelled' WHERE bookingId = ? ";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}




