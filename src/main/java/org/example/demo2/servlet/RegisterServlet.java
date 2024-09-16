package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;
import org.example.demo2.dao.UserDAO;
import org.example.demo2.model.User;
import org.example.demo2.util.HashUtil;

@WebServlet("/")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || email == null || email.isEmpty() ||
                password == null || password.isEmpty() || !isValidEmail(email)) {
            sendAlert(response, "Valid username, email, and password are required.", "/jsp/register.jsp");
            return;
        }

        if (password.length() < 8) {
            sendAlert(response, "Password must be at least 8 characters long.", "/jsp/register.jsp");
            return;
        }

        String hashedPassword = HashUtil.hashPassword(password);

        try {
            UserDAO userDAO = new UserDAO();

            if (userDAO.usernameExists(username) || userDAO.emailExists(email)) {
                sendAlert(response, "Username or email already exists.", "/jsp/register.jsp");
                return;
            }

            User user = new User(username, email, hashedPassword);
            userDAO.registerUser(user);

            sendAlert(response, "Registration successful. Please log in.", "/jsp/login.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            sendAlert(response, "An error occurred while processing your registration.", "/jsp/register.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private void sendAlert(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + escapeJavaScript(message) + "');");
        response.getWriter().println("window.location.href = '" + response.encodeURL(redirectUrl) + "';");
        response.getWriter().println("</script>");
    }

    private String escapeJavaScript(String message) {
        return message.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }
}
