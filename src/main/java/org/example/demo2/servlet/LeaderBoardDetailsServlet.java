package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.LeaderboardDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/leaderBoardDetails")
public class LeaderBoardDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userRole = (String)session.getAttribute("userRole");
        request.setAttribute("role", userRole);
        System.out.println("role ;" + userRole);
        request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("clientSearch");
        String category = request.getParameter("category");
        String amount = request.getParameter("amount");


        // Validate inputs
        if (phoneNumber == null || phoneNumber.isEmpty() ||
                category == null || category.isEmpty() ||
                amount == null || amount.isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
            return;
        }

        LeaderboardDAO leaderboardDAO = new LeaderboardDAO();

        try {
            int userId = leaderboardDAO.getUserByPhone(phoneNumber);

            if (userId == -1) {
                request.setAttribute("errorMessage", "User does not exist.");
                request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
                return;
            }

            String fullName = leaderboardDAO.getFullNameByUserId(userId);

            boolean success = leaderboardDAO.insertIntoLeaderBoard(userId, fullName, category, Double.parseDouble(amount));

            if (success) {
                request.setAttribute("successMessage", "Leaderboard entry added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add leaderboard entry.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid amount value.");
        }

        request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
    }
}