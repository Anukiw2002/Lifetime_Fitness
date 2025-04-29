package org.example.demo2.dao;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.BookSession;
import org.example.demo2.model.ClientMembership;
import org.example.demo2.util.DBConnection;
import org.example.demo2.model.BookingConstraints;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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


            try (PreparedStatement countStmt = con.prepareStatement(countQuery)) {
                countStmt.setDate(1, date);
                countStmt.setTime(2, timeSlot);

                ResultSet rs = countStmt.executeQuery();
                if (rs.next()) {
                    int currentBookings = rs.getInt(1);


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
            e.printStackTrace();
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

            }

            switch (frequency.toLowerCase()) {
                case "daily":
                    calendar.add(Calendar.DATE, 1);
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
        String sql = "SELECT bookingId, date, timeSlot FROM bookings WHERE userId = ?  AND status IN ('booked', 'rescheduled')" +
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
                    b.setBookingId(rs.getInt("bookingId"));
                    sessionList.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessionList;
    }

    public BookSession getTodayBookingForClient(int userId) {
        String sql = "SELECT bookingId, date, timeSlot FROM bookings " +
                "WHERE userId = ? AND status IN ('booked', 'rescheduled') " +
                "AND date = CURRENT_DATE AND timeSlot > CURRENT_TIME " +
                "ORDER BY timeSlot ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Date date = rs.getDate("date");
                    Time timeSlot = rs.getTime("timeSlot");

                    BookSession bookSession = new BookSession(date, timeSlot);
                    bookSession.setDate(date);
                    bookSession.setTimeSlot(timeSlot);
                    return bookSession;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<BookSession> getAllBookings() {
        List<BookSession> sessionList = new ArrayList<>();
        String sql = "SELECT b.date, b.timeSlot, b.status, u.full_name AS fname, u.username AS lname " +
                "FROM bookings b " +
                "INNER JOIN users u ON u.id = b.userId " +
                "WHERE (b.date > CURRENT_DATE OR (b.date = CURRENT_DATE AND b.timeSlot >= CURRENT_TIME)) " +
                "ORDER BY b.date, b.timeSlot ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();

                while (rs.next()) {

                    Date date = rs.getDate("date");
                    Time timeSlot = rs.getTime("timeSlot");
                    String dbStatus = rs.getString("status");


                    BookSession b = new BookSession(date, timeSlot);


                    if (dbStatus.equals("booked") &&
                            date.toLocalDate().equals(currentDate) &&
                            currentTime.isAfter(timeSlot.toLocalTime()) &&
                            currentTime.isBefore(timeSlot.toLocalTime().plusHours(1))) {
                        b.setStatus("in progress");
                    } else {
                        b.setStatus(dbStatus);
                    }

                    b.setFname(rs.getString("fname"));
                    b.setLname(rs.getString("lname"));


                    sessionList.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessionList;
    }

    public List<BookSession> getTodayBookedSessions() {
        List<BookSession> sessionList = new ArrayList<>();
        String sql = "SELECT b.date, b.timeSlot, b.status, u.full_name AS fname, u.username AS lname, cd.phone_number " +
                "FROM bookings b " +
                "INNER JOIN users u ON u.id = b.userId " +
                "LEFT JOIN client_details cd ON b.userId = cd.user_id " +
                "WHERE b.date = CURRENT_DATE AND b.timeSlot >= CURRENT_TIME AND b.status='booked' " +
                "ORDER BY b.date, b.timeSlot ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Date date = rs.getDate("date");
                    Time timeSlot = rs.getTime("timeSlot");


                    BookSession b = new BookSession(date, timeSlot);

                    b.setFname(rs.getString("fname"));
                    b.setLname(rs.getString("lname"));
                    b.setPhoneNumber(rs.getString("phone_number"));


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

    public boolean rescheduleSession(Date date, Time timeSlot, int bookingId) {
        String sql = "Update bookings SET date = ?, timeSlot = ?, status = 'rescheduled' WHERE bookingId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setDate(1, date);
            pstmt.setTime(2, timeSlot);
            pstmt.setInt(3, bookingId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BookSession getBookingById(int bookingId) {
        BookSession booking = null;
        String sql = "SELECT date, timeSlot FROM bookings WHERE bookingId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    booking = new BookSession(
                            rs.getDate("date"),
                            rs.getTime("timeSlot")
                    );
                    booking.setBookingId(bookingId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    public boolean isSlotBlocked(Date date, Time timeSlot) {
        String sql = "SELECT COUNT(*) FROM blocked_dates WHERE block_date = ? AND "
                + "(is_full_day = true OR (start_time <= ? AND end_time >= ?))";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setDate(1, date);
            if(timeSlot != null) {
                pstmt.setTime(2, timeSlot);
                pstmt.setTime(3, timeSlot);
            } else {
                pstmt.setTime(2, Time.valueOf(LocalTime.MAX));
                pstmt.setTime(3, Time.valueOf(LocalTime.MIN));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getSessionAvailabilityLabel(Date date, Time timeSlot) {

        if (isSlotBlocked(date, timeSlot)) {
            return "Blocked";
        }


        String sql = "SELECT COUNT(*) FROM bookings WHERE date = ? AND timeSlot = ? AND status IN ('booked', 'rescheduled')";
        BookingConstraints constraints = new BookingConstraintsDAO().getLatestConstraints();
        int maxBookings = constraints != null ? constraints.getMaxBookingsPerSlot() : 5;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            pstmt.setTime(2, timeSlot);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count >= maxBookings) return "Fully Booked";
                    if (count >= maxBookings * 0.8) return "Almost Full";
                    if (count >= maxBookings * 0.7) return "Filling Fast";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Available";
    }

    public boolean hasUserBookedSlot(int userId, Date date, Time timeSlot) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE userId = ? AND date = ? AND timeSlot = ? AND status IN ('booked', 'rescheduled')";
        boolean hasBooked = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setDate(2, date);
            stmt.setTime(3, timeSlot);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                hasBooked = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasBooked;

    }

    public boolean createCustomRecurringBooking(Date startDate, Time timeSlot, String status, int userId,
                                                List<String> selectedDays, Date endDate, Integer occurrences) {
        BookingConstraintsDAO constraintsDAO = new BookingConstraintsDAO();
        BookingConstraints constraints = constraintsDAO.getLatestConstraints();

        if (constraints == null) {
            System.out.println("No booking constraints found.");
            return false;
        }

        int maxWeeks = constraints.getMaxBookingAdvanceWeeks();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Calculate the end date based on parameters
        Calendar maxEndDate = Calendar.getInstance();
        maxEndDate.setTime(new Date(System.currentTimeMillis()));
        maxEndDate.add(Calendar.WEEK_OF_YEAR, maxWeeks);

        Calendar actualEndDate = Calendar.getInstance();

        if (endDate != null) {
            // Use the specified end date
            actualEndDate.setTime(endDate);
            // Make sure it doesn't exceed max allowed
            if (actualEndDate.after(maxEndDate)) {
                actualEndDate = maxEndDate;
            }
        } else if (occurrences != null) {
            // Calculate end date based on occurrences
            actualEndDate.setTime(startDate);

            // We need to count only days that match selected weekdays
            int bookedCount = 0;
            Calendar tempCal = (Calendar) calendar.clone();

            while (bookedCount < occurrences && !tempCal.after(maxEndDate)) {
                int dayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);
                String dayName = getDayName(dayOfWeek);

                if (selectedDays.contains(dayName)) {
                    bookedCount++;
                    actualEndDate.setTime(tempCal.getTime());
                }

                tempCal.add(Calendar.DATE, 1);
            }
        } else {
            // Default to max allowed
            actualEndDate = maxEndDate;
        }

        boolean success = true;
        int bookedCount = 0;

        // Don't exceed the end date
        while (!calendar.after(actualEndDate)) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String dayName = getDayName(dayOfWeek);

            if (selectedDays.contains(dayName)) {
                boolean booked = createSessionBooking(
                        new java.sql.Date(calendar.getTimeInMillis()), timeSlot, status, userId);

                if (booked) {
                    bookedCount++;
                    if (occurrences != null && bookedCount >= occurrences) {
                        break;
                    }
                } else {
                    // Don't fail the entire operation for one slot
                    System.out.println("Failed to book session on " + calendar.getTime());
                }
            }

            // Always increment by one day
            calendar.add(Calendar.DATE, 1);
        }

        return bookedCount > 0;
    }

    private String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY: return "MONDAY";
            case Calendar.TUESDAY: return "TUESDAY";
            case Calendar.WEDNESDAY: return "WEDNESDAY";
            case Calendar.THURSDAY: return "THURSDAY";
            case Calendar.FRIDAY: return "FRIDAY";
            case Calendar.SATURDAY: return "SATURDAY";
            case Calendar.SUNDAY: return "SUNDAY";
            default: return "";
        }
    }
}