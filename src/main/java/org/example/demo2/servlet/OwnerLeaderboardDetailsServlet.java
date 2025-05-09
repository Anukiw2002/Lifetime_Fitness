package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/owner/leaderboardDetails")
public class OwnerLeaderboardDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(req, resp, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        req.getRequestDispatcher("/WEB-INF/views/ownerLeaderboardDetails.jsp").forward(req, resp);
    }
}
