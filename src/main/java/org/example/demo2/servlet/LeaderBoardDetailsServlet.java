package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/leaderBoardDetails")
public class LeaderBoardDetailsServlet extends HttpServlet { // Make sure to extend HttpServlet

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Forward the request to leaderBoardDetails.jsp
        request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
    }

    // Handles POST requests from the form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        // Retrieve form data
        String category = request.getParameter("category");
        String amount = request.getParameter("amount");

        // Perform some processing here, such as saving the data or querying for leaderboard details.
        // You can add logic to handle leaderboard data based on the category and amount

        // For now, just set a success message as a request attribute and forward back to the leaderboard page
        request.setAttribute("message", "Category: " + category + ", Amount: " + amount + " has been processed.");

        // Forward back to JSP to display the message
        request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
    }
}
