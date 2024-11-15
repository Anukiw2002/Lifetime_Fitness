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
            case "/verify":
                request.getRequestDispatcher("/WEB-INF/views/instructor/firstLogInPage.jsp").forward(request, response);
                break;
            case "/step1":
                request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnboarding.jsp").forward(request, response);
                break;
            case "/step2":
                request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnboarding2.jsp").forward(request, response);
                break;
            case "/step3":
                request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnboarding3.jsp").forward(request, response);
                break;
            case "/step4":
                request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnboarding4.jsp").forward(request, response);
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

        if ("/verify".equals(step)) {
            // Later: Process form data here
            response.sendRedirect("/selfOnboarding/step1");
        }
    }
}
