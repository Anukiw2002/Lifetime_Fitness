package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testView")
public class TemporaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the page parameter from the URL
        String page = request.getParameter("page");

        // Forward to the appropriate JSP view based on the parameter
        if ("clientDashboard".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/Dummy.jsp").forward(request, response);
        } else if ("page2".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/signUp2.jsp").forward(request, response);
        } else if ("try".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client").forward(request, response);
        } else if ("ownerDashboard".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner - dashboard.jsp").forward(request, response);
        } else if ("payment".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp").forward(request, response);
        } else {
            // Default or error page
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }
}