package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/selfOnBoarding/step3")
public class SelfOnBoardingStep3Servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnBoardingStep3.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || !"instructor".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Get the arrays of certification data
        String[] certificationNames = request.getParameterValues("certificationName[]");
        String[] certificationProviders = request.getParameterValues("certificationProvider[]");

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        boolean allSuccess = true;

        // Process each certification
        if (certificationNames != null) {
            for (int i = 0; i < certificationNames.length; i++) {
                // Skip empty entries
                if (certificationNames[i].isEmpty()) continue;

                String certName = certificationNames[i];
                String certProvider = (i < certificationProviders.length) ? certificationProviders[i] : "";

                boolean success = instructorOnBoardingDAO.insertCertificates(userId, certName, certProvider);
                if (!success) {
                    allSuccess = false;
                    break;
                }
            }
        }

        if (allSuccess) {
            instructorOnBoardingDAO.changeStatus(userId);
            response.sendRedirect("completed");
        } else {
            request.setAttribute("errorMessage", "Failed to upload certificate details. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnBoardingStep3.jsp").forward(request, response);
        }
    }
}
