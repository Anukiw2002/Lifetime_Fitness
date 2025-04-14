package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ReviewDAO;
import org.example.demo2.util.SessionUtils;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/review/delete")
public class DeleteReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "client")) {
            response.sendRedirect("landingPage");
        }

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        ReviewDAO reviewDAO = new ReviewDAO();
        boolean deleted = reviewDAO.deleteReview(userId);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (deleted) {
            out.print("{\"status\":\"success\",\"message\":\"Review deleted successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\",\"message\":\"Failed to delete review\"}");
        }
    }
}