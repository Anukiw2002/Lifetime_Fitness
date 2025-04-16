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

        // Forward the request to leaderBoardDetails.jsp
        request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);
    }

    // Handles POST requests from the form submission
    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("clientSearch");
        String category = request.getParameter("category");
        String amount = request.getParameter("amount");

        LeaderboardDAO leaderboardDAO = new LeaderboardDAO();

        int userId = 0;
        try {
            userId = leaderboardDAO.getUserByPhone(phoneNumber);

            if(userId == -1){
                response.getWriter().write("{\"success\": false, \"message\": \"User does not exist.\"}");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String fullName = null;
        if(userId != -1){
            try {
                fullName = leaderboardDAO.getFullNameByUserId(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Full Name: " + fullName);
        }else {
            request.setAttribute("errorMessage", "User does not exist.");
            return;
        }
        boolean success = leaderboardDAO.insertIntoLeaderBoard(userId,fullName, category, Double.parseDouble(amount));

        // Forward back to JSP to display the message
        request.getRequestDispatcher("/WEB-INF/views/common/leaderBoardDetails.jsp").forward(request, response);

    }

}
