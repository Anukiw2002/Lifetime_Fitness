package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.model.Instructor;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/instructorEditProfile")
@MultipartConfig
public class InstructorEditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            return;
        }

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        try {
            InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
            Instructor instructor = instructorOnBoardingDAO.getInstructorById(userId);

            request.setAttribute("instructor", instructor);
            request.getRequestDispatcher("/WEB-INF/views/instructor/instructorEditProfile.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("instructorEditProfile");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "instructor")) {
            response.sendRedirect("landingPage");
            return;
        }

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        String firstName = request.getParameter("firstName");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String phoneNumber = request.getParameter("phoneNumber");
        String nic = request.getParameter("nic");
        String houseNumber = request.getParameter("houseNumber");
        String streetName = request.getParameter("streetName");
        String city = request.getParameter("city");
        String emergencyContactName = request.getParameter("emergencyContactName");
        String emergencyContactRelationship = request.getParameter("emergencyContactRelationship");
        String emergencyContactNumber = request.getParameter("emergencyContactNumber");


        Part filePart = request.getPart("profilePicture");
        byte[] profilePicture = null;

        if (filePart != null && filePart.getSize() > 0) {
            InputStream fileContent = filePart.getInputStream();
            profilePicture = fileContent.readAllBytes();
            fileContent.close();
        }


        String[] certificationNames = request.getParameterValues("certificationName");
        String[] certificationProviders = request.getParameterValues("certificationProvider");

        InstructorOnBoardingDAO dao = new InstructorOnBoardingDAO();


        boolean updateSuccess = dao.updateInstructorDetailsWithProfilePicture(
                userId, firstName, surname, dateOfBirth, emergencyContactRelationship,
                email, phoneNumber, houseNumber, streetName, city,
                emergencyContactName, emergencyContactNumber, profilePicture, nic
        );


        if (updateSuccess && certificationNames != null && certificationProviders != null) {
            dao.deleteCertificates(userId);

            for (int i = 0; i < certificationNames.length; i++) {
                if (certificationNames[i] != null && !certificationNames[i].trim().isEmpty() &&
                        certificationProviders[i] != null && !certificationProviders[i].trim().isEmpty()) {
                    dao.insertCertificates(userId, certificationNames[i], certificationProviders[i]);
                }
            }
        }
        response.sendRedirect("instructorProfile");
    }
}
