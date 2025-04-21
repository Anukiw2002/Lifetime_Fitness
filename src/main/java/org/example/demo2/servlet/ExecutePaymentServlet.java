package org.example.demo2.servlet;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.paypal.base.rest.PayPalRESTException;
import org.example.demo2.dao.PaymentServicesDAO;
import org.example.demo2.dao.OrderDAO;
import org.example.demo2.dao.UserDAO;

@WebServlet("/ExecutePayment")
public class ExecutePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExecutePaymentServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the user ID from the session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // If user ID is not available, redirect to the login page
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve paymentId and payerId from the request parameters
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        // Check if paymentId and payerId are present
        if (paymentId == null || payerId == null) {
            request.setAttribute("errorMessage", "Payment information is missing.");
            request.getRequestDispatcher("/WEB-INF/views/client/error.jsp").forward(request, response);
            return;
        }

        try {
            // Execute the payment using the service DAO
            PaymentServicesDAO paymentServices = new PaymentServicesDAO();
            Payment payment = paymentServices.executePayment(paymentId, payerId);

            // Extract payment details
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            // Extract details for saving to the orders table
            String buyerName = payerInfo.getFirstName() + " " + payerInfo.getLastName();
            String planName = transaction.getDescription();
            String orderId = payment.getId();
            BigDecimal amount = new BigDecimal(transaction.getAmount().getTotal());

            // Retrieve additional user details from the database if necessary
            UserDAO userDAO = new UserDAO();

            // Save order details into the database, linking it to the userId
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.saveOrder(orderId, userId, buyerName, planName, amount);

            // Set attributes for displaying in the receipt
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);

            // Forward to the receipt page
            request.getRequestDispatcher("/WEB-INF/views/client/receipt.jsp").forward(request, response);

        } catch (PayPalRESTException ex) {
            // Log and show the error if there is a PayPal issue
            log("PayPal Payment Error", ex);
            request.setAttribute("errorMessage", "Error during payment execution: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/error.jsp").forward(request, response);
        } catch (SQLException e) {
            // Log and handle SQL errors
            log("Database Error", e);
            request.setAttribute("errorMessage", "Database error occurred: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward POST request to GET method, since both are handling similar logic
        doGet(request, response);
    }
}
