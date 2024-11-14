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


@WebServlet("/signup")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        System.out.println("Full Name: " + firstName);
        System.out.println("Username: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirmPassword);

        // Validate inputs
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() ||
                email == null || email.isEmpty() || password == null || password.isEmpty() ||
                confirmPassword == null || confirmPassword.isEmpty() || !isValidEmail(email)) {
            sendAlert(response, "All fields are required with a valid email format.", "testView?page=page1");
            return;
        }

        if (!password.equals(confirmPassword)) {
            sendAlert(response, "Passwords do not match.", "testView?page=page1");
            return;
        }

        if (password.length() < 8) {
            sendAlert(response, "Password must be at least 8 characters long.", "testView?page=page1");
            return;
        }

        String hashedPassword = HashUtil.hashPassword(password);

        try {
            UserDAO userDAO = new UserDAO();

            if (userDAO.usernameExists(firstName) || userDAO.emailExists(email)) {
                sendAlert(response, "Username or email already exists.", "testView?page=page1");
                return;
            }

            // Register the user with fullName
            User user = new User(firstName, lastName, email, hashedPassword);
            userDAO.registerUser(user);

            sendAlert(response, "Registration successful. Please log in.", "testView?page=login");
        } catch (SQLException e) {
            e.printStackTrace();
            sendAlert(response, "An error occurred while processing your registration.", "testView?page=page1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("testView?page=page1").forward(request, response);
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
