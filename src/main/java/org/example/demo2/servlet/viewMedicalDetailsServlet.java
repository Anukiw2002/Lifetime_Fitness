package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.MedicalHistoryDAO;
import org.example.demo2.model.MedicalHistory;

import java.io.IOException;

@WebServlet("/viewDetails")
public class viewMedicalDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String email = request.getParameter("email");
        MedicalHistoryDAO dao = new MedicalHistoryDAO();
        MedicalHistory medicalHistory = dao.getMedicalHistory(email);

        if(medicalHistory != null){
            request.setAttribute("medical_history", medicalHistory);
            request.getRequestDispatcher("/WEB-INF/views/owner/viewMedicalDetails.jsp").forward(request, response);

        }
    }
}
