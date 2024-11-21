package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;

@WebServlet("/membership/add")
public class AddMembershipPlanServlet extends HttpServlet {
    private MembershipPlanDAO membershipPlanDAO;
    private DurationDAO durationDAO;
    private UniformPricingDAO uniformPricingDAO;
    private CategoryPricingDAO categoryPricingDAO;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        membershipPlanDAO = new MembershipPlanDAO(dbConnection);
        durationDAO = new DurationDAO(dbConnection);
        uniformPricingDAO = new UniformPricingDAO(dbConnection);
        categoryPricingDAO = new CategoryPricingDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/owner/addMembershipPlan.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            System.out.println("Received POST request to add membership plan");
            System.out.println("Content Type: " + request.getContentType());
            System.out.println("Character Encoding: " + request.getCharacterEncoding());
            // Log all request parameters
            request.getParameterMap().forEach((key, values) -> {
                System.out.println("Param: " + key + " | Value: " + String.join(", ", values));
            });

            // Get membership plan details
            String planName = request.getParameter("planName");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String pricingType = request.getParameter("pricingType");

            System.out.println("Plan Name: " + planName);
            System.out.println("Start Time: " + startTimeStr);
            System.out.println("End Time: " + endTimeStr);
            System.out.println("Pricing Type: " + pricingType);

            // Validate required fields
            if (planName == null || startTimeStr == null || endTimeStr == null || pricingType == null) {
                throw new IllegalArgumentException("Missing required fields");
            }

            // Parse time
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            // Create membership plan
            MembershipPlan membershipPlan = new MembershipPlan(planName, startTime, endTime, pricingType);
            membershipPlan = membershipPlanDAO.create(membershipPlan);

            System.out.println("Membership Plan created with ID: " + membershipPlan.getPlanId());

            // Process durations
            String[] durationValues = request.getParameterValues("durationValue");
            String[] durationTypes = request.getParameterValues("durationType");

            if (durationValues == null || durationTypes == null || durationValues.length != durationTypes.length) {
                throw new IllegalArgumentException("Invalid duration data");
            }

            for (int i = 0; i < durationValues.length; i++) {
                System.out.println("Processing duration " + i + ": " + durationValues[i] + " " + durationTypes[i]);
                Duration duration = new Duration(
                        membershipPlan.getPlanId(),
                        Integer.parseInt(durationValues[i]),
                        durationTypes[i]
                );
                duration = durationDAO.create(duration);

                // Process pricing based on pricing type
                if ("uniform".equals(pricingType)) {
                    String priceStr = request.getParameter("uniformPrice_" + i);
                    System.out.println("Uniform Price for duration " + i + ": " + priceStr);
                    if (priceStr == null || priceStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("Missing uniform price for duration " + i);
                    }
                    UniformPricing uniformPricing = new UniformPricing(
                            duration.getDurationId(),
                            new BigDecimal(priceStr)
                    );
                    uniformPricingDAO.create(uniformPricing);
                } else if ("category".equals(pricingType)) {
                    String[] categories = {"Gents", "Ladies", "Couple"};
                    for (String category : categories) {
                        String priceStr = request.getParameter("categoryPrice_" + i + "_" + category);
                        System.out.println("Category Price for " + category + " in duration " + i + ": " + priceStr);
                        if (priceStr == null || priceStr.trim().isEmpty()) {
                            throw new IllegalArgumentException("Missing price for category " + category + " in duration " + i);
                        }
                        CategoryPricing categoryPricing = new CategoryPricing(
                                duration.getDurationId(),
                                category,
                                new BigDecimal(priceStr)
                        );
                        categoryPricingDAO.create(categoryPricing);
                    }
                }
            }

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Membership plan created successfully");
            jsonResponse.put("redirectUrl", request.getContextPath() + "/membership/list");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        // Send JSON response
        response.getWriter().write(gson.toJson(jsonResponse));
    }
}