package org.example.demo2.servlet;

import jakarta.mail.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.LeaderboardDAO;
import org.example.demo2.model.LeaderBoard;
import org.example.demo2.model.LeaderBoardEntry;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/leaderBoard")
public class LeaderBoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int user_id = (int)session.getAttribute("userId");
        String userRole = (String)session.getAttribute("userRole");
        request.setAttribute("user_id", user_id);

        session.setAttribute("role", userRole);

        LeaderboardDAO dao = new LeaderboardDAO();
        try {
            List<LeaderBoard> leaderboardWL = dao.getWeightLossLeaderboard();
            List<LeaderBoard> streakList = dao.getStreakLeaderboard();

            for (LeaderBoard entry : leaderboardWL) {
                System.out.println("User: " + entry.getName() + ", Weight Loss: " + entry.getWeightLoss());

            }
            for (LeaderBoard entry : streakList) {
                System.out.println("User: " + entry.getName() + ", Streak: " + entry.getStreak());
            }
            System.out.println("role: " + userRole);

            request.setAttribute("leaderboard", leaderboardWL);
            request.setAttribute("streakboard", streakList);
            request.getRequestDispatcher("/WEB-INF/views/common/leaderBoard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Logs the error
            request.setAttribute("errorMessage", "Failed to load leaderboard.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userRole = (String)session.getAttribute("userRole");
        session.setAttribute("role", userRole);

        String exercise_type = request.getParameter("exercise");
        System.out.println("exercise_type: " + exercise_type);

        LeaderboardDAO dao = new LeaderboardDAO();
        try {
            List<LeaderBoard> leaderboardWL = dao.getWeightLossLeaderboard();
            List<LeaderBoard> streakList = dao.getStreakLeaderboard();
            List<LeaderBoardEntry> leaderBoard = dao.getEntriesByExercise(exercise_type);

            request.setAttribute("leaderboard", leaderboardWL);
            request.setAttribute("streakboard", streakList);
            request.setAttribute("leaderboardCategory", leaderBoard);
            request.getRequestDispatcher("/WEB-INF/views/common/leaderBoard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to load leaderboard.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
