package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.InstructorDashboardDAO;
import org.example.demo2.dao.OwnerDashboardDAO;
import org.example.demo2.util.DBConnection;
import org.example.demo2.util.SessionUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        System.out.println("Entering doGet method");
        String requestedWith = request.getHeader("X-Requested-With");
        System.out.println("X-Requested-With: " + requestedWith);


        OwnerDashboardDAO dao1 = new OwnerDashboardDAO();
        int revenue = dao1.getAmount();
        Map<String, Integer> revenueForFourMonths = dao1.getRevenueForFourMonths();
        request.setAttribute("revenueForFourMonths", revenueForFourMonths);


        if ("XMLHttpRequest".equals(requestedWith)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            List<String> months = new ArrayList<>();
            List<Integer> userCounts = new ArrayList<>();

            try (Connection conn = DBConnection.getConnection()) {
                if (conn != null) {
                    System.out.println("Connected to the database");
                }

                String query = "SELECT TO_CHAR(DATE_TRUNC('month', created_at), 'YYYY-MM') AS registration_month, COUNT(*) AS user_count " +
                        "FROM users GROUP BY registration_month ORDER BY registration_month";

                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    months.add(rs.getString("registration_month"));
                    userCounts.add(rs.getInt("user_count"));
                }

                JSONObject json = new JSONObject();
                json.put("months", months);
                json.put("userCounts", userCounts);


                System.out.println("Sending JSON to JS: " + json.toString());


                PrintWriter out = response.getWriter();
                out.write(json.toString());
                out.flush();
                out.close();

                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InstructorDashboardDAO dao = new InstructorDashboardDAO();
        int count = 0;

        int membershipPlanCount = 0;
        int instructors = 0;
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database");

                String query1 = "SELECT COUNT(*) AS count FROM membership_plans";
                String query2 = "SELECT COUNT(*) AS count1 FROM users WHERE role = 'instructor' ";
                PreparedStatement stmt1 = conn.prepareStatement(query1);
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                ResultSet rs1 = stmt1.executeQuery();
                ResultSet rs2 = stmt2.executeQuery();
                count = dao.getActiveMembers();

                if (rs1.next()) {
                    membershipPlanCount = rs1.getInt("count");
                }

                if (rs2.next()){
                    instructors = rs2.getInt("count1");
                }

                System.out.println("instructors: " + instructors);
                System.out.println("membershipPlanCount: " + membershipPlanCount);
                request.setAttribute("membershipPlanCount", membershipPlanCount);
                request.setAttribute("instructors", instructors);
                request.setAttribute("count", count);
                request.setAttribute("revenue", revenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        request.getRequestDispatcher("/WEB-INF/views/owner/owner-dashboard.jsp").forward(request, response);
    }
}

