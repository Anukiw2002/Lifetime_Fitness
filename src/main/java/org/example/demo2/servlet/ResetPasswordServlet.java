package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.UserDAO;
import org.example.demo2.util.HashUtil;
import org.example.demo2.model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");

        if (token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty() || newPassword.length() < 8) {
            request.setAttribute("message", "Valid token and new password (at least 8 characters) are required.");
            request.getRequestDispatcher("/jsp/resetPassword.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            boolean isValidToken = userDAO.validatePasswordResetToken(token);
            if (isValidToken) {
                User user = userDAO.getUserByResetToken(token);
                if (user != null) {
                    String hashedPassword = HashUtil.hashPassword(newPassword);
                    userDAO.updatePassword(user.getEmail(), hashedPassword);
                    userDAO.setResetToken(user.getEmail(), null, null); // Clear the reset token
                    request.setAttribute("message", "Password successfully reset. You can now log in.");
                    request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "Invalid or expired token.");
                    request.getRequestDispatcher("/jsp/resetPassword.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "Invalid or expired token.");
                request.getRequestDispatcher("/jsp/resetPassword.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while processing your request.");
            request.getRequestDispatcher("/jsp/resetPassword.jsp").forward(request, response);
        }
    }
}