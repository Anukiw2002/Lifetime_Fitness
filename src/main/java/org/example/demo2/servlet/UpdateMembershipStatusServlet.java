package org.example.demo2.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.MembershipPlanDAO;
import org.example.demo2.util.DBConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/membership/updateStatus")
public class UpdateMembershipStatusServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        System.out.println("UpdateMembershipStatusServlet: Processing request");
        response.setContentType("application/json");
        Map<String, Object> responseData = new HashMap<>();

        try {

            String requestBody = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);

            System.out.println("Received request body: " + requestBody);


            Map<String, Object> requestData = objectMapper.readValue(requestBody, Map.class);

            if (!requestData.containsKey("planId")) {
                throw new ServletException("Missing required parameter: planId");
            }

            Long planId = Long.valueOf(requestData.get("planId").toString());
            MembershipPlanDAO planDAO = new MembershipPlanDAO(new DBConnection());


            String currentStatus = planDAO.getStatus(planId);
            System.out.println("Current status in database: " + currentStatus);


            String newStatus = "ACTIVE".equals(currentStatus) ? "INACTIVE" : "ACTIVE";
            System.out.println("Toggling status to: " + newStatus);


            planDAO.updateStatus(planId, newStatus);


            String updatedStatus = planDAO.getStatus(planId);
            System.out.println("New status in database: " + updatedStatus);

            if (updatedStatus == null || !updatedStatus.equals(newStatus)) {
                throw new SQLException("Status update verification failed");
            }


            responseData.put("status", "success");
            responseData.put("message", "Status updated successfully");
            responseData.put("newStatus", updatedStatus);

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            responseData.put("status", "error");
            responseData.put("message", "Database error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
            responseData.put("status", "error");
            responseData.put("message", "Server error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


        objectMapper.writeValue(response.getWriter(), responseData);
    }
}