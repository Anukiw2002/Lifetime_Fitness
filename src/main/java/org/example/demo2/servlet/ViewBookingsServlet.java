package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.model.BookSession;
import org.example.demo2.dao.BookSessionDAO;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/viewBookings")
public class ViewBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Only this check is needed!
        if (!SessionUtils.isUserAuthorized(request, response, "owner", "instructor")) {
            return;
        }

        BookSessionDAO bookSessionDAO = new BookSessionDAO();
        List<BookSession> allSessions = bookSessionDAO.getAllBookings();
        request.setAttribute("allSessions", allSessions);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");

        for (BookSession b : allSessions) {
            LocalDate bookingDate = b.getDate().toLocalDate();
            if (bookingDate.equals(today)) {
                b.setDisplayDateLabel("Today");
            } else if (bookingDate.equals(tomorrow)) {
                b.setDisplayDateLabel("Tomorrow");
            } else {
                b.setDisplayDateLabel(bookingDate.format(formatter));
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/owner/viewBookings.jsp");
        dispatcher.forward(request, response);
    }
}
