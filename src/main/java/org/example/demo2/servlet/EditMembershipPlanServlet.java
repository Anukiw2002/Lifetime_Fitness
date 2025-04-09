package org.example.demo2.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;
import org.example.demo2.model.*;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@WebServlet("/membership/update")
public class EditMembershipPlanServlet extends HttpServlet {
    private MembershipPlanDAO membershipPlanDAO;
    private DurationDAO durationDAO;
    private UniformPricingDAO uniformPricingDAO;
    private CategoryPricingDAO categoryPricingDAO;

    @Override
    public void init() {
        DBConnection dbConnection = new DBConnection();
        membershipPlanDAO = new MembershipPlanDAO(dbConnection);
        durationDAO = new DurationDAO(dbConnection);
        uniformPricingDAO = new UniformPricingDAO(dbConnection);
        categoryPricingDAO = new CategoryPricingDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        String planIdParam = request.getParameter("planId");
        if (planIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing planId");
            return;
        }

        try {
            Long planId = Long.parseLong(planIdParam);
            MembershipPlan plan = membershipPlanDAO.findById(planId);

            if (plan == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Membership plan not found");
                return;
            }

            // Load durations with their pricing
            List<Duration> durations = durationDAO.findByPlanId(planId);
            for (Duration duration : durations) {
                if ("uniform".equals(plan.getPricingType())) {
                    UniformPricing pricing = uniformPricingDAO.findByDurationId(duration.getDurationId());
                    if (pricing != null) {
                        List<UniformPricing> uniformPricings = new ArrayList<>();
                        uniformPricings.add(pricing);
                        duration.setUniformPricing(uniformPricings);
                    }
                } else if ("category".equals(plan.getPricingType())) {
                    List<CategoryPricing> categoryPricings = categoryPricingDAO.findByDurationId(duration.getDurationId());
                    duration.setCategoryPricing(categoryPricings);
                }
            }
            plan.setDurations(durations);

            request.setAttribute("membershipPlan", plan);
            request.getRequestDispatcher("/WEB-INF/views/owner/editMembershipPlan.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading membership plan", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        try {
            // Update basic plan details
            Long planId = Long.parseLong(request.getParameter("planId"));
            MembershipPlan plan = membershipPlanDAO.findById(planId);
            if (plan == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Membership plan not found");
                return;
            }

            plan.setPlanName(request.getParameter("planName"));
            plan.setStartTime(LocalTime.parse(request.getParameter("startTime")));
            plan.setEndTime(LocalTime.parse(request.getParameter("endTime")));
            plan.setPricingType(request.getParameter("pricingType"));
            plan.setColour(request.getParameter("colour"));

            membershipPlanDAO.update(plan);

            // Handle duration updates
            String[] durationValues = request.getParameterValues("durationValue[]");
            String[] durationTypes = request.getParameterValues("durationType[]");

            if (durationValues != null && durationTypes != null) {
                // First, get existing durations
                List<Duration> existingDurations = durationDAO.findByPlanId(planId);

                // Update or create durations
                for (int i = 0; i < durationValues.length; i++) {
                    Duration duration;
                    if (i < existingDurations.size()) {
                        duration = existingDurations.get(i);
                        duration.setDurationValue(Integer.parseInt(durationValues[i]));
                        duration.setDurationType(durationTypes[i]);
                        // Update duration (you'll need to add this method to DurationDAO)
                        // durationDAO.update(duration);
                    } else {
                        duration = new Duration(planId,
                                Integer.parseInt(durationValues[i]),
                                durationTypes[i]);
                        duration = durationDAO.create(duration);
                    }

                    // Handle pricing based on type
                    // Replace the pricing handling section in doPost with this:
                    if ("uniform".equals(plan.getPricingType())) {
                        String priceStr = request.getParameter("uniformPrice");
                        if (priceStr != null && !priceStr.isEmpty()) {
                            UniformPricing existingPricing = uniformPricingDAO.findByDurationId(duration.getDurationId());
                            if (existingPricing != null) {
                                existingPricing.setPrice(new BigDecimal(priceStr));
                                uniformPricingDAO.update(existingPricing);
                            } else {
                                UniformPricing pricing = new UniformPricing(
                                        duration.getDurationId(),
                                        new BigDecimal(priceStr)
                                );
                                uniformPricingDAO.create(pricing);
                            }
                        }
                    } else {
                        // Handle category pricing
                        String[] categories = request.getParameterValues("category");
                        String[] prices = request.getParameterValues("categoryPrice");
                        if (categories != null && prices != null) {
                            for (int j = 0; j < categories.length; j++) {
                                List<CategoryPricing> existingPricings =
                                        categoryPricingDAO.findByDurationId(duration.getDurationId());

                                boolean found = false;
                                for (CategoryPricing existing : existingPricings) {
                                    if (existing.getCategory().equals(categories[j])) {
                                        existing.setPrice(new BigDecimal(prices[j]));
                                        categoryPricingDAO.update(existing);
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    CategoryPricing pricing = new CategoryPricing(
                                            duration.getDurationId(),
                                            categories[j],
                                            new BigDecimal(prices[j])
                                    );
                                    categoryPricingDAO.create(pricing);
                                }
                            }
                        }
                    }
                }
            }

            response.sendRedirect(request.getContextPath() + "/membership/view");
        } catch (Exception e) {
            throw new ServletException("Error updating membership plan", e);
        }
    }
}