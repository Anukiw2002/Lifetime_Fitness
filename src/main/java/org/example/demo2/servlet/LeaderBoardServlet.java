package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.LeaderboardDAO;
import org.example.demo2.model.LeaderBoard;

import java.io.IOException;
import java.util.List;

@WebServlet("/leaderBoard")
public class LeaderBoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LeaderboardDAO dao = new LeaderboardDAO();
        try {
            List<LeaderBoard> leaderboardWL = dao.getWeightLossLeaderboard();
            List<LeaderBoard> streakList = dao.getStreakLeaderboard();

            // 🖨️ Print each user and their weight loss
            for (LeaderBoard entry : leaderboardWL) {
                System.out.println("User: " + entry.getName() + ", Weight Loss: " + entry.getWeightLoss());
            }
            for (LeaderBoard entry : streakList) {
                System.out.println("User: " + entry.getName() + ", Streak: " + entry.getStreak());
            }

            request.setAttribute("leaderboard", leaderboardWL);
            request.setAttribute("streakboard", streakList);
            request.getRequestDispatcher("/WEB-INF/views/common/leaderBoard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Logs the error
            request.setAttribute("errorMessage", "Failed to load leaderboard.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
