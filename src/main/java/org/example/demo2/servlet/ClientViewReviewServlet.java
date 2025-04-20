package org.example.demo2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ReviewDAO;
import org.example.demo2.model.Review;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/viewReview")
public class ClientViewReviewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "client")) {
            response.sendRedirect("landingPage");
        }

        HttpSession session = request.getSession(false);

        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        ReviewDAO reviewDAO = new ReviewDAO();
        Review review = reviewDAO.viewUserReview(userId);
        request.setAttribute("review", review);

        request.getRequestDispatcher("/WEB-INF/views/client/review.jsp").forward(request,response);

    }
}
