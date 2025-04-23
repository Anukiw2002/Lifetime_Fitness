package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.ClientDAO;
import org.example.demo2.util.DBConnection;

import java.io.IOException;

@WebServlet("/signup/step2")
public class SignUpStep2Servlet extends HttpServlet {
    private ClientDAO clientDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        this.clientDAO = new ClientDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to calendar.jsp in WEB-INF
        request.getRequestDispatcher("/WEB-INF/views/client/signUp2.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int userId = (int) session.getAttribute("userId");
        request.setAttribute("userId", userId);

        String phoneNumber = request.getParameter("phoneNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String emergencyContactName = request.getParameter("emergencyContactRelation");
        String emergencyContactNumber = request.getParameter("emergencyContact");
        String houseNo = request.getParameter("houseNumber");
        String streetName = request.getParameter("streetName");
        String city = request.getParameter("city");
        String gender = request.getParameter("gender");

        boolean success = clientDAO.insertClientDetails(userId, phoneNumber, dateOfBirth, emergencyContactName, emergencyContactNumber,houseNo, streetName, city, gender);

        if (success) {
            response.sendRedirect("step3");
        }
        else {
            response.sendRedirect(request.getContextPath() + "/jsp/accessDenied.jsp");
        }
    }
}
