package org.example.demo2.servlet;

import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.dao.UserDAOImpl;
import org.example.demo2.dao.IUserDAO;
import org.example.demo2.dao.ClientMembershipDAO;
import org.example.demo2.model.User;
import org.example.demo2.model.ClientMembership;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;
import org.example.demo2.util.DBConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ClientMembershipDAO membershipDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        membershipDAO = new ClientMembershipDAO(dbConnection);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            sendAlert(response, "Email and password are required.");
            return;
        }

        if (!email.matches(emailRegex)) {
            sendAlert(response, "Invalid email format.");
            return;
        }

        if (password.length() < 8) {
            sendAlert(response, "Password must be at least 8 characters long.");
            return;
        }

        IUserDAO userDAO = new UserDAOImpl();
        User user = null;

        try {
            user = userDAO.getUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            sendAlert(response, "Database error occurred while retrieving user.");
            return;
        }

        if (user != null) {
            boolean passwordMatch = userDAO.verifyPassword(password, user.getHashedPassword());
            if (passwordMatch) {
                HttpSession session = request.getSession();
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("userId", user.getUser_id());
                session.setAttribute("email", user.getEmail());
                session.setMaxInactiveInterval(30 * 60);

                switch (user.getRole()) {
                    case "client":
                        try {
                            List<ClientMembership> membership = membershipDAO.getClientMembership(user.getUser_id());
                            request.setAttribute("membership", membership);

                            if (membership != null && !membership.isEmpty()) {
                                ClientMembership latestMembership = membership.get(0);
                                LocalDate currentDate = LocalDate.now();
                                if (latestMembership.getEndDate().isBefore(currentDate)) {
                                    response.sendRedirect(request.getContextPath() + "/expired");
                                } else {
                                    response.sendRedirect(request.getContextPath() + "/clientDashboard");
                                }
                            } else {
                                response.sendRedirect(request.getContextPath() + "/expired");
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                            sendAlert(response, "Error retrieving membership data.");
                        }
                        break;

                    case "owner":
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                        break;

                    case "instructor":
                        try {
                            InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
                            String onboardingStatus = instructorOnBoardingDAO.getOnBoardingStatus(user.getUser_id());
                            session.setAttribute("onboardingStatus", onboardingStatus);

                            if ("in-progress".equals(onboardingStatus)) {
                                response.sendRedirect(request.getContextPath() + "/selfOnBoarding/step1");
                            } else {
                                response.sendRedirect(request.getContextPath() + "/instructorDashboard");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            sendAlert(response, "Error checking instructor status.");
                        }
                        break;

                    default:
                        sendAlert(response, "Unknown user role.");
                        break;
                }

            } else {
                sendAlert(response, "Invalid password.");
            }
        } else {
            sendAlert(response, "No user found with the provided email.");
        }
    }

    private void sendAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        String sanitizedMessage = StringEscapeUtils.escapeJson(message);
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + sanitizedMessage + "');");
        response.getWriter().println("window.location.href = '" + response.encodeURL("testView?page=login") + "';");
        response.getWriter().println("</script>");
    }
}
