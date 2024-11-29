package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup/*")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // Default to step1 if no path specified
        if (pathInfo == null || pathInfo.equals("/")) {
            pathInfo = "/step1";
        }

        String jspPath;
        switch (pathInfo) {
            case "/step1":
                jspPath = "/WEB-INF/views/client/signUp1.jsp";
                break;
            case "/step2":
                jspPath = "/WEB-INF/views/client/signUp2.jsp";
                break;
            case "/step3":
                jspPath = "/WEB-INF/views/client/signUp3.jsp";
                break;
            case "/step4":
                jspPath = "/WEB-INF/views/client/signUp4.jsp";
                break;
            default:
                jspPath = "/WEB-INF/views/client/signUp1.jsp";
        }

        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/signup/step1");
            return;
        }

        // Handle form submissions and redirect to next step
        switch (pathInfo) {
            case "/step1":
                response.sendRedirect(request.getContextPath() + "/signup/step2");
                break;
            case "/step2":
                response.sendRedirect(request.getContextPath() + "/signup/step3");
                break;
            case "/step3":
                response.sendRedirect(request.getContextPath() + "/signup/step4");
                break;
            case "/step4":
                response.sendRedirect(request.getContextPath() + "/signup/complete");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/signup/step1");
        }
    }
}