package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.dao.LeaderboardDAO;
import org.example.demo2.model.LeaderBoard;
import org.example.demo2.model.LeaderBoardEntry;

import java.io.IOException;
import java.util.List;

@WebServlet("/leaderBoardExercise")
public class LeaderBoardExerciseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String userRole = (String) session.getAttribute("userRole");
        String exercise = req.getParameter("exercise");
        LeaderboardDAO dao = new LeaderboardDAO();
        try{
            List<LeaderBoardEntry> exerciseList =  dao.getEntriesByExercise(exercise);
            System.out.println("Exercise List size: " + exerciseList.size());
            for (LeaderBoardEntry entry : exerciseList) {
                System.out.println("Name: " + entry.getName() + ", Amount: " + entry.getAmount());
            }
            req.setAttribute("exerciseList", exerciseList);
            session.setAttribute("role", userRole);
            req.getRequestDispatcher("/WEB-INF/views/common/leaderBoardByExercise.jsp").forward(req, resp);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
