package org.example.demo2.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo2.model.BlogModel;
import org.example.demo2.model.VideoModel;

import java.io.IOException;
import java.util.List;

@WebServlet("/testView")
public class TemporaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = request.getParameter("page");


        if ("login".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/logIn.jsp").forward(request, response);
        } else if ("page1".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/signUp1.jsp").forward(request, response);
        }else if ("page2".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/signUp2.jsp").forward(request, response);
        } else if ("try".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client").forward(request, response);
        } else if ("ownerDashboard".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner - dashboard.jsp").forward(request, response);
        }else if ("page29".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/payment.jsp").forward(request, response);
        }else if ("page31".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/contentManagement.jsp").forward(request, response);



        } else if ("packages".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/packages.jsp").forward(request, response);
        } else if ("page3".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/signUp3.jsp").forward(request, response);
        } else if ("page4".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/resetPassword.jsp").forward(request, response);
        } else if ("page5".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/resetPasswordForm.jsp").forward(request, response);
        }    else if ("page6".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/signUp4.jsp").forward(request, response);
        }  else if ("page8".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/client/editProfile.jsp").forward(request, response);
        } else if ("page9".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/common/navigationTestPage.jsp").forward(request, response);
        }     else if ("page10".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/common/verticalNavBar.jsp").forward(request, response);
        } else if ("page11".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/memberManagement.jsp").forward(request, response);
        } else if ("page12".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/editMember.jsp").forward(request, response);
        } else if ("page13".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/instructorManagement.jsp").forward(request, response);
        } else if ("page14".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/addInstructor.jsp").forward(request, response);
        } else if ("page15".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/editInstructor.jsp").forward(request, response);
        } else if ("page16".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/addClient.jsp").forward(request, response);
        }
        else if ("page40".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/viewBookings.jsp").forward(request, response);
        }
        else if ("page41".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/owner/bookingConstraints.jsp").forward(request, response);
        }
        else if ("page43".equals(page)) {
            request.getRequestDispatcher("/WEB-INF/views/instructor/instructor-dashboard.jsp").forward(request, response);
        }
        else {
            // Default or error page
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }
}