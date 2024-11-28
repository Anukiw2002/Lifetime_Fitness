package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.LeaderBoardEntry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/InstructorLeaderBoard")
public class InstructorLeaderBoardServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/auth_db";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "Ishn@2002";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        List<LeaderBoardEntry> leaderboardEntries = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT rating, name, category, maximum_kg FROM leaderboard ORDER BY rating DESC";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    LeaderBoardEntry entry = new LeaderBoardEntry(
                            rs.getInt("rating"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getInt("maximum_kg")
                    );
                    leaderboardEntries.add(entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("leaderboardEntries", leaderboardEntries);
        request.getRequestDispatcher("/WEB-INF/views/instructor/instructorLeaderBoard.jsp").forward(request, response);
    }
}

