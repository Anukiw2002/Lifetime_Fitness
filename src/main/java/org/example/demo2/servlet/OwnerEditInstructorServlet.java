package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.demo2.dao.InstructorOnBoardingDAO;
import org.example.demo2.model.Instructor;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/editInstructor")
@MultipartConfig
public class OwnerEditInstructorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            response.sendRedirect("landingPage");
            return;
        }
        // Check if an instructor ID was provided
        String instructorIdParam = request.getParameter("id");

        if (instructorIdParam != null && !instructorIdParam.isEmpty()) {
            try {
                int instructorId = Integer.parseInt(instructorIdParam);
                InstructorOnBoardingDAO instructorOnBoardingDAO = new InstructorOnBoardingDAO();
                Instructor instructor = instructorOnBoardingDAO.getInstructorById(instructorId);

                if (instructor == null) {
                    // Instructor not found, redirect to the instructor management page
                    response.sendRedirect("viewInstructor");
                    return;
                }

                request.setAttribute("instructor", instructor);
                request.getRequestDispatcher("/WEB-INF/views/owner/editInstructor.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // Invalid ID format, redirect to instructor management
                response.sendRedirect("viewInstructor");
                return;
            }
        } else {
            // No ID provided, redirect to instructor management
            response.sendRedirect("viewInstructor");
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            response.sendRedirect("landingPage");
            return;
        }

        // Get form data
        int userId = Integer.parseInt(request.getParameter("userId"));
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

        // Handle profile picture upload
        Part filePart = request.getPart("profilePicture");
        byte[] profilePicture = null;

        if (filePart != null && filePart.getSize() > 0) {
            InputStream fileContent = filePart.getInputStream();
            profilePicture = fileContent.readAllBytes();
            fileContent.close();
        }

        // Process certifications
        String[] certificationNames = request.getParameterValues("certificationName");
        String[] certificationProviders = request.getParameterValues("certificationProvider");

        InstructorOnBoardingDAO dao = new InstructorOnBoardingDAO();

        // Update instructor details
        boolean updateSuccess = dao.updateInstructorDetailsWithProfilePicture(
                userId, firstName, surname, dateOfBirth, emergencyContactRelationship,
                email, phoneNumber, houseNumber, streetName, city,
                emergencyContactName, emergencyContactNumber, profilePicture, nic
        );

        // Handle certifications separately - first delete existing ones and then add new ones
        if (updateSuccess && certificationNames != null && certificationProviders != null) {
            // Delete existing certifications (this method needs to be added to the DAO)
            dao.deleteCertificates(userId);

            // Add new certifications
            for (int i = 0; i < certificationNames.length; i++) {
                if (certificationNames[i] != null && !certificationNames[i].trim().isEmpty() &&
                        certificationProviders[i] != null && !certificationProviders[i].trim().isEmpty()) {
                    dao.insertCertificates(userId, certificationNames[i], certificationProviders[i]);
                }
            }
        }

        // Redirect to the instructor view page
        response.sendRedirect("viewInstructor?id=" + userId);
    }
}