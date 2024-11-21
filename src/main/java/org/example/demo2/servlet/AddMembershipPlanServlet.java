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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.PrintWriter;

@WebServlet("/membership/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,  // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class AddMembershipPlanServlet extends HttpServlet {
    private MembershipPlanDAO membershipPlanDAO;
    private DurationDAO durationDAO;
    private UniformPricingDAO uniformPricingDAO;
    private CategoryPricingDAO categoryPricingDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            DBConnection dbConnection = new DBConnection();
            membershipPlanDAO = new MembershipPlanDAO(dbConnection);
            durationDAO = new DurationDAO(dbConnection);
            uniformPricingDAO = new UniformPricingDAO(dbConnection);
            categoryPricingDAO = new CategoryPricingDAO(dbConnection);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAOs: " + e.getMessage(), e);
        }
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
        PrintWriter out = response.getWriter();
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            // Validate required fields
            String planName = request.getParameter("planName");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String pricingType = request.getParameter("pricingType");

            if (planName == null || planName.trim().isEmpty() ||
                    startTimeStr == null || startTimeStr.trim().isEmpty() ||
                    endTimeStr == null || endTimeStr.trim().isEmpty() ||
                    pricingType == null || pricingType.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing required fields");
            }

            // Parse and validate time
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            // Create membership plan
            MembershipPlan membershipPlan = new MembershipPlan(planName, startTime, endTime, pricingType);
            membershipPlan = membershipPlanDAO.create(membershipPlan);

            // Process durations
            String[] durationValues = request.getParameterValues("durationValue");
            String[] durationTypes = request.getParameterValues("durationType");

            if (durationValues == null || durationTypes == null || durationValues.length != durationTypes.length) {
                throw new IllegalArgumentException("Invalid duration data");
            }

            for (int i = 0; i < durationValues.length; i++) {
                int durationValue = Integer.parseInt(durationValues[i]);
                String durationType = durationTypes[i];

                Duration duration = new Duration(
                        membershipPlan.getPlanId(),
                        durationValue,
                        durationType
                );
                duration = durationDAO.create(duration);

                // Handle pricing based on type
                if ("uniform".equals(pricingType)) {
                    String priceStr = request.getParameter("uniformPrice_" + i);
                    if (priceStr == null || priceStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("Missing uniform price for duration " + i);
                    }
                    BigDecimal uniformPrice = new BigDecimal(priceStr);
                    UniformPricing uniformPricing = new UniformPricing(duration.getDurationId(), uniformPrice);
                    uniformPricingDAO.create(uniformPricing);
                } else if ("category".equals(pricingType)) {
                    String[] categories = {"Gents", "Ladies", "Couple"};
                    for (String category : categories) {
                        String priceStr = request.getParameter("categoryPrice_" + i + "_" + category);
                        if (priceStr == null || priceStr.trim().isEmpty()) {
                            throw new IllegalArgumentException(
                                    "Missing price for category " + category + " in duration " + i
                            );
                        }
                        BigDecimal categoryPrice = new BigDecimal(priceStr);
                        CategoryPricing categoryPricing = new CategoryPricing(
                                duration.getDurationId(),
                                category,
                                categoryPrice
                        );
                        categoryPricingDAO.create(categoryPricing);
                    }
                }
            }

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Membership plan created successfully");
            jsonResponse.put("redirectUrl", request.getContextPath() + "/membership/list");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            out.write(gson.toJson(jsonResponse));
            out.close();
        }
    }
}
