package org.example.demo2.servlet;

import org.example.demo2.servlet.CalenderDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

@WebServlet("/book")
public class BookSlotServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        String timeSlot = request.getParameter("time");
        int userId = 1; // Replace with actual user ID from session or database

        boolean isBooked = bookSlot(date, timeSlot, userId);

        if (isBooked) {
            response.sendRedirect("calendar?success=true");
        } else {
            response.sendRedirect("calendar?error=true");
        }
    }

    private boolean bookSlot(String date, String timeSlot, int userId) {
        try (Connection conn = CalenderDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookings (date, time_slot, user_id) VALUES (?, ?, ?)")) {
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, timeSlot);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
