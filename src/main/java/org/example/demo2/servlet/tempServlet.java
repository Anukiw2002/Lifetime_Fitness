package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testView")
public class tempServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the page parameter from the URL
        String page = request.getParameter("page");

        // Forward to the appropriate JSP view based on the parameter
        if ("pg1".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/name1.jsp").forward(request, response);
        } else if ("pg2".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/name2.jsp").forward(request, response);
        } else {
            // Default or error page
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }
}
