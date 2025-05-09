package org.example.demo2.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.*;
import org.example.demo2.model.BookSession;
import org.example.demo2.model.ClientMembership;
import org.example.demo2.model.UserWeightData;
import org.example.demo2.util.DBConnection;
import java.time.LocalDate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/clientDashboard")
public class ClientDashboardServlet extends HttpServlet {
    private ClientMembershipDAO membershipDAO;
    private WorkoutExerciseDAO  workoutExerciseDAO;

    @Override
    public void init() throws ServletException {
        DBConnection dbConnection = new DBConnection();
        membershipDAO = new ClientMembershipDAO(dbConnection);
        workoutExerciseDAO = new WorkoutExerciseDAO(dbConnection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String email = (String) session.getAttribute("email");
        System.out.println("email" +email);
        int user_id = (int)session.getAttribute("userId");

        boolean hasUnread = NotificationsDAO.hasUnreadNotifications(user_id);

        session.setAttribute("hasUnread", hasUnread);


        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT full_name || ' ' || username AS name FROM users WHERE id = ?"
            );
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ClientMembership> membership = membershipDAO.getClientMembership(user_id);
            req.setAttribute("membership", membership);

            BookSessionDAO bookSessionDAO = new BookSessionDAO();
            BookSession booking = bookSessionDAO.getTodayBookingForClient(user_id);
            req.setAttribute("booking", booking);

            if (resultSet.next()) {
                req.setAttribute("userName", resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalDate today = LocalDate.now();

        int currentDay = today.getDayOfMonth();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();


        ReportDAO reportDAO = new ReportDAO();
        UserWeightData weightData = reportDAO.getWeightByEmail(email);

        DashboardDAO dashboardDAO = new DashboardDAO();
        int workoutCount = dashboardDAO.getWorkoutCountById(user_id);
        int streak = dashboardDAO.getWorkoutSteakById(user_id);
        UserWeightData data = reportDAO.getWeightByEmail(email);


        List<Map<String, Object>> weightEntries = new ArrayList<>();
        for (int i = 0; i < data.getAllWeights().size(); i++) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", data.getWeightDates().get(i).toString());
            entry.put("weight", data.getAllWeights().get(i));
            weightEntries.add(entry);
        }

        String weightEntriesJson = new Gson().toJson(weightEntries);
        req.setAttribute("weightEntriesJson", weightEntriesJson);

        req.setAttribute("beginningWeight", weightData.getBeginningWeight());
        req.setAttribute("currentWeight", weightData.getCurrentWeight());
        req.setAttribute("targetWeight", weightData.getTargetWeight());
        req.setAttribute("workoutCount", workoutCount);
        req.setAttribute("streak", streak);
        req.setAttribute("currentDay", currentDay);
        req.setAttribute("currentMonth", currentMonth);
        req.setAttribute("currentYear", currentYear);

        Map<String, Integer> weeklyWorkouts = workoutExerciseDAO.getNumberOfSessionsPerWeek(user_id);
        req.setAttribute("weeklyWorkouts", weeklyWorkouts);

        // Forward the request to the JSP
        req.getRequestDispatcher("/WEB-INF/views/client/client-dashboard.jsp").forward(req, resp);
    }
}
