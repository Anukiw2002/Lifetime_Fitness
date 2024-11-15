package org.example.demo2.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/booking/*")

public class BookingServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String step = request.getPathInfo();

        switch (step) {
            case "/owner":
                request.getRequestDispatcher("/WEB-INF/views/owner/viewBookings.jsp").forward(request, response);
                break;
            case "/step1":
                request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnboarding.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("/instructor-onboarding/step1");
                break;
        }
    }
}
