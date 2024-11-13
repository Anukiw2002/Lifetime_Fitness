package org.example.demo2.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/MembershipPlan")
public class MembershipPlan extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Handle different navigation actions based on the `action` parameter
        switch (action) {
            case "view":
                request.getRequestDispatcher("/WEB-INF/views/owner/viewMembershipPlans.jsp").forward(request, response);
                break;
            case "add":
                request.getRequestDispatcher("addPlan.jsp").forward(request, response);
                break;
            case "edit":
                request.getRequestDispatcher("editPlan.jsp").forward(request, response);
                break;
            case "delete":
                request.getRequestDispatcher("deleteConfirmation.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("errorPage.jsp"); // Redirect to an error page if action is invalid
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Handle form submissions based on the action
        switch (action) {
            case "add":
                response.sendRedirect("MembershipPlanServlet?action=view"); // Redirect to view page after adding
                break;
            case "edit":
                response.sendRedirect("MembershipPlanServlet?action=view"); // Redirect to view page after editing
                break;
            case "delete":
                response.sendRedirect("MembershipPlanServlet?action=view"); // Redirect to view page after deletion
                break;
            default:
                response.sendRedirect("errorPage.jsp"); // Redirect to an error page if action is invalid
        }
    }
}
