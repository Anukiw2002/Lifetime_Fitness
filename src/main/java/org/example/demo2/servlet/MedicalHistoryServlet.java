package org.example.demo2.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.MedicalHistoryDAO;
import org.example.demo2.model.MedicalHistory;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/signup/step3")
public class MedicalHistoryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user_email = (String)request.getSession().getAttribute("userEmail");
        System.out.println(user_email);

        String medicalCondition = request.getParameter("q1");
        String takeMedication = request.getParameter("q2");
        boolean chestPain = "yes".equals(request.getParameter("q3"));
        boolean backPain = "yes".equals(request.getParameter("q4"));
        String boneJointProblem = request.getParameter("q5");
        boolean bloodPressure = "yes".equals(request.getParameter("q6"));
        String diabetes = request.getParameter("q7");
        String stressLevel = request.getParameter("q8");
        String smoking = request.getParameter("q9");
        String activityLevel = request.getParameter("q10");
        String exerciseObjectives = request.getParameter("q11");
        String otherConditions = request.getParameter("q12");

        MedicalHistory medicalHistory = new MedicalHistory(user_email, medicalCondition, takeMedication, chestPain,
                backPain, boneJointProblem, bloodPressure, diabetes, stressLevel, smoking, activityLevel,
                exerciseObjectives, otherConditions);

        MedicalHistoryDAO dao = new MedicalHistoryDAO();
        if (dao.insertMedicalHistory(medicalHistory)){
            response.sendRedirect(request.getContextPath() + "/signup/step4");

        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/client/signUp3.jsp").forward(req, resp);
    }

}
