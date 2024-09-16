package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.UserDAO;
import org.example.demo2.model.User;
import org.example.demo2.util.EmailUtil;
import org.example.demo2.util.TokenUtil;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            request.setAttribute("message", "Email is required.");
            request.getRequestDispatcher("/jsp/forgotPassword.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                String token = TokenUtil.generateToken();
                Timestamp expiry = new Timestamp(System.currentTimeMillis() + 3600 * 1000); // 1 hour expiry
                userDAO.setResetToken(email, token, expiry);
                String resetLink = request.getRequestURL().toString().replace(request.getServletPath(), "/resetPassword?token=" + token);
                String emailBody = "To reset your password, click the following link: " + resetLink;
                try {
                    EmailUtil.sendEmail(email, "Password Reset Request", emailBody);
                    request.setAttribute("message", "Password reset link has been sent to your email.");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    request.setAttribute("message", "Failed to send email. Please try again later.");
                }
            } else {
                request.setAttribute("message", "No account found with that email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while processing your request.");
        }

        request.getRequestDispatcher("/jsp/forgotPassword.jsp").forward(request, response);
    }
}