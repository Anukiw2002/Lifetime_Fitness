package org.example.demo2.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/selfOnboarding/*")

public class SelfOnboardingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String step = request.getPathInfo();

        switch (step) {
            case "/step1":
                request.getRequestDispatcher("/WEB-INF/views/instructor/firstLogInPage.jsp")
                        .forward(request, response);
                break;
            case "/step2":
                // Handle step 2 navigation
                break;
            default:
                response.sendRedirect("/instructor-onboarding/step1");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This will be used later for form submission
        String step = request.getPathInfo();

        if ("/step1".equals(step)) {
            // Later: Process form data here
            response.sendRedirect("/instructor-onboarding/step2");
        }
    }
}