package org.example.demo2.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.http.HttpSession;
import org.example.demo2.model.Event;

@WebServlet("/booking")
public class BookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            // If the session is invalid or the user is not logged in, redirect to the login page
            resp.sendRedirect(req.getContextPath() + "/landingPage");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(req.getInputStream(), Event.class);

        // Add a unique ID to each event
        event.setId(String.valueOf(EventServlet.getEvents().size() + 1));

        EventServlet.getEvents().add(event); // Add the event to the list
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}

