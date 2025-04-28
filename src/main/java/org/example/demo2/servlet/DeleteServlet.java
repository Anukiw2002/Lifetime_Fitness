package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.Event;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet{

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {

            resp.sendRedirect(req.getContextPath() + "/landingPage");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(req.getInputStream(), Event.class);

        boolean removed = EventServlet.getEvents().removeIf(e -> e.getId().equals(event.getId()));

        if (removed) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Event not found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.getWriter().write("GET method is not supported. Please use DELETE.");
    }

}