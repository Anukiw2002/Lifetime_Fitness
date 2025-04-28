package org.example.demo2.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.Event;

@WebServlet("/events")
public class EventServlet extends HttpServlet {

    private static final List<Event> events = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            resp.sendRedirect(req.getContextPath() + "/landingPage");
            return;
        }
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), events);
    }

    public static List<Event> getEvents() {
        return events;
    }
}
