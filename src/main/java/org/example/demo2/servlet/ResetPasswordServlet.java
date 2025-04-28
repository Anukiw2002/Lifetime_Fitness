package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.UserDAO;
import org.example.demo2.model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resetCode = request.getParameter("resetCode");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        System.out.println("Received resetCode: " + resetCode);

        if (resetCode == null || resetCode.isEmpty() ||
                newPassword == null || newPassword.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty()) {
            request.setAttribute("message", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);
            return;
        }

        if (newPassword.length() < 8) {
            request.setAttribute("message", "Password must be at least 8 characters.");
            request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByResetToken(resetCode);

            if (user == null) {
                request.setAttribute("message", "Invalid or expired reset code.");
                request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);
                return;
            }


            userDAO.updatePassword(user.getEmail(), newPassword);

            System.out.println("Password reset successful for user: " + user.getEmail());


            response.sendRedirect(request.getContextPath() + "/testView?page=login");

        } catch (SQLException e) {
            System.err.println("Error occurred during password reset:");
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while resetting your password. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);
        }
    }
}