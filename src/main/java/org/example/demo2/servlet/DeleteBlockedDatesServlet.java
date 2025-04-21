package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.dao.BookingConstraintsDAO;
import org.example.demo2.util.SessionUtils;

import java.io.IOException;

@WebServlet("/booking/deleteBlockedDate")
public class DeleteBlockedDatesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.isUserAuthorized(request, response, "owner")) {
            return;
        }

        int idParam = Integer.parseInt(request.getParameter("blockId"));

        BookingConstraintsDAO bookingConstraintsDAO = new BookingConstraintsDAO();
        boolean deleted = bookingConstraintsDAO.deleteBlockedDate(idParam);

        if(deleted) {
            sendAlert(response, "Blocked dates deleted successfully!", "viewBlockedDate");
        }
        else {
            sendAlert(response, "Blocked Dates couldn't be deleted!", "viewBlockedDate");
        }
    }

    private void sendAlert(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type='text/javascript'>");
        response.getWriter().println("alert('" + message.replace("'", "\\'") + "');");
        response.getWriter().println("window.location.href = '" + redirectUrl + "';");
        response.getWriter().println("</script>");
    }
}
