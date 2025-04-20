package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.demo2.dao.ClientDAO;
import org.example.demo2.dao.ReportDAO;
import org.example.demo2.model.Client;
import org.example.demo2.model.Report;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/clientEditProfile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class ClientEditProfileServlet extends HttpServlet {
    private ClientDAO clientDAO;
    private ReportDAO reportDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
        reportDAO = new ReportDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // If session is invalid or the user is not a client, redirect to the landing page
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try {
            Client client = clientDAO.findByUserId(userId);

            // Set attributes for the JSP
            request.setAttribute("client", client);

            Report report = reportDAO.getById(userId);
            request.setAttribute("report", report);

            request.getRequestDispatcher("/WEB-INF/views/client/editProfile.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is authorized - if not, redirect
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // Get form parameters
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");
        String emailAddress = request.getParameter("emailAddress");
        String phoneNumber = request.getParameter("phoneNumber");
        String houseNo = request.getParameter("houseNo");
        String streetName = request.getParameter("streetName");
        String city = request.getParameter("city");
        String emergencyContactName = request.getParameter("emergencyContactName");
        String emergencyContactNumber = request.getParameter("emergencyContactNumber");

        // Handle profile picture - use Part for file upload
        byte[] profilePicture = null;
        Part filePart = request.getPart("profilePicture");

        if (filePart != null && filePart.getSize() > 0) {
            try (java.io.InputStream fileContent = filePart.getInputStream()) {
                profilePicture = fileContent.readAllBytes();
            }
        }

        boolean success = clientDAO.updateClientDetailsWithProfilePicture(userId, name, username, dateOfBirth, gender,
                emailAddress, phoneNumber, houseNo, streetName,
                city, emergencyContactName, emergencyContactNumber, profilePicture);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/memberProfile");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/client/editProfile.jsp").forward(request, response);
        }
    }
}