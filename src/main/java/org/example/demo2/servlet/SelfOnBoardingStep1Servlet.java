package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/selfOnBoarding/step1")
public class SelfOnBoardingStep1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnBoardingStep1.jsp").forward(request, response);
    }
}