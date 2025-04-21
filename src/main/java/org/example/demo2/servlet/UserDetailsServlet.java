package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/userDetails")
public class UserDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }
        // Set user details
        request.setAttribute("userName", "John Doe");
        request.setAttribute("userEmail", "johndoe@example.com");
        request.setAttribute("userCity", "New York");
        request.setAttribute("userTP", "123-456-7890");
        request.setAttribute("userGender", "Male");
        request.setAttribute("userAge", "30");

        // Set body details
        request.setAttribute("bodyWeight", "70 kg");
        request.setAttribute("bodyHeight", "5'9\"");
        request.setAttribute("bodyBP", "120/80");
        request.setAttribute("fitnessTest", "Passed");
        request.setAttribute("bodyBMI", "22.3");

        // Set gym details
        request.setAttribute("gymPlan", "Premium");
        request.setAttribute("lastVisited", "2024-11-13");
        request.setAttribute("lastPaid", "2024-11-01");
        request.setAttribute("payDate", "2024-12-01");
        request.setAttribute("startDate", "2024-01-01");
        request.setAttribute("endDate", "2024-12-31");
        // Forward request to JSP
        request.getRequestDispatcher("/WEB-INF/views/client/userDetails.jsp").forward(request, response);
    }
}
