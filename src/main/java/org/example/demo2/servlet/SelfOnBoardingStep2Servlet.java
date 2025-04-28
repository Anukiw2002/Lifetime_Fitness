package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.model.Instructor;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/selfOnBoarding/step2")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class SelfOnBoardingStep2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }
        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        Instructor instructor = instructorOnBoardingDAO.getExistingData(userId);
        request.setAttribute("instructor", instructor);

        request.getRequestDispatcher("/WEB-INF/views/instructor/selfOnBoardingStep2.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || !"instructor".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String firstName = request.getParameter("firstName");
        String surname = request.getParameter("surname");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String nic = request.getParameter("nic");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String houseNumber = request.getParameter("houseNumber");
        String streetName = request.getParameter("streetName");
        String city = request.getParameter("city");
        String emergencyContactName = request.getParameter("emergencyContactName");
        String emergencyContactNumber = request.getParameter("emergencyContactNumber");
        String emergencyContactRelationship = request.getParameter("emergencyContactRelationship");


        byte[] profilePicture = null;
        Part filePart = request.getPart("profilePicture");

        if (filePart != null && filePart.getSize() > 0) {
            try (java.io.InputStream fileContent = filePart.getInputStream()) {
                profilePicture = fileContent.readAllBytes();
            }
        }

        InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
        boolean success = instructorOnBoardingDAO.updateInstructorDetailsWithProfilePicture(userId, firstName, surname, dateOfBirth, emergencyContactRelationship,
                email, phoneNumber, houseNumber, streetName,
                city, emergencyContactName, emergencyContactNumber,
         profilePicture, nic);

        if (success) {
            response.sendRedirect("step3");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
            response.sendRedirect("step2");
        }
    }
}
