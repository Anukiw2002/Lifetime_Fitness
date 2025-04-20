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

@WebServlet("/ExecutePayment")
public class ExecutePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ExecutePaymentServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        try {
            PaymentServicesDAO paymentServices = new PaymentServicesDAO();
            Payment payment = paymentServices.executePayment(paymentId, payerId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            // Extract required details
            String buyerName = payerInfo.getFirstName() + " " + payerInfo.getLastName();
            String email = payerInfo.getEmail();
            String planName = transaction.getDescription(); // Plan name (from PayPal payment description)
            String orderId = payment.getId(); // PayPal Payment ID
            BigDecimal amount = new BigDecimal(transaction.getAmount().getTotal());

            // Save to database
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.saveOrder(orderId, buyerName, email, planName, amount);

            // Forward to receipt page
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.getRequestDispatcher("/WEB-INF/views/client/receipt.jsp").forward(request, response);

        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error during payment execution: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/client/error.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
