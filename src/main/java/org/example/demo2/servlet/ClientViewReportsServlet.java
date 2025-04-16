package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.UserReportDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/clientViewReport")
public class ClientViewReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserReportDAO reportDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        reportDAO = new UserReportDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/landingPage");
            return;
        }

        String email = (String) session.getAttribute("email");
        if (email == null || email.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User email not found in session.");
            return;
        }

        try {
            Map<String, Object> reportDetails = reportDAO.getLatestReportForUser(email);

            if (reportDetails != null) {
                String programNo = (String) reportDetails.get("program_no");

                List<Map<String, Object>> exercises = reportDAO.getExercisesForReport(email, programNo);

                request.setAttribute("reportDetails", reportDetails);
                request.setAttribute("exercises", exercises);

                request.getRequestDispatcher("/WEB-INF/views/client/clientFilledReport.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "No reports found for your profile.");
                request.getRequestDispatcher("/WEB-INF/views/client/clientFilledReport.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching report: " + e.getMessage());
        }
    }


}
