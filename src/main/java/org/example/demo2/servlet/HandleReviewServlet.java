package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ReviewDAO;

import java.io.IOException;

@WebServlet("/handleReview")
public class HandleReviewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        ReviewDAO reviewDAO = new ReviewDAO();
        boolean hasReview = reviewDAO.reviewExists(userId);

        if (hasReview) {
            response.sendRedirect("viewReview?userId=" + userId);
        } else {
            response.sendRedirect("insertReview?userId=" + userId);
        }
    }
}

