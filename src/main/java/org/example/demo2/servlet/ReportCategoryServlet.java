package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.OwnerDashboardDAO;
import org.example.demo2.dao.ReportCategoryDAO;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.Map;

@WebServlet("/reportCategories")
public class ReportCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(req, resp, "owner")) {
            return; // If not authorized, the redirection will be handled by the utility method
        }
        ReportCategoryDAO dao = new ReportCategoryDAO();
        Map<String, Integer> revenueByType = dao.getPlanType();
        for (Map.Entry<String, Integer> entry : revenueByType.entrySet()) {
            System.out.println("Plan Type: " + entry.getKey() + " | Count: " + entry.getValue());
        }
        Map<String, Integer> userCount = dao.getMembersCount();
        for (Map.Entry<String, Integer> entry : userCount.entrySet()) {
            System.out.println("Month: " + entry.getKey() + " | Count: " + entry.getValue());
        }
        Map<String, Integer> planCount = dao.getPlanCount();
        for (Map.Entry<String, Integer> entry : planCount.entrySet()){
            System.out.println("Plan type : " + entry.getKey() + "| Count: " + entry.getValue());
        }

        OwnerDashboardDAO dao1 = new OwnerDashboardDAO();
        Map<String, Integer> revenueForFourMonths = dao1.getRevenueForFourMonths();
        for (Map.Entry<String, Integer> entry : revenueForFourMonths.entrySet()) {
            System.out.println("Month: " + entry.getKey() + " | Count: " + entry.getValue());
        }
        req.setAttribute("planCount", planCount);
        req.setAttribute("revenueForFourMonths", revenueForFourMonths);
        req.setAttribute("userCount", userCount);
        req.setAttribute("revenueByType", revenueByType);

        req.getRequestDispatcher("/WEB-INF/views/owner/reportCategories.jsp").forward(req,resp);
    }

}
