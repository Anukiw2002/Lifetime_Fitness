package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.BookingConstraintsDAO;
import org.example.demo2.model.BlockedDates;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("/booking/viewBlockedDate")
public class ViewBlockedDates extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        BookingConstraintsDAO bookingConstraintsDAO = new BookingConstraintsDAO();
        List<BlockedDates> allBlockedDates = bookingConstraintsDAO.viewAllBlockedDates();
        request.setAttribute("allBlockedDates", allBlockedDates);

        request.getRequestDispatcher("/WEB-INF/views/owner/blockedDates.jsp").forward(request,response);
    }
}
