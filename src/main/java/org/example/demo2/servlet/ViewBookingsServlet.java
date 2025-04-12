package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.BookSession;
import org.example.demo2.dao.BookSessionDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewBookings")
public class ViewBookingsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"owner".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        BookSessionDAO bookSessionDAO = new BookSessionDAO();
        List<BookSession> allSessions = bookSessionDAO.getAllBookings();
        request.setAttribute("allSessions", allSessions);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/owner/viewBookings.jsp");
        dispatcher.forward(request, response);
    }
}
