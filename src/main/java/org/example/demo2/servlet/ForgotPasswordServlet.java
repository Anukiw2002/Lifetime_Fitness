package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        String email = request.getParameter("email");

        // Validate email input
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("message", "Email is required.");
            request.getRequestDispatcher("/WEB-INF/views/client/resetPassword.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();

        try {
            // Check if the user exists in the database
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                // Generate a reset token and expiry time
                String token = TokenUtil.generateNumericToken(6); // Generate a 6-digit numeric token
                Timestamp expiry = new Timestamp(System.currentTimeMillis() + 3600 * 1000); // 1 hour expiry

                // Save the token and expiry in the database
                userDAO.setResetToken(email, token, expiry);

                // Create the email content
                String resetLink = request.getRequestURL()
                        .toString()
                        .replace(request.getServletPath(), "/resetPassword?token=" + token);

                String emailBody = "<p>Hi " + user.getFullName() + ",</p>"
                        + "<p>Your password reset code is: <strong>" + token + "</strong></p>"
                        + "<p>This code will expire in 1 hour.</p>"
                        + "<p>If you did not request this, please ignore this email.</p>";

                // Send the email
                try {
                    EmailUtil.sendEmail(email, "Password Reset Request", emailBody);
                    request.setAttribute("message", "A reset code has been sent to your email.");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    request.setAttribute("message", "Failed to send email. Please try again later.");
                }
            }


            // Redirect to the reset code entry page
            request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);


        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while processing your request. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/client/resetPassword.jsp").forward(request, response);
        }
    }
}
