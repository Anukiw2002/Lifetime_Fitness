package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/owner/leaderBoard")
public class OwnerLeaderboard extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!SessionUtils.isUserAuthorized(req, resp, "owner")) {
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/common/leaderBoard.jsp").forward(req, resp);

    }
}
