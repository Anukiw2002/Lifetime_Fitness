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

@WebServlet("/insertReview")
public class InsertReviewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "client")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        request.getRequestDispatcher("/WEB-INF/views/client/insertReview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int rating = Integer.parseInt(request.getParameter("rating"));
        String review = request.getParameter("review");

        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        ReviewDAO reviewDAO = new ReviewDAO();
        boolean success = reviewDAO.insertReview(rating, review, userId);

        if (success) {
            response.sendRedirect("viewReview");
        }
        else {
            response.sendRedirect("failed");
        }
    }
}
