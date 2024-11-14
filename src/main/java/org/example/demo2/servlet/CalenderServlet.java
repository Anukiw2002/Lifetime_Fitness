package org.example.demo2.servlet;

import org.example.demo2.model.Booking;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/calendar")
public class CalenderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Booking> bookedSlots = fetchBookedSlots();
        request.setAttribute("bookedSlots", bookedSlots);
        request.getRequestDispatcher("/WEB-INF/views/common/calender.jsp").forward(request, response);
    }

    private List<Booking> fetchBookedSlots() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = CalenderDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(rs.getDate("date"), rs.getString("time_slot"), rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}