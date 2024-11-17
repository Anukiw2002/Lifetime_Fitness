package org.example.demo2.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.demo2.dao.MembershipPlanDAO;
import org.example.demo2.model.MembershipPlan;

import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/MembershipPlan")
public class MembershipPlan extends HttpServlet {
    private MembershipPlanDAO membershipPlanDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        membershipPlanDAO = new MembershipPlanDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            handleError(response, "Action parameter is required");
            return;
        }

        try {
            switch (action) {
                case "view":
                    List<MembershipPlan> plans = membershipPlanDAO.getAllPlans();
                    request.setAttribute("plans", plans);
                    request.getRequestDispatcher("/WEB-INF/views/owner/viewMembershipPlans.jsp")
                            .forward(request, response);
                    break;
                case "add":
                    request.getRequestDispatcher("/WEB-INF/views/owner/addMembershipPlan.jsp")
                            .forward(request, response);
                    break;
                case "edit":
                    String planId = request.getParameter("planId");
                    if (planId == null || planId.trim().isEmpty()) {
                        handleError(response, "Plan ID is required");
                        return;
                    }
                    MembershipPlan plan = membershipPlanDAO.getPlanById(Long.parseLong(planId));
                    if (plan == null) {
                        handleError(response, "Plan not found");
                        return;
                    }
                    request.setAttribute("plan", plan);
                    request.getRequestDispatcher("/WEB-INF/views/owner/editMembershipPlan.jsp")
                            .forward(request, response);
                    break;
                case "delete":
                    request.getRequestDispatcher("/WEB-INF/views/owner/deleteMembershipPlan.jsp")
                            .forward(request, response);
                    break;
                default:
                    handleError(response, "Invalid action parameter");
            }
        } catch (SQLException e) {
            handleError(response, "Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            handleError(response, "Invalid plan ID format");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            handleError(response, "Action parameter is required");
            return;
        }

        try {
            switch (action) {
                case "add":
                    handleAddPlan(request, response);
                    break;
                case "edit":
                    handleEditPlan(request, response);
                    break;
                case "delete":
                    handleDeletePlan(request, response);
                    break;
                default:
                    handleError(response, "Invalid action parameter");
            }
        } catch (Exception e) {
            handleError(response, "Error processing request: " + e.getMessage());
        }
    }

    private void handleAddPlan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MembershipPlan plan = new MembershipPlan();

            // Validate and set basic plan details
            String planName = request.getParameter("planName");
            if (planName == null || planName.trim().isEmpty()) {
                throw new IllegalArgumentException("Plan name is required");
            }
            plan.setPlanName(planName);

            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (startTime == null || endTime == null) {
                throw new IllegalArgumentException("Start time and end time are required");
            }
            plan.setStartTime(LocalTime.parse(startTime));
            plan.setEndTime(LocalTime.parse(endTime));

            plan.setActive(Boolean.parseBoolean(request.getParameter("isActive")));

            // Handle pricing type
            String pricingType = request.getParameter("pricingType");
            if (pricingType == null) {
                throw new IllegalArgumentException("Pricing type is required");
            }
            plan.setPricingType(MembershipPlan.PricingType.valueOf(pricingType.toUpperCase()));

            if ("UNIFORM".equalsIgnoreCase(pricingType)) {
                String uniformPrice = request.getParameter("uniformPrice");
                if (uniformPrice == null || uniformPrice.trim().isEmpty()) {
                    throw new IllegalArgumentException("Uniform price is required");
                }
                MembershipPlan.UniformPrice price = new MembershipPlan.UniformPrice();
                price.setPrice(new BigDecimal(uniformPrice));
                plan.setUniformPrice(price);
            } else if ("CATEGORY".equalsIgnoreCase(pricingType)) {
                List<MembershipPlan.CategoryPrice> categoryPrices = new ArrayList<>();
                String[] categories = {"gents", "ladies", "couple"};
                for (String category : categories) {
                    String price = request.getParameter(category + "Price");
                    if (price != null && !price.trim().isEmpty()) {
                        MembershipPlan.CategoryPrice categoryPrice = new MembershipPlan.CategoryPrice();
                        categoryPrice.setCategoryType(category);
                        categoryPrice.setPrice(new BigDecimal(price));
                        categoryPrices.add(categoryPrice);
                    }
                }
                if (categoryPrices.isEmpty()) {
                    throw new IllegalArgumentException("At least one category price is required");
                }
                plan.setCategoryPrices(categoryPrices);
            }

            // Handle duration options
            String[] durations = request.getParameterValues("duration");
            String[] durationUnits = request.getParameterValues("durationUnit");
            String[] durationPrices = request.getParameterValues("durationPrice");

            if (durations == null || durationUnits == null || durationPrices == null ||
                    durations.length == 0 || durations.length != durationUnits.length ||
                    durations.length != durationPrices.length) {
                throw new IllegalArgumentException("Duration options are required and must be complete");
            }

            List<MembershipPlan.DurationOption> durationOptions = new ArrayList<>();
            for (int i = 0; i < durations.length; i++) {
                MembershipPlan.DurationOption option = new MembershipPlan.DurationOption();
                option.setDurationValue(Integer.parseInt(durations[i]));
                option.setDurationUnit(durationUnits[i]);
                option.setPrice(new BigDecimal(durationPrices[i]));
                durationOptions.add(option);
            }
            plan.setDurationOptions(durationOptions);

            // Save to database
            membershipPlanDAO.savePlan(plan);
            sendJsonResponse(response, true, "Plan added successfully");

        } catch (IllegalArgumentException e) {
            sendJsonResponse(response, false, "Validation error: " + e.getMessage());
        } catch (SQLException e) {
            sendJsonResponse(response, false, "Database error: " + e.getMessage());
        } catch (Exception e) {
            sendJsonResponse(response, false, "Error adding plan: " + e.getMessage());
        }
    }

    private void handleEditPlan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String planId = request.getParameter("planId");
            if (planId == null || planId.trim().isEmpty()) {
                throw new IllegalArgumentException("Plan ID is required");
            }

            // First get the existing plan
            MembershipPlan existingPlan = membershipPlanDAO.getPlanById(Long.parseLong(planId));
            if (existingPlan == null) {
                throw new IllegalArgumentException("Plan not found");
            }

            // Update the plan with new values (reuse logic from handleAddPlan)
            MembershipPlan updatedPlan = new MembershipPlan();
            updatedPlan.setPlanId(Long.parseLong(planId));

            // Set all other fields using the same logic as in handleAddPlan
            // ... (copy the validation and setting logic from handleAddPlan)

            // Update in database (you'll need to add an update method to your DAO)
            // membershipPlanDAO.updatePlan(updatedPlan);
            sendJsonResponse(response, true, "Plan updated successfully");

        } catch (IllegalArgumentException e) {
            sendJsonResponse(response, false, "Validation error: " + e.getMessage());
        } catch (SQLException e) {
            sendJsonResponse(response, false, "Database error: " + e.getMessage());
        } catch (Exception e) {
            sendJsonResponse(response, false, "Error updating plan: " + e.getMessage());
        }
    }

    private void handleDeletePlan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String planId = request.getParameter("planId");
            if (planId == null || planId.trim().isEmpty()) {
                throw new IllegalArgumentException("Plan ID is required");
            }

            membershipPlanDAO.deletePlan(Long.parseLong(planId));
            sendJsonResponse(response, true, "Plan deleted successfully");
        } catch (IllegalArgumentException e) {
            sendJsonResponse(response, false, "Validation error: " + e.getMessage());
        } catch (SQLException e) {
            sendJsonResponse(response, false, "Database error: " + e.getMessage());
        } catch (Exception e) {
            sendJsonResponse(response, false, "Error deleting plan: " + e.getMessage());
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message)
            throws IOException {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        jsonResponse.addProperty("message", message);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse);
        }
    }

    private void handleError(HttpServletResponse response, String errorMessage)
            throws IOException {
        sendJsonResponse(response, false, errorMessage);
    }
}