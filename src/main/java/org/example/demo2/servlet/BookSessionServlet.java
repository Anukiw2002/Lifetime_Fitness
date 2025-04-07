package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.BookSessionDAO;

import java.io.IOException;

@WebServlet("/bookSession")
public class BookSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        // Retrieve userId from the session and set it as a request attribute
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        // Get start and end times from the database and set them in the session
        BookSessionDAO bookSessionDAO = new BookSessionDAO();
        bookSessionDAO.getStartEndTime(userId, session);

        // Forward the request to bookSession.jsp
        request.getRequestDispatcher("/WEB-INF/views/client/bookSession.jsp").forward(request, response);
    }
}
