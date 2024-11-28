package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.Report;

import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

// Define URL mapping for the servlet
@WebServlet("/viewReports")
public class ViewReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM user_reports";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<Report> reports = new ArrayList<>();
            while (rs.next()) {
                Report report = new Report();
                report.setId(rs.getInt("id")); // Assuming setId exists in your Report class
                report.setName(rs.getString("name")); // Assuming setName exists in your Report class
                reports.add(report);
            }

            // Attach the reports to the request and forward to the JSP
            request.setAttribute("reports", reports);
            request.getRequestDispatcher("/jsp/report.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving reports", e);
        }
    }
}
