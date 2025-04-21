package org.example.demo2.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Import your model and DAO classes
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.MembershipPlan;
import org.example.demo2.model.Duration;
import org.example.demo2.model.UniformPricing;
import org.example.demo2.model.CategoryPricing;
import org.example.demo2.dao.MembershipPlanDAO;
import org.example.demo2.dao.DurationDAO;
import org.example.demo2.dao.UniformPricingDAO;
import org.example.demo2.dao.CategoryPricingDAO;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;

@WebServlet("//signup/step4")
public class ViewPricingServlet extends HttpServlet {
    private MembershipPlanDAO membershipPlanDAO;
    private DurationDAO durationDAO;
    private UniformPricingDAO uniformPricingDAO;
    private CategoryPricingDAO categoryPricingDAO;
    private DBConnection dbConnection;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize DBConnection and DAOs
        dbConnection = new DBConnection();
        membershipPlanDAO = new MembershipPlanDAO(dbConnection);
        durationDAO = new DurationDAO(dbConnection);
        uniformPricingDAO = new UniformPricingDAO(dbConnection);
        categoryPricingDAO = new CategoryPricingDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Fetch all membership plans
            List<MembershipPlan> membershipPlans = membershipPlanDAO.findAll();
            System.out.println("Number of plans fetched: " + membershipPlans.size());

            // For each plan, fetch its durations and pricing
            for (MembershipPlan plan : membershipPlans) {
                System.out.println("Processing plan: " + plan.getPlanName());
                List<Duration> durations = durationDAO.findByPlanId(plan.getPlanId());
                System.out.println("Number of durations for plan: " + durations.size());

                // For each duration, fetch its pricing based on pricing type
                for (Duration duration : durations) {
                    if ("uniform".equals(plan.getPricingType())) {
                        UniformPricing pricing = uniformPricingDAO.findByDurationId(duration.getDurationId());
                        List<UniformPricing> pricingList = new ArrayList<>();
                        pricingList.add(pricing);
                        duration.setUniformPricing(pricingList);
                    } else if ("category".equals(plan.getPricingType())) {
                        List<CategoryPricing> pricings = categoryPricingDAO.findByDurationId(duration.getDurationId());
                        duration.setCategoryPricing(pricings);
                        System.out.println("Found category pricing: " + pricings.size());
                    }
                }

                plan.setDurations(durations);
            }

            // Store the data in request attribute
            request.setAttribute("membershipPlans", membershipPlans);

        } catch (SQLException e) {
            // Log the error
            e.printStackTrace();
            // Set error message in request
            request.setAttribute("errorMessage", "An error occurred while fetching membership plans.");
        }

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/client/signUp4.jsp").forward(request, response);
    }
}
