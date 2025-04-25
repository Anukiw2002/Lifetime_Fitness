package org.example.demo2.servlet;

import jakarta.mail.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.util.DBConnection; // Assuming this is connection utility class
import org.example.demo2.util.SessionUtils;
import org.example.demo2.dao.ReportDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/processReport1")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        HttpSession session = request.getSession(false);
        String email =(String) session.getAttribute("userEmail");
        ReportDAO dao = new ReportDAO();
        int userId = dao.getUseridByEmail(email);
        int age = dao.getAge(userId);
        String name = dao.getName(email);

        request.setAttribute("age", age);
        request.setAttribute("name", name);
        request.getRequestDispatcher("/jsp/report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // General Report Info
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String programNo = request.getParameter("program_no");
        String startingDate = request.getParameter("starting_date");
        String expireDate = request.getParameter("expire_date");
        String frequency = request.getParameter("frequency");
        int timesPerWeek = Integer.parseInt(request.getParameter("times_per_week"));
        int maxHeartRate = Integer.parseInt(request.getParameter("max_heart_rate"));
        int bpm65 = Integer.parseInt(request.getParameter("bpm_65"));
        int bpm75 = Integer.parseInt(request.getParameter("bpm_75"));
        int bpm85 = Integer.parseInt(request.getParameter("bpm_85"));
        double waistCircumference = parseDouble(request.getParameter("waist_circumference"));
        double bodyWeight = parseDouble(request.getParameter("body_weight"));
        double height = parseDouble(request.getParameter("height"));
        double fatPercentage = parseDouble(request.getParameter("fat"));
        double bmr = parseDouble(request.getParameter("bmr"));
        String goal = request.getParameter("goal");
        String warm_up = request.getParameter("warm_up");
        String flexibility = request.getParameter("flexibility");
        String cardio = request.getParameter("cardio");
        String remarks = request.getParameter("remarks");
        String userEmail = request.getParameter("userEmail");
        double target_weight = parseDouble(request.getParameter("target_weight"));

        // Exercises

        String[] exerciseDatesArray = request.getParameterValues("exercise_date[]");
        String[] weightsArray = request.getParameterValues("weight[]");

        List<String> exerciseDates = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        if (exerciseDatesArray != null) {
            System.out.println("length " + exerciseDatesArray.length);
            for (int i = 0; i < exerciseDatesArray.length; i++) {
                exerciseDates.add(exerciseDatesArray[i]);
                weights.add(Double.parseDouble(weightsArray[i]));

            }
            System.out.println("exerciseDates size: " + exerciseDates.size());
            System.out.println("weights size: " + weights.size());
        }

        // Database operation
        ReportDAO reportDAO = new ReportDAO();

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // Insert report info and get the generated report ID
                int reportId = reportDAO.insertReport(conn, name, age, programNo, startingDate, expireDate, frequency,
                        timesPerWeek, maxHeartRate, bpm65, bpm75, bpm85, waistCircumference, bodyWeight, height,
                        fatPercentage, bmr, goal, warm_up, flexibility, cardio, remarks, userEmail, target_weight);

                // Insert exercises if the report ID was successfully generated and exercises exist
                if (reportId > 0 && !exerciseDates.isEmpty()) {
                    reportDAO.insertExercises(conn, reportId, exerciseDates, weights, userEmail);
                }

                // Commit transaction if everything is successful
                conn.commit();
                response.sendRedirect(request.getContextPath() + "/first");

            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                throw new ServletException("Error saving report and exercises. Rolled back.", e);
            }

        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }


    }

    // Helper methods for parsing integer and double values
    private int parseInteger(String value) {
        return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : 0;
    }

    private double parseDouble(String value) {
        return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : 0.0;
    }




}
