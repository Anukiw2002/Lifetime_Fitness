package org.example.demo2.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;
import org.example.demo2.model.Duration;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/membership/delete")
public class DeleteMembershipPlanServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        response.setContentType("application/json");
        Map<String, Object> jsonResponse = new HashMap<>();
        Connection connection = null;

        try {
            Map<String, Object> requestData = objectMapper.readValue(request.getInputStream(), Map.class);
            Long planId = Long.valueOf(requestData.get("planId").toString());

            if (planId == null) {
                throw new ServletException("Plan ID is required");
            }

            System.out.println("Attempting to delete plan with ID: " + planId);

            DBConnection dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            try {
                MembershipPlanDAO membershipPlanDAO = new MembershipPlanDAO(dbConnection);
                DurationDAO durationDAO = new DurationDAO(dbConnection);
                CategoryPricingDAO categoryPricingDAO = new CategoryPricingDAO(dbConnection);
                UniformPricingDAO uniformPricingDAO = new UniformPricingDAO(dbConnection);

                if (membershipPlanDAO.findById(planId) == null) {
                    throw new SQLException("Plan not found with ID: " + planId);
                }

                System.out.println("Fetching durations for plan: " + planId);
                List<Duration> durations = durationDAO.findByPlanId(planId);
                System.out.println("Found " + durations.size() + " durations");


                for (Duration duration : durations) {
                    Long durationId = duration.getDurationId();
                    System.out.println("Processing duration: " + durationId);


                    categoryPricingDAO.deleteByDurationId(durationId);
                    System.out.println("Deleted category pricing for duration: " + durationId);


                    uniformPricingDAO.deleteByDurationId(durationId);
                    System.out.println("Deleted uniform pricing for duration: " + durationId);
                }


                for (Duration duration : durations) {
                    durationDAO.delete(duration.getDurationId());
                    System.out.println("Deleted duration: " + duration.getDurationId());
                }


                membershipPlanDAO.delete(planId);
                System.out.println("Deleted plan: " + planId);

                connection.commit();

                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Plan deleted successfully");
                response.setStatus(HttpServletResponse.SC_OK);

            } catch (Exception e) {
                if (connection != null) {
                    connection.rollback();
                }
                throw e;
            }

        } catch (SQLException e) {
            System.err.println("Database error while deleting plan: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.put("status", "error");

            if ("23503".equals(e.getSQLState())) {
                jsonResponse.put("message", "Cannot delete plan: As there are members enrolled in it.");
            } else {
                jsonResponse.put("message", "Database operation failed");
            }

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            System.err.println("Unexpected error while deleting plan: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }

        objectMapper.writeValue(response.getOutputStream(), jsonResponse);
    }
}