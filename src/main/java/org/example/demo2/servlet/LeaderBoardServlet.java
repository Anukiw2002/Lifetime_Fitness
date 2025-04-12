package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.LeaderboardDAO;
import org.example.demo2.model.LeaderBoard;
import org.example.demo2.model.LeaderBoardEntry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/leaderBoard")
public class LeaderBoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LeaderboardDAO dao = new LeaderboardDAO();
        List<LeaderBoard> leaderboardWL = dao.getWeightLossLeaderboard();
        request.setAttribute("leaderboard", leaderboardWL);

        request.getRequestDispatcher("leaderBoard.jsp").forward(request, response);
    }
}

