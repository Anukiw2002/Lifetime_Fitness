package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.LeaderboardDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/searchClient")
public class SearchClientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String phoneNumber = request.getParameter("phone");

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            response.getWriter().write("{\"success\": false, \"message\": \"Phone number is required\"}");
            return;
        }

        LeaderboardDAO leaderboardDAO = new LeaderboardDAO();

        try {
            int userId = leaderboardDAO.getUserByPhone(phoneNumber);

            if (userId == -1) {
                response.getWriter().write("{\"success\": false, \"message\": \"User does not exist\"}");
                return;
            }

            String fullName = leaderboardDAO.getFullNameByUserId(userId);

            // Return success with user details
            String jsonResponse = String.format(
                    "{\"success\": true, \"userId\": %d, \"fullName\": \"%s\", \"phone\": \"%s\"}",
                    userId, fullName, phoneNumber
            );
            response.getWriter().write(jsonResponse);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"Database error: " + e.getMessage() + "\"}");
        }
    }
}