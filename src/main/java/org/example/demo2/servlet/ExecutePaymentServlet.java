/**
 * ExecutePaymentServlet class - executes payment via PayPal.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package org.example.demo2.servlet;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import com.paypal.base.rest.PayPalRESTException;
import org.example.demo2.dao.PaymentServicesDAO;

@WebServlet("/execute_payment")
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

            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);

            request.getRequestDispatcher("receipt.jsp").forward(request, response);

        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error during payment execution: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
