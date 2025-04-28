package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

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
import org.example.demo2.util.SessionUtils;

import java.io.PrintWriter;

@WebServlet("/membership/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
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
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/owner/addMembershipPlan.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            String planName = request.getParameter("planName");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String pricingType = request.getParameter("pricingType");
            String colour = request.getParameter("colour");

            if (planName == null || startTimeStr == null || endTimeStr == null || pricingType == null || colour == null) {
                throw new IllegalArgumentException("Missing required fields");
            }

            if (membershipPlanDAO.isPlanNameExists(planName)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "A plan with this name already exists. Please choose a different name.");
                out.write(gson.toJson(jsonResponse));
                return;
            }

            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);
            MembershipPlan membershipPlan = new MembershipPlan(planName, startTime, endTime, pricingType, colour);
            membershipPlan = membershipPlanDAO.create(membershipPlan);


            String[] durationValues = request.getParameterValues("durationValue");
            String[] durationTypes = request.getParameterValues("durationType");

            if (durationValues != null) {
                for (int i = 0; i < durationValues.length; i++) {

                    Duration duration = new Duration(
                            membershipPlan.getPlanId(),
                            Integer.parseInt(durationValues[i]),
                            durationTypes[i]
                    );
                    duration = durationDAO.create(duration);
                    System.out.println("Created duration with ID: " + duration.getDurationId());

                    if ("uniform".equals(pricingType)) {
                        String uniformPriceStr = request.getParameter("uniformPrice");
                        if (uniformPriceStr != null && !uniformPriceStr.trim().isEmpty()) {
                            UniformPricing uniformPricing = new UniformPricing(
                                    duration.getDurationId(),
                                    new BigDecimal(uniformPriceStr)
                            );
                            uniformPricing = uniformPricingDAO.create(uniformPricing);
                            System.out.println("Created uniform pricing with ID: " + uniformPricing.getPricingId());
                        }
                    } else if ("category".equals(pricingType)) {
                        String gentsPrice = request.getParameter("categoryPriceGents");
                        String ladiesPrice = request.getParameter("categoryPriceLadies");
                        String couplePrice = request.getParameter("categoryPriceCouple");

                        if (gentsPrice != null && !gentsPrice.trim().isEmpty()) {
                            CategoryPricing gents = new CategoryPricing(
                                    duration.getDurationId(),
                                    "Gents",
                                    new BigDecimal(gentsPrice)
                            );
                            categoryPricingDAO.create(gents);
                        }

                        if (ladiesPrice != null && !ladiesPrice.trim().isEmpty()) {
                            CategoryPricing ladies = new CategoryPricing(
                                    duration.getDurationId(),
                                    "Ladies",
                                    new BigDecimal(ladiesPrice)
                            );
                            categoryPricingDAO.create(ladies);
                        }

                        if (couplePrice != null && !couplePrice.trim().isEmpty()) {
                            CategoryPricing couple = new CategoryPricing(
                                    duration.getDurationId(),
                                    "Couple",
                                    new BigDecimal(couplePrice)
                            );
                            categoryPricingDAO.create(couple);
                        }
                    }
                }
            }

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Membership plan created successfully");
            jsonResponse.put("redirectUrl", request.getContextPath() + "/membership/view");
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        out.write(gson.toJson(jsonResponse));
        out.close();
    }
}